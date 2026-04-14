
public class MergeSorter {

	public static void sort(int[] array) {  
		if (array.length <= 1) {
			return;
		}
		int[] left = new int[array.length / 2];
		int[] right = new int[array.length - left.length];
   
		for (int i = 0; i < left.length; i++) {		// Copy the first half of a into first, the second half into second
			left[i] = array[i];
		}
		for (int i = 0; i < right.length; i++) {
			right[i] = array[left.length + i]; 
		}
		
		sort(left);
		sort(right);
		merge(left, right, array);
	}

	private static void merge(int[] left, int[] right, int[] array) {  
		int iLeft = 0; 		// Next element to consider in the first array
		int iRight = 0; 		// Next element to consider in the second array
		int j = 0; 		// Next open position in a

		// As long as neither iFirst nor iSecond is past the end, move the smaller element into a
		while (iLeft < left.length && iRight < right.length) {  
			if (left[iLeft] < right[iRight]) {  
				array[j] = left[iLeft];
				iLeft++;
			}
			else {  
				array[j] = right[iRight];
				iRight++;
			}
			j++;
		}

		// Note that only one of the two loops below copies entries
		// Copy any remaining entries of the first array
		while (iLeft < left.length) { 
			array[j] = left[iLeft]; 
			iLeft++; j++;
		}
		// Copy any remaining entries of the second half
		while (iRight < right.length) { 
			array[j] = right[iRight]; 
			iRight++; j++;
		}
	}
}


