package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class EditTeacherMenuController
{
  @FXML private Button back;
  @FXML private TableView<Teacher> teacherTable;
  @FXML public TableColumn<Teacher, String> name = new TableColumn<>("Name");
  @FXML public TableColumn<Teacher, Date> sickStart = new TableColumn<>(
      "Sick start");
  @FXML public TableColumn<Teacher, Date> sickEnd = new TableColumn<>(
      "Sick end");
  @FXML public TableColumn<Teacher, String> email = new TableColumn<>("E-mail");

  public void goBack() throws IOException
  {
    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("manageteachers.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setTitle("Main Menu");
    stage.setScene(new Scene(root1));
    stage.show();
    back.getScene().getWindow().hide();
  }

  public void goEdit() throws IOException
  {
    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("editteachers.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setTitle("Edit Courses");
    stage.setScene(new Scene(root1));
    stage.show();
    back.getScene().getWindow().hide();
  }

  public void initialize()
  {
    TeacherList teachers = new TeacherList();
    teachers.loadFromBinaryFile("teacherData.bin");
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    sickStart.setCellValueFactory(new PropertyValueFactory<>("sickStart"));
    sickEnd.setCellValueFactory(new PropertyValueFactory<>("sickEnd"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    email.setPrefWidth(125);
    name.setPrefWidth(125);
    sickStart.setPrefWidth(125);
    sickEnd.setPrefWidth(125);
    //    courseName.setPrefWidth(209);
    //    courseStudentNo.setPrefWidth(205);
    teacherTable.getColumns().addAll(name, sickStart, sickEnd, email);
    for (Teacher teacher : teachers.getAllTeachers())
    {
      if(!(teacher.getName().equals("NOT ASSIGNED")))
      teacherTable.getItems().add(teacher);
    }

    teacherTable.setOnMousePressed(new EventHandler<MouseEvent>()
    {
      @Override public void handle(MouseEvent mouseEvent)
      {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
          if (mouseEvent.getClickCount() == 2)
          {
            {
              FXMLLoader fxmlLoader = new FXMLLoader(
                  getClass().getResource("editteachers.fxml"));
              try
              {
                fxmlLoader.load();
              }
              catch (IOException e)
              {
                e.printStackTrace();
              }
              EditTeachersController popup = fxmlLoader.getController();
              if (teacherTable.getSelectionModel().getSelectedItem().isSick())
              {
                popup.setData(teacherTable.getSelectionModel().getSelectedItem()
                        .getName(),
                    teacherTable.getSelectionModel().getSelectedItem()
                        .getSickStart(),
                    teacherTable.getSelectionModel().getSelectedItem()
                        .getSickEnd(),
                    teacherTable.getSelectionModel().getSelectedItem()
                        .getEmail());
              }
              else
              {
                popup.setData(teacherTable.getSelectionModel().getSelectedItem()
                        .getName(), " ", " ",
                    teacherTable.getSelectionModel().getSelectedItem()
                        .getEmail());
              }
              Parent root1 = fxmlLoader.getRoot();
              Stage stage = new Stage();
              stage.setTitle("Edit Teacher");
              stage.setScene(new Scene(root1));
              stage.show();
              back.getScene().getWindow().hide();
            }
          }
      }
    });
  }
}
