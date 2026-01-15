import java.util.HashMap;

public class Translator {

	static String translateACode (int number) {		// turn a decimal number into a string of binary
		
		StringBuilder binary = new StringBuilder();
		while (number > 0) {
			binary.insert(0, number % 2);		// inserts the remainder after dividing by 2 to the beginning of the string
			number /= 2;
		}
		for (int i = 0; i < 16 - binary.length(); i++ ) {		// adds leading 0's until it's 16 bits
			binary.insert(0, '0');
		}
	
		return binary.toString();		// convert StringBuilder to String
	}
	
	static String translateCCode (String dest, String comp, String jump) {		// turn each of these strings into strings of binary according to the hack table
		
		HashMap<String, String> destMap = new HashMap<>();
		destMap.put("", "000");
		destMap.put("M", "001");
		destMap.put("D", "010");
		destMap.put("DM", "011");
		destMap.put("A", "100");
		destMap.put("AM", "101");
		destMap.put("AD", "110");
		destMap.put("ADM", "111");
		
		HashMap<String, String> compMap = new HashMap<>();
		compMap.put("0", "0101010");
		compMap.put("1", "0111111");
		compMap.put("-1", "0111010");
		compMap.put("D", "0001100");
		compMap.put("A", "01110000");
		compMap.put("!D", "0001101");
		compMap.put("!A", "0110001");
		compMap.put("-D", "0001111");
		compMap.put("-A", "0110011");
		compMap.put("D+1", "0011111");
		compMap.put("A+1", "0110111");
		compMap.put("D-1", "0001110");
		compMap.put("A-1", "0110010");
		compMap.put("D+A", "0000010");
		compMap.put("D-A", "0010011");
		compMap.put("A-D", "0000111");
		compMap.put("D&A", "0000000");
		compMap.put("D|A", "0010101");
		compMap.put("M", "1110000");
		compMap.put("!M", "1110001");
		compMap.put("-M", "1110011");
		compMap.put("M+1", "1110111");
		compMap.put("M-1", "1110010");
		compMap.put("D+M", "1000010");
		compMap.put("D-M", "1010011");
		compMap.put("M-D", "1000111");
		compMap.put("D&M", "1000000");
		compMap.put("D|M", "1010101");
		
		HashMap<String, String> jumpMap = new HashMap<>();
		jumpMap.put("", "000");
		jumpMap.put("JGT", "001");
		jumpMap.put("JEQ", "010");
		jumpMap.put("JGE", "011");
		jumpMap.put("JLT", "100");
		jumpMap.put("JNE", "101");
		jumpMap.put("JLE", "110");
		jumpMap.put("JMP", "111");
		
		String ddd = destMap.get(dest);
		String acccccc = compMap.get(comp);
		String jjj = jumpMap.get(jump);
		
		String result = "111" + ddd + acccccc + jjj;
		return result;
	}
}
