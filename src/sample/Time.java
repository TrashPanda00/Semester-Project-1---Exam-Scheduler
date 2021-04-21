package sample;

import java.io.Serializable;

public class Time implements Serializable
{
  private int hour, minute, second;

  public Time(int h, int m, int s)
  {
    hour = h;
    minute = m;
    second = s;
    if(m>59) minute = 59;
    if(s>59) second = 59;
    if(m<0) minute = 0;
    if(s<0) second = 0;
  }

  public Time(int timeInSeconds)
  {
    if (timeInSeconds >= 60)
    {
      second = timeInSeconds % 60;
      minute = timeInSeconds / 60;
      if (minute >= 60)
      {
        hour = minute / 60;
        minute %= 60;

      }
    }
  }

  public boolean equals(Time x)
  {
    if(!(x instanceof Time))
      return false;

    Time aux = (Time) x;

    if(this.getHour() == aux.getHour() && this.getMinute() == aux.getMinute() && this.getSecond() == aux.getSecond())
      return true;
    else return false;
  }

  public boolean isBefore(Time x)
  {
    return this.getTimeInSeconds() < x.getTimeInSeconds();
  }

  public int getHour()
  {
    return hour;
  }

  public int getMinute()
  {
    return minute;
  }

  public int getSecond()
  {
    return second;
  }

  public int getTimeInSeconds()
  {
    return (hour * 3600) + (minute * 60) + second;
  }

  public void setTime(int h, int m, int s)
  {
    hour = h;
    minute = m;
    second = s;
    if(m>59) minute = 59;
    if(s>59) second = 59;
    if(m<0) minute = 0;
    if(s<0) second = 0;
  }

  public String toString()
  {
    return String.format("%02d:%02d:%02d",hour,minute,second);
  }


}
