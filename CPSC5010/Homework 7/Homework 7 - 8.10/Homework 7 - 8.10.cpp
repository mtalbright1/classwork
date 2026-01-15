
#include <iostream>
using namespace std;

class QuadraticEquation {
private:		// declaring variables
	double a;
	double b;
	double c;
public:
	QuadraticEquation(double a, double b, double c) {		// regular constructor
		this->a = a;
		this->b = b;
		this->c = c;
	}
	double getA() {		// three accessors for the three variables
		return a;
	}
	double getB() {
		return b;
	}
	double getC() {
		return c;
	}
	double getDiscriminant() {
		return b * b - 4 * a * c;
	}
	double getRoot1() {
		double discriminant = getDiscriminant();
		if (discriminant >= 0) {		// checks if discriminant is non negative
			return (-b + discriminant) / (2 * a);
		}
		else {
			cout << "No real roots exist." << endl;
			return NAN;		// "not a number" filler value
		}
	}
	double getRoot2() {
		double discriminant = getDiscriminant();
		if (discriminant >= 0) {		
			return (-b - discriminant) / (2 * a);
		}
		else {
			cout << "No real roots exist." << endl;
			return NAN;	
		}
	}
};

int main() {
	double a; double b; double c;
	cout << "For ax^2 + bx + c, enter values for a, b, and c: " << endl;
	cin >> a; cin >> b; cin >> c;
	QuadraticEquation equation = QuadraticEquation(a, b, c);		// creates the class using the given variables

	if (equation.getDiscriminant() > 0) {
		cout << "The two roots are " << equation.getRoot1() << " and " << equation.getRoot2() << endl;
	}
	else if (equation.getDiscriminant() == 0) {
		cout << "The only real root is " << equation.getRoot1();
	}
	else {
		cout << "The equation has no roots.";
	}
}
