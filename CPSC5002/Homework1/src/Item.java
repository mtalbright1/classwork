
public class Item {
	
	private String name, id;
	private int quantity;
	private double price;
	
	public Item(String name, String id, int quantity, double price) {		// constructor
		this.name = name;
		this.id = id;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getName() {		// getters
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public void setName(String name) {		// setters
		this.name = name;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Item name: " + this.name + " ID: " + this.id + " Quantity: " + this.quantity + " Price: " + this.price;
	}
}
