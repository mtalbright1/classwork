
#include <iostream>
using namespace std;

void reverseDisplay(int value);

int main() {
	cout << "Enter an integer." << endl;
	int value;
	cin >> value;

	cout << "Reversed: ";
	reverseDisplay(value);
}

void reverseDisplay(int value) {
	if (value != 0) {
		cout << value % 10;
		value /= 10;
		reverseDisplay(value);
	}
}