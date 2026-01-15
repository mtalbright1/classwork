
#include <iostream>
#include <vector>
using namespace std;

vector<int> makeDistinct(int input[]);

int main() {
    cout << "Enter ten integers seperate by a space." << endl;      // prompts the user
    int input[10];
    for (int i = 0; i < 10; i++) {      // store all ten integers into the input array
        cin >> input[i];
    }
    
    vector<int> output = makeDistinct(input);
    for (int i : output) {
        cout << i << " ";
    }
}

vector<int> makeDistinct(int input[]) {

    vector<int> output;     // use a vector for dynamic size and the ability to return it
    for (int i = 0; i < 10; i++) {      // loops through input array
        bool isDuplicate = false;       // starts as false for every i
        for (int j = 0; j < output.size(); j++) {      // loops through output vector
            if (input[i] == output[j]) {
                isDuplicate = true;     // if that number is already in output
            }
        }
        if (isDuplicate == false) {     // still false means a duplicate wasn't found
            output.push_back(input[i]);
        }
    }
    return output;
}