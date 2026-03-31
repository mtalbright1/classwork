import java.util.ArrayList;

public class Student {

	private String studentId, name;
	private ArrayList<Course> courses;
	
	public Student(String studentId, String name) {
		this.studentId = studentId;
		this.name = name;
		courses = new ArrayList<>();
	}
	
	public String getStudentId() {
		return studentId;
	}
	
	public String getStudentName() {
		return name;
	}
	
	public void addCourse(Course course) {
		courses.add(course);
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	public double calculateGPA() {
		int totalCredits = 0;
		double weightedGrades = 0;
		
		for (Course i : courses) {
			weightedGrades += i.getGradePoints() * i.getCredits();
			totalCredits += i.getCredits();
		}
		double gpa = weightedGrades / totalCredits;
		return gpa;
	}
}
