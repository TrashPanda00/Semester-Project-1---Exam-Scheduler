package sample;

import java.io.Serializable;

public class Room implements Serializable {
    private String id;
    private int capacity;
    private boolean hasVGA;
    private boolean hasHDMI;
    private String examType;
    private boolean isAvailable;
    private Course course;
    private String courseName;

    public Room(int capacity, String id)
    {
        this.capacity = capacity;
        this.id = id;
        this.hasVGA = false;
        this.hasHDMI = false;
        this.examType = "";
        this.isAvailable = false;
        this.courseName = "";
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public boolean isAvailable()
    {
        return this.isAvailable;
    }

    public void setAvailable(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }

    public int getCapacity()
    {
        return this.capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public String getExamType()
    {
        return this.examType;
    }

    public void setExamType(String examType)
    {
        this.examType = examType;
    }

    public boolean hasHDMI()
    {
        return this.hasHDMI;
    }

    public void setHDMI(boolean hasHDMI)
    {
        this.hasHDMI = hasHDMI;
    }

    public boolean hasVGA()
    {
        return this.hasVGA;
    }

    public void setVGA(boolean hasVGA)
    {
        this.hasVGA = hasVGA;
    }

    public void setCourse(Course course)
    {
        this.course = course;
        this.courseName = this.course.getName();
    }

    public String getCourseName()
    {
        return this.courseName;
    }

    public Course getCourse()
    {
        return this.course;
    }

    public String toString()
    {
        return "sample.Room - id: " + id + " with the capacity of " + capacity + " with HDMI " + hasHDMI + " and VGA " + hasVGA + " set up for exam type " + examType + " and it is available " + isAvailable;
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;

        if(!(obj instanceof Room))
            return false;

        Room aux = (Room)obj;

        if(this.isAvailable == aux.isAvailable && this.examType == aux.examType && this.hasHDMI == aux.hasHDMI && this.hasVGA == aux.hasVGA && this.id == aux.id && this.capacity == aux.capacity)
            return true;
        else return false;
    }
}
