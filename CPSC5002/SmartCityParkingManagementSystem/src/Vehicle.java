import java.time.LocalTime;

public abstract class Vehicle {

	protected String licensePlate;
	protected LocalTime entryTime;
	protected LocalTime exitTime;
	protected ParkingSpot assignedSpot;
	protected String demandUponArrival;
	
	public Vehicle(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	
	public abstract double calculateFee(String demand);
	
	// accessors and mutators
	public String getLicensePlate() { return licensePlate; }
	
	public void setEntryTime() { entryTime = LocalTime.now(); }
	
	public LocalTime getEntryTime() { return entryTime; }
	
	public void setExitTime() { exitTime = LocalTime.now(); }
	
	public LocalTime getExitTime() { return exitTime; }
	
	public void setAssignedSpot(ParkingSpot assignedSpot) { this.assignedSpot = assignedSpot; }
	
	public void setDemandUponArrival(String currentDemand) { demandUponArrival = currentDemand; }
	
	public String getDemandUponArrival() { return demandUponArrival; }
}
