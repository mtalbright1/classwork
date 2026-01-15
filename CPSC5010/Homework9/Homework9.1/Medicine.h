#pragma once
#include "Student.h"

class Medicine : public Student {
private: 
	string major;

public:
	Medicine(string name, string major);
	~Medicine();
	void getName();
	void getMajor();
};

