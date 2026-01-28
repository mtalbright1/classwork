import java.util.Scanner;

public class VendingMachineOperator {

	public static void main(String[] args) {
		
		VendingMachine snakAttak = new VendingMachine();
		snakAttak.addItem("Pretzels", "A1", 3.0, 3);
		snakAttak.addItem("Chips", "A2", 2.5, 3);
		snakAttak.addItem("Honeybun", "B1", 3.5, 2);
		snakAttak.addItem("Muffin", "B2", 3.5, 2);
		
		Scanner in = new Scanner(System.in);
		
		while (true) {

			System.out.println("+------+------SnakAttak-----+---------+");		// Print header
			System.out.println("|  ID  | Item Name  | Price | Quantity|");
			System.out.println("+------+------------+-------+---------+");
			
			for (Item i : snakAttak.getInventory()) {		// Loop through all items
				System.out.printf("| %-4s | %-10s | $%5.2f | %-7d |\n", i.getID(), i.getName(), i.getPrice(), i.getQuantity());
			}

			System.out.println("+------+------------+-------+---------+");
			
			System.out.printf("Current money inserted: $%.2f\n", snakAttak.getCurrentMoney());
			System.out.println("Select an option below:");
			System.out.println("1) Insert money");
			System.out.println("2) Select item");
			System.out.println("3) Return change");
		
			switch (in.nextLine()) {
			case "1":
				System.out.println("Type amount to insert: ");
				double amount = Integer.parseInt(in.nextLine());
				if (amount < 0) {
					System.out.println("Cannot insert negative amount.");
					break;
				}
				else {
					snakAttak.insertMoney(amount);
					break;
				}
			case "2": 
				System.out.println("Enter an item ID:");
				int result = snakAttak.purchaseItem(in.nextLine());		// attempt to purchase
				switch(result) {
				case 0:		// no error
					System.out.println("Purchase successful");
					break;
				case 1:
					System.out.println("Insufficient money");
					break;
				case 2: 
					System.out.println("Out of stock");
					break;
				case 3: 
					System.out.println("ID not found");
				}
				break;
			case "3":
				System.out.println("Change returned: " + snakAttak.getCurrentMoney());
				snakAttak.resetCurrentMoney();
				break;
			default:
				System.out.println("Not a valid selection");
				break;
			}
		}
	}
}
