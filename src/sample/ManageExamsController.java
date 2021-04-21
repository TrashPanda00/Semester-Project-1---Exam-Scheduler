package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class ManageExamsController
{
    private Date examStart = new Date(1, 5, 2020);
    private Date examFinish = new Date(1, 28, 2020);
    @FXML Button back;
    @FXML Button edit;
    @FXML Label message;
    @FXML TextField startHour;
    @FXML TextField startMinute;
    @FXML TextField finishHour;
    @FXML TextField finishMinute;
    @FXML Button create;
    @FXML RadioButton written;
    @FXML RadioButton oral;
    @FXML ComboBox pickCourse;
    @FXML DatePicker pickDate;
    @FXML ComboBox pickRoom;
    @FXML ComboBox pickExaminer;
    @FXML ComboBox pickCoExaminer;
    private int id = 0;

    public void goBack() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root1,300,275));
        stage.show();
        back.getScene().getWindow().hide();
    }
    public void goEdit() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editexammenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit Courses");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }


    public void goCreate() throws IOException
    {
        try
        {
            int id2 = 0;

            ExamList examlist = new ExamList();
            examlist.loadFromBinaryFile("examData.bin");

            if(examlist.getSize() == 0)
                id2 = 1;
            else id2 = examlist.getAllExams().get(examlist.getSize() - 1).getId() + 1;

            CourseList courseList = new CourseList();
            courseList.loadFromBinaryFile("courseData.bin");

            TeacherList teacherList = new TeacherList();
            teacherList.loadFromBinaryFile("teacherData.bin");

            Time startTime = new Time(Integer.parseInt(startHour.getText()), Integer.parseInt(startMinute.getText()), 0);
            Time finishTime = new Time(Integer.parseInt(finishHour.getText()), Integer.parseInt(finishMinute.getText()), 0);

            RoomList roomList = new RoomList();
            roomList.loadFromBinaryFile( "roomData.bin");

            Date examDate = new Date(pickDate.getValue().getMonthValue(), pickDate.getValue().getDayOfMonth(), pickDate.getValue().getYear());

            String examType;

            if(!(examStart.isBefore(examDate) && examDate.isBefore(examFinish)))
            {
                message.setText("Exam creation FAILED \r\n EXAM DATE OUT OF LIMITS  \n" +
                        " 6/1/2020 - 27/1/2020");
                throw new IllegalArgumentException("WRONG DATE");
            }


            if(oral.isSelected())
                examType = "oral";
            else examType = "written";
            Exam exam;

            if(!(pickExaminer.getValue().toString().equals("            ")) && !(pickCoExaminer.getValue().toString().equals("            ")))
                exam = new Exam(id2, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher(pickExaminer.getValue().toString()), teacherList.getTeacher(pickCoExaminer.getValue().toString()), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);
            else if((pickExaminer.getValue().toString().equals("            ")) && (pickCoExaminer.getValue().toString().equals("            ")))
                exam = new Exam(id2, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher("NOT ASSIGNED"), teacherList.getTeacher("NOT ASSIGNED"), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);
            else if(!(pickExaminer.getValue().toString().equals("            ")) && (pickCoExaminer.getValue().toString().equals("            ")))
                exam = new Exam(id2, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher(pickExaminer.getValue().toString()), teacherList.getTeacher("NOT ASSIGNED"), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);
            else if((pickExaminer.getValue().toString().equals("            ")) && !(pickCoExaminer.getValue().toString().equals("            ")))
                exam = new Exam(id2, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher("NOT ASSIGNED"), teacherList.getTeacher(pickCoExaminer.getValue().toString()), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);
            else exam = new Exam(id2, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher(pickExaminer.getValue().toString()), teacherList.getTeacher(pickCoExaminer.getValue().toString()), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);

            if(exam.getExaminer().isSick() && exam.getExaminer().getSickStartObj().isBefore(examDate) && examDate.isBefore(exam.getExaminer().getSickEndObj()))
            {
                message.setText("Exam creation FAILED \r\n EXAMINER SICK");
                throw new IllegalArgumentException("EXAMINER SICK");
            }


            if(exam.getCoexaminer().isSick() && exam.getCoexaminer().getSickStartObj().isBefore(examDate) && examDate.isBefore(exam.getCoexaminer().getSickEndObj()))
            {
                message.setText("Exam creation FAILED \r\n COEXAMINER SICK");
                throw new IllegalArgumentException("COEXAMINER SICK");
            }

            String aux = exam.getCourse().getName();
            if(aux.charAt(aux.length() - 2) == '7' && (exam.getDate().daysBetween(examFinish) < 3))
            {
                message.setText("Exam creation FAILED \r\n DATE TOO LATE 7TH SEM");
                throw new IllegalArgumentException("DATE TOO LATE 7TH SEM");
            }


            for(int i = 0; i < examlist.getSize(); i++)
            {
                if(!(examDate.daysBetween(examlist.getAllExams().get(i).getDate()) > 1) && exam.getCourse().getName().charAt(exam.getCourse().getName().length() - 2) == examlist.getAllExams().get(i).getCourse().getName().charAt(examlist.getAllExams().get(i).getCourse().getName().length() - 2) && exam.getCourse().getName().charAt(exam.getCourse().getName().length() - 1) == examlist.getAllExams().get(i).getCourse().getName().charAt(examlist.getAllExams().get(i).getCourse().getName().length() - 1))
                {
                    message.setText("Exam creation FAILED \r\n COURSE HAS EXAM TOO CLOSE");
                    throw new IllegalArgumentException("COURSE TIRED AFTER EXAM");
                }

                if(exam.getDate().equals(examlist.getAllExams().get(i).getDate()))
                {
                    //COURSE
                    if(exam.getCourse().equals(examlist.getAllExams().get(i).getCourse()) || exam.getCourse().getName().charAt(exam.getCourse().getName().length() - 2) == examlist.getAllExams().get(i).getCourse().getName().charAt(examlist.getAllExams().get(i).getCourse().getName().length() - 2) || exam.getCourse().getName().charAt(exam.getCourse().getName().length() - 1) == examlist.getAllExams().get(i).getCourse().getName().charAt(examlist.getAllExams().get(i).getCourse().getName().length() - 1))
                        if(exam.getStartTime().isBefore(examlist.getAllExams().get(i).getStartTime()) && exam.getStartTime().isBefore(examlist.getAllExams().get(i).getFinish()) ||
                                (exam.getFinish().isBefore(examlist.getAllExams().get(i).getFinish()) && examlist.getAllExams().get(i).getStartTime().isBefore(exam.getStartTime())) ||
                    exam.getStartTime().equals(examlist.getAllExams().get(i).getStartTime()) && exam.getFinish().equals(examlist.getAllExams().get(i).getFinish()))
                    {
                        message.setText("Exam creation FAILED \r\n COURSE HAS EXAM AT SAME TIME");
                        throw new IllegalArgumentException("COURSE OCCUPIED");
                    }


                    // ROOM
                    if(exam.getRoom().equals(examlist.getAllExams().get(i).getRoom()))
                        if(exam.getStartTime().isBefore(examlist.getAllExams().get(i).getStartTime()) && exam.getStartTime().isBefore(examlist.getAllExams().get(i).getFinish()) ||
                                (exam.getFinish().isBefore(examlist.getAllExams().get(i).getFinish()) && examlist.getAllExams().get(i).getStartTime().isBefore(exam.getStartTime())) ||
                                exam.getStartTime().equals(examlist.getAllExams().get(i).getStartTime()) && exam.getFinish().equals(examlist.getAllExams().get(i).getFinish()))
                    {
                        message.setText("Exam creation FAILED \r\n ROOM ALLREADY OCCUPIED");
                        throw new IllegalArgumentException("ROOM OCCUPIED");
                    }

                    //EXAMINER
                    if(!exam.getExaminer().equals("NOT ASSIGNED"))
                    if(exam.getExaminer().equals(examlist.getAllExams().get(i).getExaminer()) || exam.getExaminer().equals(examlist.getAllExams().get(i).getCoexaminer()))
                        if(exam.getStartTime().isBefore(examlist.getAllExams().get(i).getStartTime()) && exam.getStartTime().isBefore(examlist.getAllExams().get(i).getFinish()) ||
                                (exam.getFinish().isBefore(examlist.getAllExams().get(i).getFinish()) && examlist.getAllExams().get(i).getStartTime().isBefore(exam.getStartTime())) ||
                                exam.getStartTime().equals(examlist.getAllExams().get(i).getStartTime()) && exam.getFinish().equals(examlist.getAllExams().get(i).getFinish()))
                    {
                        message.setText("Exam creation FAILED \r\n EXAMINER HAS EXAM AT SAME TIME");
                        throw new IllegalArgumentException("EXAMINER OCCUPIED");
                    }

                    //COEXAMINER
                    if(!exam.getCoexaminer().equals("NOT ASSIGNED"))
                    if(exam.getCoexaminer().equals(examlist.getAllExams().get(i).getCoexaminer()) || exam.getCoexaminer().equals(examlist.getAllExams().get(i).getExaminer()))
                        if(exam.getStartTime().isBefore(examlist.getAllExams().get(i).getStartTime()) && exam.getStartTime().isBefore(examlist.getAllExams().get(i).getFinish()) ||
                                (exam.getFinish().isBefore(examlist.getAllExams().get(i).getFinish()) && examlist.getAllExams().get(i).getStartTime().isBefore(exam.getStartTime())) ||
                                exam.getStartTime().equals(examlist.getAllExams().get(i).getStartTime()) && exam.getFinish().equals(examlist.getAllExams().get(i).getFinish()))
                    {
                        message.setText("Exam creation FAILED \r\n COEXAMINER HAS EXAM AT SAME TIME");
                        throw new IllegalArgumentException("EXAMINER OCCUPIED");
                    }


                }
            }
            message.setText("Exam added successfully");
            examlist.addExam(exam);
            examlist.saveToBinaryFile();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void recommendRoom()
    {
        RoomList roomList = new RoomList();
        roomList.loadFromBinaryFile( "roomData.bin");
        for(int i = 0; i < roomList.getSize(); i++)
            if((roomList.getAllRooms().get(i).getCourse().getName().charAt(roomList.getAllRooms().get(i).getCourse().getName().length() - 2) == pickCourse.getValue().toString().charAt(pickCourse.getValue().toString().length() - 2)) && (roomList.getAllRooms().get(i).getCourse().getName().charAt(roomList.getAllRooms().get(i).getCourse().getName().length() - 1) == pickCourse.getValue().toString().charAt(pickCourse.getValue().toString().length() - 1)))
                message.setText("     You should pick room \r\n                   " + roomList.getAllRooms().get(i).getId());
    }



    public void initialize() throws IOException
    {
        ObservableList<String> options = FXCollections.observableArrayList();
        CourseList courseList = new CourseList();
        courseList.loadFromBinaryFile("courseData.bin");
        for(int i = 0; i < courseList.getSize(); i++)
            if(!courseList.getAllCourses().get(i).getName().equals("NOT ASSIGNED"))
            options.add(courseList.getAllCourses().get(i).getName());
        pickCourse.setItems(options);

        ObservableList<String> options2 = FXCollections.observableArrayList();
        RoomList roomList = new RoomList();
        roomList.loadFromBinaryFile("roomData.bin");
        for(int i = 0; i < roomList.getSize(); i++)
            options2.add(roomList.getAllRooms().get(i).getId());
        pickRoom.setItems(options2);

        ObservableList<String> options3 = FXCollections.observableArrayList();
        TeacherList teacherList = new TeacherList();
        teacherList.loadFromBinaryFile("teacherData.bin");
        for(int i = 0; i < teacherList.getSize(); i++)
            options3.add(teacherList.getAllTeachers().get(i).getName());
        pickExaminer.setItems(options3);
        pickCoExaminer.setItems(options3);

        pickDate.setValue(LocalDate.of(2020, 1, 6));

    }


}
