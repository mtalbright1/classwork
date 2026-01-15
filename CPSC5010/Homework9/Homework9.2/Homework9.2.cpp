
#include <iostream>
using namespace std;

template <class num>
void Calculate(num& x, num& y) {
	num addition = x + y;
	num subtraction = x - y;
	num product = x * y;
	num division = x / y;

	cout << "Numbers are: " << x << " and " << y << endl;
	cout << "Addition is: " << addition << endl;
	cout << "Subtraction is: " << subtraction << endl;
	cout << "Product is: " << product << endl;
	cout << "Division is: " << division << endl;
}

int main() {
	int x1 = 2;
	int y1 = 1;

	cout << "Int results: " << endl;
	Calculate(x1, y1);
	cout << endl;

	float x2 = 2.4;
	float y2 = 1.2;

	cout << "Float results: " << endl;
	Calculate(x2, y2);
}
