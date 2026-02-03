
public class ElectronicDevice implements PowerEfficiency {
	
	public double efficiency;
	
	ElectronicDevice (double e) {
		efficiency = e;
	}
	
	public void printMessage() {
		System.out.println("I am a ElectronicDevice, buzz buzz!");
	}
	
	public String getName() {
		return getClass().getName();
	}
	
	public double getEfficiency() {
		return efficiency;
	}
}
