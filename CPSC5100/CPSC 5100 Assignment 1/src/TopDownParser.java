import java.util.ArrayList;
import java.util.List;

public class TopDownParser {
	
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
		
		S();		// checks if valid for FIRST(S)
		
		if (tokens.get(currentToken).equals("$")) {		// successfully made it to the end
			if (acceptState == true) {			// acceptState is still true, so print ACCEPT. Otherwise, REJECT was already printed so do nothing
				System.out.println("ACCEPT");
			}
			
		}
		else if (acceptState == true) {			// a problem input
			int problemPosition = characterTracker.get(currentToken);
	        System.out.println("REJECT at position " + (problemPosition + 1) + " unexpected token '" + tokens.get(currentToken) + "'");
		}
		
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

	static void S() {
		if (tokens.get(currentToken).equals("id") || tokens.get(currentToken).equals("(")) {
			T();
			S();		// check T, then check S
		}
	}
	
	static void T() {
		if (tokens.get(currentToken).equals("id")) {
			currentToken++;		// it's good, move on to next token
			return;
		}
		else if (tokens.get(currentToken).equals("(")) {
			currentToken++;		// advance past ( to S
			S();
			if (!tokens.get(currentToken).equals(")")) {		// if there's not a ) after S
				acceptState = false;
				int problemPosition = characterTracker.get(currentToken);
				System.out.println("REJECT at position " + problemPosition + " expected ')'");
			}
			else {
				currentToken++;		// move past )
			}
		}
		else {		// error, id or ( expected but absent
			acceptState = false;
			int problemPosition = characterTracker.get(currentToken);
			System.out.println("REJECT at position " + problemPosition + " expected '(' or 'id'");
		}
	}
}
