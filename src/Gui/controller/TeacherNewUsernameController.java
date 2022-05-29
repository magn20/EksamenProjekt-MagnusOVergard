package Gui.controller;

import Gui.model.TeacherModel;
import Gui.utill.SingletonUser;
import be.Teacher;
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

public class TeacherNewUsernameController implements Initializable {


    @FXML
    private TextField txtRepeatNewUsername;
    @FXML
    private TextField txtNewUsername;
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
     * saves the new username of a Teacher
     * checks that both textfields for username match
     */
    public void onSaveBtn(ActionEvent actionEvent) throws SQLException {
        try {

            if (!txtNewUsername.getText().equals(txtRepeatNewUsername.getText())) {
                DisplayMessage.displayMessage("Brugernavn Matcher ikke");
            } else {
                Teacher teacher = singletonUser.getTeacher();
                teacher.setUsername(txtNewUsername.getText());
                teacherModel.updateTeacher(teacher);

                lblStatus.setText("Status: opdateret brugernavn");
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
