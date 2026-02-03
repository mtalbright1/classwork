
public class Grade {

	String gradeLetter;
	double gradePoints;
	
	Grade (String gradeLetter) {
		this.gradeLetter = gradeLetter;
	}
	
	String getGradeLetter () {
		return gradeLetter;
	}
	
	double getGradePoints () {
		switch (gradeLetter) {
		case "A":
			return 4.0;
		case "B":
			return 3.0;
		case "C":
			return 2.0;
		case "D":
			return 1.0;
		case "F":
			return 0.0;
		default:
			return -1;	// error checking;
		}
	}
}
