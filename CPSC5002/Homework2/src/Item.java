
public class Item {

	private String name, id;
	private double price;
	private int quantity;
	
	Item(String name, String id, double price, int quantity) {
		this.name = name;
		this.id = id;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	
	public String getID() {
		return id;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void decrementQuantity() {
		quantity--;
	}
}
