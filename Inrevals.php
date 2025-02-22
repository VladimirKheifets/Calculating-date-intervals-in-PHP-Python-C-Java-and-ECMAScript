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

?>