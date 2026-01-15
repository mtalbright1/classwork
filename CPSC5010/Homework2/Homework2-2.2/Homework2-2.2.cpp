
#include <iostream>
#include <cmath>
using namespace std;

bool overlap(int c1xyr[3], int c2xyr[3]);
bool inside(int c1xyr[3], int c2xyr[3]);

int main() {
    cout << "Enter the coordinates and radius of the first circle seperated by a space." << endl;
    int c1xyr[3];        // puts the input in an int array
    cin >> c1xyr[0] >> c1xyr[1] >> c1xyr[2];
    cout << "Enter the coordinates and radius of the second circle seperated by a space." << endl;
    int c2xyr[3];        // puts the input in an int array
    cin >> c2xyr[0] >> c2xyr[1] >> c2xyr[2];

    if (inside(c1xyr, c2xyr) == true) {     // checks with the inside function
        cout << "Circle 2 is inside circle 1.";
        return 0;
    }
    if (overlap(c1xyr,c2xyr) == true) {     // if it isn't inside, checks with the overlap function
        cout << "Circle 2 overlaps with circle 1.";
        return 0;
    }
    cout << "Circle 2 is neither inside nor overlaps with circle 1.";       // if neither
    return 0;
}

bool inside(int c1xyr[3], int c2xyr[3]) {
    double distance = sqrt(pow((c2xyr[0] - c1xyr[0]), 2) + pow((c2xyr[1] - c1xyr[1]), 2));      // calculates distance between centers
    if (distance <= abs(c1xyr[2] - c2xyr[2])) {
        return true;
    }
    return false;       // otherwise return false
}

bool overlap(int c1xyr[3], int c2xyr[3]) {
    double distance = sqrt(pow((c2xyr[0] - c1xyr[0]), 2) + pow((c2xyr[1] - c1xyr[1]), 2));
    if (distance <= c1xyr[2] + c2xyr[2]) {
        return true;
    }
    return false;
}
