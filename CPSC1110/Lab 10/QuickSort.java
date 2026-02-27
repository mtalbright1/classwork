import java.util.Arrays;
import java.util.Scanner;

public class QuickSort {

	public static void main(String[] args)
	   {  
	      Scanner in = new Scanner(System.in);
	      System.out.print("First array length: ");
	      int firstLength = in.nextInt();
	      System.out.print("Number of arrays: ");
	      int numberOfArrays = in.nextInt();

	      StopWatch timer = new StopWatch();      
	      
	      for (int k = 1; k <= numberOfArrays; k++)
	      {
	         int n = k * firstLength;

	         // Construct random array
	   
	         int[] a = ArrayUtil.randomIntArray(n, 100);
	      
	         // Use stop watch to time selection sort
	      
	         timer.start();
	         Arrays.sort(a);
	         timer.stop();
	         System.out.println("Quick Sort:");
	         System.out.printf("Array length:%8d Elapsed milliseconds:%8d%n",
	            n, timer.getElapsedTime());
	         timer.reset();
	      }
	      in.close();
	   }
	
}
