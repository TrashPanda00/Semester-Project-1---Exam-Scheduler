package sample;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date implements Serializable
{

    private int month, day, year;

    public Date(int m, int d, int y)
    {
        set(d, m, y);
    }

    public Date()
    {
        Calendar now = GregorianCalendar.getInstance();
        day = now.get(Calendar.DAY_OF_MONTH);
        month = now.get(Calendar.MONTH) + 1;
        year = now.get(Calendar.YEAR);
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    public int getYear()
    {
        return year;
    }

    public void set(int day, int month, int year)
    {
        if (year < 0)
            year = Math.abs(year);
        if (month < 1)
            month = 1;
        if (month > 12)
            month = 12;
        if (day < 1)
            day = 1;
        if (day > numberOfDaysInMonth(month))
            day = numberOfDaysInMonth(month);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String toString()
    {
        String date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);

        return date;
    }

    public String getMonthName()
    {
        switch (month)
        {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                break;
        }
        return "";
    }

    public boolean isLeapYear()
    {
        if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)))
            return true;
        else
            return false;
    }

    public int numberOfDaysInMonth(int monthNumber)
    {

        if (monthNumber < 1 || monthNumber > 12)
            return -1;
        if (monthNumber == 1 || monthNumber == 3 || monthNumber == 5
                || monthNumber == 7 || monthNumber == 8 || monthNumber == 10
                || monthNumber == 12)
            return 31;
        else if (monthNumber != 2)
            return 30;
        else if (isLeapYear())
            return 29;
        else
            return 28;
    }

    public void stepForwardOneDay()
    {
        if (month == 12 && day == 31)
        {
            year++;
            month = 1;
            day = 1;
        }
        else if (day == numberOfDaysInMonth(month))
        {
            day = 1;
            month++;
        }
        else
            day++;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof Date))
            return false;
        else
        {
            Date other = (Date) obj;
            return other.day == day && other.month == month && other.year == year;
        }
    }

    public static int convertToMonthNumber(String monthName)
    {
        monthName = monthName.toLowerCase();
        switch (monthName)
        {
            case "january":
                return 1;
            case "february":
                return 2;
            case "march":
                return 3;
            case "april":
                return 4;
            case "may":
                return 5;
            case "june":
                return 6;
            case "july":
                return 7;
            case "august":
                return 8;
            case "september":
                return 9;
            case "october":
                return 10;
            case "november":
                return 11;
            case "december":
                return 12;
            default:
                return -1;
        }
    }

    public void stepForward(int days)
    {
        for (int i = 0; i < days; i++)
            stepForwardOneDay();
    }
    public boolean isBefore(Date other)
    {
        if(other.year > this.year)
            return true;
        else if(other.year < this.year)
            return false;
        else if(other.month > this.month)
            return true;
        else if(other.month < this.month)
            return false;
        else if(other.day > this.day)
            return true;
        else return false;
    }
    public int yearsBetween(Date other)
    {
        if(isBefore(other))
        {
            int diff = other.year - year;
            if ((month > other.month) || (month == other.month && day > other.day))
                diff--;
            return diff;
        }
        else
        {
            int diff = year - other.year;
            if ((other.month > month) || (other.month == month && other.day > day))
                diff--;
            return diff;
        }
    }

    public Date copy()
    {
        Date copy = new Date(month,day,year);
        return copy;
    }

    public int daysBetween(Date other)
    {
        int c = 0;
        if(isBefore(other))
        {
            Date counterDate = this.copy();
            while(counterDate.isBefore(other))
            {
                counterDate.stepForwardOneDay();
                c++;
            }
        }
        else
        {
            Date counterDate = other.copy();
            while(counterDate.isBefore(this))
            {
                counterDate.stepForwardOneDay();
                c++;
            }
        }
        return c;
    }
    public static Date now()
    {
        Date now = new Date();
        return now;
    }
}
