"""
This is a one-time script to setup the users as follows:
    1. creates users/alice/ and users/bob/ if they don't exist.
    2. Generates an RSA-2048 key pair for each user in their respective directory
    3. Writes a users/<username>/fingerprints.txt file listing the fingerprint 
       filenames from fingerprint_db/ that are assinged to that user.
    
Because this is just a demo, passphrases are hardcoded here as constants. In a real
environment these would be generated or entered on account creation.
"""

import os
import sys
import rsa_utils

USERS_DIR = "users"
FINGERPRINT_DB_DIR = "fingerprint_db"
USR_SUB_IDS = {"alice": "2", "bob": "1"}
PASSPHRASES = {
    "alice": b"alice_demo_pass",
    "bob": b"bob_demo_pass"
}


# Helper Functions
def find_fingerprints(sub_id: str, db_dir: str) -> list[str]:
    if not os.path.isdir(db_dir):
        raise FileNotFoundError(f"Fingerprint database directory '{db_dir}' not found. ")
 
    prefix = f"{sub_id}__"  # SOCOFing naming convention
    matches = sorted(
        f for f in os.listdir(db_dir)
        if f.startswith(prefix)
    )
 
    if not matches:
        raise ValueError(
            f"No fingerprint files found for subject '{sub_id}' in '{db_dir}'. "
            f"Expected filenames starting with '{prefix}'."
        )
 
    return matches


def write_fingerprints(username: str, fingerprints: list[str]) -> None:
    """
    Write users/<username>/fingerprints.txt — one filename per line.
    These are filenames only (not full paths); the biometric module will
    resolve them against FINGERPRINT_DB_DIR at runtime.
    """
    finger_path = os.path.join(USERS_DIR, username, "fingerprints.txt")
    with open(finger_path, "w") as f:
        f.write("\n".join(fingerprints) + "\n")
    print(f"  Wrote fingerprint list ({len(fingerprints)} files) -> {finger_path}")


def setup_user(username: str) -> None:
    print(f"\n[{username}]")

    # RSA keys
    try:
        rsa_utils.generate_keypair(
            username=username,
            users_dir=USERS_DIR,
            passphrase=PASSPHRASES[username]
        )
        print(f" RSA key pair generated")
    except FileExistsError as e:
        print(f" RSA keys already exist, skipping key gen. ({e})")
    
    # Fingerprints
    subject_id = USR_SUB_IDS[username]
    fingerprints = find_fingerprints(subject_id, FINGERPRINT_DB_DIR)
    write_fingerprints(username, fingerprints)

# Main
def main() -> None:
    if not os.path.isdir(FINGERPRINT_DB_DIR):
        print(f"ERROR: {FINGERPRINT_DB_DIR}/ not found in the current directory!")
        sys.exit(1)
    
    print("==== User Setup ====")

    for username in USR_SUB_IDS:
        setup_user(username)
    
    print("\n ==== Setup Complete ====")
    
if __name__ == "__main__":
    main()
    