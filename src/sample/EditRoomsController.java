package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.lang.String;

import java.io.IOException;

public class EditRoomsController
{
    @FXML Button back;
    @FXML private TableView<Room> tableView;
    @FXML public TableColumn<Room, String> roomId = new TableColumn<>("ID");
    @FXML public TableColumn<Room, Integer> capacity = new TableColumn<>("Capacity");
    @FXML public TableColumn<Room, String> roomCourse = new TableColumn<>("Course");


    public void goBack() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manageroomsmenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Manage Rooms");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }

    public void initialize()
    {
        RoomList roomList = new RoomList();
        roomList.loadFromBinaryFile("roomData.bin");

        roomId.setCellValueFactory(new PropertyValueFactory<>("id"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        roomCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));


        roomId.setPrefWidth(125);
        capacity.setPrefWidth(125);
        roomCourse.setPrefWidth(125);


        TableColumn<Room, Boolean> genColumn = new TableColumn<>("HDMI");
        genColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().hasHDMI()));
        genColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "YES" : "NO");
            }
        });

        TableColumn<Room, Boolean> genVGA = new TableColumn<>("VGA");
        genVGA.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().hasVGA()));
        genVGA.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "YES" : "NO");
            }
        });

        genVGA.setPrefWidth(125);
        genColumn.setPrefWidth(125);



        tableView.getColumns().addAll(roomId, capacity, genColumn, genVGA, roomCourse);

        for(Room room : roomList.getAllRooms())
        {
            tableView.getItems().add(room);
        }


        tableView.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent mouseEvent)
            {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2)
                {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editroomspopup.fxml"));
                    try {
                        fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EditRoomsPopupController popup = fxmlLoader.getController();
                    popup.setData(tableView.getSelectionModel().getSelectedItem().getId(), Integer.toString(tableView.getSelectionModel().getSelectedItem().getCapacity()), tableView.getSelectionModel().getSelectedItem().getCourse().getName(), tableView.getSelectionModel().getSelectedItem().hasHDMI(), tableView.getSelectionModel().getSelectedItem().hasVGA(), tableView.getSelectionModel().getSelectedItem().getExamType());
                    Parent root = fxmlLoader.getRoot();
                    Stage stage = new Stage();
                    stage.setTitle("Edit Room");
                    stage.setScene(new Scene(root));
                    stage.show();
                    back.getScene().getWindow().hide();
                }
            }
        });



    }







}
