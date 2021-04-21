package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditTeachersController
{
    @FXML TextField name;
    @FXML DatePicker startDate;
    @FXML DatePicker endDate;
    @FXML TextField email;
    @FXML TextField oldName;
    @FXML TextField oldStartDate;
    @FXML TextField oldEndDate;
    @FXML TextField oldEmail;
    @FXML Button back;
    @FXML Button delete;
    @FXML Label message;

    public void goBack() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("editteachermenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit sample.Teacher");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }

    public void setData(String oldName, String oldStartDate, String oldEndDate, String oldEmail)
    {
        this.oldName.setText(oldName);
        this.oldStartDate.setText(oldStartDate);
        this.oldEndDate.setText(oldEndDate);
        this.oldEmail.setText(oldEmail);
    }

    public void goEdit()
    {
        try {
            if(name.getText().equals("") || email.getText().equals(""))
                throw new IllegalArgumentException("NO ARGUMENTS");
            TeacherList teachers = new TeacherList();
            teachers.loadFromBinaryFile("teacherData.bin");
            Teacher teacher;
            if (startDate.getValue() == null || endDate.getValue() == null) {
                teacher = new Teacher(name.getText(), email.getText());
            } else {
                teacher = new Teacher(name.getText(), email.getText(), new Date(startDate.getValue().getMonthValue(),
                        startDate.getValue().getDayOfMonth(), startDate.getValue().getYear()),
                        new Date(endDate.getValue().getMonthValue(),
                                endDate.getValue().getDayOfMonth(), endDate.getValue().getYear()));
            }
            teachers.addTeacher(teacher);
            teachers.removeTeacher(oldName.getText(), oldEmail.getText());
            teachers.saveToBinaryFile();
            message.setText("Teacher edited successfully");
            oldName.setText(teacher.getName());
            oldEmail.setText(teacher.getEmail());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            message.setText("Teacher edit FAILED");
        }
    }

    public void goDelete()
    {
        try {
            TeacherList teachers = new TeacherList();
            teachers.loadFromBinaryFile("teacherData.bin");
            teachers.removeTeacher(oldName.getText(), oldEmail.getText());
            teachers.saveToBinaryFile();
            message.setText("Teacher deleted successfully");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            message.setText("Teacher deletion FAILED");
        }
    }

    public void initialize() throws IOException
    {
        name.setText("");
        email.setText("");
    }

}
