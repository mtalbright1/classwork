# prompts the user for biometric input, calls biometric.py, can call encryption.py

import biometric
import encryption

match_threshold = 75
fingerprint_database_path = "Fingerprint database"
input_fingerprint_path = input("Provide filepath to input fingerprint: ")       # prompt the user for input

similarity_score = biometric.compare_fingerprints(input_fingerprint_path, fingerprint_database_path)

if similarity_score < match_threshold:
    print("Authentication failed: match not found")
    exit()

print("Authentication successful")