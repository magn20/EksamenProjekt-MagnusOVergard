package Gui.controller;

import Gui.model.StudentModel;
import Gui.model.TeacherModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Student;
import be.Teacher;
import bll.utill.BCrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class LoginController implements Initializable {

    @FXML
    private PasswordField lblPassword;
    @FXML
    private TextField lblUsername;

    private SceneSwapper sceneSwapper;
    private TeacherModel teacherModel;
    private SingletonUser singletonUser;
    private StudentModel studentModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper = new SceneSwapper();
        teacherModel = new TeacherModel();
        studentModel = new StudentModel();

        singletonUser = SingletonUser.getInstance();
    }


    /**
     * login functions and will switch to stage
     */
    public void onLoginBtn(ActionEvent actionEvent) throws IOException, SQLException {
        try {

            boolean correctLogin = false;

            //checks for admin
            if (lblPassword.getText().equals("admin") & lblUsername.getText().equals("admin")){
                sceneSwapper.sceneSwitch(new Stage(), "AdminScreen.fxml");
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
                correctLogin = true;
            }

            for (Teacher teacher : teacherModel.getTeachers()){
                if (teacher.getUsername().equals(lblUsername.getText())){
                    if(BCrypt.checkpw(lblPassword.getText(), teacher.getPassword())){
                        // Sceneswapper
                        correctLogin = true;
                        singletonUser.setTeacher(teacher);
                        sceneSwapper.TeacherScreen(new Stage());
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();

                        break;
                    }

                }
            }

            for (Student student : studentModel.getStudents()){
                if (student.getUsername().equals(lblUsername.getText())){
                    if(BCrypt.checkpw(lblPassword.getText(), student.getPassword())){
                        // Sceneswapper
                        correctLogin = true;
                        singletonUser.setStudent(student);
                        sceneSwapper.sceneSwitch(new Stage(), "StudentScreen.fxml");
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();

                        break;
                    }

                }
            }


            // if login failed due to wrong information
            if (!correctLogin){
                displayMessage("Brugernavn eller Kodeord var Forkert");
            }

        }catch (Exception e){
            displayError(e);
        }
    }

}
