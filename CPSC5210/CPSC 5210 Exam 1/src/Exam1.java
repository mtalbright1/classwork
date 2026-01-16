
public class Exam1 {

	public static void main(String[] args) {
		int n = 5;
		int sum = question1(n);
		System.out.println(sum);
	}

	static int question1 (int n) {
		int sum = 0;
		
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= i * i * i; j++) {
				if (j % i == 0) {
					for (int k = 1; k <= j; k++) {
						sum++;
					}
				}
			}
		}
		
		return sum;
	}
}
