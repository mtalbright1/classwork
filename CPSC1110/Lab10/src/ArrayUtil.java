import java.util.Random;

public class ArrayUtil { 
   private static Random generator = new Random();

   public static int[] randomIntArray(int length, int range) {  		// returns an array of random ints
      int[] array = new int[length];      
      for (int i = 0; i < array.length; i++) {
         array[i] = generator.nextInt(range);
      }
      
      return array;
   }

   public static void swap(int[] array, int i, int j) {		// swaps elements
      int temp = array[i];
      array[i] = array[j];
      array[j] = temp;
   }
}
      


