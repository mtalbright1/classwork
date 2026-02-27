
public class SalariedEmployee extends Employee {

	private double annualSalary;
	
	public SalariedEmployee(String name, double annualSalary) {
		super(name);
		this.annualSalary = annualSalary;
	}
	
	@Override
	public double getPay() {
		return annualSalary / 52;
	}
	
}
