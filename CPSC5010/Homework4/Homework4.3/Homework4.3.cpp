
#include <iostream>
using namespace std;

int main() {
    double weeklyHours[3][7] = {        // three employees, seven days
        {0, 8, 8, 8, 8, 8, 0},      // 40 hours
        {0, 4, 4, 4, 4, 4, 0},      // 20 hours
        {12, 0, 0, 0, 0, 0, 12}     // 24 hours
    };
    
    double totalHours[3];       // stores weekly hour sums
    for (int i = 0; i < 3; i++) {
        double sum = 0;
        for (int j = 0; j < 7; j++) {
            sum += weeklyHours[i][j];
        }
        totalHours[i] = sum;
    }
    
    
    for (int i = 0; i < 3; i++) {       // print all three employees' hours

        double mostHours = 0;
        int employeeIndex;
        for (int j = 0; j < 3; j++) {       // find the employee with the most hours
            if (totalHours[j] > mostHours) {
                mostHours = totalHours[j];
                employeeIndex = j;
            }
        }

        cout << "Employee " << employeeIndex << " worked " << totalHours[employeeIndex] << " hours." << endl;
        totalHours[employeeIndex] = 0;      // removes the printed employee from consideration
    }
}