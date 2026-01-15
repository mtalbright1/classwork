#pragma once
#include "Student.h"

class Engineering : public Student {
private:
	string major;
public:
	Engineering(string name, string major);
	~Engineering();
	void getName();
	void getMajor();
};

