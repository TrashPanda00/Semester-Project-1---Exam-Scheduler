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

public class ManageTeachersMenuController
{
    @FXML Button back;
    @FXML Button edit;
    private int x = 0;
    @FXML Label message;
    @FXML TextField name;
    @FXML DatePicker sickStart;
    @FXML TextField email;
    @FXML DatePicker sickEnd;

    public void goCreate()
    {
        TeacherList t = new TeacherList();
        t.loadFromBinaryFile("teacherData.bin");
        try
        {
            if(name.getText().equals("") || email.getText().equals(""))
                throw new IllegalArgumentException("Wheres the fucking data?");
            Teacher newTeacher;
            for(int i = 0; i < t.getSize(); i++)
                if(name.getText().equals(t.getAllTeachers().get(i).getName()))
                {
                    x++;
                    message.setText("TEACHER\r\nALLREADY\r\nMADE");
                    throw new IllegalArgumentException("NAME ALLREADY USED");
                }
            if (sickStart.getValue() == null || sickEnd.getValue() == null)
            {
                newTeacher = new Teacher(name.getText(), email.getText());
                t.addTeacher(newTeacher);
            }
            else
            {
                newTeacher = new Teacher(name.getText(), email.getText(),
                        new Date(sickStart.getValue().getMonthValue(),
                                sickStart.getValue().getDayOfMonth(),
                                sickStart.getValue().getYear()),
                        new Date(sickEnd.getValue().getMonthValue(),
                                sickEnd.getValue().getDayOfMonth(),
                                sickEnd.getValue().getYear()));
                t.addTeacher(newTeacher);
            }
            message.setText(" Teacher \n" + "created");
            name.setText("");
            email.setText("");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if(x == 0)
                message.setText(" Teacher \n\r" + "    not \n\r" + "created");
            else x = 0;
        }
        t.saveToBinaryFile();
    }

    public void goBack() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("mainmenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root1, 300, 275));
        stage.show();
        back.getScene().getWindow().hide();
    }

    public void goEdit() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("editteachermenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit Teachers");
        stage.setScene(new Scene(root1, 500, 400));
        stage.show();
        back.getScene().getWindow().hide();
    }

    public void initialize() throws IOException
    {
        name.setText("");
        email.setText("");
    }
}
