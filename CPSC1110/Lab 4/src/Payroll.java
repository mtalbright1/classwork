import java.util.ArrayList;

public class Payroll extends Employee {

	private ArrayList<Employee> employees;
	
	public Payroll() {
		super("Payroll");
		employees = new ArrayList<>();
	}
	
	public void add(Employee e) {
		employees.add(e);
	}
	
	@Override
	public double getPay() {
		double totalPay = 0;
		for (Employee i : employees) {
			totalPay += i.getPay();
		}
		return totalPay;
	}
}
