package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditCoursePopupController
{
    @FXML Button back;
    @FXML Button edit;
    @FXML TextField name;
    @FXML TextField noStudents;
    @FXML TextField newName;
    @FXML TextField newNoStudents;
    @FXML Button delete;
    @FXML Label message;
    private int id = 0;



    public void goBack() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editcourses.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }
    public void setData(String name, String noStudents)
    {
        this.name.setText(name);
        this.noStudents.setText(noStudents);
    }

    public void setMsg(String s)
    {
        message.setText(s);
    }

    public void goDelete() throws IOException, InterruptedException {
        CourseList courseList = new CourseList();
        courseList.loadFromBinaryFile("courseData.bin");
        Course old = new Course(name.getText(),Integer.parseInt(noStudents.getText()));
        courseList.removeCourse(old);
        courseList.saveToBinaryFile();
        setMsg("Course deleted succesefully");
    }

    public void goEdit() throws IOException, InterruptedException {

        try {
            CourseList courseList = new CourseList();
            courseList.loadFromBinaryFile("courseData.bin");
            Course newcourse = new Course(newName.getText(), Integer.parseInt(newNoStudents.getText()));
            Course old = new Course(name.getText(), Integer.parseInt(noStudents.getText()));
            for (int i = 0; i < courseList.getSize(); i++) {
                if (courseList.getAllCourses().get(i).getName().equals(newcourse.getName()) && !courseList.getAllCourses().get(i).equals(old)) {
                    setMsg("Course allready made");
                    id++;
                    throw new IllegalArgumentException("EXAM ALLREADY MADE");
                }
            }
            courseList.addCourse(newcourse);
            courseList.removeCourse(old);
            courseList.saveToBinaryFile();
            setMsg("Course edited succesefully");
        } catch (Exception e) {
            if (id == 0)
                setMsg("Course edit FAILED");
            else id = 0;
        }
    }

    }
