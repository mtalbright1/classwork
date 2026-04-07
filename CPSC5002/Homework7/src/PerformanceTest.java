import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PerformanceTest {

	public static void main(String[] args) {
		int[] array1 = {4, 2, 1, 3, 5};
		
		System.out.println("Before bubble sort: " + Arrays.toString(array1));
		BubbleSort.sort(array1);
		System.out.println("After bubble sort: " + Arrays.toString(array1));
		System.out.println();
		
		int[] array2 = {4, 2, 1, 3, 5};
		
		System.out.println("Before merge sort: " + Arrays.toString(array2));
		MergeSort.sort(array2);
		System.out.println("After merge sort: " + Arrays.toString(array2));
		System.out.println();
		
		int[] array3 = {4, 2, 1, 3, 5};
		
		System.out.println("Before quick sort: " + Arrays.toString(array3));
		QuickSort.sort(array3);
		System.out.println("After quick sort: " + Arrays.toString(array3));
		System.out.println();
		
		int[] bubble100 = new int[100];
		int[] bubble1000 = new int[1000];
		int[] bubble10000 = new int[10000];
		
		int[] merge100 = new int[100];
		int[] merge1000 = new int[1000];
		int[] merge10000 = new int[10000];
		
		int[] quick100 = new int[100];
		int[] quick1000 = new int[1000];
		int[] quick10000 = new int[10000];
		
		ArrayList<int[]> arrays = new ArrayList<>();
		arrays.add(bubble100);
		arrays.add(bubble1000);
		arrays.add(bubble10000);
		arrays.add(merge100);
		arrays.add(merge1000);
		arrays.add(merge10000);
		arrays.add(quick100);
		arrays.add(quick1000);
		arrays.add(quick10000);
		
		Random rand = new Random();
		
		for (int[] i : arrays) {
			for (int j = 0; j < i.length; j++) {
				i[j] = rand.nextInt(100);
			}
		}
		
		long start, duration;
		
		System.out.println("Bubble sort runtimes:");
		start = System.nanoTime();
		BubbleSort.sort(bubble100);
		duration = (System.nanoTime() - start) / 1000;
		System.out.println("100: " + duration + " microseconds");
		start = System.nanoTime();
		BubbleSort.sort(bubble1000);
		duration = (System.nanoTime() - start) / 1000;
		System.out.println("1000: " + duration + " microseconds");
		start = System.nanoTime();
		BubbleSort.sort(bubble10000);
		duration = (System.nanoTime() - start) / 1000;
		System.out.println("10000: " + duration + " microseconds");
		System.out.println();
		
		System.out.println("Merge sort runtimes:");
		start = System.nanoTime();
		MergeSort.sort(merge100);
		duration = (System.nanoTime() - start) / 1000;
		System.out.println("100: " + duration + " microseconds");
		start = System.nanoTime();
		MergeSort.sort(merge1000);
		duration = (System.nanoTime() - start) / 1000;
		System.out.println("1000: " + duration + " microseconds");
		start = System.nanoTime();
		MergeSort.sort(merge10000);
		duration = (System.nanoTime() - start) / 1000;
		System.out.println("10000: " + duration + " microseconds");
		System.out.println();
		
		System.out.println("Quick sort runtimes:");
		start = System.nanoTime();
		QuickSort.sort(quick100);
		duration = (System.nanoTime() - start) / 1000;
		System.out.println("100: " + duration + " microseconds");
		start = System.nanoTime();
		QuickSort.sort(quick1000);
		duration = (System.nanoTime() - start) / 1000;
		System.out.println("1000: " + duration + " microseconds");
		start = System.nanoTime();
		QuickSort.sort(quick10000);
		duration = (System.nanoTime() - start) / 1000;
		System.out.println("10000: " + duration + " microseconds");
		System.out.println();
	}
}
