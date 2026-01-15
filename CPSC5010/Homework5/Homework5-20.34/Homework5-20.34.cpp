
#include <iostream>
using namespace std;

bool eightQueens(int row, char board[8][8]);
bool check(int row, int column, char board[8][8]);

int main() {
	char board[8][8] = {
		{'.', '.', '.', '.', '.', '.', '.', '.'},
		{'.', '.', '.', '.', '.', '.', '.', '.'},
		{'.', '.', '.', '.', '.', '.', '.', '.'},
		{'.', '.', '.', '.', '.', '.', '.', '.'},
		{'.', '.', '.', '.', '.', '.', '.', '.'},
		{'.', '.', '.', '.', '.', '.', '.', '.'},
		{'.', '.', '.', '.', '.', '.', '.', '.'},
		{'.', '.', '.', '.', '.', '.', '.', '.'},
	};

	eightQueens(0, board);		// modify the board, starting at row 0

	// print the modified board
	cout << "  - - - - - - - -  " << endl;
	for (int i = 0; i < 8; i++) {
		cout << "| ";
		for (int j = 0; j < 8; j++) {
			cout << board[i][j] << ' ';
		}
		cout << "|" << endl;
	}
	cout << "  - - - - - - - -  " << endl;
}

bool eightQueens(int row, char board[8][8]) {
	if (row == 8) {		// finished after rows 0-7 are placed, base case
		return true;
	}
	for (int column = 0; column < 8; column++) {		// for each column's square in this row
		if (check(row, column, board)) {		// check if that placement is legal
			board[row][column] = 'Q';		// if legal, place a queen
			if (eightQueens(row + 1, board)) {		// recur until the base case
				return true;
			}
			else {
				board[row][column] = '.';		// if unable to find a legal spot, change back to .
			}
		}
	}
	return false;
}

bool check(int row, int column, char board[8][8]) {		// check if placement is legal
	for (int i = 0; i < 8; i++) {
		if (board[i][column] == 'Q') {
			return false;		// there's a queen in the same column
		}
	}

	// Left-to-right diagonal: A queen at (row, col) is on the same left-to-right diagonal as another queen at (i, j) if row - col == i - j.
	for (int i = 0; i < 8; i++) {
		for (int j = 0; j < 8; j++) {
			if (board[i][j] == 'Q') {
				if (abs(row - i) == abs(column - j)) {
					return false; // Queen found on the left-to-right diagonal
				}
			}
		}
	}

	// Right-to-left diagonal: A queen at (row, col) is on the same right-to-left diagonal as another queen at (i, j) if row + col == i + j.
	for (int i = 0; i < 8; i++) {
		for (int j = 0; j < 8; j++) {
			if (board[i][j] == 'Q') {
				if (row + column == i + j) {
					return false; // Queen found on the right-to-left diagonal
				}
			}
		}
	}
	return true;
}

