
#include <iostream>
using namespace std;

void printBoard(char board[3][3]);
bool checkVictory(char board[3][3]);

int main() {
    char board[3][3] = {        // set the board to empty
        {' ',' ',' '},
        {' ',' ',' '},
        {' ',' ',' '}
    };
    printBoard(board);      // pritns initial, empty board

    char turnTracker = 'X';     // X goes first

    while (!checkVictory(board)) {      // loop until victory

        cout << "Player " << turnTracker << ": choose coordinates seperated by a space (row space column)." << endl;        // prompts current player
        int row, column;
        cin >> row;
        cin >> column;

        if (row > 2 || row < 0 || column > 2 || column < 0 || board[row][column] != ' ') {      // checks legality
            cout << "Not a legal move" << endl;
        }
        else {      // only proceed if move was legal
            board[row][column] = turnTracker;       // places current player's mark on the chosen coordinates
            printBoard(board);      // print the updated board

            if (turnTracker == 'X') {       // change the turn
                turnTracker = 'O';
            }
            else {
                turnTracker = 'X';
            }
        }  
    }

    if (turnTracker == 'X') {       // the player who made the last turn before victory is the winner
        cout << "Congratulations! Player O has won!";
    }
    else {
        cout << "Congratulations! Player X has won!";
    }
}

void printBoard(char board[3][3]) {
    cout << "     [0] [1] [2]" << endl;     // column headers
    cout << "     -----------" << endl;     // spacing
    for (int i = 0; i < 3; i++) {
        cout << "[" << i << "] | ";     // row headers
        for (int j = 0; j < 3; j++) {
            cout << board[i][j] << " | ";
        }
        cout << endl << "     -----------" << endl;     // horizontal lines
    }
}

bool checkVictory(char board[3][3]) {
    for (int i = 0; i < 3; i++) {
        if (board[i][0] == 'X' && board[i][1] == 'X' && board[i][2] == 'X') {       // checks for horizontal X victories
            return true;
        }
        if (board[0][i] == 'X' && board[1][i] == 'X' && board[2][i] == 'X') {       // checks for vertical X victories
            return true;
        }
        if (board[i][0] == 'O' && board[i][1] == 'O' && board[i][2] == 'O') {       // checks for horizontal O victories
            return true;
        }
        if (board[0][i] == 'O' && board[1][i] == 'O' && board[2][i] == 'O') {       // checks for vertical O victories
            return true;
        }
    }
    if (board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X') {       // checks for forward diagonal X victory
        return true;
    }
    if (board[2][0] == 'X' && board[1][1] == 'X' && board[2][0] == 'X') {       // checks for reverse diagonal X victory
        return true;
    }
    if (board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O') {       // checks for forward diagonal O victory
        return true;
    }
    if (board[2][0] == 'O' && board[1][1] == 'O' && board[2][0] == 'O') {       // checks for reverse diagonal O victory
        return true;
    }

    return false;
}