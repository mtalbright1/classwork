import java.time.Duration;
import java.time.LocalTime;

public class Car extends Vehicle {

	public Car(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	
	public void setEntryTime() {
		entryTime = LocalTime.now();
	}
	
	public void setAssignedSpot(ParkingSpot assignedSpot) {
		this.assignedSpot = assignedSpot;
	}
	
	@Override
	public double calculateFee(String demand) {
		double fee = 0.00;
		LocalTime exitTime = LocalTime.now();
		
		long elapsed = Duration.between(entryTime, exitTime).toHours();
		
		fee = 5.00 * elapsed;		// $5 per hour
		
		if (demand == "HIGH") { fee *= 1.5; }		// demand adjusted pricing
		else if (demand == "LOW") { fee *= 0.7; }
		
		if (fee > 50) { fee = 50.00; }		// maximum fee of $50
		else if (fee < 2.00) { fee = 2.00; }		// minimum fee of $2
		
		return fee;
	}
}
