import java.util.ArrayList;

public class BankAccount {
	
   private double balance;		// Current balance in the account
   ArrayList<String> transactions;
   
   public BankAccount(double initialBalance) {
	   balance = initialBalance;
	   if (balance < 0) {		// Ensure that the balance is set correctly (no negative balances allowed)
		   System.out.println("negative balance");
	   }
	   transactions = new ArrayList<>();		// Initialize the transaction history list to an empty list
	   transactions.add("Deposit: "+ initialBalance + " | Balance: " + balance);		// Log the initial deposit by adding it to the history list
   }

   public boolean deposit(double amount) {
	   if (amount < 0) {		// fails if deposit is negative
		   transactions.add("Deposit: " + amount + " | Invalid Transaction");		// stores failed transaction
		   return false;
	   }
	   else {
		   balance += amount;		// adds to amount
		   transactions.add("Deposit: " + amount + " | Balance " + getBalance());		// Log the deposit in the transaction history
		   return true;
	   }  
   }

   public boolean withdraw(double amount) {
	   if (amount < 0 || amount > getBalance()) {		// fails if withdrawal is negative or greater than balance
		   transactions.add("Withdrawal: " + amount + " | Invalid Transaction");
		   return false;
	   }
	   else {
		   balance -= amount;
		   transactions.add("Withdrawal: " + amount + " | Balance: " + getBalance());
		   return true;
	   }
   }

   public double getBalance() {
      return balance;  
   }

   public ArrayList<String> getTransactionHistory() {
      return transactions;
   }
}
