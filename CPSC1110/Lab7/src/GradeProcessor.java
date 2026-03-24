import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GradeProcessor {

	public static void main(String[] args) {
		Scanner consoleScanner = new Scanner(System.in);
		File inputFile = null;
		ArrayList<Student> studentList = new ArrayList<>();
		
		while (inputFile == null) {
			System.out.print("Enter file name (grades.txt): ");
			String fileName = consoleScanner.nextLine();
			
			try {
				inputFile = new File(fileName);
				Scanner fileScanner = new Scanner(inputFile);
				
				while (fileScanner.hasNextLine()) {
					String line = fileScanner.nextLine();
					
					if (line.isEmpty()) {
						continue;
					}
					
					String[] parts = line.split("\t");		// split on tab
					
					String studentName = parts[0].trim(); 		// first part is name
					Student newStudent = new Student(studentName);		// create a new student using this name
					
					for (int i = 1; i < parts.length; i++) {
						String token = parts[i];		// go through each part
						try {
							double grade = Double.parseDouble(token);		// try to turn the token into a double
							newStudent.addGrade(grade);		// add the grade to the student
						} catch (NumberFormatException error) {		// // Invalid grade ("--") skip it and keep going
							System.out.println("  Skipping invalid grade for " + studentName + ": \"" + token + "\"");
						}
					}
					
					studentList.add(newStudent);		// add the student to the list
				}
				
				fileScanner.close();
				
			} catch (FileNotFoundException error) {
				System.out.println("File not found. Please retry.");
				inputFile = null;		// reset inputFile to null to restart while loop
			}
		}
		
		try {
			PrintWriter writer = new PrintWriter("averages.txt");		// try to write a file
			
			writer.printf("%-25s %s%n", "Student Name", "Average");		// left-aligned, 25 characters wide, string, string, new line
			writer.println("-----------------------------------");
			
			for (Student i : studentList) {
				writer.printf("%-25s %.2f%n", i.getName(), i.getAverage());		// left-aligned, 25 characters wide, string, float with 2 digits after decimal point, new line
			}
			
			writer.close();
			System.out.println("Average grades written to averages.txt");
			
		} catch (FileNotFoundException error) {
			System.out.println("Could not write averages.txt");
		}
		
		consoleScanner.close();
	}
}