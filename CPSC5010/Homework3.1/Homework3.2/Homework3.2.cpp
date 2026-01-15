
#include <iostream>
#include <cstdlib>
using namespace std;

int main() {

	int randomNumbers[100];
	for (int i = 0; i < 100; i++) {		// fills the array with random integers between 0 and 9
		randomNumbers[i] = rand() % 10;
	}
	int count[10] = {0};		// makes an array filled with 0
	for (int i : randomNumbers) {
		count[i]++;		// increments that number's position in count
	}
	cout << "Count for each integer 0 - 9:" << endl;
	for (int i = 0; i < 10; i++) {
		cout << i << ": " << count[i] << endl;
	}
}