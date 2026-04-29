import java.time.Duration;
import java.time.LocalTime;

public class Motorcycle extends Vehicle {

	public Motorcycle(String licensePlate) {
		super(licensePlate);
	}
	
	@Override
	public double calculateFee(String demand) {
		double fee = 0.00;
		long elapsed = Duration.between(entryTime, exitTime).toHours();
		
		System.out.print("Vehicle: ElectricVehicle (" + licensePlate + ")\n" +
						 "Entry time: " + entryTime.toString() + "\n" +
						 "Exit time: " + exitTime.toString() + "\n" +
						 "Duration: " + elapsed + "hours\n\n");
		
		fee = 5.00 * elapsed;		// $5 per hour
		System.out.print("Rate calculation: \n" + 
						 "Base rate: $5.00 x " + elapsed + " = $" + fee + "\n");
		
		if (assignedSpot.getType().equals("COMPACT")) {
			System.out.printf("Motorcycle discount (-50%): -$$f.2\n", fee*0.5);
			fee *= 0.5;		// 50% motorcycle discount if in compact spot
		}
		
		if (demand.equals("HIGH")) {		// demand adjusted pricing
			System.out.printf("Demand adjusted pricing (+50%): +$%f.2\n", fee*0.5);
			fee *= 1.5;
		}
		else if (demand.equals("LOW")) {
			System.out.printf("Demand adjusted pricing (-30%): -$%f.2\n", fee*0.3);
			fee *= 0.7;
		}
		
		if (fee > 50) { fee = 50.00; }		// maximum fee of $50
		else if (fee < 2.00) { fee = 2.00; }		// minimum fee of $2
		
		System.out.printf("Total: $%f.2\n", fee);
		System.out.println("Receipt printed. Thank you!");
		return fee;
	}
}
