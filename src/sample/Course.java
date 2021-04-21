package sample;

import java.io.Serializable;

public class Course implements Serializable {
    private String name;
    private boolean isUsed;
    private int numberOfStudents;

    public Course(String name, int numberOfStudents)
    {
        this.name = name;
        this.numberOfStudents = numberOfStudents;
        isUsed = true;
    }

    public boolean isUsed()
    {
        return isUsed;
    }

    public void setUsedFalse()
    {
        this.isUsed = false;
    }

    public void setUsedTrue()
    {
        this.isUsed = true;
    }

    public void setNumberOfStudents(int numberOfStudents)
    {
        this.numberOfStudents = numberOfStudents;
    }

    public  int getNumberOfStudents()
    {
        return this.numberOfStudents;
    }

    public String getName()
    {
        return this.name;
    }

    public String toString()
    {
        return name + " " + isUsed + " " + numberOfStudents;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof Course))
            return false;
        else
            {
            Course aux = (Course) obj;
            return this.numberOfStudents == aux.numberOfStudents
                    && this.name.equals(aux.name);
        }
    }
}











