
#include <iostream>
using namespace std;

int findGCD(int m, int n);

int main() {
	cout << "Enter two integers seperated by a space." << endl;
	int m, n;
	cin >> m >> n;

	cout << "The GCD is " << findGCD(m, n);
}

//if  m% n is 0,   gcd(m, n) is n;   Otherwise, gcd(m, n) is gcd(n, m % n).
int findGCD(int m, int n) {
	if (m % n == 0) {
		return n;
	}
	else {
		return findGCD(n, m % n);
	}
}
