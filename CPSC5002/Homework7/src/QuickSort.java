import java.util.Arrays;

public class QuickSort {

	private static int recursionCount;
	
	public static void sort(int[] array) {
		recursionCount = 0;
		quickSort(array, 0, array.length - 1);		// pass to helper method
		//System.out.println("Recusion count: " + recursionCount);
	}
	
	private static void quickSort(int[] array, int low, int high) {
		recursionCount++;
		
		if (low < high) {
			int pivotIndex = partition(array, low, high);
			quickSort(array, low, pivotIndex - 1);		// sort the left half
			quickSort(array, pivotIndex + 1, high);		// sort the right half
		}
	}
	
	private static int partition(int[] array, int low, int high) {
		int pivot = array[high];		// Lomuto scheme
		int lowerBounds = low - 1;
		
		//System.out.println("Partitioning " + Arrays.toString(Arrays.copyOfRange(array, low, high + 1)) + " with pivot " + pivot);
		
		for (int i = low; i < high; i++) {
			if (array[i] <= pivot) {
				lowerBounds++;
				int temp = array[lowerBounds];
				array[lowerBounds] = array[i];
				array[i] = temp;
			}
		}
		
		int temp = array[lowerBounds + 1];
		array[lowerBounds + 1] = array[high];
		array[high] = temp;
		
		return lowerBounds + 1;
	}
}
