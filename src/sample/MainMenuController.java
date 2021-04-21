package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import persistence.MyXmlConverter;
import persistence.XmlConverterException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class MainMenuController
{
    @FXML private Button manageExams;
    @FXML private Button manageTeachers;
    @FXML private Button manageRooms;
    @FXML private Button manageCourses;
    @FXML private Button viewTimetable;

    public void openExams() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manageexams.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Manage Exams");
        stage.setScene(new Scene(root1));
        stage.show();
        manageExams.getScene().getWindow().hide();
    }

    public void openTeachers() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manageteachers.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Manage Teachers");
        stage.setScene(new Scene(root1));
        stage.show();
        manageExams.getScene().getWindow().hide();
    }

    public void openCourses() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("managecoursesmenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Manage Courses");
        stage.setScene(new Scene(root1));
        stage.show();
        manageExams.getScene().getWindow().hide();
    }

    public void openRooms() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manageroomsmenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Manage Rooms");
        stage.setScene(new Scene(root1));
        stage.show();
        manageExams.getScene().getWindow().hide();
    }

    public void openTimetable() throws IOException {
        //ExamList examList = new ExamList();
        //examList.loadFromBinaryFile("examData.bin");
        //MyXmlConverter converter = new MyXmlConverter();
        //ExamList list = converter.fromXml("courseData.xml", examList.getAllExams().get(1));
        //File file = converter.toXml(examList.getAllExams().get(0), "courseDataxml");

    }

    public void initialize() throws IOException
    {
        TeacherList teacherList = new TeacherList();
        teacherList.loadFromBinaryFile("teacherData.bin");
        if(teacherList.getSize() == 0)
        {
            Teacher newTeacher = new Teacher("NOT ASSIGNED", "NOT ASSIGNED");
            teacherList.addTeacher(newTeacher);
            teacherList.saveToBinaryFile();
        }

        CourseList courseList = new CourseList();
        courseList.loadFromBinaryFile("courseData.bin");
        if(courseList.getSize() == 0)
        {
            Course  newCourse = new Course("NOT ASSIGNED", 0);
            courseList.addCourse(newCourse);
            courseList.saveToBinaryFile();
        }

    }
}
