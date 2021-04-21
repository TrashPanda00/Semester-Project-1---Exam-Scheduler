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

public class ManageCoursesMenuController
{
  private int id = 0;
  @FXML Button create;
  @FXML Button back;
  @FXML Button edit;
  @FXML TextField nameBox;
  @FXML TextField numberBox;
  @FXML Label message;

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
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editcourses.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setTitle("Edit Courses");
    stage.setScene(new Scene(root1));
    stage.show();
    back.getScene().getWindow().hide();
  }

  public void goCreate()
  {
    String bai = "Course \n" + "created";
    CourseList courseList = new CourseList();
    courseList.loadFromBinaryFile("courseData.bin");
    try
    {
      Course c = new Course(nameBox.getText(), Integer.parseInt(numberBox.getText()));
      for(int i = 0; i < courseList.getSize(); i++)
      {
        if(courseList.getAllCourses().get(i).equals(c))
        {
          bai = "Course \r\nallready \r\ncreated!";
          id++;
          throw new IllegalArgumentException("EXAM ALLREADY MADE");
        }
      }
      courseList.addCourse(c);
      int x = Integer.parseInt(String.valueOf(nameBox.getText().charAt(nameBox.getText().length() - 2)));
    }
    catch (Exception e)
    {
      if(id == 0)
        bai = "Course \r\n   not \r\ncreated";
      else id = 0;
    }

    message.setText(bai);

    nameBox.clear();
    numberBox.clear();
    courseList.saveToBinaryFile();
  }

}
