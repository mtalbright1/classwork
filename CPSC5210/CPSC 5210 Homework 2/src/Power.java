
public class Power {

	public static void main(String[] args) {
		int n = 5;
		System.out.println(partA(n));
		System.out.println(partB(n));
		System.out.println(partC(n));
	}
	
	static int partA(int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= i; j++) {
				sum++;
				sum++;
			}
		}
		return sum;
	}

	static int partB(int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= 3 * i * i; j++)
				sum++;
			for (int k = 1; k <= 2000000; k++)
				sum++;
		}
		return sum;
	}
	
	static int partC(int n)	{
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= i * i; j++) {
				if (j % i == 0) {
					for (int k = 1; k <= j; k++)
						sum++;
				}
			}
		}
		return sum;
	}
	
	static int pow(int x, int n) {
		if (n == 0)
			return 1;		// special case x^0 = 1
		
		int half = pow(x, n / 2);		// save time by computing only half the exponent, then square it
		
		if (n % 2 == 0) 		// if n is even, square it for the answer
			return half * half;
		
		else 		// if odd, do the same with an extra x
			return x * half * half;
	}
}
