package Gui.controller;

import Gui.model.TeacherModel;
import Gui.utill.SingletonUser;
import be.Teacher;
import bll.utill.BCrypt;
import bll.utill.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeacherNewPasswordController implements Initializable {


    @FXML
    private TextField txtRepeatNewPassword;
    @FXML
    private TextField txtNewPassword;
    @FXML
    private Label lblStatus;

    private SingletonUser singletonUser;
    private TeacherModel teacherModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singletonUser = SingletonUser.getInstance();
        teacherModel = new TeacherModel();

    }

    /**
     * updates a Teacher with new hashed password
     * checks both textfields for password match
     */
    public void onSaveBtn(ActionEvent actionEvent) throws SQLException {
        try {

            if (!txtNewPassword.getText().equals(txtRepeatNewPassword.getText())) {
                DisplayMessage.displayMessage("Brugernavn Matcher ikke");
            } else {

                String salt = BCrypt.gensalt(10);

                String hashed = BCrypt.hashpw(txtNewPassword.getText(), salt);

                Teacher teacher = singletonUser.getTeacher();
                teacher.setPassword(hashed);
                teacherModel.updateTeacher(teacher);

                lblStatus.setText("Status: opdateret Password");
            }
        } catch (Exception exception) {
            DisplayMessage.displayError(exception);
            exception.printStackTrace();
        }


    }

    /**
     * closes the stage
     */
    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
