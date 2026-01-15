
#include <iostream>
#include <vector>
using namespace std;

template<typename value>
class LinkedList {
private:
	vector<value> list;

public:
	void insertAtEnd(value v) {
		list.push_back(v);
	}
	void deleteByKey(value key) {
		for (auto i = list.begin(); i != list.end(); ) {		// make an iterator of auto type, left the increment empty
			if (*i == key)		// if what i points at is equal to the key...
				i = list.erase(i);		// erase() returns the next element
			else
				++i;		// increments, then passes
		}
	}
	void displayList() {
		for (auto i = list.begin(); i != list.end(); ++i) {
			cout << *i << ", ";
		}
		cout << endl;
	}

	LinkedList operator+ (const LinkedList& otherList) {
		LinkedList newList;		// make a new list
		for (auto i = list.begin(); i != list.end(); ++i) {		// add the first list
			newList.list.push_back(*i);
		}
		for (auto i = otherList.list.begin(); i != otherList.list.end(); ++i) {		// add the second list
			newList.list.push_back(*i);
		}
		return newList;
	}

	friend ostream& operator<< (ostream& os, const LinkedList& list) {
		for (auto i = list.list.begin(); i != list.list.end(); ++i) {
			os << *i;
			if (i + 1 != list.list.end()) {		// as long as it's not the end, add a comma
				os << ", ";
			}
		}
		return os;
	}
};

int main() {
	LinkedList<int> list1;
	list1.insertAtEnd(1);
	list1.insertAtEnd(2);
	list1.insertAtEnd(3);

	LinkedList<int> list2;
	list2.insertAtEnd(4);
	list2.insertAtEnd(5);
	list2.insertAtEnd(6);

	cout << "List 1: " << list1 << endl;
	cout << "List 2: " << list2 << endl;
	
	LinkedList<int> list3 = list1 + list2;
	cout << "Combined lists: " << list3 << endl;
}
