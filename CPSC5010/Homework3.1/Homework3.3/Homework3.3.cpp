
#include <iostream>
#include <vector>
using namespace std;

struct student {
    string name;
    int score;
};

int getBestStudentIndex(vector<student> students);

int main() {

    cout << "Enter the number of students." << endl;
    int numberOfStudents;
    cin >> numberOfStudents;

    vector<student> students(numberOfStudents);

    for (int i = 0; i < numberOfStudents; i++) {
        cout << "Student " << i + 1 << "'s name: ";
        cin >> students[i].name;
        cout << "Student " << i + 1 << "'s score (0 - 100): ";
        cin >> students[i].score;
    }

    cout << "Students by score (decreasing): " << endl;
    for (int i = 0; i < numberOfStudents; i++) {
        int bestIndex = getBestStudentIndex(students);
        cout << students[bestIndex].name << " - " << students[bestIndex].score << endl;
        students.erase(students.begin() + bestIndex);
    }
}

int getBestStudentIndex(vector<student> students) {
    int highestScore = 0;
    int bestIndex;
    for (int i = 0; i < students.size(); i++) {
        if (students[i].score >= highestScore) {
            highestScore = students[i].score;
            bestIndex = i;
        }
    }
    return bestIndex;
}

