import java.util.ArrayList;

public class Student {

	private String name;
	private ArrayList<Double> grades;

	public Student(String name) {
		this.name = name;
		this.grades = new ArrayList<Double>();
	}

	public void addGrade(double grade) {
		grades.add(grade);
	}

	public double getAverage() {
		if (grades.isEmpty()) {
			return 0.0;
		}
		
		double sum = 0;
		for (double g : grades) {
			sum += g;
		}
		
		return sum / grades.size();
	}

	public String getName() {
		return name;
	}
}