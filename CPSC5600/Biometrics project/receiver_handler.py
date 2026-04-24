# handles tkinter, listens to network, unwraps AES key and decrypts

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

HOST      = "localhost"
PORT      = 9999
USERS_DIR = "users"
USERS     = ["alice", "bob"]
CHUNK_SIZE = 4096       # How many bytes to read at once from the socket

def recv_all(sock: socket.socket) -> bytes:
    data = []
    while True:     # Read all bytes from *sock* until the sender closes the connection
        chunk = sock.recv(CHUNK_SIZE)
        if not chunk:
            break
        data.append(chunk)
    return b"".join(data)       # Returns the complete payload as a single bytes object

def parse_bundle(raw: bytes) -> tuple[bytes, bytes]:        # Split the raw socket payload into (wrapped_aes_key, encrypted_file)
# [4 bytes length of wrapped key][AES key][encrypted file]
    if len(raw) < 4:
        raise ValueError("Bundle too short — missing key length prefix.")

    key_len = struct.unpack(">I", raw[:4])[0]   # get 4-byte key length

    if len(raw) < 4 + key_len:
        raise ValueError(f"Bundle too short — expected {4 + key_len} bytes for key, got {len(raw)}.")

    wrapped_key = raw[4 : 4 + key_len]
    encrypted_file = raw[4 + key_len :]

    if not encrypted_file:
        raise ValueError("Bundle contains no encrypted file data.")

    return wrapped_key, encrypted_file

class FingerprintDialog(tk.Toplevel):
    # asks the user for their hand and finger, then runs biometric

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
            text="Fingerprint Verification",
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
            print("Please enter your hand and finger.")
            return

        try:
            selected_path = authentication.select_fp(self.username, hand_finger)
        except (FileNotFoundError, ValueError) as e:
            print(f"Error: {e}")
            self.result = False
            return

        try:
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
            self.destroy()

    def _on_cancel(self) -> None:
        self.result = None
        self.destroy()

# receiver window
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
        # header
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
            text="RECEIVER",
            bg="#0d2137", fg="#50fa7b",
            font=("Courier New", 10, "bold"),
            pady=16, padx=20,
        ).pack(side="right")

        # Body
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

        # Listen and Stop buttons
        btn_row = tk.Frame(body, bg="#16213e")
        btn_row.pack(fill="x", pady=(0, 14))

        self._listen_btn = tk.Button(
            btn_row,
            text="Start Listening",
            font=("Courier New", 11, "bold"),
            bg="#50fa7b", fg="#0d0d1a",
            activebackground="#2ecc71", activeforeground="#0d0d1a",
            relief="flat", pady=10,
            command=self._on_start_listening,
        )
        self._listen_btn.pack(side="left", fill="x", expand=True, padx=(0, 6))

        self._stop_btn = tk.Button(
            btn_row,
            text="Stop",
            font=("Courier New", 11, "bold"),
            bg="#2c2c3e", fg="#a0a0c0",
            activebackground="#3c3c4e", activeforeground="white",
            relief="flat", pady=10,
            state="disabled",
            command=self._on_stop_listening,
        )
        self._stop_btn.pack(side="right", fill="x", expand=True, padx=(6, 0))

    def _on_start_listening(self) -> None:
        username = self._username_entry.get().strip()
        passphrase = self._passphrase_entry.get().strip()

        if not username:
            messagebox.showwarning("Missing input", "Please enter your username.")
            return
        if not passphrase:
            messagebox.showwarning("Missing input", "Please enter your private key passphrase.")
            return
        if username not in USERS:
            messagebox.showwarning("Unknown user", f"'{username}'")
            return

        self._username = username
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
        print(f"Listening on {HOST}:{PORT}")

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
        print("Server stopped")

    def _on_close(self) -> None:
        self._on_stop_listening()
        self.destroy()

    def _server_loop(self) -> None:
        try:
            self._server_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self._server_sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            self._server_sock.bind((HOST, PORT))
            self._server_sock.listen(1)
        except OSError as e:
            print(f"Failed to bind: {e}")
            self.after(0, self._on_stop_listening)
            return

        while self._listening:
            try:
                conn, addr = self._server_sock.accept()
                print(f"Connection from {addr[0]}:{addr[1]}")
                threading.Thread(
                    target=self._handle_connection,
                    args=(conn,),
                    daemon=True,
                ).start()
            except OSError:
                break

    def _handle_connection(self, conn: socket.socket) -> None:
        try:
            with conn:
                raw = recv_all(conn)
            print(f"Bundle received ({len(raw)} bytes)")
            self.after(0, lambda: self._process_bundle(raw))
        except Exception as e:
            print(f"Receive error: {e}")

    def _process_bundle(self, raw: bytes) -> None:
        try:
            wrapped_key, enc_bytes = parse_bundle(raw)
        except ValueError as e:
            print(f"Malformed bundle: {e}")
            return

        print(f"Parsed bundle — key: {len(wrapped_key)}B, file: {len(enc_bytes)}B")

        dialog = FingerprintDialog(self, self._username)
        self.wait_window(dialog)

        if dialog.result is None:
            print("Biometric verification cancelled.")
            return
        if not dialog.result:
            print("Biometric verification failed. File discarded.")
            return

        print("Biometric verification passed. Decrypting.")
        threading.Thread(
            target=self._decrypt_and_save,
            args=(wrapped_key, enc_bytes),
            daemon=True,
        ).start()

    def _decrypt_and_save(self, wrapped_key: bytes, enc_bytes: bytes) -> None:
        try:
            private_key = rsa_utils.load_private_key(self._username, USERS_DIR, self._passphrase)
            aes_key = rsa_utils.unwrap_aes_key(wrapped_key, private_key)

            tmp_enc = "__tmp_received.enc"
            tmp_dec = "__tmp_decrypted"

            with open(tmp_enc, "wb") as f:
                f.write(enc_bytes)

            encryption.decrypt_file(aes_key, tmp_enc, tmp_dec)      # decrypt
            os.remove(tmp_enc)      # clean up temp files

            with open(tmp_dec, "rb") as f:
                dec_bytes = f.read()
            os.remove(tmp_dec)      # clean up temp files

            print("Decryption successful.")
            save_path = os.path.join(os.getcwd(), "received_file")
            with open(save_path, "wb") as f:
                f.write(dec_bytes)
            print(f"File saved to: {save_path}")

        except Exception as e:
            print(f"Decryption failed: {e}")
            for tmp in ("__tmp_received.enc", "__tmp_decrypted"):
                if os.path.exists(tmp):
                    os.remove(tmp)      # still clean up temp files on fail

# entry point
if __name__ == "__main__":
    app = ReceiverApp()
    app.mainloop()
