
public class Parser {

	static String parse (String line) { 

		if (line.charAt(0) == '@') {		// if that line starts with @
			if (!Character.isDigit(line.charAt(1))) {		// and if, following @, there's not chars 0-9
				String variableSymbol = line.substring(1);		// take the variable that's after @
				int variableValue = SymbolTable.retrieve(variableSymbol);		// swap it for it's value
				Translator.translateACode(variableValue);		// translate that value to binary
			}
			else {		// is a digit
				int i = Integer.parseInt(line.substring(1));
				return Translator.translateACode(i);
			}
		}
		else {		// c-instruction: destination = computation; jump
			int equalsIndex = line.indexOf('=');		// the index of the =
			int semicolonIndex = line.indexOf(';');		// will be -1 if there is no ;
			String destination = "";
			String computation = "";
			String jump = "";
			
			destination = line.substring(0, equalsIndex);		// from the beginning up to but not including the =
			if (semicolonIndex != -1) {		// if there's a semicolon
				computation = line.substring(equalsIndex + 1, semicolonIndex);		// computation is from after = up to the ;
				jump = line.substring(semicolonIndex + 1, line.length());		// jump is after ; to the end
			}
			else {		// if no ;
				computation = line.substring(equalsIndex + 1, line.length());		// computation is after the = and to the end
			}
			
			return Translator.translateCCode(destination, computation, jump);
		}
		return "error parsing";		// error catching
	}
}

