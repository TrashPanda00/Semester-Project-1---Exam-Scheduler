package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.io.IOException;

public class EditExamMenuController
{
    @FXML Button back;
    @FXML private TableView<Exam> examTable;
    @FXML public TableColumn<Exam, Date> date = new TableColumn<>("Date");
    @FXML public TableColumn<Exam, Integer> id = new TableColumn<>("ID");

    public void goBack() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("manageexams.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Manage Exams");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }

    public void goEdit() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("editexampopup.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit sample.Exam");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }

    public void initialize()
    {
        ExamList exams = new ExamList();
        exams.loadFromBinaryFile("examData.bin");

        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));


        TableColumn courseName = new TableColumn("Course Name");
        courseName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exam, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Exam, String> p) {
                return new SimpleStringProperty(p.getValue().getCourse().getName());
            }
        });


        TableColumn startTime = new TableColumn("Start Time");
        startTime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exam, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Exam, String> p) {
                return new SimpleStringProperty(p.getValue().getStartTime().toString());
            }
        });

        TableColumn  finishTime = new TableColumn("Finish Time");
        finishTime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exam, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Exam, String> p) {
                return new SimpleStringProperty(p.getValue().getFinish().toString());
            }
        });

        TableColumn  roomName = new TableColumn("Room");
        roomName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exam, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Exam, String> p) {
                return new SimpleStringProperty(p.getValue().getRoom().getId());
            }
        });

        TableColumn  examType = new TableColumn("Type");
        examType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exam, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Exam, String> p) {
                return new SimpleStringProperty(p.getValue().getExamType());
            }
        });

        TableColumn  examiner = new TableColumn("Examiner");
        examiner.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exam, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Exam, String> p) {
                return new SimpleStringProperty(p.getValue().getExaminer().getName());
            }
        });

        TableColumn  coExaminer = new TableColumn("Co Examiner");
        coExaminer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exam, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Exam, String> p) {
                return new SimpleStringProperty(p.getValue().getCoexaminer().getName());
            }
        });

        courseName.setPrefWidth(100);
        date.setPrefWidth(100);
        startTime.setPrefWidth(100);
        finishTime.setPrefWidth(100);
        roomName.setPrefWidth(100);
        examiner.setPrefWidth(100);
        coExaminer.setPrefWidth(100);
        id.setPrefWidth(100);
        examTable.getColumns().addAll(id, courseName, date, startTime, finishTime, roomName, examiner, coExaminer);

        for (Exam exam : exams.getAllExams())
        {
            examTable.getItems().add(exam);
        }


        examTable.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                    if (mouseEvent.getClickCount() == 2)
                    {
                        {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editexampopup.fxml"));
                            try
                            {
                                fxmlLoader.load();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            EditExamPopupController popup = fxmlLoader.getController();
                            popup.setData(examTable.getSelectionModel().getSelectedItem().getExamType(),examTable.getSelectionModel().getSelectedItem().getCourse().getName(),examTable.getSelectionModel().getSelectedItem().getDate().toString(),Integer.toString(examTable.getSelectionModel().getSelectedItem().getStartTime().getHour()),Integer.toString(examTable.getSelectionModel().getSelectedItem().getStartTime().getMinute()),Integer.toString(examTable.getSelectionModel().getSelectedItem().getFinish().getHour()),Integer.toString(examTable.getSelectionModel().getSelectedItem().getFinish().getMinute()),examTable.getSelectionModel().getSelectedItem().getRoom().getId(),examTable.getSelectionModel().getSelectedItem().getExaminer().getName(),examTable.getSelectionModel().getSelectedItem().getCoexaminer().getName(),examTable.getSelectionModel().getSelectedItem());
                            Parent root1 = fxmlLoader.getRoot();
                            Stage stage = new Stage();
                            stage.setTitle("Edit Exam");
                            stage.setScene(new Scene(root1));
                            stage.show();
                            back.getScene().getWindow().hide();
                        }
                    }
            }
        });
    }

}
