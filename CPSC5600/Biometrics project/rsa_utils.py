"""
RSA key wrapping utilities

Each user will have an RSA-2048 key pair stored under users/<username>/:
    private_key.pem > encrypted with a passphrase, chmod 600
    public_key.pem > standard public key that is world-readable

Public API
    generate_keypair(username: str, users_dir: str, passphrase: bytes) -> None
    load_public_key(username: str, users_dir: str) -> RSAPublicKey
    load_private_key(username: str, users_dir: str, passphrase: bytes) -> RSAPrivateKey
    wrap_aes_key(aes_key: bytes, public_key: RSAPublicKey) -> bytes
    unwrap_aes_key(wrapped_key: bytes, private_key: RSAPrivateKey) -> bytes
"""
import os

from cryptography.hazmat.primitives.asymmetric import rsa, padding
from cryptography.hazmat.primitives import hashes, serialization


# Key Generation
def generate_keypair(username: str, users_dir: str, passphrase: bytes) -> None:
    user_dir = os.path.join(users_dir, username)
    os.makedirs(user_dir, exist_ok=True)
 
    priv_path = os.path.join(user_dir, "private_key.pem")
    pub_path  = os.path.join(user_dir, "public_key.pem")
 
    if os.path.exists(priv_path) or os.path.exists(pub_path):
        raise FileExistsError(f"Key files already exist for '{username}'. ")
 
    # Generate private key
    private_key = rsa.generate_private_key(
        public_exponent=65537,  # standard safe exponent
        key_size=2048,
    )
 
    # Serialise private key — encrypted with passphrase, never plaintext on disk
    priv_pem = private_key.private_bytes(
        encoding=serialization.Encoding.PEM,
        format=serialization.PrivateFormat.PKCS8,
        encryption_algorithm=serialization.BestAvailableEncryption(passphrase),
    )
 
    # Serialise public key — no encryption needed
    pub_pem = private_key.public_key().public_bytes(
        encoding=serialization.Encoding.PEM,
        format=serialization.PublicFormat.SubjectPublicKeyInfo,
    )
 
    with open(priv_path, "wb") as f:
        f.write(priv_pem)
    os.chmod(priv_path, 0o600)  # owner read/write only — mirrors encryption.py
 
    with open(pub_path, "wb") as f:
        f.write(pub_pem)


# Public key loading
def load_public_key(username: str, users_dir: str):
    pub_path = os.path.join(users_dir, username, "public_key.pem")
    with open(pub_path, "rb") as f:
        public_key = serialization.load_pem_public_key(f.read())
 
    return public_key
 

# Loads private key 
def load_private_key(username: str, users_dir: str, passphrase: bytes):
    priv_path = os.path.join(users_dir, username, "private_key.pem")
    with open(priv_path, "rb") as f:
        private_key = serialization.load_pem_private_key(f.read(), password=passphrase)
 
    return private_key


# Key wrapping / unwrapping
def wrap_aes_key(aes_key: bytes, public_key) -> bytes:
    wrapped = public_key.encrypt(
        aes_key,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None,
        )
    )
    return wrapped
 
 
def unwrap_aes_key(wrapped_key: bytes, private_key) -> bytes:
    aes_key = private_key.decrypt(
        wrapped_key,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None,
        )
    )
    return aes_key