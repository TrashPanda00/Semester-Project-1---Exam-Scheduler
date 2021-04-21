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

public class EditCoursesController
{
    @FXML private Button back;
    @FXML private TableView<Course> tableView;
    @FXML public TableColumn<Course, String> courseName = new TableColumn<>("Course Name");
    @FXML public TableColumn<Course, String> courseStudentNo = new TableColumn<>("No. Students");


    public void goBack() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("managecoursesmenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Manage Courses");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }
    public void goEdit() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editcoursepopup.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit sample.Course");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }
    public void initialize()
    {
        CourseList courses = new CourseList();
        courses.loadFromBinaryFile("courseData.bin");
        courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseStudentNo.setCellValueFactory(new PropertyValueFactory<>("numberOfStudents"));
        courseName.setPrefWidth(209);
        courseStudentNo.setPrefWidth(205);
        ObservableList<Course> data = FXCollections.observableArrayList(courses.getAllCourses());
        tableView.getColumns().addAll(courseName,courseStudentNo);
        for(Course course : courses.getAllCourses())
        {
            if(!course.getName().equals("NOT ASSIGNED"))
            tableView.getItems().add(course);
        }

        tableView.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent mouseEvent)
            {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2)
                {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editcoursepopup.fxml"));
                    try {
                        fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EditCoursePopupController popup = fxmlLoader.getController();
                    popup.setData(tableView.getSelectionModel().getSelectedItem().getName(), Integer.toString(tableView.getSelectionModel().getSelectedItem().getNumberOfStudents()));
                    Parent root1 = fxmlLoader.getRoot();
                    Stage stage = new Stage();
                    stage.setTitle("Edit Course");
                    stage.setScene(new Scene(root1));
                    stage.show();
                    back.getScene().getWindow().hide();
                }
            }
        });

    }

}
