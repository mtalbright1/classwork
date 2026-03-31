
public class Course {

	private String courseNumber, courseName;
	private int credits;
	private Grade grade;
	
	public Course(String courseNumber, String courseName, int credits, Grade grade) {
		this.courseNumber = courseNumber;
		this.courseName = courseNumber;
		this.credits = credits;
		this.grade = grade;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public String getCourseNumber() {
		return courseNumber;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getGrade() {
		return grade.getGradeLetter();
	}
	
	public double getGradePoints() {
		return grade.getGradePoints();
	}
}
