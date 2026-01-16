
public class Question3 {

		public static void main (String args[]) {
			foo(5, 'A', 'B', 'C');
		}
		
		public static void foo(int n, char A, char B, char C) {
			
			if (n <= 1) {
				return; 		// unit operation
			}
			
			foo(n - 2, A, C, B);
			
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					System.out.println("n= " + n); 			// unit operation
				}
			}
			
			foo(n - 2, B, A, C);
		}
}
