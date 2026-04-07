import java.util.Scanner;

 // Greek's method to approximate the square root of a given number.

public class SquareRootComputer {
	public static void main(String[] args) {
		// this is your tester
		// read a value from the user and print the results 
		// along with expected value
		
		Scanner input = new Scanner(System.in);
	    System.out.print("Enter a positive number: ");
	    
	    double number = Math.abs(input.nextDouble());		// forces positive input
	    double result = squareRoot(number);					// runs the method
	    
	    System.out.println("Result: " + result);
	    System.out.println("Expected: " + Math.sqrt(number));
	    
	    input.close();
	    }

	public static double squareRoot(double number) {
		// from this method come up with a guess
		// and then make the call to the recursive method
		// squareRootGuesser()
		double guess = number / 2.0;		// start guessing a half to be faster
		return squareRootGuess(number, guess);
	}

	private static double squareRootGuess(double number, double guess) {
		// recursive method to compute the square root of x
		// you will need to have a base case and a recursive case
		// in this method
		double precision = 0.0001;		// how convergent the guesses become
		double nextGuess = 0.5 * (guess + (number / guess));		// the formula
		
		if (Math.abs(nextGuess - guess) < precision)
			return nextGuess;
		else
			return squareRootGuess (number, nextGuess);
	}
}
