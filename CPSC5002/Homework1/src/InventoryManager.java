import java.util.ArrayList;

public class InventoryManager {
	private ArrayList<Item> inventory;
	
	InventoryManager() {
		inventory = new ArrayList<>();
	}
	
	public void addItem(String name, String id, int quantity, double price) {
		Item i = new Item(name, id, quantity, price);
		inventory.add(i);
	}
	
	public void removeItem(String id) {
		for (Item i : inventory) {
			if (i.getId() == id) {
				inventory.remove(i);
			}
		}
	}
	
	public void updateQuantity(String id, int quantity) {
		Item i = searchById(id);
		i.setQuantity(quantity);
	}
	
	public void listItems() {
		for (Item i : inventory) {
			System.out.println(i.toString());
		}
	}
	
	public Item searchById(String id) {
		for (Item i : inventory) {
			if (i.getId() == id) {
				return i;
			}
		}
		return null;
	}
	
	public static void main (String[] args) {
		InventoryManager inventory = new InventoryManager();
		
		inventory.addItem("brocolli", "broc1", 15, 2.5);
		inventory.addItem("beans", "bean1", 25, 1.5);
		inventory.addItem("rice", "rice1", 24, 10);
		
		inventory.updateQuantity("broc1", 10);
		
		inventory.removeItem("bean1");
		
		inventory.listItems();
	}
}
