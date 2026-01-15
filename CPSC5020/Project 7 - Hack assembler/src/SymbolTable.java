import java.util.HashMap;

public class SymbolTable {
	
	static HashMap<String, Integer> symbolTable;
	
	SymbolTable() {		// initializes and fills with pre-defined symbols
		
		symbolTable = new HashMap<>();
		
		symbolTable.put("R0", 0);		// predefined symbols
		symbolTable.put("R1", 1);
		symbolTable.put("R2", 2);
		symbolTable.put("R3", 3);
		symbolTable.put("R4", 4);
		symbolTable.put("R5", 5);
		symbolTable.put("R6", 6);
		symbolTable.put("R7", 7);
		symbolTable.put("R8", 8);
		symbolTable.put("R9", 9);
		symbolTable.put("R10", 10);
		symbolTable.put("R11", 11);
		symbolTable.put("R12", 12);
		symbolTable.put("R13", 13);
		symbolTable.put("R14", 14);
		symbolTable.put("R15", 15);
		symbolTable.put("SCREEN", 16384);
		symbolTable.put("KBD", 24576);
		symbolTable.put("SP", 0);
		symbolTable.put("LCL", 1);
		symbolTable.put("ARG", 2);
		symbolTable.put("THIS", 3);
		symbolTable.put("THAT", 4);
	}
	
	static void addLoop (String symbol, int value) {
		if (!symbolTable.containsKey(symbol)) {
			symbolTable.put(symbol, value);
		}
	}
	
	static int registerCounter = 16;
	static void addVariable (String symbol) {		// the variable is the key and the value is the next available register
		if (!symbolTable.containsKey(symbol)) {		// only adds the variable if it's not already there
			symbolTable.put(symbol, registerCounter);
			registerCounter++;
		}
	}
	
	static int retrieve (String symbol) {
		return symbolTable.get(symbol);		// returns key's value
	}
}
