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

public class ManageRoomsMenuController
{
    @FXML Button create;
    private int x = 0;
    @FXML Button back;
    @FXML Button edit;
    @FXML TextField nameBox;
    @FXML TextField sizeBox;
    @FXML ComboBox coursePick = new ComboBox();
    @FXML CheckBox hdmi;
    @FXML CheckBox vga;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editrooms.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit Rooms");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }

    public void goCreate()
    {
        RoomList roomList = new RoomList();
        roomList.loadFromBinaryFile("roomData.bin");
        try
        {

            CourseList courseList = new CourseList();
            courseList.loadFromBinaryFile("courseData.bin");
            Room room = new Room(Integer.parseInt(sizeBox.getText()), nameBox.getText());
            for(int i = 0; i < roomList.getSize(); i++)
                if(room.getId().equals(roomList.getAllRooms().get(i).getId()))
                {
                    message.setText("ROOM ALLREADY MADE");
                    x++;
                    throw new IllegalArgumentException("ROOM ALLREADY MADE");
                }
            room.setAvailable(true);
            room.setCourse(courseList.getCourse(coursePick.getValue().toString()));
            if(room.getCapacity() < room.getCourse().getNumberOfStudents())
            {
                message.setText("ROOM TOO SMALL FOR COURSE\r\nROOM MADE WITHOUT COURSE");
                room.setCourse(courseList.getCourse("NOT ASSIGNED"));
                roomList.addRoom(room);
                roomList.saveToBinaryFile();
                x++;
                throw new IllegalArgumentException("ROOM TOO SMALL");
            }
            for(int i = 0; i < roomList.getSize(); i++)
                if(room.getCourse().equals(roomList.getAllRooms().get(i).getCourse()))
                {
                    message.setText("COURSE ALLREADY USED \r\n ROOM MADE WITHOUT COURSE");
                    room.setCourse(courseList.getCourse("NOT ASSIGNED"));
                    roomList.addRoom(room);
                    roomList.saveToBinaryFile();
                    x++;
                    throw new IllegalArgumentException("COURSE ALLREADY USED");
                }

            if (hdmi.isSelected())
                room.setHDMI(true);
            if (vga.isSelected())
                room.setVGA(true);
            message.setText("    Room   ncreation SUCCEEDED");
            roomList.addRoom(room);
        }
        catch (Exception e)
        {
            if(x == 0)
                message.setText("    Room   creation   FAILED");
            x = 0;
        }
        hdmi.setSelected(false);
        vga.setSelected(false);
        nameBox.clear();
        sizeBox.clear();
        roomList.saveToBinaryFile();
    }

    public void initialize()
    {
        ObservableList<String> options = FXCollections.observableArrayList();
        CourseList courseList = new CourseList();
        courseList.loadFromBinaryFile("courseData.bin");
        for(int i = 0; i < courseList.getSize(); i++)
            options.add(courseList.getAllCourses().get(i).getName());
        coursePick.setItems(options);
    }
}
