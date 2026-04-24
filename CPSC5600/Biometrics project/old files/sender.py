"""
sender.py — Biometric Secure File Transfer (Sender)

Tkinter GUI flow:
    1. Enter username
    2. Select file to send via file browser
    3. Select recipient from dropdown
    4. Click "Authenticate & Send"
       a. Popup dialog prompts for hand and finger
       b. Biometric auth runs against fingerprint_db/
       c. On success: AES key generated, file encrypted,
          AES key wrapped with recipient's RSA public key,
          bundle sent over TCP socket to localhost:9999
    5. Status bar updates throughout

Wire format sent over socket:
    [4 bytes big-endian: length of wrapped AES key]
    [N bytes: RSA-wrapped AES key]
    [remaining bytes: encrypted file (nonce + ciphertext)]
"""

import os
import socket
import struct
import threading
import tkinter as tk
from tkinter import filedialog, messagebox, ttk

import authentication
import encryption
import rsa_utils

# ---------------------------------------------------------------------------
# Configuration
# ---------------------------------------------------------------------------

HOST       = "localhost"
PORT       = 9999
USERS_DIR  = "users"
USERS      = ["alice", "bob"]   # known users — extend as needed


# ---------------------------------------------------------------------------
# Networking
# ---------------------------------------------------------------------------

def send_bundle(wrapped_key: bytes, encrypted_file: bytes) -> None:
    """
    Connect to the receiver at HOST:PORT and transmit:
        [4-byte length of wrapped key][wrapped key][encrypted file bytes]

    Raises
    ------
    ConnectionRefusedError  if the receiver is not running
    OSError                 on other socket errors
    """
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        sock.connect((HOST, PORT))

        # Prefix the wrapped key with its length so the receiver knows where it ends
        key_len = struct.pack(">I", len(wrapped_key))   # 4 bytes, big-endian unsigned int 
        sock.sendall(key_len + wrapped_key + encrypted_file)


# ---------------------------------------------------------------------------
# Biometric dialog
# ---------------------------------------------------------------------------

class FingerprintDialog(tk.Toplevel):
    """
    Modal dialog that asks the user for their hand and finger,
    then runs authentication.authenticate() in a background thread
    so the GUI stays responsive during SIFT matching.
    """

    def __init__(self, parent, username: str):
        super().__init__(parent)
        self.username  = username
        self.result    = None   # True / False set after auth completes
        self.title("Biometric Verification")
        self.resizable(False, False)
        self.grab_set()         # make modal
        self._build_ui()
        self.protocol("WM_DELETE_WINDOW", self._on_cancel)

    def _build_ui(self) -> None:
        PAD = {"padx": 18, "pady": 8}

        header = tk.Frame(self, bg="#1a1a2e")
        header.pack(fill="x")
        tk.Label(
            header,
            text="🔐  Fingerprint Verification",
            bg="#1a1a2e", fg="#e0e0e0",
            font=("Courier New", 13, "bold"),
            pady=14,
        ).pack()

        body = tk.Frame(self, bg="#16213e", pady=10)
        body.pack(fill="both", expand=True)

        tk.Label(
            body,
            text=f"Authenticating as:  {self.username}",
            bg="#16213e", fg="#a0a0c0",
            font=("Courier New", 10),
        ).pack(**PAD)

        tk.Label(
            body,
            text="Hand and finger  (e.g.  Left index,  Right thumb)",
            bg="#16213e", fg="#c0c0d8",
            font=("Courier New", 10),
        ).pack(padx=18, pady=(12, 2))

        self._entry = tk.Entry(
            body,
            font=("Courier New", 11),
            bg="#0f3460", fg="#e0e0e0",
            insertbackground="#e0e0e0",
            relief="flat", bd=6, width=28,
        )
        self._entry.pack(padx=18, pady=(0, 10))
        self._entry.focus_set()
        self._entry.bind("<Return>", lambda _: self._on_submit())

        self._status = tk.Label(
            body,
            text="",
            bg="#16213e", fg="#f0c040",
            font=("Courier New", 9),
            wraplength=280,
        )
        self._status.pack(**PAD)

        btn_frame = tk.Frame(body, bg="#16213e")
        btn_frame.pack(pady=(4, 14))

        tk.Button(
            btn_frame, text="Verify",
            font=("Courier New", 10, "bold"),
            bg="#0f3460", fg="#e0e0e0",
            activebackground="#1a5276", activeforeground="white",
            relief="flat", padx=18, pady=6,
            command=self._on_submit,
        ).pack(side="left", padx=6)

        tk.Button(
            btn_frame, text="Cancel",
            font=("Courier New", 10),
            bg="#2c2c3e", fg="#a0a0c0",
            activebackground="#3c3c4e", activeforeground="white",
            relief="flat", padx=18, pady=6,
            command=self._on_cancel,
        ).pack(side="left", padx=6)

    def _on_submit(self) -> None:
        hand_finger = self._entry.get().strip()
        if not hand_finger:
            self._status.config(text="Please enter your hand and finger.")
            return

        self._status.config(text="⏳  Scanning… please wait")
        self.update()

        # Run SIFT matching in a thread — keeps the dialog window alive
        def run_auth():
            # Temporarily patch authentication.select_fp to use our hand_finger
            # without requiring another stdin prompt
            try:
                selected_path = authentication.select_fp(self.username, hand_finger)
            except (FileNotFoundError, ValueError) as e:
                self.after(0, lambda: self._finish(False, str(e)))
                return

            try:
                import biometric
                score = biometric.compare_fingerprints(
                    input_fingerprint_path=selected_path,
                    username=self.username,
                    show_visual=False,
                )
            except (ValueError, FileNotFoundError) as e:
                self.after(0, lambda: self._finish(False, str(e)))
                return

            threshold = authentication.MATCH_THRESHOLD
            success   = score >= threshold
            msg       = f"Score: {score:.1f}%  (threshold: {threshold}%)"
            self.after(0, lambda: self._finish(success, msg))

        threading.Thread(target=run_auth, daemon=True).start()

    def _finish(self, success: bool, message: str) -> None:
        self.result = success
        if success:
            self._status.config(text=f"✅  {message}", fg="#50fa7b")
            self.after(800, self.destroy)
        else:
            self._status.config(text=f"❌  {message}", fg="#ff5555")

    def _on_cancel(self) -> None:
        self.result = None
        self.destroy()


# ---------------------------------------------------------------------------
# Main sender window
# ---------------------------------------------------------------------------

class SenderApp(tk.Tk):

    def __init__(self):
        super().__init__()
        self.title("Secure File Transfer — Sender")
        self.resizable(False, False)
        self.configure(bg="#1a1a2e")
        self._selected_file = tk.StringVar(value="No file selected")
        self._build_ui()

    def _build_ui(self) -> None:
        # ── Header ──────────────────────────────────────────────────────────
        header = tk.Frame(self, bg="#0f3460")
        header.pack(fill="x")
        tk.Label(
            header,
            text="🔒  BIOMETRIC SECURE FILE TRANSFER",
            bg="#0f3460", fg="#e0e0e0",
            font=("Courier New", 14, "bold"),
            pady=16, padx=20,
        ).pack(side="left")
        tk.Label(
            header,
            text="SENDER",
            bg="#0f3460", fg="#f0c040",
            font=("Courier New", 10, "bold"),
            pady=16, padx=20,
        ).pack(side="right")

        # ── Body ─────────────────────────────────────────────────────────────
        body = tk.Frame(self, bg="#16213e", padx=28, pady=20)
        body.pack(fill="both", expand=True)

        def label(parent, text):
            return tk.Label(
                parent, text=text,
                bg="#16213e", fg="#a0a0c0",
                font=("Courier New", 9, "bold"),
                anchor="w",
            )

        def entry(parent, textvariable=None, **kw):
            return tk.Entry(
                parent,
                font=("Courier New", 11),
                bg="#0f3460", fg="#e0e0e0",
                insertbackground="#e0e0e0",
                relief="flat", bd=6,
                textvariable=textvariable,
                **kw,
            )

        # Username
        label(body, "USERNAME").pack(fill="x", pady=(0, 2))
        self._username_entry = entry(body, width=32)
        self._username_entry.pack(fill="x", pady=(0, 14))

        # File selection
        label(body, "FILE TO SEND").pack(fill="x", pady=(0, 2))
        file_row = tk.Frame(body, bg="#16213e")
        file_row.pack(fill="x", pady=(0, 14))
        tk.Label(
            file_row,
            textvariable=self._selected_file,
            bg="#0d2137", fg="#c0d8f0",
            font=("Courier New", 9),
            anchor="w", relief="flat", padx=8, pady=6,
            width=30,
        ).pack(side="left", fill="x", expand=True)
        tk.Button(
            file_row, text="Browse",
            font=("Courier New", 9),
            bg="#0f3460", fg="#e0e0e0",
            activebackground="#1a5276", activeforeground="white",
            relief="flat", padx=10, pady=6,
            command=self._browse_file,
        ).pack(side="right", padx=(8, 0))

        # Recipient
        label(body, "RECIPIENT").pack(fill="x", pady=(0, 2))
        self._recipient_var = tk.StringVar(value=USERS[0])
        recipient_menu = ttk.Combobox(
            body,
            textvariable=self._recipient_var,
            values=USERS,
            state="readonly",
            font=("Courier New", 11),
            width=29,
        )
        recipient_menu.pack(fill="x", pady=(0, 20))

        # Send button
        self._send_btn = tk.Button(
            body,
            text="Authenticate & Send  →",
            font=("Courier New", 11, "bold"),
            bg="#e94560", fg="white",
            activebackground="#c0392b", activeforeground="white",
            relief="flat", pady=10,
            command=self._on_send,
        )
        self._send_btn.pack(fill="x")

        # ── Status bar ───────────────────────────────────────────────────────
        self._status_var = tk.StringVar(value="Ready.")
        status_bar = tk.Label(
            self,
            textvariable=self._status_var,
            bg="#0d0d1a", fg="#50fa7b",
            font=("Courier New", 9),
            anchor="w", padx=12, pady=6,
        )
        status_bar.pack(fill="x", side="bottom")

    # ── Callbacks ────────────────────────────────────────────────────────────

    def _browse_file(self) -> None:
        path = filedialog.askopenfilename(title="Select file to send")
        if path:
            self._selected_file.set(path)

    def _set_status(self, msg: str, color: str = "#50fa7b") -> None:
        self._status_var.set(msg)
        # find the status bar label and update its fg
        for widget in self.winfo_children():
            if isinstance(widget, tk.Label) and widget.cget("textvariable") == str(self._status_var):
                widget.config(fg=color)
                break

    def _on_send(self) -> None:
        username  = self._username_entry.get().strip()
        file_path = self._selected_file.get()
        recipient = self._recipient_var.get()

        # Basic validation
        if not username:
            messagebox.showwarning("Missing input", "Please enter your username.")
            return
        if file_path == "No file selected" or not os.path.exists(file_path):
            messagebox.showwarning("Missing input", "Please select a valid file to send.")
            return
        if username == recipient:
            messagebox.showwarning("Invalid", "Sender and recipient cannot be the same user.")
            return

        # Biometric auth via dialog
        self._set_status("⏳  Waiting for biometric verification…", "#f0c040")
        dialog = FingerprintDialog(self, username)
        self.wait_window(dialog)

        if dialog.result is None:
            self._set_status("Cancelled.", "#a0a0c0")
            return
        if not dialog.result:
            self._set_status("❌  Authentication failed. Transfer aborted.", "#ff5555")
            return

        # Auth passed — encrypt and send in background thread
        self._set_status("✅  Authenticated. Encrypting…", "#50fa7b")
        self._send_btn.config(state="disabled")
        threading.Thread(
            target=self._encrypt_and_send,
            args=(file_path, recipient),
            daemon=True,
        ).start()

    def _encrypt_and_send(self, file_path: str, recipient: str) -> None:
        """
        Background thread: encrypt file, wrap key, send bundle.
        Uses self.after() to update the GUI from the main thread.
        """
        try:
            # 1. Generate a fresh AES-256 key for this transfer
            aes_key = encryption.generate_key()

            # 2. Encrypt the file to a temp path
            enc_path = file_path + ".enc"
            encryption.encrypt_file(aes_key, file_path, enc_path)

            # 3. Wrap the AES key with the recipient's RSA public key
            pub_key     = rsa_utils.load_public_key(recipient, USERS_DIR)
            wrapped_key = rsa_utils.wrap_aes_key(aes_key, pub_key)

            # 4. Read the encrypted file bytes
            with open(enc_path, "rb") as f:
                enc_bytes = f.read()
            os.remove(enc_path)     # clean up temp file

            # 5. Send bundle over socket
            self.after(0, lambda: self._set_status("📡  Sending…", "#f0c040"))
            send_bundle(wrapped_key, enc_bytes)

            self.after(0, lambda: self._set_status(
                f"✅  Sent '{os.path.basename(file_path)}' to {recipient} successfully.", "#50fa7b"
            ))

        except ConnectionRefusedError:
            self.after(0, lambda: self._set_status(
                "❌  Connection refused — is receiver.py running?", "#ff5555"
            ))
        except Exception as e:
            self.after(0, lambda: self._set_status(f"❌  Error: {e}", "#ff5555"))
        finally:
            self.after(0, lambda: self._send_btn.config(state="normal"))


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

if __name__ == "__main__":
    app = SenderApp()
    app.mainloop()
