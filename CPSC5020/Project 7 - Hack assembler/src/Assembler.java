import java.io.*;
import java.util.ArrayList;

public class Assembler {

	public static void main(String[] args) {
		
		String inputFile = "src/Add.asm";		// add the relevant file path here
		String outputFile = "src/Add.hack";
		ArrayList<String> instructions = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {		// starts a buffered reader
			String line;
			while ((line = reader.readLine()) != null) {		// as long as there's another line to be read
				instructions.add(line);
			}
			
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
		
		firstPass(instructions);		// catches all loops
		secondPass(instructions);		// catches all variables
		
		ArrayList<String> result = new ArrayList<>();
		for (String line : instructions) {
			if (line.charAt(0) != '/' && line.charAt(0) != '\n' && line.charAt(0) != '(') {		// doesn't send white spaces
				result.add(Parser.parse(line));		// parses, translates, and adds it to the result
			}
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
			for (String line : result) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("error writing to output file");
		}
	}
	
	static void firstPass(ArrayList<String> instructions) {		// scan for ( while counting lines
		
		int lineCounter = 0;
		for (String i : instructions) {
			if (i.charAt(0) != '/' && i.charAt(0) != '(') {		// doesn't count white spaces
				lineCounter++;
			}
			if (i.charAt(0) == '(') {		// if the line starts with (...
				String loopName = i.substring(1, i.length() - 1);		// excludes the first and last characters (the parentheses)
				SymbolTable.addLoop(loopName, lineCounter + 1);		// adds the loop's name with it's following instruction line
			}
		}
	}
	
	static void secondPass(ArrayList<String> instructions) {		// scan for @ followed by not a number
		
		for (String i : instructions) {		// for each line
			if (i.charAt(0) == '@') {		// if that line starts with @
				for (int j = 0; j < 10; j++) {
					if (i.charAt(1) != ('0' + j)) {		// and if, following @, there's not chars 0-9
						String variableName = i.substring(1);		// makes a new string starting at index 1
						SymbolTable.addVariable(variableName);		// adds the variable to the symbol table
					}
				}
				
			}
		}
	}
}
