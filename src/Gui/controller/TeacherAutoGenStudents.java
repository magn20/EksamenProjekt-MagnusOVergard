package Gui.controller;

import Gui.model.StudentModel;
import Gui.utill.GenerateLogin;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Student;
import bll.utill.BCrypt;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherAutoGenStudents {
    @FXML
    private Label lblGuide;
    @FXML
    private TextArea txtStudents;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnAdd;

    private StudentModel studentModel = new StudentModel();
    private ArrayList<Student> newStudents = new ArrayList<Student>();
    private GenerateLogin generateLogin = new GenerateLogin();
    private SingletonUser singletonUser = SingletonUser.getInstance();
    private TeacherController controller = new SceneSwapper().getTeacherController();

    public void onAddBtn(ActionEvent actionEvent) throws SQLException, IOException {
        lblGuide.setText("Ind sæt Fornavne og Efternavne");

        btnAdd.setDisable(true);
        btnClose.setDisable(true);
        txtStudents.setEditable(false);
        lblGuide.setText("Laver Elever");
        final Runnable runnable = () -> {
            for (String line : txtStudents.getText().split("\\n")) {
                try {
                    handleCreation(line);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        controller.setTableview();
                        btnAdd.setDisable(false);
                        btnClose.setDisable(false);
                        txtStudents.setEditable(true);
                        lblGuide.setText("ELever Lavet");
                        updateTextArea();
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        };

        Thread thread = new Thread(runnable);
        thread.start();

    }

    private void updateTextArea() {
        txtStudents.clear();
        for(Student student: newStudents){

            txtStudents.setText( txtStudents.getText() + "Fornavn: " + student.getFName() + " Efternavn: " + student.getLName() + " Brugernavn: " + student.getUsername() + " Kodeord: "+ student.getPassword() + "\n");
        }
    }

    public void handleCreation(String line) throws SQLException, IOException {


        String fName = "";
        String lName = "";
        String username = "";
        String password = "";



        fName = line.substring(0, line.indexOf(" "));
        lName = line.substring(line.indexOf(" "), line.length());

        username = generateLogin.createUsername(fName);
        password = generateLogin.createPassword();


        String salt = BCrypt.gensalt(10);
        String hashedPw =  BCrypt.hashpw(password, salt);

        Student student = new Student(-1,singletonUser.getTeacher().getSchoolId(),fName,lName,username,password);
        newStudents.add(student);
        studentModel.createStudent( new Student(-1,singletonUser.getTeacher().getSchoolId(),fName,lName,username,hashedPw));

    }

    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
