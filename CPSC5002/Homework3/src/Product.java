
public abstract class Product {

	private String name, brand;
	private double price;
	
	public Product(String name, String brand, double price) {
		this.name = name;
		this.brand = brand;
		this.price = price;
	}
	
	public String getName() {		// accessors
		return name;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public double getPrice() {
		return price;
	}
	
	public abstract void printDetails();		// abstract method to be overridden
	
}
