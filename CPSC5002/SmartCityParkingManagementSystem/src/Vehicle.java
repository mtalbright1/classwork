import java.time.LocalTime;

public abstract class Vehicle {

	protected String licensePlate;
	protected LocalTime entryTime;
	protected ParkingSpot assignedSpot;
	
	public abstract double calculateFee(String demand);
}
