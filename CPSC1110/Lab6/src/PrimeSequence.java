
public class PrimeSequence implements Sequence {
    private int n;
    private boolean isPrime;
    
    public PrimeSequence() {
    	n = 0;
    }
    
    public int next() {
    	while(true) {
    		isPrime = true;
    		n++;
    		for (int i = 2; i <= n/2; i++) {		// skip 1, only needs to go up to half n
    			if (n % i == 0) {
    				isPrime = false;
    				break;							// not a prime, exit for loop
    			}
    		}
    		if (isPrime == true) {
    			return n;
    		}
    	}
    }
}