
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
  patt1 = re.compile(r'^([a-zA-Z]+).+(\d+)$')
  patt2 = re.compile(r'^(\d{4})\-(\d{2})\-(\d{2})$')
  intervals = {}

  for item in inData:
    if re.match(patt1,item):
      out = patt1.findall(item)
      month = list(calendar.month_name).index(out[0][0]);
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