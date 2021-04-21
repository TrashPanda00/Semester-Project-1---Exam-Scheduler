package sample;

import java.io.*;
import java.util.ArrayList;

public class ExamList implements Serializable, FileIO
{
    private ArrayList<Exam> exams;

    public ExamList()
    {
        exams= new ArrayList<Exam>();
    }

    public void addExam(Exam exam)
    {
        exams.add(exam);
    }

    public void removeExam(Exam exam)
    {
        for(int i = 0; i < exams.size(); i++)
            if(exams.get(i).equals(exam))
                exams.remove(i);
    }

    public void removeExam(int id)
    {
        for(int i = 0; i < exams.size(); i++)
            if(exams.get(i).getId() == id)
                exams.remove(i);
    }


    public int getSize()
    {
        return exams.size();
    }

    public ArrayList<Exam> getAllExams()
    {
        return (ArrayList<Exam>) exams.clone();
    }

    @Override public void loadFromBinaryFile(String filePath)
    {
        ObjectInputStream in = null;
        try
        {
            File file = new File(filePath);
            InputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);

            exams = (ArrayList<Exam>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    @Override public void saveToBinaryFile()
    {
        ObjectOutputStream out = null;
        try
        {
            File file = new File("examData.bin");
            FileOutputStream fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(exams);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
