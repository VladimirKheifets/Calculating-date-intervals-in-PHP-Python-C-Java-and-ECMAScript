
/*
    Calculating date intervals in C++
    Version: 2.0, 2025-02-22
    Author: Vladimir Kheifets vladimir.kheifets@online.de
    Copyright © 2025 Vladimir Kheifets All Rights Reserved
*/

#include <langinfo.h>
#include <locale.h>
#include <iostream>
#include <string>
#include <regex>
#include <map>

using namespace std;

//------------------------------------------------------------

vector<string> getMonths() {
   const nl_item nl_months[12] = {MON_1, MON_2, MON_3, MON_4, MON_5, MON_6,
                                   MON_7, MON_8, MON_9, MON_10, MON_11, MON_12};
    int i;
    vector<string> months;
    setlocale(LC_ALL, "");
    for (i = 0; i < 12; i++) {
        months.push_back(nl_langinfo(nl_months[i]));
    }
    return months;
}

//------------------------------------------------------------

int getMonthNumber(string month) {
    vector<string> months = getMonths();
    auto it = find(months.begin(), months.end(), month);
    return distance(months.begin(), it);
}

//------------------------------------------------------------

void viewIntervals( map< int, vector<string> > intervals){
    vector<string> months;
    months = getMonths();
    cout << "-----------------------------"  << endl << endl;
    cout << "Intervals:" << endl << endl;

    for (const auto& pair : intervals)
    {
        int mI = pair.first;
        string mN = "Days in " + months[mI] + ":";
        cout << mN << endl;
        vector interval = pair.second;
        for( string numberDay : interval)
        {
            cout << numberDay << endl;
        }
         cout <<  endl;
    }
}

//------------------------------------------------------------

map< int, vector <string> > getIntervals(vector<string> inData) {

   vector<string> months;
   months = getMonths();

    map< int, vector<int> > groups;
    string interval;
    string valBefor;
    string val;
    vector <int> group;
    map< int, vector <string> > intervals;

    regex rS1("^([a-zA-Z]+).+(\\d+)$");
    regex rS2("(\\d{4})\\-(\\d{2})\\-(\\d{2})");
    regex rR("\\ ");
    smatch m;

    for (string item : inData)
    {
        int mI;
        int d;

        if(regex_search(item, m, rS1))
        {
            mI = getMonthNumber(m[1]);
            d = stoi(m[2]);
        }
        else if(regex_search(item, m, rS2))
        {
             mI = stoi(m[2]) - 1;
             d = stoi(m[3]);
        }
        else
            return intervals;

        groups[mI].push_back(d);
    }

    for (const auto& pair : groups)
    {
        int month = pair.first;
        group = pair.second;
        sort(group.begin(), group.end());

        int numberBefor = 0;
        for (int number : group)
        {
            val = to_string(number);
            if (numberBefor == 0)
                interval = val;
            else
            {
                if ((number - numberBefor) > 1)
                {
                    valBefor = to_string(numberBefor);
                    if (interval != valBefor)
                        interval += " - " + valBefor;
                    intervals[month].push_back(interval);
                    interval = val;
                }
            }
            numberBefor = number;
        }

        if (interval != val)
            interval += " - " + val;
        intervals[month].push_back(interval);
    }

    return intervals;
}

//----------------------------------------------------------------------------

int main()
{
    vector<string> data = {"January 8","January 7", "January 3","January 1", "January 4","January 5", "February 23", "February 24", "February 25", "February 26", "March 12", "March 13", "March 14", "April 1", "April 2", "April 3","May 10", "May 11", "May 12"};

    map< int, vector <string> > intervals = getIntervals(data);

    viewIntervals(intervals);

    //------------------------------------------------------

    data = {"2025-01-08","2025-01-07", "2025-01-03","2025-01-01", "2025-01-04","2025-01-05", "2025-02-23", "2025-02-24", "2025-02-25", "2025-02-26", "2025-03-12", "2025-03-13", "2025-03-14", "2025-04-01", "2025-04-02", "2025-04-03","2025-05-10", "2025-05-11", "2025-05-12"};

    intervals = getIntervals(data);

    viewIntervals(intervals);
    /*
        Intervals:

        Days in January
        1
        3 - 5
        7 - 8

        Days in February
        23 - 26

        Days in March
        12 - 14

        Days in April
        1 - 3

        Days in May
        10 - 12
    */

    return 0;
}