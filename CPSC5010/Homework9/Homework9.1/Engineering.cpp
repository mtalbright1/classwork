#include "Engineering.h"

Engineering::Engineering(string name, string major) : Student(name) {		// pass the name the Student(string name)
	this->major = major;
};

Engineering::~Engineering() {};

void Engineering::getName() {
	Student::getName();
};

void Engineering::getMajor() {
	cout << major << endl;
};