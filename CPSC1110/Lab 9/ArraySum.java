
public class ArraySum {

	public static void main (String[] args) {
		
		int[] array = {1, 2, 3, 4, 5};		// Input here
		int sum = sum(array, 0);			// Starts the loop at the first position
		System.out.println("The sum is: " + sum);
	}
	
	static int sum (int[] array, int position) {
		
		if (array.length == position)		// loops until past the final position
			return 0;
		else
			return array[position] + sum(array, position +1);		// adds this position to the next one
	}
}
