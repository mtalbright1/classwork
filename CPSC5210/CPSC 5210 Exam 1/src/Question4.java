import java.util.Arrays;

public class Question4 {

	public static void main(String args[]) {
		String[] students = {"XYZ123", "ABC123", "XYZ456", "ABC456"};
		
		System.out.println("Before sorting" + Arrays.toString(students));
		mergesort(students);
		System.out.println("After sorting" + Arrays.toString(students));
	}
	
	static void mergesort(String[] students) {
		
		if (students.length <= 1) {		// base case, done splitting
			return;
		}
		
		int mid = students.length / 2;		// make new arrays of half the size
		String[] left = new String[mid];
		String[] right = new String[students.length - mid];
		
		System.arraycopy(students, 0, left, 0, mid);		// source, start, destination, start, size
		System.arraycopy(students, mid, right, 0, students.length - mid);
		
		mergesort(left);		// recursively continue to divide the array
		mergesort(right);
		
		merge(left, right, students);
	}
	
	static void merge(String[] left, String[] right, String[] students) {
		int leftIndex = 0, rightIndex = 0, mergedIndex = 0;
        
        while (leftIndex < left.length && rightIndex < right.length) {		// Compare elements and merge back together
            if (left[leftIndex].compareTo(right[rightIndex]) <= 0) {		// if left comes first
                students[mergedIndex++] = left[leftIndex++];
            }
            else {
                students[mergedIndex++] = right[rightIndex++];
            }
        }
        
        while (leftIndex < left.length) {			// catch any remaining elements
            students[mergedIndex++] = left[leftIndex++];
        }
        while (rightIndex < right.length) {
        	students[mergedIndex++] = right[rightIndex++];
        }
            
	}
	
}
