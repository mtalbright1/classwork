
#include <iostream>
#include <string>		// needed for to_string()
using namespace std;

class Person {
public:
	string name, address, phoneNumber, emailAddress;

	Person(string name, string address, string phoneNumber, string emailAddress) {		// regular constructor
		this->name = name;
		this->address = address;
		this->phoneNumber = phoneNumber;
		this->emailAddress = emailAddress;
	}

	string toString() {		// return all the variables as a string
		return "Name: " + name + ", Address: " + address + ", Phone Number: " + phoneNumber + ", Email Address: " + emailAddress;
	}
};

class Student : public Person {		// public access so it can be seen through later
public: 
	const string classStatus;

	Student(string name, string address, string phoneNumber, string emailAddress, string classStatus)
		: Person(name, address, phoneNumber, emailAddress), classStatus(classStatus) {
	}		// an initializer list to assign variables in a different class and const variables

	string toString() {		// adds onto the Person's toString method
		return Person::toString() + ", Class Status: " + classStatus;
	}
};

class MyDate {		// for the Employee's dateHired
public:
	int day, month, year;

	MyDate() {		// default constructor to keep the other classes happy
		day = 0;
		month = 0;
		year = 0;
	}

	MyDate(int day, int month, int year) {		// regular constructor
		this->day = day;
		this->month = month;
		this->year = year;
	}

	string getDateHired() {		// dd/mm/yyyy format
		return to_string(day) + "/" + to_string(month) + "/" + to_string(year);
	}
};

class Employee : public Person {
public:
	string office;
	MyDate dateHired;
	double salary;

	Employee(string name, string address, string phoneNumber, string emailAddress, string office, MyDate dateHired, double salary)
		: Person(name, address, phoneNumber, emailAddress) {
		this->office = office;
		this->dateHired = dateHired;
		this->salary = salary;
	}

	string toString() {
		return Person::toString() + ", Office: " + office + ", Date Hired: " + dateHired.getDateHired() + ", Salary: " + to_string(salary);
	}
};

class Faculty : Employee {
public:
	string officeHours, rank;

	Faculty(string name, string address, string phoneNumber, string emailAddress, string office, MyDate dateHired, double salary, string officeHours, string rank)
		: Employee(name, address, phoneNumber, emailAddress, office, dateHired, salary) {
		this->officeHours = officeHours;
		this->rank = rank;
	}

	string toString() {
		return Employee::toString() + ", Office Hours: " + officeHours + ", Rank: " + rank;
	}
};

class Staff : Employee {
public:
	string title;

	Staff(string name, string address, string phoneNumber, string emailAddress, string office, MyDate dateHired, double salary, string title)
		: Employee(name, address, phoneNumber, emailAddress, office, dateHired, salary) {
		this->title = title;
	}

	string toString() {
		return Employee::toString() + ", Title: " + title;
	}
};

int main() {

	Person Mark("Mark", "123 Oakdale", "4234231234", "email@address.com");
	Student Matt("Matt", "321 Riverrun", "4231234321", "email@zoo.com", "Freshman");
	Employee Dave("Dave", "543 Seeplusplus", "5126546543", "an@email.com", "4A", MyDate(4, 20, 1969), 42000);
	Faculty Ashlyn("Ashlyn", "567 Sevierville", "8976546857", "hello@kitty.com", "5B", MyDate(5, 12, 1993), 120000, "8am - 5pm", "S+");
	Staff Mary("Mary", "1234 Maple St.", "1234561234", "email@email.email", "13C", MyDate(1, 1, 1991), 85612, "Captain");

	cout << Mark.toString() << endl;
	cout << Matt.toString() << endl;
	cout << Dave.toString() << endl;
	cout << Ashlyn.toString() << endl;
	cout << Mary.toString() << endl;
}
