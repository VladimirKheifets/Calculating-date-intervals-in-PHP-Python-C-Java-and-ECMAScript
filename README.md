# Calculating date intervals in PHP, Python, C++, Java and ECMAScript

### Version: 2.0, 2025-02-22

Author: Vladimir Kheifets <vladimir.kheifets@online.de>

Copyright &copy; 2025 Vladimir Kheifets All Rights Reserved

## Problem Statement

An unordered set of dates for one year is given.
It is required to obtain an ordered list of months and for each month ordered date intervals.
Dates must be specified in two formats:
- full month name and day;
- ISO date format.

## For example:

### Set of dates:
```
"January 8", "May 11", "January 3","January 1", "April 2", "January 4","January 5", "May 11","February 24", "April 1", "February 25", "February 26", "March 12", "January 7", "March 13", "January 3", "March 14", "February 24", "April 3","May 10", "May 12","February 23"
```
or
```
"2025-05-10", "2025-03-14","2025-05-11", "2025-01-04", "2025-03-13", "2025-01-08","2025-05-12", "2025-01-03",  "2025-04-03","2025-02-24", "2025-01-01", "2025-01-05", "2025-02-23",  "2025-02-25", "2025-02-26", "2025-03-12",  "2025-04-01", "2025-04-02", "2025-01-07
```
### Result:

```

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

```

In order to compare the capabilities of programming languages, the task should be
implemented in PHP, Python, C++, Java and ECMAScript.

## PHP
```php
<?php

/*
	Calculating date intervals in PHP
	Version: 2.0, 2025-02-22
	Author: Vladimir Kheifets vladimir.kheifets@online.de
	Copyright © 2025 Vladimir Kheifets All Rights Reserved
*/

function getIntervals($inData){

	$months = getMonths();

	foreach((array)$inData as $item){

		$pattern1 = "/^(\D+)(\d+)$/";
		$pattern2 = "/^(\d{4})\-(\d{2})\-(\d{2})$/";
		if(preg_match($pattern1, $item, $out))
		{
			$month = array_search(trim($out[1]), $months);
			$day = intval($out[2]);
		}
		else if(preg_match($pattern2, $item, $out2))
		{
			$month = intval($out2[2])-1;
			$day = intval($out2[3]);
		}
		else
			return false;

		$groups[$month][] = $day;
	}

	ksort($groups);

	foreach($groups as $preff => $group){

		sort($group);

		//---------------------------------
		$interval = "";
        $numberBefor = 0;
		foreach($group as  $number)
		{
			if($numberBefor == 0)
				$interval = $number;
			else
			{
				if(($number - $numberBefor) > 1)
				{
					if($interval != $numberBefor)
						$interval .= " - $numberBefor";
					$intervals[$preff][] = $interval;
					$interval = $number;
				}
			}
            $numberBefor = $number;
		}
		if($interval != $number)
			$interval .= " - $number";
		$intervals[$preff][] = $interval;
	}
	return $intervals;
}

//---------------------------------------------------------

function viewIntervals($intervals){
	if($intervals)
	{
		$months = getMonths();
		$out = "-----------------------------------<br>";
		$out .= "Intervals:<br>";
		foreach($intervals  as $month => $interval){
			$out .= "Days in  {$months[$month]}:<br>";
			foreach($interval as $numberDays)
			{
				$out .= "$numberDays<br>";
			}
			$out .= "<br>";
		}
		echo $out;
	}
}

//---------------------------------------------------------

function getMonths(){
  return  array_map(fn (int $m): string => (DateTime::createFromFormat('m-d', "$m-1")
  -> format('F')),  range(1, 12),);
}

//---------------------------------------------------------

$data = ["January 8", "May 11", "January 3","January 1", "April 2", "January 4","January 5", "May 11","February 24", "April 1", "February 25", "February 26", "March 12", "January 7", "March 13", "January 3", "March 14", "February 24", "April 3","May 10", "May 12","February 23"];


$intervals = getIntervals($data);
viewIntervals($intervals);

//-------------------------------------------------------

$data = ["2025-05-10", "2025-03-14","2025-05-11", "2025-01-04", "2025-03-13", "2025-01-08","2025-05-12", "2025-01-03",  "2025-04-03","2025-02-24", "2025-01-01", "2025-01-05", "2025-02-23",  "2025-02-25", "2025-02-26", "2025-03-12",  "2025-04-01", "2025-04-02", "2025-01-07"];


$intervals = getIntervals($data);
viewIntervals($intervals);

/*

-----------------------------------
	Intervals:
	Days in January:
	1
	3 - 5
	7 - 8

	Days in February:
	23 - 26

	Days in March:
	12 - 14

	Days in April:
	1 - 3

	Days in May:
	10 - 12

	-----------------------------------
	Intervals:
	Days in January:
	01
	03 - 05
	07 - 08

	Days in February:
	23 - 26

	Days in March:
	12 - 14

	Days in April:
	01 - 03

	Days in May:
	10 - 12

*/
```
## Pyton
```pyton

"""
	Calculating date intervals in Python
	Version: 2.0, 2025-02-22
	Author: Vladimir Kheifets vladimir.kheifets@online.de
	Copyright © 2025 Vladimir Kheifets All Rights Reserved
"""


import re
import datetime
import calendar

def getintervals(inData):

  groups = {}
  patt1 = re.compile(r'^(\D+)(\d+)$')
  patt2 = re.compile(r'^(\d{4})\-(\d{2})\-(\d{2})$')
  intervals = {}

  for item in inData:
    if re.match(patt1,item):
      out = patt1.findall(item)
      month = list(calendar.month_name).index(out[0][0].strip());
      day = int(out[0][1])
    elif re.match(patt2,item):
      out = patt2.findall(item)
      month = int(out[0][1])
      day = int(out[0][2])
    else:
      return intervals

    if month in groups:
      groups[month].append(day)
    else:
      groups[month]=[day]

  monthGroups = sorted(list(groups.keys()))

  for preff in monthGroups:
    group = groups[preff]
    group.sort()
    numberBefor = 0
    for number in group:
      val = str(number)
      if numberBefor == 0:
        interval = val
      else:
        if number - numberBefor > 1:
          valBefor = str(numberBefor)
          if interval != valBefor:
            interval += " - " + valBefor
          if preff in intervals:
            intervals[preff].append(interval)
          else:
            intervals[preff]=[interval]
          interval = val

      numberBefor = number

    if interval != val:
      interval += " - " + val;
    if preff in intervals:
       intervals[preff].append(interval)
    else:
       intervals[preff] = [interval]

  return intervals;

#--------------------------------------

def viewIntervals(intervals):
  out = "---------------------\n\n"
  out += "intervals:\n\n"

  for month in  intervals:
    out +=  "Days in  " +    calendar.month_name[month] + "\n"
    for numberDays in intervals[month]:
       out +=  numberDays + "\n"
    out +=  "\n"
  print(out)

#--------------------------------------

data = ["January 8", "May 11", "January 3","January 1", "April 2", "January 4","January 5", "May 11","February 24", "April 1", "February 25", "February 26", "March 12", "January 7", "March 13", "January 3", "March 14", "February 24", "April 3","May 10", "May 12","February 23"];

intervals = getintervals(data)
viewIntervals(intervals)

#------------------------------------------------------------

data = ["2025-05-10", "2025-03-14","2025-05-11", "2025-01-04", "2025-03-13", "2025-01-08","2025-05-12", "2025-01-03",  "2025-04-03","2025-02-24", "2025-01-01", "2025-01-05", "2025-02-23",  "2025-02-25", "2025-02-26", "2025-03-12",  "2025-04-01", "2025-04-02", "2025-01-07"];

intervals = getintervals(data)
viewIntervals(intervals)

"""
Output:

---------------------

intervals:

Days in  January
1
3 - 5
7 - 8

Days in  February
23 - 26

Days in  March
12 - 14

Days in  April
1 - 3

Days in  May
10 - 12


---------------------

intervals:

Days in  January
1
3 - 5
7 - 8

Days in  February
23 - 26

Days in  March
12 - 14

Days in  April
1 - 3

Days in  May
10 - 12

"""
```
## C++
```cpp

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

    regex rS1("^(\\D+)(\\d+)$");
    regex rS2("(\\d{4})\\-(\\d{2})\\-(\\d{2})");
    regex rR("\\ ");
    smatch m;

    for (string item : inData)
    {
        string mN;
        int mI;
        int d;

        if(regex_search(item, m, rS1))
        {
            mN = m[1];
            mN = regex_replace(mN, rR, "");
            mI = getMonthNumber(mN);
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
```
## Java
```java

/*
	Calculating date intervals in Java
	Version: 2.0, 2025-02-22
	Author: Vladimir Kheifets vladimir.kheifets@online.de
	Copyright © 2025 Vladimir Kheifets All Rights Reserved
*/

import java.util.*;
import java.util.regex.*;
import java.util.function.Function;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;

public class Main {


    public static Map< Integer, Vector<String>> getIntervals(String[] inData)
    {
        Map< Integer, Vector<Integer> > groups = new HashMap<>();
        //List<String> intervals = new ArrayList<String>();
        Map< Integer, Vector<String> > intervals = new HashMap<>();

        String[] months = new DateFormatSymbols().getMonths();

        Pattern p1 = Pattern.compile("^(\\D+)(\\d+)$");
        Pattern p2 = Pattern.compile("(\\d{4})\\-(\\d{2})\\-(\\d{2})");
        String mN = "";
        int mI = 0;
        int d = 0;

        for(String item : inData)
        {
            Matcher m1 = p1.matcher(item);
            boolean b1 = m1.matches();
            Matcher m2 = p2.matcher(item);
            boolean b2 = m2.matches();

            if(b1)
            {
              mN = m1.group(1).trim();
              mI = Arrays.asList(months).indexOf(mN);
              d = Integer.parseInt(m1.group(2));
            }
            else if (b2)
            {
              mI = Integer.parseInt(m2.group(2))-1;
              d = Integer.parseInt(m2.group(3));
            }
            else
              return intervals;

            if(groups.get(mI) == null)
                groups.put(mI, new Vector<Integer>());
            groups.get(mI).add(d);
        }

        groups.entrySet().forEach(entry -> {

          int month = entry.getKey();

          Vector<Integer> group = new Vector<Integer>();
          group = entry.getValue();
          Collections.sort(group);

          int numberBefor = 0;
          String interval = "";
          String val = "";

          intervals.put(month, new Vector<String>());
          for(int number : group)
          {
            val = Integer.toString(number);
            if (numberBefor == 0)
              interval = val;
            else
            {
                if (number - numberBefor > 1)
                {
                    String valBefor = Integer.toString(numberBefor);

                    if (!interval.equals(valBefor))
                        interval += " - " + valBefor;

                    //intervals[month].add(interval);
                    intervals.get(month).add(interval);
                    interval = val;
                }
            }

            numberBefor = number;

          }


          if (interval != val)
              interval += " - " + val;
           intervals.get(month).add(interval);

        });
        return intervals;
    }

    //------------------------------------------------------------------------------

    public static void viewIntervals(Map< Integer, Vector<String> > intervals){

      String[] months = new DateFormatSymbols().getMonths();

      System.out.println("------------------------\n");
      System.out.println("Intervals:\n");
      intervals.entrySet().forEach(entry -> {
          int i = entry.getKey();
           System.out.println("Days in " + months[i] + ":");
          Vector <String> interval = entry.getValue();
          for(String numberDay : interval)
           System.out.println(numberDay);
          System.out.println("");
      });
    }

    //------------------------------------------------------------------------------


    public static void main(String[] args) throws Exception{

    //------------------------------------------------------------------------------

     String[] data1 = {"January 8", "May 11", "January 3","January 1", "April 2", "January 4","January 5", "May 11","February 24", "April 1", "February 25", "February 26", "March 12", "January 7", "March 13", "January 3", "March 14", "February 24", "April 3","May 10", "May 12","February 23"};

      Map< Integer, Vector<String>> intervals = getIntervals(data1);

      viewIntervals(intervals);

      //---------------------------------------------------------------

    String[] data2 = {"2025-05-10", "2025-03-14","2025-05-11", "2025-01-04", "2025-03-13", "2025-01-08","2025-05-12", "2025-01-03",  "2025-04-03","2025-02-24", "2025-01-01", "2025-01-05", "2025-02-23",  "2025-02-25", "2025-02-26", "2025-03-12",  "2025-04-01", "2025-04-02", "2025-01-07"};

    intervals = getIntervals(data2);

    viewIntervals(intervals);

    /*
    ------------------------

    Intervals:

    Days in January:
    1
    3 - 5
    7 - 8

    Days in February:
    23 - 26

    Days in March:
    12 - 14

    Days in April:
    1 - 3

    Days in May:
    10 - 12

    ------------------------

    Intervals:

    Days in January:
    1
    3 - 5
    7 - 8

    Days in February:
    23 - 26

    Days in March:
    12 - 14

    Days in April:
    1 - 3

    Days in May:
    10 - 12

    */
  }
}
```
## ECMAScript

```js

/*
	Calculating date intervals in ECMAScript
	Version: 2.0, 2025-02-22
	Author: Vladimir Kheifets vladimir.kheifets@online.de
	Copyright © 2025 Vladimir Kheifets All Rights Reserved
*/


function getIntervals(inData){

	const pattern1 = /^(\D+)(\d+)$/;
	const pattern2 = /^(\d{4})\-(\d{2})\-(\d{2})$/;

	const months = getMonths();

	let groups = {};

	for(let item of inData)
	{
		let out1 = pattern1.exec(item);
		let out2 = pattern2.exec(item);
		let day, month;
		if(out1)
		{
			day = parseInt(out1[2]);
			month = months.indexOf(out1[1].trim());
		}
		else if(out2)
		{
			day = parseInt(out2[3]);
			month = parseInt(out2[2]) - 1;
		}
		else
			return false;

		if(!groups[month])
			groups[month]=[];
		groups[month].push(day);
	}

	let intervals = {};

	for( let preff in groups)
	{
		let group = groups[preff];
		group.sort();

		let numberBefor = 0;

		intervals[preff] = [];
		for (var number of group)
		{
			if(numberBefor == 0)
				var interval = number;
			else
			{
				if((number - numberBefor) > 1)
				{
					if(interval != numberBefor)
						interval += ` - ${numberBefor}`;
					intervals[preff].push(interval);
					interval = number;
				}
			}
			numberBefor = number;

		}
		if(interval != number)
			interval += ` - ${number}`;
		intervals[preff].push(interval);
	}
	return intervals;
}

//---------------------------------------------------------

function viewIntervals(intervals){
	if(intervals)
	{
		months = getMonths();
		let out = "Intervals:\n\n";
		for(var key in  intervals )
		{
			out += `Days in  ${months[key]}:\n`;
			for(var interval of intervals[key] )
			{
				out += `${interval}\n`;
			}
			out += "\n";
		}
		alert(out);
	}
}

//---------------------------------------------------------

function getMonths(){
	return months = [...Array(11).keys()].map(key => new Date(0, key).
		toLocaleString("en",{ month: 'long' }));
}

//-----------------------------------------------------------

let data = ["January 8", "May 11", "January 3","January 1", "April 2", "January 4","January 5", "May 11","February 24", "April 1", "February 25", "February 26", "March 12", "January 7", "March 13", "January 3", "March 14", "February 24", "April 3","May 10", "May 12","February 23"];

let intervals = getIntervals(data);
viewIntervals(intervals);

//---------------------------------------------------------

data = ["2025-05-10", "2025-03-14","2025-05-11", "2025-01-04", "2025-03-13", "2025-01-08","2025-05-12", "2025-01-03",  "2025-04-03","2025-02-24", "2025-01-01", "2025-01-05", "2025-02-23",  "2025-02-25", "2025-02-26", "2025-03-12",  "2025-04-01", "2025-04-02", "2025-01-07"];

intervals = getIntervals(data);
viewIntervals(intervals);

/*
-----------------------------------
Intervals:

Days in January:
1
3 - 5
7 - 8

Days in February:
23 - 26

Days in March:
12 - 14

Days in April:
1 - 3

Days in May:
10 - 12

-----------------------------------
Intervals:

Days in January:
01
03 - 05
07 - 08

Days in February:
23 - 26

Days in March:
12 - 14

Days in April:
01 - 03

Days in May:
10 - 12

*/
```