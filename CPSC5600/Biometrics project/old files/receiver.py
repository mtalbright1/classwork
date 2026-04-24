"""
receiver.py — Biometric Secure File Transfer (Receiver)

Tkinter GUI flow:
    1. Enter username and passphrase for RSA private key
    2. Click "Start Listening" — TCP server binds to localhost:9999
    3. When a bundle arrives:
       a. Bundle is parsed: [4-byte key length][wrapped AES key][encrypted file]
       b. Biometric auth dialog prompts for hand and finger
       c. On success: RSA private key loaded, AES key unwrapped,
          file decrypted, save dialog offered
    4. Status bar updates throughout

Wire format received over socket:
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
import biometric
import encryption
import rsa_utils

# ---------------------------------------------------------------------------
# Configuration
# ---------------------------------------------------------------------------

HOST      = "localhost"
PORT      = 9999
USERS_DIR = "users"
USERS     = ["alice", "bob"]

# How many bytes to read at once from the socket
CHUNK_SIZE = 4096


# ---------------------------------------------------------------------------
# Networking
# ---------------------------------------------------------------------------

def recv_all(sock: socket.socket) -> bytes:
    """
    Read all bytes from *sock* until the sender closes the connection.
    Returns the complete payload as a single bytes object.
    """
    data = []
    while True:
        chunk = sock.recv(CHUNK_SIZE)
        if not chunk:
            break
        data.append(chunk)
    return b"".join(data)


def parse_bundle(raw: bytes) -> tuple[bytes, bytes]:
    """
    Split the raw socket payload into (wrapped_aes_key, encrypted_file).

    Format:
        [4 bytes big-endian uint: length of wrapped key]
        [N bytes: RSA-wrapped AES key]
        [remaining: encrypted file bytes]

    Raises
    ------
    ValueError  if the payload is too short or malformed
    """
    if len(raw) < 4:
        raise ValueError("Bundle too short — missing key length prefix.")

    key_len = struct.unpack(">I", raw[:4])[0]   # read 4-byte big-endian length

    if len(raw) < 4 + key_len:
        raise ValueError(
            f"Bundle too short — expected {4 + key_len} bytes for key, "
            f"got {len(raw)}."
        )

    wrapped_key    = raw[4 : 4 + key_len]
    encrypted_file = raw[4 + key_len :]

    if not encrypted_file:
        raise ValueError("Bundle contains no encrypted file data.")

    return wrapped_key, encrypted_file


# ---------------------------------------------------------------------------
# Biometric dialog  (mirrors sender.py's FingerprintDialog)
# ---------------------------------------------------------------------------

class FingerprintDialog(tk.Toplevel):
    """
    Modal dialog that asks the user for their hand and finger,
    then runs biometric auth in a background thread.
    """

    def __init__(self, parent, username: str):
        super().__init__(parent)
        self.username = username
        self.result   = None    # True / False after auth completes
        self.title("Biometric Verification")
        self.resizable(False, False)
        self.grab_set()
        self._build_ui()
        self.protocol("WM_DELETE_WINDOW", self._on_cancel)

    def _build_ui(self) -> None:
        PAD = {"padx": 18, "pady": 8}

        header = tk.Frame(self, bg="#0d2137")
        header.pack(fill="x")
        tk.Label(
            header,
            text="🔐  Fingerprint Verification",
            bg="#0d2137", fg="#e0e0e0",
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
            bg="#0d2137", fg="#e0e0e0",
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
            bg="#0d2137", fg="#e0e0e0",
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

        def run_auth():
            try:
                selected_path = authentication.select_fp(self.username, hand_finger)
            except (FileNotFoundError, ValueError) as e:
                self.after(0, lambda: self._finish(False, str(e)))
                return

            try:
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
# Main receiver window
# ---------------------------------------------------------------------------

class ReceiverApp(tk.Tk):

    def __init__(self):
        super().__init__()
        self.title("Secure File Transfer — Receiver")
        self.resizable(False, False)
        self.configure(bg="#1a1a2e")

        self._server_thread  = None
        self._listening      = False
        self._server_sock    = None     # kept so we can close it on stop

        self._build_ui()
        self.protocol("WM_DELETE_WINDOW", self._on_close)

    def _build_ui(self) -> None:
        # ── Header ──────────────────────────────────────────────────────────
        header = tk.Frame(self, bg="#0d2137")
        header.pack(fill="x")
        tk.Label(
            header,
            text="🔒  BIOMETRIC SECURE FILE TRANSFER",
            bg="#C416A7", fg="#e0e0e0",
            font=("Courier New", 14, "bold"),
            pady=16, padx=20,
        ).pack(side="left")
        tk.Label(
            header,
            text="RECEIVER",
            bg="#0d2137", fg="#50fa7b",
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

        def entry(parent, show=None, **kw):
            return tk.Entry(
                parent,
                font=("Courier New", 11),
                bg="#0d2137", fg="#e0e0e0",
                insertbackground="#e0e0e0",
                relief="flat", bd=6,
                show=show,
                **kw,
            )

        # Username
        label(body, "USERNAME").pack(fill="x", pady=(0, 2))
        self._username_entry = entry(body, width=32)
        self._username_entry.pack(fill="x", pady=(0, 14))

        # Passphrase (for RSA private key decryption)
        label(body, "PRIVATE KEY PASSPHRASE").pack(fill="x", pady=(0, 2))
        self._passphrase_entry = entry(body, show="•", width=32)
        self._passphrase_entry.pack(fill="x", pady=(0, 20))

        # Listen / Stop buttons
        btn_row = tk.Frame(body, bg="#16213e")
        btn_row.pack(fill="x", pady=(0, 14))

        self._listen_btn = tk.Button(
            btn_row,
            text="▶  Start Listening",
            font=("Courier New", 11, "bold"),
            bg="#50fa7b", fg="#0d0d1a",
            activebackground="#2ecc71", activeforeground="#0d0d1a",
            relief="flat", pady=10,
            command=self._on_start_listening,
        )
        self._listen_btn.pack(side="left", fill="x", expand=True, padx=(0, 6))

        self._stop_btn = tk.Button(
            btn_row,
            text="■  Stop",
            font=("Courier New", 11, "bold"),
            bg="#2c2c3e", fg="#a0a0c0",
            activebackground="#3c3c4e", activeforeground="white",
            relief="flat", pady=10,
            state="disabled",
            command=self._on_stop_listening,
        )
        self._stop_btn.pack(side="right", fill="x", expand=True, padx=(6, 0))

        # Activity log
        label(body, "ACTIVITY LOG").pack(fill="x", pady=(8, 2))
        log_frame = tk.Frame(body, bg="#0d0d1a", bd=0)
        log_frame.pack(fill="both", expand=True)

        self._log = tk.Text(
            log_frame,
            font=("Courier New", 9),
            bg="#0d0d1a", fg="#c0d8f0",
            insertbackground="#e0e0e0",
            relief="flat", bd=6,
            height=10, width=46,
            state="disabled",
            wrap="word",
        )
        scrollbar = tk.Scrollbar(log_frame, command=self._log.yview)
        self._log.configure(yscrollcommand=scrollbar.set)
        self._log.pack(side="left", fill="both", expand=True)
        scrollbar.pack(side="right", fill="y")

        # ── Status bar ───────────────────────────────────────────────────────
        self._status_var = tk.StringVar(value="Not listening.")
        tk.Label(
            self,
            textvariable=self._status_var,
            bg="#0d0d1a", fg="#a0a0c0",
            font=("Courier New", 9),
            anchor="w", padx=12, pady=6,
        ).pack(fill="x", side="bottom")

    # ── Logging helpers ───────────────────────────────────────────────────────

    def _log_msg(self, msg: str, color: str = "#c0d8f0") -> None:
        """Append a line to the activity log. Must be called from main thread."""
        self._log.config(state="normal")
        self._log.insert("end", msg + "\n")
        self._log.see("end")
        self._log.config(state="disabled")

    def _set_status(self, msg: str, color: str = "#50fa7b") -> None:
        self._status_var.set(msg)

    # ── Listen / Stop ─────────────────────────────────────────────────────────

    def _on_start_listening(self) -> None:
        username   = self._username_entry.get().strip()
        passphrase = self._passphrase_entry.get().strip()

        if not username:
            messagebox.showwarning("Missing input", "Please enter your username.")
            return
        if not passphrase:
            messagebox.showwarning("Missing input", "Please enter your private key passphrase.")
            return
        if username not in USERS:
            messagebox.showwarning("Unknown user", f"'{username}' is not a known user.")
            return

        self._username   = username
        self._passphrase = passphrase.encode()

        # Validate private key loads before starting the server
        try:
            rsa_utils.load_private_key(self._username, USERS_DIR, self._passphrase)
        except Exception as e:
            messagebox.showerror("Key error", f"Could not load private key:\n{e}")
            return

        self._listening = True
        self._listen_btn.config(state="disabled")
        self._stop_btn.config(state="normal")
        self._username_entry.config(state="disabled")
        self._passphrase_entry.config(state="disabled")
        self._set_status(f"👂  Listening on {HOST}:{PORT}…", "#f0c040")
        self._log_msg(f"[+] Server started — listening on {HOST}:{PORT}")

        self._server_thread = threading.Thread(
            target=self._server_loop, daemon=True
        )
        self._server_thread.start()

    def _on_stop_listening(self) -> None:
        self._listening = False
        if self._server_sock:
            try:
                self._server_sock.close()
            except OSError:
                pass
        self._listen_btn.config(state="normal")
        self._stop_btn.config(state="disabled")
        self._username_entry.config(state="normal")
        self._passphrase_entry.config(state="normal")
        self._set_status("Stopped.", "#a0a0c0")
        self._log_msg("[−] Server stopped.")

    def _on_close(self) -> None:
        self._on_stop_listening()
        self.destroy()

    # ── Server loop (background thread) ──────────────────────────────────────

    def _server_loop(self) -> None:
        """
        Blocking TCP accept loop. Runs in a daemon thread.
        Each accepted connection is handled in its own thread.
        """
        try:
            self._server_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self._server_sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            self._server_sock.bind((HOST, PORT))
            self._server_sock.listen(1)
        except OSError as e:
            self.after(0, lambda: self._log_msg(f"[!] Failed to bind: {e}", "#ff5555"))
            self.after(0, self._on_stop_listening)
            return

        while self._listening:
            try:
                conn, addr = self._server_sock.accept()
                self.after(0, lambda a=addr: self._log_msg(f"[→] Connection from {a[0]}:{a[1]}"))
                threading.Thread(
                    target=self._handle_connection,
                    args=(conn,),
                    daemon=True,
                ).start()
            except OSError:
                break   # server socket was closed by _on_stop_listening

    # ── Connection handler (background thread) ────────────────────────────────

    def _handle_connection(self, conn: socket.socket) -> None:
        """
        Receive the full bundle, then hand off to the main thread
        to run biometric auth (which requires the Tkinter dialog).
        """
        try:
            with conn:
                raw = recv_all(conn)
            self.after(0, lambda: self._log_msg(f"[+] Bundle received ({len(raw)} bytes)"))
            self.after(0, lambda: self._process_bundle(raw))
        except Exception as e:
            self.after(0, lambda: self._log_msg(f"[!] Receive error: {e}", "#ff5555"))

    # ── Bundle processing (main thread) ──────────────────────────────────────

    def _process_bundle(self, raw: bytes) -> None:
        """
        Parse bundle, run biometric auth dialog, decrypt, and offer save.
        Must run on the main thread (Tkinter dialog requirement).
        """
        # Parse bundle
        try:
            wrapped_key, enc_bytes = parse_bundle(raw)
        except ValueError as e:
            self._log_msg(f"[!] Malformed bundle: {e}", "#ff5555")
            return

        self._log_msg(f"[+] Parsed bundle — key: {len(wrapped_key)}B, file: {len(enc_bytes)}B")
        self._set_status("📥  File received — biometric verification required", "#f0c040")

        # Biometric auth
        self._log_msg("[?] Waiting for biometric verification…")
        dialog = FingerprintDialog(self, self._username)
        self.wait_window(dialog)

        if dialog.result is None:
            self._log_msg("[−] Biometric verification cancelled.", "#a0a0c0")
            self._set_status(f"👂  Listening on {HOST}:{PORT}…", "#f0c040")
            return
        if not dialog.result:
            self._log_msg("[✗] Biometric verification failed. File discarded.", "#ff5555")
            self._set_status(f"👂  Listening on {HOST}:{PORT}…", "#f0c040")
            return

        self._log_msg("[✓] Biometric verification passed. Decrypting…")

        # Decrypt in background thread
        threading.Thread(
            target=self._decrypt_and_save,
            args=(wrapped_key, enc_bytes),
            daemon=True,
        ).start()

    # ── Decrypt and save (background thread) ─────────────────────────────────

    def _decrypt_and_save(self, wrapped_key: bytes, enc_bytes: bytes) -> None:
        """
        Unwrap the AES key with the receiver's RSA private key,
        decrypt the file, then prompt the user to save it.
        """
        try:
            # Unwrap AES key
            private_key = rsa_utils.load_private_key(
                self._username, USERS_DIR, self._passphrase
            )
            aes_key = rsa_utils.unwrap_aes_key(wrapped_key, private_key)

            # Write encrypted bytes to a temp file for decryption
            tmp_enc  = "__tmp_received.enc"
            tmp_dec  = "__tmp_decrypted"

            with open(tmp_enc, "wb") as f:
                f.write(enc_bytes)

            encryption.decrypt_file(aes_key, tmp_enc, tmp_dec)
            os.remove(tmp_enc)

            # Read decrypted bytes
            with open(tmp_dec, "rb") as f:
                dec_bytes = f.read()
            os.remove(tmp_dec)

            self.after(0, lambda: self._log_msg("[✓] Decryption successful."))
            self.after(0, lambda: self._prompt_save(dec_bytes))

        except Exception as e:
            self.after(0, lambda: self._log_msg(f"[!] Decryption failed: {e}", "#ff5555"))
            self.after(0, lambda: self._set_status(
                f"👂  Listening on {HOST}:{PORT}…", "#f0c040"
            ))
            # Clean up temp files if they exist
            for tmp in ("__tmp_received.enc", "__tmp_decrypted"):
                if os.path.exists(tmp):
                    os.remove(tmp)

    def _prompt_save(self, dec_bytes: bytes) -> None:
        """Offer a save dialog and write the decrypted file."""
        save_path = filedialog.asksaveasfilename(
            title="Save decrypted file",
            defaultextension="",
        )
        if not save_path:
            self._log_msg("[−] Save cancelled — decrypted file discarded.", "#a0a0c0")
        else:
            with open(save_path, "wb") as f:
                f.write(dec_bytes)
            self._log_msg(f"[✓] File saved to: {save_path}")
            self._set_status("✅  File received and saved.", "#50fa7b")

        # Return to listening state
        self.after(1000, lambda: self._set_status(
            f"👂  Listening on {HOST}:{PORT}…", "#f0c040"
        ))


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

if __name__ == "__main__":
    app = ReceiverApp()
    app.mainloop()
