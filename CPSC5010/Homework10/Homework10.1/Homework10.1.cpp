
#include <iostream>
#include <vector>
using namespace std;

template <typename value>
class Stack {
private:
	vector<value> stack;		// using vector for dynamic sizing

public:
	void push(const value& v) {		// const prevents change for security, passing by reference is faster than making a copy
		stack.push_back(v);
	}

	void pop() {
		if (!stack.empty()) {		// only pop if there's something to pop
			stack.pop_back();
		}
	}

	value top() {
		return stack.back();		// returns the value on the back of the stack
	}
};

int main() {
	Stack<int> intStack;
	intStack.push(1);
	intStack.push(2);
	cout << "Top of the int stack: " << intStack.top() << endl;
	intStack.pop();
	cout << "Top of the int stack after pop: " << intStack.top() << endl;

	Stack<string> stringStack;
	stringStack.push("one");
	stringStack.push("two");
	cout << "Top of the string stack: " << stringStack.top() << endl;
	stringStack.pop();
	cout << "Top of the string stack after pop: " << stringStack.top() << endl;
}
