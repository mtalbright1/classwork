
#include <iostream>
#include "Student.h"
#include "Engineering.h"
#include "Medicine.h"
#include "Science.h"
using namespace std;

void output(Student* students[], int size);

int main() {
    
    Engineering mark ("Mark", "Engineering");       // creating objects of derived classes
    Medicine mary ("Mary", "Medicine");
    Science catherine("Catherine", "Science");

    Student* students[3];        // makes an array of pointers of size three
    students[0] = &mark;
    students[1] = &mary;
    students[2] = &catherine;
    output(students, 3);
}

void output(Student* students[], int size) {       // takes an array of student pointers and uses their functions
    for (int i = 0; i < size; i++) {
        students[i]->getName();
        students[i]->getMajor();
    }
}