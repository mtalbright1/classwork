
#include <iostream>
using namespace std;

bool getIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, double &i1, double &i2);		// returns bool to declare if the points intersect or not

int main() {
	double x1, y1, x2, y2;
	cout << "Enter the end points for the first line segment in the format (x1 y1 x2 y2)" << endl;
	cin >> x1; cin >> y1; cin >> x2; cin >> y2;

	double x3, y3, x4, y4;
	cout << "Next, enter the end points for the second line segment in the format (x3 y3 x4 y4)" << endl;
	cin >> x3; cin >> y3; cin >> x4; cin >> y4;

	double i1, i2;		// these will be changed by the getIntersection method

	bool valid = getIntersection(x1, y1, x2, y2, x3, y3, x4, y4, i1, i2);		// passing i1 and i2 by reference will let the method change them
	if (valid == true) {
		cout << "The intersection point is (" << i1 << ", " << i2 << ")." << endl;
	}
	else {
		cout << "The line segments do not intersect." << endl;
	}
}

bool getIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, double &i1, double &i2) {

	double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
    
    if (denominator == 0) {		// a denominator of 0 means the lines are parallel and do not intersect
        return false;
    }

	double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
    double u = ((x1 - x3) * (y1 - y2) - (y1 - y3) * (x1 - x2)) / denominator;

	if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {		// between 0 and 1 means on the line
        i1 = x1 + t * (x2 - x1);		// calculates the intersection point's x and y
        i2 = y1 + t * (y2 - y1);
        return true;		// confirms that an intersection point exists
    }
	else {
		return false;		// the intersection is beyond one or both of the line segments
	}
}
