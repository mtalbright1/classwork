
public class BubbleSort {

	public static void sort(int[] array) {
		int n = array.length;		// calculated once for efficiency
		int swapCount = 0;
		
		for (int i = 0; i < n; i++) {
			boolean swapped = false;
			
			for (int j = 0; j < n - 1; j++) {
				if (array[j] > array[j+1]) {
					int temp = array[j];		// swap
					array[j] = array[j+1];
					array[j+1] = temp;
					swapped = true;
					swapCount++;
				}
			}
			if (swapped == false) {
				//System.out.println("Number of swaps: " + swapCount);
				break;			// if no swapped occurred this i loop, we're done
			}
		}
	}
}
