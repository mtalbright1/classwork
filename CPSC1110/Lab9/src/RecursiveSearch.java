import java.util.Arrays;

public class RecursiveSearch {

	public static void main(String[] args) {
		int[] array = {0, 1, 2, 3, 4};
		System.out.println("Array: " + Arrays.toString(array));
		
		int result = findIndex(array, 3, 0);
		System.out.println("Searching array for 3 (expected 3): " + result);
		
		result = findIndex(array, 0, 0);
		System.out.println("Searching array for 0 (expected 0): " + result);
		
		result = findIndex(array, 5, 0);
		System.out.println("Searching array for 5 (expected -1): " + result);
	}
	
	public static int findIndex(int[] arr, int target, int index) {
		if (index >= arr.length) {
			return -1;		// base case, target not found
		}
		else if (arr[index] == target) {
			return index;		// base case, target found
		}
		else {
			return findIndex(arr, target, ++index);		// recursive case, recur with index incremented
		}
	}
}
