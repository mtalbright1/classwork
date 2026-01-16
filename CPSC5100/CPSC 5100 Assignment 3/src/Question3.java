
public class Question3 {

	public static void main(String[] args) {
		Salary s = new Salary(30000);
		Height h = new Height(188);
		
		printSalary(h);		// this call checks the type and prints an appropriate message
	}
	
	static void printSalary(Salary s) {
		System.out.println("Salary is: " + s.getSalary());
	}
	
	static void printSalary(Height h) {
		System.out.println("Height is not a salary.");
	}
}

class Height {
	private int value;
	
	public Height (int value) {
		this.value = value;
	}
	
	public int getHeight() {
		return value;
	}
}

class Salary {
	private int value;
	
	public Salary (int value) {
		this.value = value;
	}
	
	public int getSalary() {
		return value;
	}
}