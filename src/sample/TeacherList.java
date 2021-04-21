package sample;

import java.io.*;
import java.util.ArrayList;

public class TeacherList implements Serializable, FileIO
{
  private ArrayList<Teacher> teachers;

  public TeacherList()
  {
    this.teachers = new ArrayList<Teacher>();
  }

  public void addTeacher(Teacher teacher1)
  {
    this.teachers.add(teacher1);
  }

  public Teacher getTeacher(String name)
  {
    for(int i = 0; i < teachers.size(); i++)
      if(teachers.get(i).getName().equals(name))
        return teachers.get(i);
      return null;
  }

  public void removeTeacher(String name, String email)
  {
    for (int i = 0; i < teachers.size(); i++)
    {
      if (teachers.get(i).getName().equals(name))
      {
        if (teachers.get(i).getEmail().equals(email))
          teachers.remove(i);
      }
    }
  }

  public ArrayList<Teacher> getHealthyTeachers()
  {
    ArrayList<Teacher> result = new ArrayList<Teacher>();
    for (Teacher teacher : teachers)
    {
      if (!(teacher.isSick()))
        result.add(teacher);
    }
    return result;
  }

  public int getSize()
  {
    return teachers.size();
  }

  public ArrayList<Teacher> getAllTeachers()
  {
    return teachers;
  }

  @Override public void saveToBinaryFile()
  {
    ObjectOutputStream out = null;
    try
    {
      File file = new File("teacherData.bin");
      FileOutputStream fos = new FileOutputStream(file);
      out = new ObjectOutputStream(fos);
      out.writeObject(teachers);
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
      if (file.length() != 0)
      {
        InputStream fis = new FileInputStream(file);
        in = new ObjectInputStream(fis);
        teachers = (ArrayList<Teacher>) in.readObject();
      }
    }
    catch (IOException | ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

}
