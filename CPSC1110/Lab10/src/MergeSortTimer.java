import java.util.Scanner;

public class MergeSortTimer {  
	
   public static void main(String[] args) {  
      Scanner in = new Scanner(System.in);
      
      System.out.print("First array length: ");
      int firstLength = in.nextInt();
      System.out.print("Number of arrays: ");
      int numberOfArrays = in.nextInt();

      StopWatch timer = new StopWatch();

      for (int i = 1; i <= numberOfArrays; i++) {		// each array is i times larger
         int n = i * firstLength;
         int[] a = ArrayUtil.randomIntArray(n, 100);		// Construct random array
         
         timer.start();		// Use stop watch to time merge sort
         MergeSorter.sort(a);
         timer.stop();
         
         System.out.printf("Length:%8d Elapsed milliseconds:%8d%n", n, timer.getElapsedTime());
         timer.reset();
      }
      in.close();
   }
}

   


