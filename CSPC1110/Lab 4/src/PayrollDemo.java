
public class PayrollDemo {

    public static void main(String[] args) {
        Payroll payroll = new Payroll();
        
        // Add employees to the payroll
        payroll.add(new HourlyEmployee("Alice", 20.0, 40));   // $800
        payroll.add(new SalariedEmployee("Bob", 52000.0));    // $1000 (weekly)
        payroll.add(new HourlyEmployee("Charlie", 15.0, 30)); // $450
        
        // Run test: total payroll
        System.out.println("Total payroll: $" + payroll.getPay());
        System.out.println("Expected: $2250.0");
    }
}