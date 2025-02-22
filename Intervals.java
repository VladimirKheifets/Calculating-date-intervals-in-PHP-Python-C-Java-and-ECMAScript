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