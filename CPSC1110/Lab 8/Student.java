import java.util.ArrayList;

public class Student {

	String studentId, name;
	ArrayList <Course> courses;
	
	Student (String studentId, String name) {
		this.studentId = studentId;
		this.name = name;
		courses = new ArrayList<>();
	}
	
	String getStudentId () {
		return studentId;
	}
	
	String getStudentName () {
		return name;
	}
	
	void addCourse (Course course) {
		courses.add(course);
	}
	
	ArrayList <Course> getCourses () {
		return courses;
	}
	
	double calculateGPA () {
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
