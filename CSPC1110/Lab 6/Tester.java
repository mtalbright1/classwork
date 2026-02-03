import java.util.Random;

public class Tester {

	public static final int NUM_ELECTRONIC_DEVICES = 10;
	
	public static void main (String[] args) {
		
		ElectronicDevice[] devices = new ElectronicDevice[NUM_ELECTRONIC_DEVICES];
		
		Random rand = new Random();
		
		for (int i = 0; i < NUM_ELECTRONIC_DEVICES; i++) {
			
			int randomNumber = rand.nextInt(4);
			
			switch (randomNumber) {
			case 0:
				devices[i] = new ElectronicDevice(rand.nextDouble() * 50 + 50);
				break;
			case 1:
				devices[i] = new Laptop(rand.nextDouble() * 50 + 50);
				break;
			case 2:
				devices[i] = new Smartphone(rand.nextDouble() * 50 + 50);
				break;
			case 3:
				devices[i] = new SmartTV(rand.nextDouble() * 50 + 50);
				break;
			}
		}
		
		for (ElectronicDevice i : devices) {
			i.printMessage();
		}
		
		for (ElectronicDevice i : devices) {
			System.out.printf(i.getName() + ": %.3f%n", i.efficiency);
		}
		
		ElectronicDevice target = (ElectronicDevice) getFirstBelowT(devices, 70);
		if (target == null) {
			System.out.println("There is no device with efficiency less than 70");
		}
		else {
			System.out.printf("The first object with efficiency less than 70 is " + target.getName() + " with an efficiency of %.3f%n", target.getEfficiency());
		}
	}
	
	static PowerEfficiency getFirstBelowT(PowerEfficiency[] array, double threshold) {
		
		for (PowerEfficiency i : array) {
			if (i.getEfficiency() < threshold) {
				return i;
			}
		}
		return null;
	}
}
