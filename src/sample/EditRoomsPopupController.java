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

public class EditRoomsPopupController
{
    private Room oldRoom;
    @FXML Button back;
    @FXML Button delete;
    @FXML Button edit;
    @FXML TextField idBox;
    @FXML TextField sizeBox;
    @FXML ComboBox pickCourse;
    @FXML TextField oldId;
    @FXML TextField oldSize;
    @FXML TextField oldCourse;
    @FXML CheckBox hdmi;
    @FXML CheckBox vga;
    @FXML Label message;
    private int x = 0;



    public void goBack() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editrooms.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit Rooms");
        stage.setScene(new Scene(root1));
        stage.show();
        back.getScene().getWindow().hide();
    }

    public void goEdit() throws IOException
    {
        RoomList roomList = new RoomList();
        roomList.loadFromBinaryFile("roomData.bin");
        Room aux;
        try
        {
            aux  = new Room(Integer.parseInt(oldSize.getText()), oldId.getText());
            roomList.removeRoom(oldId.getText());
            CourseList courseList = new CourseList();
            courseList.loadFromBinaryFile("courseData.bin");
            Room room = new Room(Integer.parseInt(sizeBox.getText()), idBox.getText());
            for(int i = 0; i < roomList.getSize(); i++)
                if(room.getId().equals(roomList.getAllRooms().get(i).getId()))
                {
                    message.setText("ROOM ALLREADY MADE\r\nTRY ANOTHER NAME");
                    x++;
                    throw new IllegalArgumentException("ROOM ALLREADY MADE");
                }
            room.setAvailable(true);
            room.setCourse(courseList.getCourse(pickCourse.getValue().toString()));
            if(room.getCapacity() < room.getCourse().getNumberOfStudents())
            {
                message.setText("ROOM TOO SMALL FOR COURSE\r\nROOM EDITED WITHOUT COURSE");
                room.setCourse(courseList.getCourse("NOT ASSIGNED"));
                roomList.addRoom(room);
                roomList.saveToBinaryFile();
                x++;
                throw new IllegalArgumentException("ROOM TOO SMALL");
            }
            for(int i = 0; i < roomList.getSize(); i++)
                if(room.getCourse().equals(roomList.getAllRooms().get(i).getCourse()))
                {
                    message.setText("COURSE ALLREADY USED\r\nROOM EDITED WITHOUT COURSE");
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
            message.setText("Room creation SUCCEEDED");
            roomList.addRoom(room);
            oldId.setText(room.getId());
        }
        catch (Exception e)
        {
            if(x == 0)
                message.setText("    Room   creation   FAILED");
            x = 0;
            aux =  new Room(Integer.parseInt(oldSize.getText()), oldId.getText());
            roomList.addRoom(aux);
        }
        hdmi.setSelected(false);
        vga.setSelected(false);
        idBox.clear();
        sizeBox.clear();
        roomList.saveToBinaryFile();


    }

    public void goDelete() throws IOException
    {
        RoomList roomList = new RoomList();
        roomList.loadFromBinaryFile("roomData.bin");
        roomList.removeRoom(oldId.getText());
        roomList.saveToBinaryFile();
        message.setText("Room deleted succesefully");
    }

    public void setData(String id, String capacity, String courseName, Boolean hasHDMI, Boolean hasVGA, String examType)
    {
        this.oldId.setText(id);
        this.oldSize.setText(capacity);
        this.oldCourse.setText(courseName);
        if(hasHDMI)
            this.hdmi.setSelected(true);
        if(hasVGA)
            this.vga.setSelected(true);
        this.oldRoom = oldRoom;
    }

    public void initialize()
    {
        ObservableList<String> options = FXCollections.observableArrayList();
        CourseList courseList = new CourseList();
        courseList.loadFromBinaryFile("courseData.bin");
        for(int i = 0; i < courseList.getSize(); i++)
            options.add(courseList.getAllCourses().get(i).getName());
        pickCourse.setItems(options);
    }
}
