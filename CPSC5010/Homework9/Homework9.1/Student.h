#pragma once
#include <string>
#include <iostream>
using namespace std;

class Student {
private:
	string name;

public:
	Student(string name);
	~Student();
	void getName();
	virtual void getMajor() = 0;		// pure virtual function
};

