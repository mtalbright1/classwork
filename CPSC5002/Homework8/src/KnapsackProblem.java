import java.util.HashMap;

public class KnapsackProblem {

	public static void main(String[] args) {
		int[] values = {60, 100, 120};
		int[] weights = {10, 20, 30};
		int capacity = 50;
		
		knapsack(values, weights, capacity);
	}
	
	public static void knapsack(int[] values, int[] weights, int capacity) {
		
		int n = values.length;
		int[][] knapsack = new int[n + 1][capacity+1];		// a value for every possible weight
		
		for (int item = 1; item <= n; item++) {
			for (int weight = 0; weight <= capacity; weight++) {		// what's the most value we can fit at this weight
				
				knapsack[item][weight] = knapsack[item - 1][weight];		// consider the previous item when choosing the next one
				
				if (weights[item - 1] <= weight) {			// as long as item's weight is less than the weight we're working on
					int currentValue = values[item - 1] + knapsack[item - 1][weight - weights[item - 1]];		// the value of this item plus the value of items at the capacity minus this item's weight
					if (currentValue > knapsack[item][weight]) {
						knapsack[item][weight] = currentValue;
					}
				}
			}
		}
		
		System.out.println("Maximum value: " + knapsack[n][capacity]);
		System.out.print("Selected items: ");
		int c = capacity;
		for (int item = n; item >= 1; item--) {		// loop back through the knapsack array to find what made that value
			if (knapsack[item][c] != knapsack[item -1][c]) {		// no repeated items
				System.out.println("Item " + item + " (Value: " + values[item -1] + ", Weight: " + weights[item - 1] + ")");
				c -= weights[item - 1];
			}
		}
	}
}
