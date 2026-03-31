import java.util.Arrays;

public class BinarySearch {

	public int iterativeBinarySearch(int[] array, int target) {
		int low = 0;
		int high = array.length - 1;
		int comparisonCount = 0;
		
		while (low <= high) {
			int mid = (low + high) / 2;
			comparisonCount++;
			
			if (target == array[mid]) {
				System.out.println("Comparison count: " + comparisonCount);
				return mid;		// target found
			}
			
			if (target < array[mid]) {
				high = mid - 1;		// shrink the high to just under the mid
			}
			
			if (target > array[mid]) {
				low = mid + 1;		// raise the low to just above the mid
			}
		}
		
		System.out.println("Comparison count: " + comparisonCount);
		return -1;		// passed through the loop without returning - target not found
	}
	
	public int iterativeBinarySearchRotated(int[] array, int target) {
		int low = 0;
		int high = array.length - 1;
		int comparisonCount = 0;
		
		while (low <= high) {
			int mid = (low + high) / 2;
			comparisonCount++;
			
			if (target == array[mid]) {
				System.out.println("Comparison count: " + comparisonCount);
				return mid;		// target found
			}
			
			if (array[low] <= array[mid]) {		// if left half is sorted
				if (target >= array[low] && target < array[mid]) {
					high = mid - 1;		// shrink the high to just under the mid
				}
				else {
					low = mid + 1;
				}
			}
				
			else {			// if right half is sorted
				if (target > array[mid] && target <= array[high]) {
					low = mid + 1;		// raise the low to just above the mid
				}
				else {
					high = mid - 1;
				}
			}
			
		}
		
		System.out.println("Comparison count: " + comparisonCount);
		return -1;		// passed through the loop without returning - target not found
	}
	
	public int recursiveBinarySearch(int[] array, int target) {
		int recursionDepth = 0;
		return recursiveBinarySearch(array, target, recursionDepth);
	}
	
	public int recursiveBinarySearch(int[] array, int target, int recursionDepth) {		// overloaded to track recursion depth
		recursionDepth++;
		
		if (array.length == 0) {
			System.out.println("Recursion depth: " + recursionDepth);
			return -1;			// base case, target not found
		}
		
		int mid = array.length / 2;		// integer division rounds down
		
		if (target == array[mid]) {
			System.out.println("Recursion depth: " + recursionDepth);
			return mid;			// base case, target found
		}
		
		if (target < array[mid]) {
			int[] leftHalf = Arrays.copyOfRange(array, 0, mid);
			return recursiveBinarySearch(leftHalf, target, recursionDepth);			// recur using only the left half
		}
		
		else {
			int[] rightHalf = Arrays.copyOfRange(array, mid + 1, array.length);			// recur using only the right half
			int result = recursiveBinarySearch(rightHalf, target, recursionDepth);
			if (result == -1) {
				return -1;
			}
			else {
				return result + mid + 1;		// add the left side back to get the correct index for the original array
			}
		}
	}
	
	public int recursiveBinarySearchRotated(int[] array, int target) {
		int recursionDepth = 0;
		return recursiveBinarySearchRotated(array, target, 0, array.length - 1, recursionDepth);
	}
	
	public int recursiveBinarySearchRotated(int[] array, int target, int low, int high, int recursionDepth) {		// overloaded to track recursion depth
		recursionDepth++;
		
		if (low > high) {
			System.out.println("Recursion depth: " + recursionDepth);
			return -1;			// base case, target not found
		}
		
		int mid = (low + high) / 2;		// integer division rounds down
		
		if (target == array[mid]) {
			System.out.println("Recursion depth: " + recursionDepth);
			return mid;			// base case, target found
		}
		
		if (array[low] <= array[mid]) {		// if the left half is sorted
			if (target >= array[low] && target < array[mid]) {
				return recursiveBinarySearchRotated(array, target, low, mid - 1, recursionDepth);
			}
			else {
				return recursiveBinarySearchRotated(array, target, mid + 1, high, recursionDepth);
			}
		}
		
		else {
			if (target > array[mid] && target <= array[high]) {
				return recursiveBinarySearchRotated(array, target, mid + 1, high, recursionDepth);
			}
			else {
				return recursiveBinarySearchRotated(array, target, low, mid - 1, recursionDepth);
			}
		}
	}
	
	public static void main(String[] args) {
		int[] arrayEven = {0, 1, 2, 3};
		int[] arrayOdd = {0, 1, 2, 3, 4};
		int[] arrayRotated = {2, 3, 4, 0, 1};
		int targetA = 1;		// positive left half
		int targetB = 3;		// positive right half
		int targetC = 5;		// negative
		
		BinarySearch demo = new BinarySearch();
		
		System.out.println("Test 1: (expected 1) " + demo.iterativeBinarySearch(arrayEven, targetA));
		System.out.println("");
		System.out.println("Test 2: (expected 3) " + demo.iterativeBinarySearch(arrayOdd, targetB));
		System.out.println("");
		System.out.println("Test 3: (expected -1) " + demo.iterativeBinarySearch(arrayOdd, targetC));
		System.out.println("");
		
		System.out.println("Test 4: (expected 1) " + demo.recursiveBinarySearch(arrayEven, targetA));
		System.out.println("");
		System.out.println("Test 5: (expected 3) " + demo.recursiveBinarySearch(arrayOdd, targetB));
		System.out.println("");
		System.out.println("Test 6: (expected -1) " + demo.recursiveBinarySearch(arrayOdd, targetC));
		System.out.println("");
		
		System.out.println("Test 7: (expected 4) " + demo.iterativeBinarySearchRotated(arrayRotated, targetA));
		System.out.println("");
		System.out.println("Test 8: (expected 1) " + demo.iterativeBinarySearchRotated(arrayRotated, targetB));
		System.out.println("");
		System.out.println("Test 9: (expected -1) " + demo.iterativeBinarySearchRotated(arrayRotated, targetC));
		System.out.println("");
		
		System.out.println("Test 10: (expected 4) " + demo.recursiveBinarySearchRotated(arrayRotated, targetA));
		System.out.println("");
		System.out.println("Test 11: (expected 1) " + demo.recursiveBinarySearchRotated(arrayRotated, targetB));
		System.out.println("");
		System.out.println("Test 12: (expected -1) " + demo.recursiveBinarySearchRotated(arrayRotated, targetC));
		System.out.println("");
	}
}
