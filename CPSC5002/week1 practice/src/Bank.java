import java.util.Scanner;

public class Bank {
	double balance;
	
	Bank (double balance) {
		this.balance = balance;
	}
	
	double checkBalance() {
		return this.balance;
	}
	
	void deposit(double amount) {
		
		if (amount < 0) {
			System.out.println("Error: deposits cannot be negative.");
			return;
		}
		
		else {
			this.balance = balance + amount;
			System.out.println("Deposit successful. Current balance: $" + this.checkBalance());
		}
	}
	
	void withdraw(double amount) {
		
		if (amount > this.balance) {
			System.out.println("Insufficient balance.");
		}
		else {
			this.balance = balance - amount;
			System.out.println("Withdrawal successful. Current balance: $" + this.checkBalance());
		}
	}
	
	public static void main (String[] args) {
		Bank customer1 = new Bank(1000);
		Scanner in = new Scanner(System.in);
		
		while (true) {
			System.out.println("==== Banking Menu ====\r\n"
					+ "1. Check Balance\r\n"
					+ "2. Deposit\r\n"
					+ "3. Withdraw\r\n"
					+ "4. Exit\r\n"
					+ "Enter your choice:");
			
			int choice = Integer.parseInt(in.nextLine());
			
			switch (choice) {
				case 1:
					System.out.println("Balance: $" + customer1.checkBalance());
					break;
				case 2:
					System.out.println("Enter deposit amount: ");
					double depositAmount = Double.parseDouble(in.nextLine());
					customer1.deposit(depositAmount);
					break;
				case 3:
					System.out.println("Enter withdrawal amount: ");
					double withdrawalAmount = Double.parseDouble(in.nextLine());
					customer1.withdraw(withdrawalAmount);
					break;
				case 4:
					in.close();
					System.out.println("Thank you for using our banking app!");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice, try again.");
			}
		}
		
		
		 
	}
}
