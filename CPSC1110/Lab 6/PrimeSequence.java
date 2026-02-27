
public class PrimeSequence implements Sequence {
    private int n = 2;
    
    public int next() {
    	
    	if (n == 2) {
    		return n++;
    	}
    	
    	n++;
    	
    	while (true) {
    		boolean isPrime = true;
    		for (int i = 2; i < n; i++) {
    			if (n % i == 0) {
    				isPrime = false;
    				break;
    			}
    		}
    		if (isPrime == true) {
    			return n;
    		}
    		n++;
    	}
    }
}