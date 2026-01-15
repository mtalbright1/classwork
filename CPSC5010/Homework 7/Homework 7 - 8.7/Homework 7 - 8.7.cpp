
#include <iostream>
using namespace std;

class Account {
private:		// declaring variables
	int id;
	double balance;
	double annualInterestRate;
	string dateCreated = "3/18/2025";		// the instructions call for a "Date" data type, which isn't a thing

public:
	Account() {		// default constructor
		id = 0;
		balance = 0;
		annualInterestRate = 0;
	}
	Account(int id, double balance) {		// regular constructor takes arguments
		this->id = id;
		this->balance = balance;
	}
	int getId() {		// accessor for ID
		return id;
	}
	void setId(int id) {		// mutator for ID
		this->id = id;
	}
	double getBalance() {
		return balance;
	}
	void setBalance(double balance) {
		this->balance = balance;
	}
	double getAnnualInterestRate() {
		return annualInterestRate;
	}
	void setAnnualInterestRate(double annualInterestRate) {
		this->annualInterestRate = annualInterestRate;
	}
	string getDateCreated() {
		return dateCreated;
	}
	double getMonthlyInterestRate() {
		return annualInterestRate / 12;
	}
	double getMonthlyInterest() {
		return balance * (getMonthlyInterestRate() / 100);
	}
	void withdraw(double withdrawal) {
		balance -= withdrawal;
	}
	void deposit(double deposit) {
		balance += deposit;
	}
};

int main() {
	Account savingsAccount(1122, 20000);
	savingsAccount.setAnnualInterestRate(4.5);
	savingsAccount.withdraw(2500);
	savingsAccount.deposit(3000);
	cout << "The balance is: " << savingsAccount.getBalance() << endl;
	cout << "The monthly interest is: " << savingsAccount.getMonthlyInterest() << endl;
	cout << "The date the account was created is: " << savingsAccount.getDateCreated() << endl;
}
