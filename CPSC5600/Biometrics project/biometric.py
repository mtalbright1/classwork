# accept a fingerprint .bmp and returns the closest match in a database and a score

import os       # for accessing files
import cv2      # computer vision, install with $pip install opencv-python

# can download fingerprint .bmp's from SOCOFing or FVC2002
stored_fingerprints = "directory of stored fingerprints goes here"
input_fingerprint = cv2.imread("filepath to input .bmp goes here")
input_fingerprint = cv2.resize(input_fingerprint, None, fx=2.5, fy=2.5)     # scale up image size, if needed

best_score = 0      # similarity score for the current best match
match_filename = None      # will be filled with best match
match_image = None
keypoints_input = None      # keypoints are conspicuous parts of an image
keypoints_stored = None
match_keypoints = None

for file in os.listdir(stored_fingerprints):     # loop through the stored fingerprints ('file' is just a string of the file's name)
    full_path = os.path.join(stored_fingerprints, file)     # path of directory + file
    fingerprint_image = cv2.imread(full_path)
    sift = cv2.SIFT_create()        # sift object that creates keypoints and descriptors from an image

    keypoints_1, descriptors_1 = sift.detectAndCompute(input_fingerprint, None)     # descriptors describe keypoints
    keypoints_2, descriptors_2 = sift.detectAndCompute(fingerprint_image, None)     # a 128-number vector describing what the local area around the keypoint looks like

    matches = cv2.FlannBasedMatcher({'algorithm': 1, 'trees': 10}, {}).knnMatch(descriptors_1, descriptors_2, k=2)
    # Fast Library for Approximate Nearest Neighbor
    # algorithm 1 is KD-tree with 10 trees. Searching for 'k' nearest neighbors

    matched_points = []
    for i, j in matches:        # 'i' is best match, 'j' is second best. If 'i' is a significantly better match than 'j', we are confident it is a correct match
        if i.distance < 0.7 * j.distance:       # Lowe's ratio test - ratio can be adjusted, lower is stricter. Distance is difference from input
            matched_points.append(i)

    keypoints = 0
    if len(keypoints_1) < len(keypoints_2):     # get the one with fewer keypoints
        keypoints = len(keypoints_1)
    else:
        keypoints = len(keypoints_2)

    if keypoints == 0:      # just avoiding divide by zero in case of improper input
        continue

    if len(matched_points) / keypoints * 100 > best_score:      # is the percent of matching keypoints the highest so far?
        best_score = len(matched_points) / keypoints * 100
        match_filename = file       # save this one as the best match
        match_image = fingerprint_image
        keypoints_input = keypoints_1
        keypoints_stored = keypoints_2
        match_keypoints = matched_points

# output for testing
print("Best match: " + match_filename)
print("Score: " + str(best_score))

# visualize the matching
result = cv2.drawMatches(input_fingerprint, keypoints_input, match_image, keypoints_stored, match_keypoints, None)
result = cv2.resize(result, None, fx=4, fy=4)     # resize the image
cv2.imshow("Result", result)
cv2.waitKey(0)      # wait forever until I press any key
cv2.destroyAllWindows()     # then close all windows
