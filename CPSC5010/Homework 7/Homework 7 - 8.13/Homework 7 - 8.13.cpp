
#include <iostream>
using namespace std;

class Location {
public:
    int row, column;
    double maxValue;

    Location(int row, int column, double maxValue) {        // regular constructor to create Location classes
        this->row = row;
        this->column = column;
        this->maxValue = maxValue;
    }
};

Location locateLargest(double** a, int rows, int columns);

int main() {

    cout << "Enter number of rows in the two-dimensional array" << endl;
    int rows;
    cin >> rows;
    cout << "Enter the number of columns" << endl;
    int columns;
    cin >> columns;

    double** array = new double* [rows];        // a pointer to an array of pointers makes a dynamic two-dimensional array
    for (int i = 0; i < rows; i++) {
        array[i] = new double[columns];     // each of array's elements gets an array of size 'columns'
    }

    cout << "Making a " << rows << " x " << columns << " two-dimensional array: " << endl;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            cout << "Enter the value of the element at position [" << i << "][" << j << "]: ";
            cin >> array[i][j];     // stores the input in that spot
        }
    }

    Location result = locateLargest(array, rows, columns);
    cout << "The largest element in the array is " << result.maxValue << " at position [" << result.row << "][" << result.column << "]." << endl;

    for (int i = 0; i < rows; i++) {
        delete[] array[i];      // delete each column in the array
    }
    delete[] array;     // delete the rows
}

Location locateLargest(double** a, int rows, int columns) {
    double maxValue = a[0][0];      // start by assuming the max is the first spot
    int maxRow = 0; int maxColumn = 0;

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            if (a[i][j] > maxValue) {
                maxValue = a[i][j];
                maxRow = i;
                maxColumn = j;
            }
        }
    }

    return Location(maxRow, maxColumn, maxValue);       // creates a new Location and returns it
}