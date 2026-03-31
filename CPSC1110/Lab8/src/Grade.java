
public class Grade {

	private String gradeLetter;
	private double gradePoints;
	
	public Grade(String gradeLetter) {
		this.gradeLetter = gradeLetter;
		
		switch (gradeLetter) {		// calculate point value of gradeLetter
		case "A":
			gradePoints = 4.0;
		case "B":
			gradePoints = 3.0;
		case "C":
			gradePoints = 2.0;
		case "D":
			gradePoints = 1.0;
		case "F":
			gradePoints = 0.0;
		default:
			gradePoints = -1;	// error checking
		}
	}
	
	public String getGradeLetter() {
		return gradeLetter;
	}
	
	public double getGradePoints() {
		return gradePoints;
	}
}
