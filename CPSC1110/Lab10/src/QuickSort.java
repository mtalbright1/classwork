import java.util.Arrays;
import java.util.Scanner;

public class QuickSort {

	public static void main(String[] args) {  
	      Scanner in = new Scanner(System.in);
	      
	      System.out.print("First array length: ");
	      int firstLength = in.nextInt();
	      System.out.print("Number of arrays: ");
	      int numberOfArrays = in.nextInt();

	      StopWatch timer = new StopWatch();      
	      
	      for (int i = 1; i <= numberOfArrays; i++) {		// each array is i times larger
	         int n = i * firstLength;
	         int[] a = ArrayUtil.randomIntArray(n, 100);		 // Construct random array
	      
	         timer.start();		// Use stop watch to time selection sort
	         Arrays.sort(a);		// uses Java's built in sorter
	         timer.stop();
	         
	         System.out.println("Quick Sort:");
	         System.out.printf("Array length:%8d Elapsed milliseconds:%8d%n", n, timer.getElapsedTime());
	         timer.reset();
	      }
	      in.close();
	}
	
}
