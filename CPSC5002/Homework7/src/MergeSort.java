import java.util.Arrays;

public class MergeSort {

	public static void sort(int[] array) {
		mergeSort(array, 0, array.length - 1);		// pass to the helper method
	}
	
	private static void mergeSort(int[] array, int left, int right) {
		if (left < right) {		// will go until single elements
			int mid = (left + right) / 2;
			
			mergeSort(array, left, mid);		// recur with left half
			mergeSort(array, mid + 1, right);		// recur with right half
			merge(array, left, mid, right);		// merge them all back together
		}
	}
	
	private static void merge(int[] array, int left, int mid, int right) {
		int leftLength = mid - left + 1;
		int rightLength = right - mid;
		
		int[] leftArray = new int[leftLength];		// temporary arrays will be merged
		int[] rightArray = new int [rightLength];
		
		for (int i = 0; i < leftLength; i++) {		// fill the temporary arrays
			leftArray[i] = array[left + i];
		}
		for (int i = 0; i < rightLength; i++) {
			rightArray[i] = array[mid + 1 + i];
		}
		
		// visualizing the merging
		//System.out.println("Merging " + Arrays.toString(leftArray) + " with " + Arrays.toString(rightArray));
		
		int leftIndex = 0, rightIndex = 0, currentIndex = left;
		
		while (leftIndex < leftLength && rightIndex < rightLength) {
			if (leftArray[leftIndex] <= rightArray[rightIndex]) {		// if left is lesser, it goes first
				array[currentIndex] = leftArray[leftIndex];
				currentIndex++;
				leftIndex++;
			}
			else {
				array[currentIndex] = rightArray[rightIndex];
				currentIndex++;
				rightIndex++;
			}
		}
		
		while (leftIndex < leftLength) {		// unequal sizes may cause leftovers, move them over now
			array[currentIndex] = leftArray[leftIndex];
			currentIndex++;
			leftIndex++;
		}
		while (rightIndex < rightLength) {
			array[currentIndex] = rightArray[rightIndex];
			currentIndex++;
			rightIndex++;
		}
	}
}
