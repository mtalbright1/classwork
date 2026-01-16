import java.util.ArrayList;
import java.util.List;

public class BottomUpParser {

	static List<String> tokens;
	static List<Integer> characterTracker;		// this records the position of each token in the input for the error message's reference
	static int currentToken;
	static boolean acceptState;
	
	public static void main(String[] args) {
		
		String[] tests = { "id", "(id)", "(id)(id)", ")id", "(id, id)", "id(" };		// three ACCEPTs followed by three REJECTs
		
		for (String i : tests) {
			System.out.println("Input: " + i);
			parse(i);		// prints ACCEPT or REJECT according to grammar rules
			System.out.println();
		}
	}
	
	static void parse(String input) {
		acceptState = true;
		tokens = tokenize(input);		// get the token list
		currentToken = 0;
		
		if (acceptState == false) {		// there was a problem in the tokenizer, a REJECT was already printed, don't continue
			return;
		}
		
		bottomUp();
	}
	
	static List<String> tokenize(String input) {		// breaks the input string into a list of tokens
		 List<String> result = new ArrayList<>();
		 characterTracker = new ArrayList<>();
		 int index = 0;
		 
		 while (index < input.length()) {
			 if (input.startsWith("id", index)) {
				 result.add("id");			// if "id" is found, add an "id" token to the token list
				 characterTracker.add(index);		// wherever the index currently is, add that number to the matching index of the result
				 index += 2;		// increment the index by 2 to now skip over "id"
			 }
			 else if (input.charAt(index) == '(') {
				 result.add("(");
				 characterTracker.add(index);
				 index++;
			 }
			 else if (input.charAt(index) == ')') {
				 result.add(")");
				 characterTracker.add(index);
				 index++;
			 }
			 else {
			     System.out.println("REJECT at position " + (index + 1) + " unexpected character '" + input.charAt(index) + "'");
			     acceptState = false;
			     
			     result.clear();		// just don't run it, clear everything, and send back $
			     result.add("$");
			     characterTracker.clear();
			     characterTracker.add(index);
			     return result;
			 }
		 }
		 
		 result.add("$");		// add the end cap
		 characterTracker.add(index);
		 return result;
	}

	static void bottomUp() {
		List<String> stack = new ArrayList<>();
		
		while (true) {		// loop until the input is done
			String lookahead = tokens.get(currentToken);
			boolean reduce = true;
			
			if (lookahead.equals("$")) {
				break;
			}
			
			while (reduce == true) {			// keep running the tryReduce method until it returns false, meaning it couldn't reduce further
				reduce = tryReduce(stack);
			}
			
			stack.add(lookahead);		// shift onto stack
			currentToken++;
		}
		
		boolean reduce = true;
		while (reduce == true) {			// adding the $ to the stack at the end of the previous loop enables the final reduction
			reduce = tryReduce(stack);
		}
		
		if (stack.size() == 1 && stack.get(0).equals("S") && acceptState == true) {			// stack has been reduced to S
            System.out.println("ACCEPT");
        }
		
		for (int i = 0; i < stack.size(); i++) {		// loop through stack looking for anything that couldn't be reduced
            if (!stack.get(i).equals("S")) {
                System.out.println("REJECT at position " + characterTracker.get(i + 1) + " unexpected token '" + stack.get(i) + "'");
                return;
            }
        }
	}
	
	static boolean tryReduce (List<String> stack) {
		int n = stack.size();		// this is just for convenience

        if (n >= 1 && stack.get(n - 1).equals("id")) {			// T -> id
            stack.remove(n - 1);
            stack.add("T");
            return true;
        }

        if (n >= 3 && stack.get(n - 3).equals("(") && stack.get(n - 2).equals("S") && stack.get(n - 1).equals(")")) {			// T -> (S)
            stack.remove(n - 1);
            stack.remove(n - 2);
            stack.remove(n - 3);
            stack.add("T");
            return true;
        }

        if (n >= 2 && stack.get(n - 2).equals("T") && stack.get(n - 1).equals("S")) {        // S -> T S
            stack.remove(n - 1);
            stack.remove(n - 2);
            stack.add("S");
            return true;
        }

        if (n >= 1 && stack.get(n - 1).equals("T")) {	    // S -> T
            stack.remove(n - 1);
            stack.add("S");
            return true;
        }
        
        if (n >= 2 && stack.get(n - 2).equals("S") && stack.get(n - 1).equals("S")) {        // S -> S S
            stack.remove(n - 1);
            stack.remove(n - 2);
            stack.add("S");
            return true;
        }

        return false;			// can't be reduced
    }
}
