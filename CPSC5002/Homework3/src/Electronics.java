
public class Electronics extends Product {

	int warrantyMonths;
	boolean isSmartDevice;
	
	public Electronics(String name, String brand, double price, int warrantyMonths, boolean isSmartDevice) {
		super(name, brand, price);
		this.warrantyMonths = warrantyMonths;
		this.isSmartDevice = isSmartDevice;
	}
	
	@Override
	public void printDetails() {
		System.out.println("Name: " + this.getName());
		System.out.println("Brand: " + this.getBrand());
		System.out.println("Price: $" + this.getPrice());
		System.out.println("Warranty: " + warrantyMonths + " months");
		System.out.println("Is smart device?: " + isSmartDevice);
	}
}
