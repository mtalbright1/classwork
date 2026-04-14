import java.util.Scanner;

public class SelectionSortTimer {  
	
   public static void main(String[] args) {  
      Scanner in = new Scanner(System.in);
      
      System.out.print("First array length: ");
      int firstLength = in.nextInt();
      System.out.print("Number of arrays: ");
      int numberOfArrays = in.nextInt();

      StopWatch timer = new StopWatch();      // make a new StopWatch called timer
      
      for (int i = 1; i <= numberOfArrays; i++) {		// each array is i times larger
         int n = i * firstLength;
         int[] a = ArrayUtil.randomIntArray(n, 100);		// Construct random array
      
         timer.start();		// Use stop watch to time selection sort
         SelectionSorter.sort(a);
         timer.stop();
         System.out.println("Selection Sort:");
         System.out.printf("Array length:%8d Elapsed milliseconds:%8d%n", n, timer.getElapsedTime());
         timer.reset();
      }
      in.close();
   }
}


