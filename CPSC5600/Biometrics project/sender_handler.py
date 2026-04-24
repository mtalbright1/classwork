# handles tkinter, selects a fingerprint, authenticates it, sends over network

import os
import socket
import struct
import tkinter as tk
from tkinter import filedialog, messagebox, ttk

import authentication
import encryption
import rsa_utils

HOST       = "localhost"
PORT       = 9999
USERS_DIR  = "users"
USERS      = ["alice", "bob"]

def send_bundle(wrapped_key: bytes, encrypted_file: bytes) -> None:
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:     # uses IPv4 address and TCP stream
        sock.connect((HOST, PORT))
        key_len = struct.pack(">I", len(wrapped_key))       # Prefix the wrapped key with its length so the receiver knows where it ends
        sock.sendall(key_len + wrapped_key + encrypted_file)        # [4-byte length of wrapped key][wrapped key][encrypted file bytes]

class FingerprintDialog(tk.Toplevel):
    def __init__(self, parent, username: str):
        super().__init__(parent)
        self.username = username
        self.result   = None
        self.title("Biometric Verification")
        self.resizable(False, False)
        self.grab_set()
        self._build_ui()
        self.protocol("WM_DELETE_WINDOW", self._on_cancel)

    def _build_ui(self) -> None:
        PAD = {"padx": 18, "pady": 8}
        header = tk.Frame(self, bg="#1a1a2e")
        header.pack(fill="x")
        tk.Label(
            header,
            text="Fingerprint Verification",
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
            print("Please enter your hand and finger.")
            return

        try:
            selected_path = authentication.select_fp(self.username, hand_finger)
        except (FileNotFoundError, ValueError) as e:
            print(f"Error: {e}")
            self.result = False
            return

        try:
            import biometric
            score = biometric.compare_fingerprints(
                input_fingerprint_path=selected_path,
                username=self.username,
                show_visual=False,
            )
        except (ValueError, FileNotFoundError) as e:
            print(f"Error: {e}")
            self.result = False
            return

        threshold = authentication.MATCH_THRESHOLD
        self.result = score >= threshold
        print(f"{'Success' if self.result else 'Fail'}: {score:.1f}% (threshold: {threshold}%)")
        if self.result:
            self.after(800, self.destroy)

    def _on_cancel(self) -> None:
        self.result = None
        self.destroy()

class SenderApp(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Secure File Transfer — Sender")
        self.resizable(False, False)
        self.configure(bg="#1a1a2e")
        self._selected_file = tk.StringVar(value="No file selected")
        self._build_ui()

    def _build_ui(self) -> None:
        header = tk.Frame(self, bg="#0f3460")
        header.pack(fill="x")
        tk.Label(
            header,
            text="BIOMETRIC SECURE FILE TRANSFER",
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

        label(body, "USERNAME").pack(fill="x", pady=(0, 2))
        self._username_entry = entry(body, width=32)
        self._username_entry.pack(fill="x", pady=(0, 14))

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

        label(body, "RECIPIENT").pack(fill="x", pady=(0, 2))
        self._recipient_var = tk.StringVar(value=USERS[0])
        ttk.Combobox(
            body,
            textvariable=self._recipient_var,
            values=USERS,
            state="readonly",
            font=("Courier New", 11),
            width=29,
        ).pack(fill="x", pady=(0, 20))

        tk.Button(
            body,
            text="Authenticate & Send  →",
            font=("Courier New", 11, "bold"),
            bg="#e94560", fg="white",
            activebackground="#c0392b", activeforeground="white",
            relief="flat", pady=10,
            command=self._on_send,
        ).pack(fill="x")

    def _browse_file(self) -> None:
        path = filedialog.askopenfilename(title="Select file to send")
        if path:
            self._selected_file.set(path)

    def _on_send(self) -> None:
        username  = self._username_entry.get().strip()
        file_path = self._selected_file.get()
        recipient = self._recipient_var.get()

        if not username:
            messagebox.showwarning("Missing input", "Please enter your username.")
            return
        if file_path == "No file selected" or not os.path.exists(file_path):
            messagebox.showwarning("Missing input", "Please select a valid file to send.")
            return
        if username == recipient:
            messagebox.showwarning("Invalid", "Sender and recipient cannot be the same user.")
            return

        dialog = FingerprintDialog(self, username)
        self.wait_window(dialog)

        if dialog.result is None:
            print("Cancelled.")
            return
        if not dialog.result:
            print("Authentication failed. Transfer cancelled.")
            return

        self._encrypt_and_send(file_path, recipient)

    # encrypt file, wrap key, send bundle
    def _encrypt_and_send(self, file_path: str, recipient: str) -> None:
        try:
            aes_key = encryption.generate_key()     # Generate a fresh AES-256 key for this transfer
            enc_path = file_path + ".enc"   # Encrypt the file to a temp path
            encryption.encrypt_file(aes_key, file_path, enc_path)

            pub_key = rsa_utils.load_public_key(recipient, USERS_DIR)   # Wrap the AES key with the recipient's RSA public key
            wrapped_key = rsa_utils.wrap_aes_key(aes_key, pub_key)

            with open(enc_path, "rb") as f:     # Read the encrypted file bytes
                enc_bytes = f.read()
            os.remove(enc_path)     # clean up temp file

            send_bundle(wrapped_key, enc_bytes)     # Send bundle over socket
            print(f"Sent '{os.path.basename(file_path)}' to {recipient} successfully.")

        except ConnectionRefusedError:
            print("Connection refused — is receiver.py running?")
        except Exception as e:
            print(f"Error: {e}")

# entry point
if __name__ == "__main__":
    app = SenderApp()
    app.mainloop()