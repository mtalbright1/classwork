import java.util.ArrayList;

public class Store {

	ArrayList<Product> inventory;
	
	public Store() {
		inventory = new ArrayList<>();
	}
	
	public void add(Product p) {
		inventory.add(p);
	}
	
	public void list() {
		System.out.println("Inventory list:");
		for (Product i : inventory) {
			System.out.println(i.getName());
		}
		System.out.println();
	}
	
	public void getDetails() {
		for (Product i : inventory) {
			i.printDetails();
			System.out.println();
		}
	}
	
	public static void main (String[] args) {
		
		Store FoodStuffs = new Store();
		
		FoodStuffs.add(new Grocery("brocolli", "Broc", 2.5, "03/15/2026", 0.3));
		FoodStuffs.add(new Electronics("headset", "Earz", 30.0, 24, false));
		
		FoodStuffs.list();
		
		FoodStuffs.getDetails();
	}
	
}
