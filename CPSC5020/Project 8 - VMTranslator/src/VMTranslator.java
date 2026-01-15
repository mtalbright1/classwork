import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class VMTranslator {

	public static void main (String[] args) {
		
		String inputFile = "BasicTLoop.vm";		// add the relevant file path here
		String outputFile = "BasicLoop.asm";
		ArrayList<String> instructions = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {		// starts a buffered reader
			String line;
			while ((line = reader.readLine()) != null) {		// as long as there's another line to be read
				if (line.length() > 0 && line.charAt(0) != '/' && !Character.isWhitespace(line.charAt(0))) {		// won't add // comments and empty lines
					instructions.add(line.trim());		// trim removes leading and trailing spaces
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
		
		ArrayList<String> result = new ArrayList<>();
		for (String line : instructions) {
			result.add("// " + line);
			result.add(Translator.translate(line));		// puts each line into translate then adds the return to result
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
			for (int i = 0; i < result.size(); i++) {
		        writer.write(result.get(i));  		// Write the current line
		        if (i < result.size() - 1) {  		// If this is not the last line
		            writer.newLine(); 				 // Add a new line after the current line
		        }
		    }
			
		} catch (IOException e) {
			System.out.println("error writing to output file");
		}
	}
}
