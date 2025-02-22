
/*
	Calculating date intervals in ECMAScript
	Version: 2.0, 2025-02-22
	Author: Vladimir Kheifets vladimir.kheifets@online.de
	Copyright © 2025 Vladimir Kheifets All Rights Reserved
*/

function getIntervals(inData){

	const pattern1 = /^([a-zA-Z]+).+(\d+)$/;
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
			month = months.indexOf(out1[1]);
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