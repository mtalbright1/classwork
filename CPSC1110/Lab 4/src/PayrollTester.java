import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PayrollTester {
	
	@Test
    void testHourlyEmployeePay() {
        HourlyEmployee emp = new HourlyEmployee("Alice", 20.0, 40);

        assertEquals(800.0, emp.getPay());
    }
}
