import java.util.ArrayList;

public class VendingMachine {

	private double currentMoney;		// money that the customer has added to purchase an item
	private ArrayList<Item> inventory;
	
	VendingMachine() {		// constructor to initialize values
		currentMoney = 0;
		inventory = new ArrayList<Item>();
	}
	
	public double getCurrentMoney() {
		return currentMoney;
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
	public void addItem(String name, String id, double price, int quantity) {
		Item newItem = new Item(name, id, price, quantity);
		inventory.add(newItem);
	}
	
	public void insertMoney(double money) {
		currentMoney += money;
	}
	
	public int purchaseItem(String id) {
		for (Item i : inventory) {		// check the inventory
			if (i.getID().equals(id)) {		// find the id
				
				if (i.getQuantity() > 0) {		// must be in stock
					
					if (i.getPrice() <= currentMoney) {		// must have already inserted enough money
						currentMoney -= i.getPrice();
						i.decrementQuantity();
						return 0;		// successful purchase
					}
					else
						return 1;		// error 1: insufficient money
					
				}
				else
					return 2;		// error 2: out of stock
				
			}
		}
		return 3;		// error 3: id not found
	}
	
	public void resetCurrentMoney() {
		currentMoney = 0;
	}
}
