package sample;

import java.io.Serializable;

public class Exam implements Serializable {
    private int id;
    private Time examFinish;
    private Date examDate;
    private Room examRoom;
    private Teacher examiner;
    private Teacher coExaminer;
    private Course examCourse;
    private Time examStart;
    private String examType;

    public Exam(int id, Date examDate,
                Room examRoom, Teacher examiner, Teacher coExaminer, Course examCourse, Time examStart, Time examFinish, String examType) {
        this.id = id;
        this.examFinish = examFinish;
        this.examDate = examDate;
        this.examRoom = examRoom;
        this.examiner = examiner;
        this.coExaminer = coExaminer;
        this.examCourse = examCourse;
        this.examStart = examStart;
        this.examType = examType;
    }

    public Exam(Exam exam)
    {
        this.id = exam.id;
        this.examFinish = exam.examFinish;
        this.examDate = exam.examDate;
        this.examRoom = exam.examRoom;
        this.examiner = exam.examiner;
        this.coExaminer = exam.coExaminer;
        this.examCourse = exam.examCourse;
        this.examStart = exam.examStart;
        this.examType = exam.examType;
    }

    public int getId()
    {
        return this.id;

    }

    public Date getDate()
    {
        return this.examDate;
    }

    public Course getCourse()
    {
        return examCourse;
    }

    public Teacher getExaminer()
    {
        return examiner;
    }

    public Teacher getCoexaminer()
    {
        return coExaminer;
    }

    public Room getRoom() {
        return examRoom;
    }
    public String getExamType()
    {
        return this.examType;
    }

    public void setExamType(String examType)
    {
        this.examType = examType;
    }

    public Time getStartTime() {
        return examStart;
    }

    public Time getFinish() {
        return this.examFinish;
    }

    public void setDate(Date date) {
        this.examDate = date;
    }

    public void setFinish(Time examFinish) {
        this.examFinish = examFinish;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public void setExamRoom(Room examRoom) {
        this.examRoom = examRoom;
    }

    public void setExaminer(Teacher examiner) {
        this.examiner = examiner;
    }

    public void setCoexaminer(Teacher coExaminer) {
        this.coExaminer = coExaminer;
    }

    public void setExamCourse(Course examCourse) {
        this.examCourse = examCourse;
    }

    public void setExamFinish(Time examFinish) {
        this.examFinish = examFinish;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof Exam))
            return false;

        Exam aux = (Exam)obj;

        if((this.id == aux.id) && (this.examFinish.equals(aux.examFinish)) && (this.examDate.equals(aux.examDate)) && (this.examRoom.equals(aux.examRoom)) && (this.examiner.equals(aux.examiner)) && (this.coExaminer.equals(aux.coExaminer)) && (this.examCourse.equals(aux.examCourse)) && ((this.examStart.equals(aux.examStart))))
            return true;
        else return false;
    }

    public String toString()
    {
        return this.id + " " + this.examCourse.toString();
    }


}
