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
import java.time.LocalDate;

public class EditExamPopupController
{
    private Date examStart = new Date(1, 5, 2020);
    private Date examFinish = new Date(1, 28, 2020);
    private Exam toDelete;
    private Exam aux2;
    private int id2 = 0;
    @FXML Button back;
    @FXML Button delete;
    @FXML Button edit;
    @FXML TextField startHour;
    @FXML TextField startMinute;
    @FXML TextField finishHour;
    @FXML TextField finishMinute;
    @FXML RadioButton written;
    @FXML RadioButton oral;
    @FXML ComboBox pickCourse;
    @FXML DatePicker pickDate;
    @FXML ComboBox pickRoom;
    @FXML ComboBox pickExaminer;
    @FXML ComboBox pickCoExaminer;
    @FXML Label message;

    @FXML TextField oldStartHour;
    @FXML TextField oldStartMinute;
    @FXML TextField oldFinishHour;
    @FXML TextField oldFinishMinute;
    @FXML RadioButton oldWritten;
    @FXML RadioButton oldOral;
    @FXML TextField oldCourse;
    @FXML TextField oldDate;
    @FXML TextField oldRoom;
    @FXML TextField oldExaminer;
    @FXML TextField oldCoExaminer;

    public void goBack() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editexammenu.fxml"));
        Parent root11 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit Exams");
        stage.setScene(new Scene(root11));
        stage.show();
        back.getScene().getWindow().hide();
    }

    public void setData(String examType, String oldCourse, String oldDate, String oldStartHour, String oldStartMinute, String oldFinishHour, String oldFinishMinute, String oldRoom, String oldExaminer, String oldCoExaminer, Exam todelete)
    {
        this.oldOral.setSelected(false);
        this.oldWritten.setSelected(false);
        if(examType.equals("Oral"))
            this.oldOral.setSelected(true);
        else
            this.oldWritten.setSelected(true);
        this.oldCourse.setText(oldCourse);
        this.oldDate.setText(oldDate);
        this.oldStartHour.setText(oldStartHour);
        this.oldStartMinute.setText(oldStartMinute);
        this.oldFinishHour.setText(oldFinishHour);
        this.oldFinishMinute.setText(oldFinishMinute);
        this.oldRoom.setText(oldRoom);
        this.oldExaminer.setText(oldExaminer);
        this.oldCoExaminer.setText(oldCoExaminer);
        this.toDelete = new Exam(todelete);
    }


    public void goEdit() throws IOException
    {

        RoomList roomList = new RoomList();
        roomList.loadFromBinaryFile( "roomData.bin");

        CourseList courseList = new CourseList();
        courseList.loadFromBinaryFile("courseData.bin");

        TeacherList teacherList = new TeacherList();
        teacherList.loadFromBinaryFile("teacherData.bin");

        ExamList examlist = new ExamList();
        examlist.loadFromBinaryFile("examData.bin");

        try
        {
            Time startTime1 = new Time(Integer.parseInt(startHour.getText()), Integer.parseInt(startMinute.getText()), 0);
            Time finishTime1 = new Time(Integer.parseInt(finishHour.getText()), Integer.parseInt(finishMinute.getText()), 0);
            Date examDate1 = new Date(pickDate.getValue().getMonthValue(), pickDate.getValue().getDayOfMonth(), pickDate.getValue().getYear());
            Exam exam2 = new Exam(1, examDate1, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher(pickExaminer.getValue().toString()), teacherList.getTeacher(pickCoExaminer.getValue().toString()), courseList.getCourse(pickCourse.getValue().toString()), startTime1, finishTime1, "written");
        }
        catch (Exception e)
        {
            message.setText("Exam editing FAILED \r\n FIELDS NOT COMPLETED");
            id2++;
            throw new IllegalArgumentException("FIELDS EMPTY");
        }
        if(id2 == 0)
        try
        {
            int id = 0;
            if(examlist.getSize() == 0)
                id = 1;
            else id = examlist.getAllExams().get(examlist.getSize() - 1).getId() + 1;



            aux2 = new Exam(toDelete);
            examlist.removeExam(toDelete.getId());


            Time startTime = new Time(Integer.parseInt(startHour.getText()), Integer.parseInt(startMinute.getText()), 0);
            Time finishTime = new Time(Integer.parseInt(finishHour.getText()), Integer.parseInt(finishMinute.getText()), 0);




            Date examDate = new Date(pickDate.getValue().getMonthValue(), pickDate.getValue().getDayOfMonth(), pickDate.getValue().getYear());

            String examType;

            if(!(examStart.isBefore(examDate) && examDate.isBefore(examFinish)))
            {
                message.setText("Exam editing FAILED \r\n EXAM DATE OUT OF LIMITS *6/1/2020 - 27/1/2020*");
                id2++;
                throw new IllegalArgumentException("WRONG DATE");
            }


            if(oral.isSelected())
                examType = "oral";
            else examType = "written";

            Exam exam;

            if(!(pickExaminer.getValue().toString().equals("            ")) && !(pickCoExaminer.getValue().toString().equals("            ")))
                exam = new Exam(id, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher(pickExaminer.getValue().toString()), teacherList.getTeacher(pickCoExaminer.getValue().toString()), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);
            else if((pickExaminer.getValue().toString().equals("            ")) && (pickCoExaminer.getValue().toString().equals("            ")))
                exam = new Exam(id, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher("NOT ASSIGNED"), teacherList.getTeacher("NOT ASSIGNED"), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);
            else if(!(pickExaminer.getValue().toString().equals("            ")) && (pickCoExaminer.getValue().toString().equals("            ")))
                exam = new Exam(id, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher(pickExaminer.getValue().toString()), teacherList.getTeacher("NOT ASSIGNED"), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);
            else if((pickExaminer.getValue().toString().equals("            ")) && !(pickCoExaminer.getValue().toString().equals("            ")))
                exam = new Exam(id, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher("NOT ASSIGNED"), teacherList.getTeacher(pickCoExaminer.getValue().toString()), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);
            else exam = new Exam(id, examDate, roomList.getRoom(pickRoom.getValue().toString()), teacherList.getTeacher(pickExaminer.getValue().toString()), teacherList.getTeacher(pickCoExaminer.getValue().toString()), courseList.getCourse(pickCourse.getValue().toString()), startTime, finishTime, examType);

            if(exam.getExaminer().isSick() && exam.getExaminer().getSickStartObj().isBefore(examDate) && examDate.isBefore(exam.getExaminer().getSickEndObj()))
            {
                message.setText("Exam creation FAILED \r\n EXAMINER SICK");
                id2++;
                throw new IllegalArgumentException("EXAMINER SICK");
            }


            if(exam.getCoexaminer().isSick() && exam.getCoexaminer().getSickStartObj().isBefore(examDate) && examDate.isBefore(exam.getCoexaminer().getSickEndObj()))
            {
                message.setText("Exam creation FAILED \r\n COEXAMINER SICK");
                id2++;
                throw new IllegalArgumentException("COEXAMINER SICK");
            }

            String aux = exam.getCourse().getName();
            if(aux.charAt(aux.length() - 2) == '7' && (exam.getDate().daysBetween(examFinish) < 3))
            {
                message.setText("Exam creation FAILED \r\n DATE TOO LATE 7TH SEM");
                id2++;
                throw new IllegalArgumentException("DATE TOO LATE 7TH SEM");
            }


            for(int i = 0; i < examlist.getSize(); i++)
            {
                if(!(examDate.daysBetween(examlist.getAllExams().get(i).getDate()) > 1) && exam.getCourse().getName().charAt(exam.getCourse().getName().length() - 2) == examlist.getAllExams().get(i).getCourse().getName().charAt(examlist.getAllExams().get(i).getCourse().getName().length() - 2) && exam.getCourse().getName().charAt(exam.getCourse().getName().length() - 1) == examlist.getAllExams().get(i).getCourse().getName().charAt(examlist.getAllExams().get(i).getCourse().getName().length() - 1))
                {
                    message.setText("Exam creation FAILED \r\n COURSE HAS EXAM TOO CLOSE");
                    id2++;
                    throw new IllegalArgumentException("COURSE TIRED AFTER EXAM");
                }

                if(exam.getDate().equals(examlist.getAllExams().get(i).getDate()))
                {
                    //COURSE
                    if(exam.getCourse().equals(examlist.getAllExams().get(i).getCourse()) || exam.getCourse().getName().charAt(exam.getCourse().getName().length() - 2) == examlist.getAllExams().get(i).getCourse().getName().charAt(examlist.getAllExams().get(i).getCourse().getName().length() - 2) || exam.getCourse().getName().charAt(exam.getCourse().getName().length() - 1) == examlist.getAllExams().get(i).getCourse().getName().charAt(examlist.getAllExams().get(i).getCourse().getName().length() - 1))
                        if(exam.getStartTime().isBefore(examlist.getAllExams().get(i).getStartTime()) && exam.getStartTime().isBefore(examlist.getAllExams().get(i).getFinish()) ||
                                (exam.getFinish().isBefore(examlist.getAllExams().get(i).getFinish()) && examlist.getAllExams().get(i).getStartTime().isBefore(exam.getStartTime())) ||
                                exam.getStartTime().equals(examlist.getAllExams().get(i).getStartTime()) && exam.getFinish().equals(examlist.getAllExams().get(i).getFinish()))
                        {
                            message.setText("Exam creation FAILED \r\n COURSE HAS EXAM AT SAME TIME");
                            id2++;
                            throw new IllegalArgumentException("COURSE OCCUPIED");
                        }


                    // ROOM
                    if(exam.getRoom().equals(examlist.getAllExams().get(i).getRoom()))
                        if(exam.getStartTime().isBefore(examlist.getAllExams().get(i).getStartTime()) && exam.getStartTime().isBefore(examlist.getAllExams().get(i).getFinish()) ||
                                (exam.getFinish().isBefore(examlist.getAllExams().get(i).getFinish()) && examlist.getAllExams().get(i).getStartTime().isBefore(exam.getStartTime())) ||
                                exam.getStartTime().equals(examlist.getAllExams().get(i).getStartTime()) && exam.getFinish().equals(examlist.getAllExams().get(i).getFinish()))
                        {
                            message.setText("Exam creation FAILED \r\n ROOM ALLREADY OCCUPIED");
                            id2++;
                            throw new IllegalArgumentException("ROOM OCCUPIED");
                        }

                    //EXAMINER
                    if(exam.getExaminer().equals(examlist.getAllExams().get(i).getExaminer()) || exam.getExaminer().equals(examlist.getAllExams().get(i).getCoexaminer()))
                        if(exam.getStartTime().isBefore(examlist.getAllExams().get(i).getStartTime()) && exam.getStartTime().isBefore(examlist.getAllExams().get(i).getFinish()) ||
                                (exam.getFinish().isBefore(examlist.getAllExams().get(i).getFinish()) && examlist.getAllExams().get(i).getStartTime().isBefore(exam.getStartTime())) ||
                                exam.getStartTime().equals(examlist.getAllExams().get(i).getStartTime()) && exam.getFinish().equals(examlist.getAllExams().get(i).getFinish()))
                        {
                            message.setText("Exam creation FAILED \r\n EXAMINER HAS EXAM AT SAME TIME");
                            id2++;
                            throw new IllegalArgumentException("EXAMINER OCCUPIED");
                        }

                    //COEXAMINER
                    if(exam.getCoexaminer().equals(examlist.getAllExams().get(i).getCoexaminer()) || exam.getCoexaminer().equals(examlist.getAllExams().get(i).getExaminer()))
                        if(exam.getStartTime().isBefore(examlist.getAllExams().get(i).getStartTime()) && exam.getStartTime().isBefore(examlist.getAllExams().get(i).getFinish()) ||
                                (exam.getFinish().isBefore(examlist.getAllExams().get(i).getFinish()) && examlist.getAllExams().get(i).getStartTime().isBefore(exam.getStartTime())) ||
                                exam.getStartTime().equals(examlist.getAllExams().get(i).getStartTime()) && exam.getFinish().equals(examlist.getAllExams().get(i).getFinish()))
                        {
                            message.setText("Exam creation FAILED \r\n COEXAMINER HAS EXAM AT SAME TIME");
                            id2++;
                            throw new IllegalArgumentException("EXAMINER OCCUPIED");
                        }


                }
            }
            message.setText("Exam added successfully");
            examlist.addExam(exam);
            toDelete.setId(exam.getId());
            examlist.saveToBinaryFile();
        }
        catch (Exception e)
        {
            if(id2 == 0)
                message.setText("Exam creation FAILED \r\n UNKNOWN ERROR CONGRATS");
            else id2 = 0;
            examlist.addExam(aux2);
            examlist.saveToBinaryFile();
            e.printStackTrace();
        }

    }

    public void goDelete() throws IOException
    {
        try
        {
            ExamList examList = new ExamList();
            examList.loadFromBinaryFile("examData.bin");
            examList.removeExam(toDelete.getId());
            examList.saveToBinaryFile();
            message.setText("Exam deletion SUCCEEDED");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            message.setText("Exam deletion FAILED");
        }
    }

    public void recommendRoom()
    {
        RoomList roomList = new RoomList();
        roomList.loadFromBinaryFile( "roomData.bin");
        for(int i = 0; i < roomList.getSize(); i++)
            if((roomList.getAllRooms().get(i).getCourse().getName().charAt(roomList.getAllRooms().get(i).getCourse().getName().length() - 2) == pickCourse.getValue().toString().charAt(pickCourse.getValue().toString().length() - 2)) && (roomList.getAllRooms().get(i).getCourse().getName().charAt(roomList.getAllRooms().get(i).getCourse().getName().length() - 1) == pickCourse.getValue().toString().charAt(pickCourse.getValue().toString().length() - 1)))
                message.setText("     You should pick room \r\n                  " + roomList.getAllRooms().get(i).getId());
    }


    public void initialize() throws IOException
    {
        ObservableList<String> options = FXCollections.observableArrayList();
        CourseList courseList = new CourseList();
        courseList.loadFromBinaryFile("courseData.bin");
        for(int i = 0; i < courseList.getSize(); i++)
            options.add(courseList.getAllCourses().get(i).getName());
        pickCourse.setItems(options);

        ObservableList<String> options2 = FXCollections.observableArrayList();
        RoomList roomList = new RoomList();
        roomList.loadFromBinaryFile("roomData.bin");
        for(int i = 0; i < roomList.getSize(); i++)
            options2.add(roomList.getAllRooms().get(i).getId());
        pickRoom.setItems(options2);

        ObservableList<String> options3 = FXCollections.observableArrayList();
        TeacherList teacherList = new TeacherList();
        teacherList.loadFromBinaryFile("teacherData.bin");
        for(int i = 0; i < teacherList.getSize(); i++)
            options3.add(teacherList.getAllTeachers().get(i).getName());
        pickExaminer.setItems(options3);
        pickCoExaminer.setItems(options3);

        pickDate.setValue(LocalDate.of(2020, 1, 6));


    }

}
