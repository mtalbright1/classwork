import java.util.Arrays;
import java.util.Random;


public class ArrayMethods {

	//***NOTE that these methods will change the array itself


	//part a, fill in this method
	public static void swapFirstAndLast(int[] values) {
		// save the first element to a temp var
		int temp = values[0];
		//move the last element to the first position
		values[0] = values[values.length-1];
		// now put the saved first element into the last position
		values[values.length-1] = temp;


	}

	//part b, fill in this method
	public static void shiftRight(int[] values) {
		int temp = values[values.length-1];
		for (int i = values.length-1; i > 0; i--) {
			values[i] = values[i-1];
		}
		values[0] = temp;
	}

	//part c, set all even elements to 0.
	public static void setEvensToZero(int[] values) {
		for (int i = 0; i < values.length; i++) {
			if (values[i] % 2 == 0) {
				values[i] = 0;
			}
		}
	}

	//part d, replace each element except the first and last by larger of two 
	//around it
	public static int[] largerOfAdjacents(int[] values) {
		int[] newArray = new int[values.length];		// create a new array
		newArray[0] = values[0];
		newArray[newArray.length-1] = values[values.length-1];		// add the first and last elements to the new array
		for (int i = 1; i < values.length-1; i++) {		// skip the first and last elements
			if (values[i-1] > values[i+1]) {
				newArray[i] = values[i-1];
			}
			else {
				newArray[i] = values[i+1];
			}
		}
		return newArray;
	}

	//part e, remove middle el if odd length, else remove middle two els.
	public static int[] removeMiddle(int[] values) {
		int size = values.length;
		if (size % 2 != 0) {		// if odd
			int middle = size / 2;
			int[] newArray = new int[size-1];		// make an array that's 1 smaller
			for (int i = 0; i < middle; i++) {		// copy the left half
				newArray[i] = values[i];
			}
			for (int i = middle + 1; i < size; i++) {		// copy the right half
				newArray[i-1] = values[i];
			}
			return newArray;
		}
		
		else {		// else even
			int middle1 = size/2 - 1;
			int middle2 = size/2;
			int[] newArray = new int[size - 2];		// make an array that's 2 smaller
			for (int i = 0; i < middle1; i++) {		// copy left half
				newArray[i] = values[i];
			}
			for (int i = middle2 + 1; i < size; i++) {		// copy right half
				newArray[i-2] = values[i];
			}
			return newArray;
		}
	}

	//part f - move all evens to front
	public static void moveEvensToFront(int[] values) {
		int tracker = 0;
		int temp;
		for (int i = 0; i < values.length; i++) {		
			if (values[i] % 2 == 0) {
				temp = values[tracker];
				values[tracker] = values[i];
				values[i] = temp;
				tracker++;
			}
		}
	}

	//part g - return second largest element in array
	public static int ret2ndLargest(int[] values) {
		int largest = 0;
		int secondLargest = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] > largest) {
				secondLargest = largest;
				largest = values[i];
			}
		}
		return secondLargest;
	}

	//part H - returns true if array is sorted in increasing order 
	public static boolean isSorted(int[] values) {
		for (int i = 0; i < values.length - 1; i++) {		// no need to go to the end
			if (values[i] > values[i+1]) {
				return false;
			}
		}
		return true; 
	}

	//PART I - return true if array contains 2 adjacent duplicate values
	public static boolean hasAdjDuplicates(int[] values) {
		for (int i = 0; i < values.length - 1; i++) {		// no need to go to the end
			if (values[i] == values[i+1]) {
				return true;
			}
		}
		return false;
	}

	//PART J - return true if array contains 2 duplicate values
	//duplicates need not be adjacent to return true
	public static boolean hasDuplicates(int[] values) {
		for (int i = 0; i < values.length - 1; i++) {
			for (int j = i+1; j < values.length; j++) {
				if (values[i] == values[j]) {
					return true;
				}
			}
		}
		return false; 
	}
}
