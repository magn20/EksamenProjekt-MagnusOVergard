package Gui.controller;

import Gui.model.StudentModel;
import Gui.utill.GenerateLogin;
import Gui.utill.SingletonUser;
import be.Student;
import bll.utill.BCrypt;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherAutoGenStudents {
    public TextArea txtStudents;
    private StudentModel studentModel = new StudentModel();
    private ArrayList<Student> newStudents = new ArrayList<Student>();
    private GenerateLogin generateLogin = new GenerateLogin();
    private SingletonUser singletonUser = SingletonUser.getInstance();

    public void onAddBtn(ActionEvent actionEvent) throws SQLException, IOException {
        for (String line : txtStudents.getText().split("\\n")) handleCreation(line);

        updateTextArea();
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
    }
}
