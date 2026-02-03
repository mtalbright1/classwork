import java.util.Scanner;
import java.util.ArrayList;

public class BankAccountTest
{
    public static void main(String[] args)
    {
		// Initialize a scanner to take user input
        Scanner scanner = new Scanner(System.in);

        // Print welcome message to the user
        System.out.println("Welcome to the Bank Account Simulation!");
		
		// Prompt user to enter their initial deposit amount
        System.out.print("Please enter your initial deposit amount: ");
        double initialDeposit = scanner.nextDouble();

        // Create a new BankAccount with the initial deposit
        BankAccount account = new BankAccount(initialDeposit);
        
        // Print initial deposit and balance
        System.out.println("Initial deposit: " + initialDeposit);
        System.out.println("Balance: " + account.getBalance());

        // Start simulation
        boolean running = true;  // loop variable, to keep running. initially true
        while (running)
        {
            // Show menu options with clear instructions
            System.out.println("\nWould you like to (1) Deposit, (2) Withdraw, or (3) Check balance?");
            System.out.print("Enter a number: ");
            int choice = scanner.nextInt();
            
            switch (choice)
            {
                case 1: // Deposit, prompt the user to enter the deposit amount and read it from console
                    System.out.println("\nYou have chosen to deposit money.");
                    System.out.print("Enter the deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    
                    // Validate and process deposit
                    if (account.deposit(depositAmount)) { 
                        System.out.println("Deposit successful!"); 
                    } else {
                        System.out.println("Invalid deposit amount. Please enter a positive value.");
                    }
                    
                    // Display updated balance after deposit
                    System.out.println("Balance: " + account.getBalance());
                    break;

                case 2: // Withdraw, prompt the user to enter the withdrawal amount and read it from console
                    System.out.println("\nYou have chosen to withdraw money.");
                    System.out.print("Enter the withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    
                    // Validate and process withdrawal
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful!");
                    } else {
                        System.out.println("Invalid withdrawal amount or insufficient funds.");
                    }
                    
                    // Display updated balance after withdrawal
                    System.out.println("Balance: " + account.getBalance());
                    break;

                case 3: // Check balance
                    System.out.println("\nYou have chosen to check your balance.");
                    System.out.println("Balance: " + account.getBalance());
                    break;

                default:
                    // Invalid input from user
                    System.out.println("Invalid choice. Please select a valid option: 1, 2, or 3.");
                    break;
            }

            // Ask if user wants to continue
            System.out.print("\nWould you like to perform another transaction? (y/n): ");
            String continueChoice = scanner.next();
            if (continueChoice.equalsIgnoreCase("n")) {
                running = false;
            }
        }

        // Print transaction history at the end of the simulation
        System.out.println("\nTransaction History:");
        ArrayList<String> history = account.getTransactionHistory();
        
        // Display each transaction along with the balance after each transaction
        for (String transaction : history) {
            System.out.println(transaction);
        }

        // Close the scanner object to prevent resource leak
        scanner.close();
    }
}