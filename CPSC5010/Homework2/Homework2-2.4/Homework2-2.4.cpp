
#include <iostream>
using namespace std;

int calculateWeekday(int month, int weekday, bool leapYear);
string convertMonth(int month);
string convertWeekday(int weekday);

int main() {
    cout << "Enter the year and a number representing the day of the week, where 0 is Sunday and 6 is Saturday." << endl;
    int year, weekday;
    cin >> year >> weekday;
    bool leapYear = false;

    if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)) {      // if leap year
        leapYear = true;
    }

    for (int month = 0; month < 12; month++) {     // loops through months
        weekday = calculateWeekday(month, weekday, leapYear);
        cout << convertMonth(month+1) << " 1, " << year << " is " << convertWeekday(weekday) << endl; 
    }
}

int calculateWeekday(int month, int weekday, bool leapYear) {
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {       // if 31 days
        weekday = (weekday + 31) % 7;       // adds the appropriate amount of days, then keeps in within 0-6
    }
    else if (month == 4 || month == 6 || month == 9 || month == 11) {       // if 30 days
        weekday = (weekday + 30) % 7;
    }
    else if (month == 2 && leapYear) {       // if 29 days
        weekday = (weekday + 29) % 7;
    }
    else if (month == 2 && !leapYear) {      // if 28 days
        weekday = (weekday + 28) % 7;
    }
    return weekday;     // month 1 passes through without changing the weekday
}

string convertMonth(int month) {        // converts 1-12 to month names
    switch (month) {
    case 1: return "January";
    case 2: return "February";
    case 3: return "March";
    case 4: return "April";
    case 5: return "May";
    case 6: return "June";
    case 7: return "July";
    case 8: return "August";
    case 9: return "September";
    case 10: return "October";
    case 11: return "November";
    case 12: return "December";
    default: return "invalid month";        // edge case
    }
}

string convertWeekday(int weekday) {        // converts 0-6 to day names
    switch (weekday) {
    case 0: return "Sunday";
    case 1: return "Monday";
    case 2: return "Tuesday";
    case 3: return "Wednesday";
    case 4: return "Thursday";
    case 5: return "Friday";
    case 6: return "Saturday";
    default: return "invalid weekday";       // edge case
    }
}
