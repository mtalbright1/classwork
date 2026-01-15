
#include <iostream>
#include <string>
using namespace std;

int main() {
    
    cout << "Please think of a number 1-100." << endl;
    int guess = 50;     // first guess is in the middle
    cout << "My initial guess is " << guess << ". Answer 'too high', 'too low', or 'correct'." << endl;

    string response;
    getline(cin, response);     // must get the whole line to include the space

    int floor = 0;      // closes in around the number by using previous incorrect guesses as boundaries
    int ceiling = 101;
    int attempts = 1;       // tracks number of attempts

    while (response != "correct") {
        if (response == "too low") {
            floor = guess;      // raises the floor because the guess was too low
            guess += (ceiling - floor) / 2;     // new guess is halway between the floor and ceiling
        }
        else if (response == "too high") {
            ceiling = guess;
            guess -= (ceiling - floor) / 2;
        }
        cout << "My new guess is " << guess << "." << endl;     // keeps trying until correct
        attempts++;
        getline(cin, response);
    }

    cout << "I have guessed your number in " << attempts << " attempts.";
}

