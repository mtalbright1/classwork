#include "Science.h"

Science::Science(string name, string major) : Student(name) {
	this->major = major;
}

Science::~Science() {}

void Science::getName() {
	Student::getName();
}

void Science::getMajor() {
	cout << major << endl;
}
