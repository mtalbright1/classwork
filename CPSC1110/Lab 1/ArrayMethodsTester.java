import java.util.Arrays;
import java.util.Random;


public class ArrayMethodsTester {

	//helper method to print an array
	public static void printArray(int[] values) {
		System.out.println(Arrays.toString(values));
	}
	public static void main(String[] args) {
		
		int[] a = {0, 1, 1, 1, 1, 1, 1, 1, 1, 2};
		int[] b = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
		int[] c = {2, 1, 2, 1, 2, 1, 2, 1, 2, 1};
		int[] d = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		int[] e = {0, 0, 0, 0, 1, 1, 0, 0, 0, 0};
		int[] f = {1, 1, 1, 2, 1, 2, 1, 2, 2, 2};
		int[] g = {0, 1, 2, 1, 0, 1, 0, 1, 0, 1};
		int[] h1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		int[] h2 = {0, 2, 1, 3, 4, 5, 6, 7, 8, 9};
		int[] i1 = {0, 1, 1, 0, 0, 0, 0, 0, 0, 0};
		int[] i2 = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
		int[] j1 = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
		int[] j2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

		System.out.println("Before swapFirstAndLast:");
		printArray(a);
		ArrayMethods.swapFirstAndLast(a);
		System.out.println("After swapFirstAndLast:");
		printArray(a);
		System.out.println();

		System.out.println("Before shiftRight:");
		printArray(b);
		ArrayMethods.shiftRight(b);
		System.out.println("After shiftRight:");
		printArray(b);
		System.out.println();
		
		System.out.println("Before setEvensToZero:");
		printArray(c);
		ArrayMethods.setEvensToZero(c);
		System.out.println("After setEvensToZero:");
		printArray(c);
		System.out.println();
		
		System.out.println("Before largerOfAdjacents:");
		printArray(d);
		System.out.println("After largerOfAdjacents:");
		
		printArray(ArrayMethods.largerOfAdjacents(d));
		System.out.println();
		
		System.out.println("Before removeMiddle:");
		printArray(e);
		System.out.println("After removeMiddle:");
		printArray(ArrayMethods.removeMiddle(e));
		System.out.println();
		
		System.out.println("Before moveEvensToFront:");
		printArray(f);
		System.out.println("After moveEvensToFront:");
		ArrayMethods.moveEvensToFront(f);
		printArray(f);
		System.out.println();
		
		System.out.println("Before ret2ndLargest:");
		printArray(g);
		System.out.println(ArrayMethods.ret2ndLargest(g));
		System.out.println();
		
		System.out.println("Before isSorted true:");
		printArray(h1);
		System.out.println(ArrayMethods.isSorted(h1));
		System.out.println();
		System.out.println("Before isSorted false:");
		printArray(h2);
		System.out.println(ArrayMethods.isSorted(h2));
		System.out.println();
		
		System.out.println("Before hasAdjDuplicates true:");
		printArray(i1);
		System.out.println(ArrayMethods.hasAdjDuplicates(i1));
		System.out.println();
		System.out.println("Before hasAdjDuplicates false:");
		printArray(i2);
		System.out.println(ArrayMethods.hasAdjDuplicates(i2));
		System.out.println();
		
		System.out.println("Before hasDuplicates true:");
		printArray(j1);
		System.out.println(ArrayMethods.hasDuplicates(j1));
		System.out.println();
		System.out.println("Before hasDuplicates false:");
		printArray(j2);
		System.out.println(ArrayMethods.hasDuplicates(j2));
		System.out.println();
		
	}
}
