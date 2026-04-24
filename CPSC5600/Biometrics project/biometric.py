# accept a fingerprint .bmp and returns the closest match in a database and a score

import os       # for accessing files
import cv2      # computer vision, install with $pip install opencv-python

FINGERPRINT_DB_DIR = "fingerprint_db"       # const var so dir str doesn't have to be retyped
USERS_DIR = "users"         # const var so users dir str doesn't have to be retyped


def load_usr_fp_paths(username: str) -> list[str]:
    """
    Reads uses/<username>/fingerprints.txt and return a list of ful paths to that user's
    enrolled fingerprint files in fingerprint_db/
    """
    manifest_path = os.path.join(USERS_DIR, username, "fingerprints.txt")
    filenames = []

    # checking if fingerprints.txt exists for user; if not then raise error
    if not os.path.exists(manifest_path):
        raise FileNotFoundError(f"No fingerprint manifest found for '{username}' at '{manifest_path}'. ")
    
    # makes list of fingerprint file names in manifest
    with open(manifest_path, "r") as f:
        for line in f:
            line = line.strip()
            if line: filenames.append(line)

    # creates the full filepath of each fingerprint that points to fingerprint_db/
    full_paths = []
    for filename in filenames:
        full_path = os.path.join(FINGERPRINT_DB_DIR, filename)
        if not os.path.exists(full_path):
            raise FileNotFoundError(f"Fingerprint file listed in manifest not found: {full_path}")
        full_paths.append(full_path)
    
    # comepleted files paths list
    return full_paths


def compare_fingerprints(input_fingerprint_path: str, username: str, show_visual: bool = True) -> float:
    """
    Compare the input fingerprint against all enrolled finerprints for <username> and return
    the best similarity score (0 - 100)
    """
    # can download fingerprint .bmp's from SOCOFing or FVC2002
    stored_paths = load_usr_fp_paths(username)
    input_fingerprint = cv2.imread(input_fingerprint_path)
    #input_fingerprint = cv2.resize(input_fingerprint, None, fx=2.5, fy=2.5)     # scale up image size, if needed
    if input_fingerprint is None:       # added error checking
        raise ValueError(f"Could not read input fingerprint image at {input_fingerprint_path}")

    best_score = 0      # similarity score for the current best match
    match_filename = None      # will be filled with best match
    match_image = None
    keypoints_input = None      # keypoints are conspicuous parts of an image
    keypoints_stored = None
    match_keypoints = None

    # moved outside the loop because these objects don't need to be reinstantiated every loop cycle
    sift = cv2.SIFT_create()        # sift object that creates keypoints and descriptors from an image
    keypoints_1, descriptors_1 = sift.detectAndCompute(input_fingerprint, None)     # descriptors describe keypoints

    for full_path in stored_paths:
        fingerprint_image = cv2.imread(full_path)
        if fingerprint_image is None: continue      # skip unreadable files rather than crashing
        
        keypoints_2, descriptors_2 = sift.detectAndCompute(fingerprint_image, None)     # a 128-number vector describing what the local area around the keypoint looks like

        matches = cv2.FlannBasedMatcher({'algorithm': 1, 'trees': 10}, {}).knnMatch(descriptors_1, descriptors_2, k=2)
        # Fast Library for Approximate Nearest Neighbor
        # algorithm 1 is KD-tree with 10 trees. Searching for 'k' nearest neighbors

        matched_points = []
        for i, j in matches:        # 'i' is best match, 'j' is second best. If 'i' is a significantly better match than 'j', we are confident it is a correct match
            if i.distance < 0.7 * j.distance:       # Lowe's ratio test - ratio can be adjusted, lower is stricter. Distance is difference from input
                matched_points.append(i)

        keypoints = min(len(keypoints_1), len(keypoints_2))     # python has a built-in min function
        if keypoints == 0:      # just avoiding divide by zero in case of improper input
            continue
        
        score = len(matched_points) / keypoints * 100

        if score > best_score:      # is the percent of matching keypoints the highest so far?
            best_score = score
            match_image = fingerprint_image
            keypoints_input = keypoints_1
            keypoints_stored = keypoints_2
            match_keypoints = matched_points

            match_filename = full_path       # save this one as the best match - segregated since not in use

    # output for testing
    # print("Best match: " + match_filename)
    # print("Score: " + str(best_score))

    # visualize the matching - added a bool so we can choose whether we want the visual or not
    if show_visual and match_image is not None:
        result = cv2.drawMatches(input_fingerprint, keypoints_input, match_image, keypoints_stored, match_keypoints, None)
        result = cv2.resize(result, None, fx=3, fy=3)     # resize the image
        cv2.imshow("Result", result)
        cv2.waitKey(0)      # wait forever until I press any key
        cv2.destroyAllWindows()     # then close all windows
    

    return best_score
