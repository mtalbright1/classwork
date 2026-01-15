
#include <iostream>
using namespace std;

void reverse(int number);
int countDigits(int number);

int main() {
	cout << "Enter a number." << endl;		// gets input
	int number;
	cin >> number;
	reverse(number);		// runs reverse function
}

void reverse(int number) {

	int digits = countDigits(number);
	int* arr = new int[digits];		// dynamic array size

	for (int i = 0; i < digits; i++) {
		arr[i] = number % 10;		// stores the tens place, then removes the tens place
		number /= 10;
	}

	int result = 0;
	for (int i = 0; i < digits; i++) {
		result = result * 10 + arr[i];		// adds a zero, then adds the next digit
	}
	cout << result;		// prints result
}

int countDigits(int number) {		// returns digit count
	int digitCounter = 0;
	while (number != 0) {
		number /= 10;
		digitCounter++;
	}
	return digitCounter;
}
