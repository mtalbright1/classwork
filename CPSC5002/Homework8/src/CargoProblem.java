
public class CargoProblem {

	public static void main(String[] args) {
		int [] profits = {150, 200, 300};
		int [] weights = {1, 3, 4};
		int [] volumes = {2, 3, 5};
		int maxWeight = 5;
		int maxVolume = 7;
		
		cargo(profits, weights, volumes, maxWeight, maxVolume);
	}
	
	public static void cargo(int[] profits, int[] weights, int[] volumes, int maxWeight, int maxVolume) {
		
		int n = profits.length;
		int[][][] manifest = new int[n + 1][maxWeight + 1][maxVolume + 1];		// add 1 to have an empty 0 and to avoid offset
		
		for (int item = 1; item <= n; item++) {		// item = 1 is the first item due to the new offset
			for (int weight = 0; weight <= maxWeight; weight++) {		// go through every weight to find maximum value there
				for (int volume = 0; volume <= maxVolume; volume++) {		// also every volume
					
					manifest[item][weight][volume] = manifest[item - 1][weight][volume];		// start with the weight and volume while excluding this item but including the previous ones
					
					if (weights[item - 1] <= weight && volumes[item - 1] <= volume) {		// if it fits (-1 because weights and volumes weren't offset like items)
						int currentValue = manifest[item-1][weight - weights[item-1]][volume - volumes[item-1]] + profits[item-1];
						// the current value of the manifest with this item is the value of this item plus the value of the rest of the manifest minus this item's space
						if (currentValue > manifest[item][weight][volume]) {
							manifest[item][weight][volume] = currentValue;
						}
					}
				}
			}
		}
		
		System.out.println("Optimal Cargo Load: ");
		
		int w = maxWeight;
		int v = maxVolume;
		int totalWeight = 0;
		int totalVolume = 0;
		
		for (int i = n; i >= 1; i--) {
			if (manifest[i][w][v] != manifest[i-1][w][v]) {
				System.out.println("Container " + i + "(Profit: " + profits[i-1] + ", Weight: " + weights[i-1] + ", Volume: " + volumes[i-1] + ")");
				totalWeight += weights[i-1];
				totalVolume += volumes[i-1];
				w -= weights[i-1];
				v -= volumes[i-1];
			}
		}
		System.out.println("Total Profit: " + manifest[n][maxWeight][maxVolume]);
		System.out.println("Total Weight: " + totalWeight + "/" + maxWeight);
		System.out.println("Total Volume: " + totalVolume + "/" + maxVolume);
	}
}