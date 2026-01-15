#include "Student.h"

Student::Student(string name) {		// regualr constructor
	this->name = name;
}

Student::~Student() {}		// default constructor

void Student::getName() {
	cout << name << endl;
}

// void getMajor() does not need to be here because this is the base class
