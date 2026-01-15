
#include <random>
#include <iostream>
using namespace std;

int main() {
	
	mt19937 gen(random_device{}());		// Mersenne Twister algorithm using a period of 19937 and requesting device randomness for a seed
	uniform_int_distribution<int> dis(1, 100);		// distributed uniformly between 1-100
	int randomNumber = dis(gen);		// calls for a random number

	cout << "Try to guess the number 1-100." << endl;
	cout << "(it is " << randomNumber << ")" << endl;

	int guess;
	cin >> guess;

	int guessCounter = 0;
	while (guess != randomNumber) {
		if (guess > randomNumber) {
			guessCounter++;
			cout << "too high, please try again" << endl;
			cin >> guess;
		}
		else if (guess < randomNumber) {
			guessCounter++;
			cout << "too low, please try again" << endl;
			cin >> guess;
		}
	}
	cout << "correct!" << endl;
	cout << "Number of guesses taken: " << guessCounter;
}
