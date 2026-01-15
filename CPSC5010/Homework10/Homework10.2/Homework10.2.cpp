
#include <iostream>
using namespace std;

class Complex {
private:
	double real, imaginary;
public:

	Complex() {		// default constructor
		real = 0;
		imaginary = 0;
	}
	Complex(double real, double imaginary) {		// regular constructor
		this->real = real;
		this->imaginary = imaginary;
	}
			// the left side of the operator is 'this' and the right side is the argument named 'other'
	Complex operator+ (const Complex other) {		// z1 + z2 = (a + c) + (b + d)i
		return Complex(real + other.real, imaginary + other.imaginary);
	}

	Complex operator- (const Complex other) {
		return Complex(real - other.real, imaginary - other.imaginary);
	}

	Complex operator* (const Complex other) {		// z1 * z2 = (ac - bd) + (ad + bc)i
		double newReal = real * other.real - imaginary * other.imaginary;
		double newImaginary = real * other.imaginary + imaginary * other.real;
		return Complex(newReal, newImaginary);
	}

	Complex operator/ (const Complex other) {		// z1 / z2 = [(ac + bd) + (bc - ad)i] / (c² + d²)

		double denominator = other.real * other.real + other.imaginary * other.imaginary;
		if (denominator == 0) {
			throw runtime_error("Can't divide by zero");
		}

		double newReal = (real * other.real + imaginary * other.imaginary) / denominator;
		double newImaginary = (imaginary * other.real - real * other.imaginary) / denominator;

		return Complex(newReal, newImaginary);
	}

	friend ostream& operator<< (ostream& os, const Complex& c);		// declared in the class for access
};

ostream& operator<< (ostream& os, const Complex& c) {		// output format
	os << c.real;		// just print the real number
	if (c.imaginary >= 0)
		os << " + " << c.imaginary << "i";		// if positive, add it then follow with an i
	else
		os << " - " << -c.imaginary << "i";		// minus, but then swap the sign
	return os;
}

int main() {
	Complex x(5, -4.2);
	Complex y(2, 5.7);

	cout << "x is " << x << " and y is " << y << endl;
	cout << "addition: " << x + y << endl;
	cout << "subtraction: " << x - y << endl;
	cout << "multiplication: " << x * y << endl;
	cout << "division: " << x / y << endl;
}
