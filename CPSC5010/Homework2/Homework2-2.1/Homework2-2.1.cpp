
#include <iostream>
using namespace std;

bool checkValidity(double a, double b, double c);

int main() {
    cout << "Enter side a";     // get sides from user
    double a;
    cin >> a;
    cout << "Enter side b";
    double b;
    cin >> b;
    cout << "Enter side c";
    double c;
    cin >> c;
    if (checkValidity(a, b, c) == false) {      // check if these sides make a valid triangle
        cout << "This triangle is not valid.";
        return 1;
    }
    else {
        cout << "The perimeter is: " << a + b + c << endl;        // output perimeter
        return 0;
    }
}

bool checkValidity(double a, double b, double c) {
    if (a + b < c) {        // the sum of any two sides should be greater than the third
        return false;       // if not return false
    }
    if (a + c < b) {
        return false;
    }
    if (b + c < a) {
        return false;
    }
    return true;        // otherwise return true
}