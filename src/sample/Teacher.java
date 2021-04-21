package sample;

import java.io.Serializable;

public class Teacher implements Serializable
{
  private String name;
  private Date sickStart;
  private Date sickEnd;
  private String email;

  public Teacher(String name, String email, Date sickStart, Date sickEnd)
  {
    this.name = name;
    this.email = email;
    this.sickStart = sickStart;
    this.sickEnd = sickEnd;
  }
  public Teacher(String name, String email)
  {
    this.name = name;
    this.email = email;
    this.sickStart = null;
    this.sickEnd = null;
  }
  public String getSickStart()
  {
    try
    {
      return sickStart.toString();
    }
    catch(NullPointerException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public Date getSickStartObj()
  {
    try
    {
      return this.sickStart;
    }
    catch(NullPointerException e)
    {
      e.printStackTrace();
      return null;
    }
  }


  public String getSickEnd()
  {
    try
    {
      return sickEnd.toString();
    }
    catch(NullPointerException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public Date getSickEndObj()
  {
    try
    {
      return this.sickEnd;
    }
    catch(NullPointerException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public String getName()
  {
    return this.name;
  }

  public void callSick(Date startDay, Date stopDay)
  {
    this.sickStart = startDay;
    this.sickEnd = stopDay;
  }

  public void callHealthy()
  {
    this.sickStart = null;
    this.sickEnd = null;
  }

  public boolean isSick()
  {
    return !(sickEnd == null || sickStart == null);
  }

  public boolean isSick(Date date)
  {
    return (date.isBefore(this.sickEnd) && sickStart.isBefore(date));
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getEmail()
  {
    return this.email;
  }


  public String toString()
  {
    if(isSick())
      return this.name + " " + this.email +  " " + this.isSick() + " " + this.sickStart.toString() + " " + this.sickEnd.toString();
    else return this.name + " " + this.email +  " " + this.isSick();
  }

  public boolean equals(Object obj)
  {
    if(!(obj instanceof Teacher))
      return false;
    else
    {
      Teacher other = (Teacher) obj;
      if(this.sickStart == null && other.sickStart == null)
        return this.name.equals(other.name) && this.email.equals(other.email);
      else
      return this.name.equals(other.name) && this.email.equals(other.email) && this.sickStart.equals(other.sickStart) && this.sickEnd.equals(other.sickEnd);
    }
  }
}
