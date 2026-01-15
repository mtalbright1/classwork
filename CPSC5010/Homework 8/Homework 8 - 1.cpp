
#include <iostream>
#include <cmath>        // for power function
using namespace std;

class Point {
public:
    double x; double y;

    Point() {       //default constructor
        x = 0; y = 0;
    }

    Point(double x, double y) {
        this->x = x;
        this->y = y;
    }

    double getX() {     // accessors
        return x;
    }

    double getY() {
        return y;
    }
};

class Circle2D {
public:
    Point center;
    double radius;
    const double pi = 3.14159265358979323846;       // easiest way to implement pi

    Circle2D() {        // default constructor
        center.x = 0;
        center.y = 0;
        radius = 1;
    }

    Circle2D(double x, double y, double radius) {       // regular constructor
        center.x = x;
        center.y = y;
        this->radius = radius;
    }

    Point getCenter() {
        return center;
    }

    double getRadius() {
        return radius;
    }

    double getArea() {
        return pi * pow(radius, 2);
    }

    double getPerimeter() {
        return pi * 2 * radius;
    }

    bool contains(double x, double y) {
        double distance = sqrt(pow((center.x - x), 2) + pow((center.y - y), 2));
        if (distance < radius) {        // the point is closer to the center of the circle than the circle's radius
            return true;
        }
        else {
            return false;
        }
    }

    bool contains(Circle2D circle) {
        double distance = sqrt(pow((center.x - circle.center.x), 2) + pow((center.y - circle.center.y), 2));
        double givenRadius = circle.getRadius();
        if (radius >= (distance + givenRadius)) {       // the distance to the given circle's center plus its radius
            return true;
        }
        else {
            return false;
        }
    }

    bool overlaps(Circle2D circle) {
        double distance = sqrt(pow((center.x - circle.center.x), 2) + pow((center.y - circle.center.y), 2));
        double givenRadius = circle.getRadius();
        if (distance < (radius + givenRadius)) {        // if their centers are closer than the sum of their radii
            return true;
        }
        else {
            return false;
        }
    }
};

int main() {
    Circle2D c1(2, 2, 5.5);
    cout << "c1 is a circle with center (2,2) and radius 5.5" << endl;
    cout << "The area of c1 is: " << c1.getArea() << endl;
    cout << "The perimeter (circumference) is: " << c1.getPerimeter() << endl;
    cout << boolalpha;      // converts 0 and 1 to false and true
    cout << "A point (3,3) is contained in c1: " << c1.contains(3, 3) << endl;
    cout << "A circle with center (4,5) and radius 10.5 is contained in c1: " << c1.contains(Circle2D(4, 5, 10.5)) << endl;
    cout << "A circle with center (3,5) and radius 2.3 overlaps c1: " << c1.overlaps(Circle2D(3, 5, 2.3)) << endl;
}
