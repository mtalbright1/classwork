

public class poly {

	static double poly(double x, int n, double[] c) {
		double result = c[n];		// start at c[n] because each step multiplies by x, c[n] will get the most x's
		for (int i = n-1; i >= 0; i--) {		// loop backwards
			result = result * x + c[i];		// go through the factored out list, like (5x + 2)x + ...
		}
		return result;
	}
	
	public static void main (String[] args) {
		// example: 5x^2 + 4x + 3, where x is 4, = 99
		double x = 4;
		int n = 2;
		double[] c = {3, 4, 5};			// ordered lesser power to greater
		
		System.out.print(poly(x, n, c));
	}
}
