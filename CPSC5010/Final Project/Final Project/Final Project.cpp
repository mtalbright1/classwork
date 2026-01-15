
#include <iostream>
#include <list>
#include <string>
using namespace std;

bool equalsIgnoreCase(string a, string b) {		// just checks if two strings match
	if (a.length() != b.length()) {
		return false;
	}
	for (int i = 0; i < a.length(); i++) {
		if (tolower(a[i]) != tolower(b[i])) {
			return false;
		}
	}
	return true;
}

class Item {
public:
	virtual void displayInfo() const = 0;		// pure virtual function
};

class Book : public Item {
private:
	string title, author;
	double price;

public:
	Book() {		// default constructor
		this->title = "Unknown title";
		this->author = "Unknown author";
		this->price = 0.0;
	}
	Book(string title, string author, double price) {		// parametized constructor
		this->title = title;
		this->author = author;
		this->price = price;
	}
	Book(const Book& other) {		// copy constructor
		this->title = other.title;
		this->author = other.author;
		this->price = other.price;
	}
	string getTitle() const {		// accessors
		return title;
	}
	string getAuthor() const {
		return author;
	}
	double getPrice() const {
		return price;
	}
	void changeTitle(string newTitle) {		// mutators
		title = newTitle;
	}
	void changeAuthor(string newAuthor) {
		author = newAuthor;
	}
	void changePrice(double newPrice) {
		price = newPrice;
	}
	void displayInfo() const override {		// overriding the virtual function
		cout << "\"" << title << "\"" << " by " << author << " for $" << price << endl;
	}
};

class Catalog {
private:
	list<Book> inventory;		// linked list

public:
	Catalog() {
	}
	void addBook(Book b) {		// add element
		inventory.push_back(b);
	}
	void removeBook(string key) {
		for (auto i = inventory.begin(); i != inventory.end(); ) {		// make an iterator of auto type, left the increment empty
			if (i->getTitle() == key || i->getAuthor() == key)		// if there's a match
				i = inventory.erase(i);		// erase() returns the next element
			else
				++i;		// increments, then passes
		}
	}
	void displayInventory() {
		for (Book i : inventory) {
			i.displayInfo();
		}
	}

	void sortInventory(string sortBy) {		// sorts the inventory depending on the choice given
		if (equalsIgnoreCase(sortBy, "title")) {
			inventory.sort([](const Book& a, const Book& b) {
				return a.getTitle() < b.getTitle();
				});
		}
		else if (equalsIgnoreCase(sortBy, "author")) {
			inventory.sort([](const Book& a, const Book& b) {
				return a.getAuthor() < b.getTitle();
				});
		}
		else if (equalsIgnoreCase(sortBy, "price")) {
			inventory.sort([](const Book& a, const Book& b) {
				return a.getPrice() < b.getPrice();
				});
		}
	}
	Book checkInventory(string query) {
		for (Book i : inventory) {
			if (equalsIgnoreCase(i.getTitle(), query) || equalsIgnoreCase(i.getAuthor(), query)) {
				return i;
			}
		}
		return Book();
	}
};

int main() {
	Catalog Catalog;
	Book book1("The Hobbit", "J.R.R.Tolkien", 20.00);
	Book book2("Lolita", "Vladimir Nabokov", 23.00);
	Book book3("1984", "George Orwell", 19.84);
	Catalog.addBook(book1);
	Catalog.addBook(book2);
	Catalog.addBook(book3);

	cout << "Welcome to the bookstore." << endl;
	bool running;
	running = true;
	while (running) {
		cout << "Please select an option:" << endl;
		cout << "1) List catalog" << endl;
		cout << "2) Sort catalog" << endl;
		cout << "3) Search by title or author" << endl;
		cout << "4) Purchase by title or author" << endl;
		cout << "5) Quit" << endl;

		int input; string choice; Book result;
		cin >> input;
		switch (input) {
		case 1: 
			cout << endl;
			Catalog.displayInventory();
			cout << endl;
			break;
		case 2:
			cout << "Sort by Title, Author, or Price?" << endl;
			cin >> choice;
			while (!equalsIgnoreCase(choice, "title") && !equalsIgnoreCase(choice, "author") && !equalsIgnoreCase(choice, "price")) {
				cout << "Invalid entry" << endl << "Sort by Title, Author, or Price?" << endl;
				cin >> choice;
			}
			Catalog.sortInventory(choice);
			cout << endl << "The catalog has been sorted by " << choice << "." << endl << endl;
			break;
		case 3:
			cout << "Enter a title or author to search for." << endl;
			cin.ignore();
			getline(cin, choice);		// gets the whole line in case of spaces
			result = Catalog.checkInventory(choice);
			if (result.getAuthor() == "Unknown author" && result.getTitle() == "Unknown title" && result.getPrice() == 0.0) {
				cout << endl << "Book was not found." << endl;
			}
			else {
				cout << endl << result.getTitle() << " by " << result.getAuthor() << " for $" << result.getPrice() << endl << endl;
			}
			break;
		case 4:
			cout << "Enter the title or author of the book you wish to purchase." << endl;
			cin.ignore();
			getline(cin, choice);
			result = Catalog.checkInventory(choice);
			if (result.getAuthor() == "Unknown author" && result.getTitle() == "Unknown title" && result.getPrice() == 0.0) {
				cout << endl << "Book was not found." << endl;
			}
			else {
				cout << endl << "The price is $" << result.getPrice() << endl << "Thank you for your purchase." << endl << endl;
				Catalog.removeBook(choice);
			}
			break;
		case 5:
			running = false;
			break;
		default:
			cout << "Invalid entry. Please enter a number 1-5" << endl;
			break;
		}
	}
	cout << "Thank you, come again." << endl;
}
