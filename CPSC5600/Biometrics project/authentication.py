# prompts the user for biometric input, calls biometric.py
import os
import biometric

MATCH_THRESHOLD = 75
FINGERPRINT_DB_DIR = "fingerprint_db"
USERS_DIR = "users"


def select_fp(username: str, hand_finger: str) -> str:
    """
    Look up a fingerprint filename from the user's manifest that matches
    the requested hand and finger
    """
    target = hand_finger.replace(" ", "_").lower()      # the hand and finger we are auth-ing, normalized
    manifest_path = os.path.join(USERS_DIR, username, "fingerprints.txt")   # path to requisite fingerprint in fp_db
    if not os.path.exists(manifest_path):
        raise FileNotFoundError(f"No fingerprint manifest for '{username}'")
    
    # finds the target file in the fp database
    with open(manifest_path, "r") as f:
        for line in f:
            filename = line.strip()     # this is the true filename we are checking
            if not filename: continue

            # Dev note: fp file name = <id>__<sex>_<hand>_<finger>_finger.BMP
            parts = filename.lower().split("__")    # normalize the filename for comparison
            if len(parts) < 2: continue     # <id> half of parts that we don't care about
            after_id = parts[1]     # other half of parts - <sex>_ and so on
            after_sex = after_id[2:]     # skip sex marker
            if after_sex.startswith(target):
                return os.path.join(FINGERPRINT_DB_DIR, filename)
    
    raise ValueError(f"No enrolled fingerprint found for '{username}' matching '{hand_finger}'. Check spelling")
    

def authenticate(username: str, show_vis: bool = False) -> bool:
    """
    Run the biometric.py authentication for <username>
    Prompts for hand and finger via stdin, locates the corresponding enrolled template,
    and compares it against the fingerprint_db.
    """
    print(f"Authenticating user: {username}")

    # hand and finger the user is using to biometrically auth
    hand_finger = input("Enter hand and finger [e.g., Left index]: ").strip()

    # try-except block to verify fingerprint file exists
    try:
        selected_path = select_fp(username, hand_finger)
    except (FileNotFoundError, ValueError) as e:
        print(f"Authentication error: {e}")
        return False

    # try-except block for biometric comparison of selected fingerprint against database - uses biometric.py
    try:
        score = biometric.compare_fingerprints(input_fingerprint_path=selected_path, username=username, show_visual=show_vis)
    except (ValueError, FileNotFoundError) as e:
        print(f"Biometric comparison error: {e}")
        return False
    
    # nice print statement format for resutlts of comparison and auth
    print(f"Similarity score: {score:.2f}%  (threshold: {MATCH_THRESHOLD}%)")

    # final auth success check and results print
    if score >= MATCH_THRESHOLD:
        print("Authentication successful.")
        return True
    else:
        print("Authentication failed: match not found.")
        return False


# Standalone entry point for testing outside of demo
if __name__ == "__main__":
    username = input("Enter username: ").strip()
    result = authenticate(username, show_vis=True)
    if not result:
        raise SystemExit(1)