#include <cassert>
#include <iostream>
using namespace std;

class Fraction {
private:
    int m_numerator;
    int m_denominator;

public:
    
    Fraction(int numerator = 0, int denominator = 1) : m_numerator(numerator), m_denominator(denominator) {     // Default constructor
        assert(denominator != 0);
    }

    Fraction(const Fraction& copy) : m_numerator(copy.m_numerator), m_denominator(copy.m_denominator) {     // Copy constructor
        // no need to check for a denominator of 0 here since copy must already be a valid Fraction
        cout << "Copy constructor called\n"; // just to prove it works
    }

    Fraction& operator= (const Fraction& fraction);         // Overloaded assignment
    Fraction operator+ (const Fraction& object);
    Fraction operator- (const Fraction& object);

    friend ostream& operator<<(ostream& out, const Fraction& f1);
};

ostream& operator<<(ostream& out, const Fraction& f1) {
    out << f1.m_numerator << "/" << f1.m_denominator;
    return out;
}

Fraction& Fraction::operator= (const Fraction& fraction) {      // A simplistic implementation of operator= (see better implementation below)

    m_numerator = fraction.m_numerator;         // do the copy
    m_denominator = fraction.m_denominator;

    return *this;       // return the existing object so we can chain this operator
}

Fraction Fraction::operator+ (const Fraction& object) {
    int newNumerator = this->m_numerator * object.m_denominator + this->m_denominator * object.m_numerator;
    int commonDenominator = this->m_denominator * object.m_denominator;
    return Fraction(newNumerator, commonDenominator);
}

Fraction Fraction::operator- (const Fraction& object) {
    int newNumerator = this->m_numerator * object.m_denominator - this->m_denominator * object.m_numerator;
    int commonDenominator = this->m_denominator * object.m_denominator;
    return Fraction(newNumerator, commonDenominator);
}

int main() {
    Fraction fiveThirds(5, 3);
    Fraction f;
    f = fiveThirds; // calls overloaded assignment
    cout << f << endl;;

    Fraction threeFifths(3, 5);
    Fraction g;
    g = fiveThirds + threeFifths;
    cout << g << endl;

    Fraction h;
    h = fiveThirds - threeFifths;
    cout << h << endl;
}
