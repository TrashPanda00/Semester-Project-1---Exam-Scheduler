package sample;


import java.io.*;
import java.util.ArrayList;

public class CourseList implements Serializable, FileIO
{
    private ArrayList<Course> courses;

    public CourseList()
    {
        courses = new ArrayList<Course>();
    }

    public void addCourse(Course course)
    {
        this.courses.add(course);
    }

    public Course getCourse(String courseName)
    {
        for(int i = 0; i < courses.size(); i++)
            if(courses.get(i).getName().equals(courseName))
                return courses.get(i);
            return null;
    }

    public void removeCourse(Course course)
    {
        for(int i = 0; i < courses.size(); i++)
            if(courses.get(i).equals(course))
                courses.remove(i);
    }

    public int getSize()
    {
        return courses.size();
    }

    public ArrayList<Course> getAllCourses()
    {
        return (ArrayList<Course>)courses.clone();
    }

    @Override public void saveToBinaryFile()
    {
        ObjectOutputStream out = null;
        try
        {
            File file = new File("courseData.bin");
            FileOutputStream fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(courses);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                assert out != null;
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    @Override public void loadFromBinaryFile(String filePath)
    {
        ObjectInputStream in = null;
        try
        {
            File file = new File(filePath);
            InputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);

            courses = (ArrayList<Course>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                assert in != null;
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
