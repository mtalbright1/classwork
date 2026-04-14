
public class SelectionSorter {

   public static void sort(int[] array) {  
      for (int i = 0; i < array.length - 1; i++) {  
         int minPos = minimumPosition(array, i);
         ArrayUtil.swap(array, minPos, i);
      }
   }

   private static int minimumPosition(int[] array, int currentPosition) {  
      int minPos = currentPosition;
      for (int i = currentPosition + 1; i < array.length; i++) {
         if (array[i] < array[minPos]) {
        	 minPos = i;
         }
      }
      return minPos;
   }
}

