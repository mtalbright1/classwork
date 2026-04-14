"""
AES-256-GCM encrypt / decrypt for the biometric secure file transfer

Public API
-------------
    generate_key() -> bytes (32-byte AES key)
    save_key(key: bytes, path: str) -> None
    load_key(path: str) -> bytes
    encrypt_file(key: bytes, src_path: str, dst_path: str) -> None
    decrypt_file(key: bytes, src_path: str, dst_path: str) -> None

"""

import os
from cryptography.hazmat.primitives.ciphers.aead import AESGCM

KEY_SIZE_BYTES = 32  # AES-256 key size
NONCE_SIZE_BYTES = 12  # Recommended nonce size for AES-GCM

def generate_key() -> bytes:
    """Generates a random 256-bit (32-byte) AES key."""
    return os.urandom(KEY_SIZE_BYTES)

def save_key(key: bytes, path: str) -> None:
    """
    Write *key* to a file at *path*.
    Raises ValueError if the key length is wrong.
    """
    if len(key) != KEY_SIZE_BYTES:
        raise ValueError(f"Key must be {KEY_SIZE_BYTES} bytes long, got {len(key)}")
    with open(path, "wb") as f:
        f.write(key)
    
    os.chmod(path, 0o600)  # Set file permissions to read/write for owner only

def load_key(path: str) -> bytes:
    """
    Load a key from a file at *path*.
    Raises ValueError if the key length is wrong.
    """
    with open(path, "rb") as f:
        key = f.read()
    if len(key) != KEY_SIZE_BYTES:
        raise ValueError(f"Key file '{path}' contains {len(key)} bytes; expected {KEY_SIZE_BYTES} bytes")
    return key

def encrypt_file(key: bytes, src_path: str, dst_path: str) -> None:
    """
    Encrypt the file at *src_path* and write the result to *dst_path*.

    Output format: [nonce (12 bytes)][ciphertext + 16-bytes GCM tag]
    """
    if len(key) != KEY_SIZE_BYTES:
        raise ValueError(f"Key must be {KEY_SIZE_BYTES} bytes long, got {len(key)}")
    
    nonce = os.urandom(NONCE_SIZE_BYTES)
    aesgcm = AESGCM(key)

    with open(src_path, "rb") as f:
        plaintext = f.read()

    ciphertext = aesgcm.encrypt(nonce, plaintext, None)

    with open(dst_path, "wb") as f:
        f.write(nonce + ciphertext) # Prepend nonce for decryption

def decrypt_file(key: bytes, src_path: str, dst_path: str) -> None:
    """
    Decrypt the file at *src_path* and write the plaintext to *dst_path*.
    Expects input format: [nonce (12 bytes)][ciphertext + 16-bytes GCM tag]

    Raises cryptography.exceptions.InvalidTag if decryption fails (e.g. wrong key or corrupted data).
    """
    if len(key) != KEY_SIZE_BYTES:
        raise ValueError(f"Key must be {KEY_SIZE_BYTES} bytes long, got {len(key)}")

    with open(src_path, "rb") as f:
        raw = f.read()

    if len(raw) < NONCE_SIZE_BYTES + 16:  # Minimum length: nonce + GCM tag
        raise ValueError("Encrypted file is too short to be valid")

    nonce = raw[:NONCE_SIZE_BYTES]
    ciphertext = raw[NONCE_SIZE_BYTES:]

    aesgcm = AESGCM(key)
    plaintext = aesgcm.decrypt(nonce, ciphertext, None)     # aesgcm.decrypt raises InvalidTag automatically if auth fails

    with open(dst_path, "wb") as f:
        f.write(plaintext)
