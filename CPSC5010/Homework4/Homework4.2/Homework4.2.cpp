
#include <iostream>
using namespace std;

bool isPrime(int number);

int main() {
    for (int i = 2; i <= 1000; i++) {       // up to 1000, skips 0 and 1
        if (isPrime(i) && isPrime(i+2)) {
            cout << "(" << i << ", " << i + 2 << ")" << endl;
        }
    }
}

bool isPrime(int number) {
    if (number == 2) {      // 2 is prime
        return true;
    }
    for (int i = 2; i < number; i++) {
        if (number % i == 0) {      // if divisible by any lower number
            return false;       // not prime
        }
    }
    return true;        // otherwise, prime
}