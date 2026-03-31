import java.io.PrintWriter;
import java.util.ArrayList;

public class TermReport {

	private String term;
	private ArrayList<Student> students;
	
	public TermReport(String term) {
		this.term = term;
		students = new ArrayList<>();
	}
	
	public void addStudent(Student student) {
		students.add(student);
	}
	
	public void generateReport() {
		
		try (PrintWriter output = new PrintWriter("Term Report Output.txt")) {
			for (Student i : students) {
				output.println ("Term: " + term);
				output.println ("");
				output.println ("Student ID: " + i.getStudentId());
				output.println ("Name: " + i.getStudentName());
				output.printf ("%-10s %-40s %-15s %-7s %-12s%n", "CRN", "Course Name", "Credit Hours", "Grade", "Grade Points");
				
				ArrayList<Course> courses = new ArrayList<>();
				courses = i.getCourses();
				
				for (Course j : courses) {
					output.printf ("%-10s %-40s %-15s %-7s %-12s%n", j.getCourseNumber(), j.getCourseName(), j.getCredits(), j.getGrade(), j.getGradePoints());
				}
				
				output.printf ("GPA: %.2f%n", i.calculateGPA());
				
				for (int h = 0; h < 84; h++) {
					output.print("-");
				}
			}
		}
		catch (Exception error) {
			System.out.println ("Unable to write");
		}
	}
}
