
#include <iostream>
using namespace std;

string decimalToHex(int value);

int main() {
	cout << "Enter a decimal integer." << endl;
	int value;
	cin >> value;

	cout << decimalToHex(value);
}

// repeatedly divide by 16. Treat the remainder. 10=a, 15=f
string decimalToHex(int value) {
	if (value == 0) {
		return "";		// base case
	}

	int remainder;
	remainder = value % 16;
	string hex = "";

	if (remainder < 10) {		// adds the current digit to the left side of hex
		hex = char(remainder + '0') + hex;		// bumps the int up to its ascii version
	}
	else {
		hex = char(remainder - 10 + 'a') + hex;		// if remainder is 10, it'll add 'a'. 11 will add 'b', etc
	}

	value /= 16;
	return decimalToHex(value) + hex;		// adds the next digit to the left side of hex
}