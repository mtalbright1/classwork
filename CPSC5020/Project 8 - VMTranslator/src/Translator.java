
public class Translator {

	static String translate (String line) {
		
		String[] words = line.split(" ");		// words[0] is command, words[1] is segment, words[2] is index
		
		switch (words[0]) {		// all the arithmetic/logic commands have static replacements, but push and pop are dynamic
			case "add" : return "@SP\nAM=M-1\nD=M\nA=A-1\nM=D+M\n";
			case "sub" : return "@SP\nAM=M-1\nD=M\nA=A-1\nM=M-D\n";
			case "neg" : return "@SP\nA=M\nA=A-1\nM=-M\n"; 
			case "eq" : return "@SP\nAM=M-1\nD=M\n@SP\nAM=M-1\nD=M-D\n@isTrue\nD;JEQ\nD=0\n@isFalse\n0;JMP\n(isTrue)\nD=-1\n(isFalse)\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
			case "gt" : return "@SP\nAM=M-1\nD=M\n@SP\nAM=M-1\nD=M-D\n@isTrue\nD;JGT\nD=0\n@isFalse\n0;JMP\n(isTrue)\nD=-1\n(isFalse)\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
			case "lt" : return "@SP\nAM=M-1\nD=M\n@SP\nAM=M-1\nD=M-D\n@isTrue\nD;JLT\nD=0\n@isFalse\n0;JMP\n(isTrue)\nD=-1\n(isFalse)\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
			case "and" : return "@SP\nAM=M-1\nD=M\nA=A-1\nM=D&M\n";
			case "or" : return "@SP\nAM=M-1\nD=M\nA=A-1\nM=D|M\n";
			case "not" : return "@SP\nA=M\nA=A-1\nM=!M\n";
			case "push" : return pushCommand(words[1], words[2]);
			case "pop" : return popCommand(words[1], words[2]);
			case "label" : return "(" + words[1] + ")\n";
			case "goto" : return "@" + words[1] + "\n0;JMP\n";
			case "if-goto" : return "@ARG\nD=M\n@" + words[1] + "\nD;JGT\n";
			default : return "Error: Unexpected first word";
		}
	}
	
	static String pushCommand (String segment, String number) {
		
		String convertedSegment = convertSegment(segment, number);		// changes "local" to "LCL" and so forth

		switch (segment) {
			case "constant" : return "@" + number + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
			case "local" :
			case "argument" :
			case "this" :
			case "that" :
			case "pointer" : return "@" + convertedSegment + "\nD=M\n@" + number + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
			case "temp" : return "@" + convertedSegment + "\nD=A\n@" + number + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
			case "static" : return "@" + convertedSegment + "\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
			default : return "Error: unexpected segment in pushCommand";
		}
	}
	
	static String popCommand (String segment, String number) {
		
		String convertedSegment = convertSegment(segment, number);
		
		switch (segment) {
			case "constant" : return "Error: unexpected pop constant";		// I don't think you can pop the stack to constant
			case "local" :
			case "argument" :
			case "this" :
			case "that" :
			case "pointer" : return "@" + convertedSegment + "\nD=M\n@" + number + "\nA=D+A\nD=A\n@R13\nM=D\n@SP\nA=M-1\nD=M\n@R13\nA=M\nM=D\n@SP\nM=M-1\n";
			case "temp" : return "@" + convertedSegment + "\nD=A\n@" + number + "\nA=D+A\nD=A\n@R13\nM=D\n@SP\nA=M-1\nD=M\n@R13\nA=M\nM=D\n@SP\nM=M-1\n";
			case "static" : return "@SP\nAM=M-1\nD=M\n@" + convertedSegment + "\nM=D";
			default : return "Error: unexpected segment in popCommand";
		}
	}
	
	static String convertSegment (String segment, String number) {
		switch (segment) {
			case "local" : return "LCL";
			case "argument" : return "ARG";
			case "this" : return "THIS";
			case "that" : return "THAT";
			case "static" : return "Foo." + number;		// Foo.i which will be set to registers 16+
			case "temp" : return "R5";		// R5 + i for registers 5-12
			case "pointer" : return "R3";		// R3 + 0 = THIS, R3 + 1 = THAT
			default : return "Error: unexpected segment in convertSegment";
		}
	}
}
