#include "Medicine.h"

Medicine::Medicine(string name, string major) : Student(name) {
	this->major = major;
}

Medicine::~Medicine() {}

void Medicine::getName() {
	Student::getName();
}

void Medicine::getMajor() {
	cout << major << endl;
}
