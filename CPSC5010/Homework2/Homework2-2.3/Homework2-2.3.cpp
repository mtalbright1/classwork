
#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> leapYears;      // dynamically sized array for storing leap years
    for (int i = 2001; i <= 2100; i++) {
        if ((i % 4 == 0) && (i % 100 != 0 || i % 400 == 0)) {       // if divisble by 4 but not 100 unless also divisble by 400
            leapYears.push_back(i);
        }
    }
    int counter = 0;
    for (int i : leapYears) {
        cout << i << " ";
        counter++;
        if (counter == 10) {
            cout << endl;
            counter = 0;
        }
    }
}