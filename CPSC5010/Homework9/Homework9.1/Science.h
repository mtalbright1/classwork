#pragma once
#include "Student.h"

class Science : public Student {
private:
	string major;

public:
	Science(string major, string name);
	~Science();
	void getName();
	void getMajor();
};

