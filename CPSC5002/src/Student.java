
public class Student {
	String name;
	int id;
	double score1, score2, score3;		// test scores
	
	Student (String name, int id, double score1, double score2, double score3) {		// constructor
		this.name = name;
		this.id = id;
		this.score1 = score1;
		this.score2 = score2;
		this.score3 = score3;
	}
	
	double averageScore() {
		double sum = 0;
		
		sum = this.score1 + this.score2 + this.score3;
		return sum / 3;
	}
	
	boolean isPassing() {
		double average = averageScore();
		
		if (average >= 60) {
			return true;
		}
		else {
			return false;
		}
	}
	
	void printSummary() {
		System.out.println("Student's name: " + this.name);
		System.out.println("Student's ID: " + this.id);
		System.out.println("Average score: " + averageScore());
		System.out.println("Pass/Fail Status: " + (isPassing() ? "Pass" : "Fail"));
	}
	
	public static void main (String[] args) {
		Student caesar = new Student("Caesar", 16, 75, 80, 51);
		Student augustus = new Student("Augustus", 17, 80, 90, 32);
		
		caesar.printSummary();
		augustus.printSummary();
		
		if (caesar.averageScore() > augustus.averageScore()) {
			System.out.println("Caesar has the higher score");
		}
		else if (caesar.averageScore() < augustus.averageScore()) {
			System.out.println("Augustus has the higher score");
		}
		else {
			System.out.println("Neither has a higher score");
		}
	}
}
