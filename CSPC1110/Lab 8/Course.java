
public class Course {

	String courseNumber, courseName;
	int credits;
	Grade grade;
	
	Course (String courseNumber, String courseName, int credits, Grade grade) {
		this.courseNumber = courseNumber;
		this.courseName = courseNumber;
		this.credits = credits;
		this.grade = grade;
	}
	
	int getCredits () {
		return credits;
	}
	
	String getCourseNumber() {
		return courseNumber;
	}
	
	String getCourseName () {
		return courseName;
	}
	
	String getGrade () {
		return grade.getGradeLetter();
	}
	
	double getGradePoints () {
		return grade.getGradePoints();
	}
}
