
public class Grocery extends Product {

	private String expirationDate;
	private double weightKg;
	
	public Grocery(String name, String brand, double price, String expirationDate, double weightKg) {
		super(name, brand, price);
		this.expirationDate = expirationDate;
		this.weightKg = weightKg;
	}
	
	@Override
	public void printDetails() {
		System.out.println("Name: " + this.getName());
		System.out.println("Brand: " + this.getBrand());
		System.out.println("Price: $" + this.getPrice());
		System.out.println("Expiration Date: " + expirationDate);
		System.out.println("Weight: " + weightKg + " kg");
	}
}