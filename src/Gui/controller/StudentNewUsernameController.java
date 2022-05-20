package Gui.controller;

import Gui.model.StudentModel;
import Gui.utill.SingletonUser;
import be.Student;
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

public class StudentNewUsernameController implements Initializable {


    @FXML
    private TextField txtRepeatNewUsername;
    @FXML
    private TextField txtNewUsername;
    @FXML
    private Label lblStatus;

    private SingletonUser singletonUser;
    private StudentModel studentModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singletonUser = SingletonUser.getInstance();
        studentModel = new StudentModel();

    }

    /**
     * saves the new username of a Teacher
     * checks that both textfields for username match
     */
    public void onSaveBtn(ActionEvent actionEvent) throws SQLException {
        try {

            if (!txtNewUsername.getText().equals(txtRepeatNewUsername.getText())){
                DisplayMessage.displayMessage("Brugernavn Matcher ikke");
            }else {
                Student student = singletonUser.getStudent();
                student.setUsername(txtNewUsername.getText());
                studentModel.updateStudent(student);

                lblStatus.setText("Status: opdateret brugernavn");
            }
        }catch (Exception exception){
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
