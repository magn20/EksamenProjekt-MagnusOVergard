package Gui.controller;

import Gui.model.TeacherModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Teacher;
import bll.utill.BCrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class SchoolAdminAddTeacherController implements Initializable {
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    SceneSwapper sceneSwapper;
    SingletonUser singletonUser = SingletonUser.getInstance();
    TeacherModel teacherModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper  = new SceneSwapper();
        teacherModel = new TeacherModel();
    }

    /**
     * switching scene and stage back to admin screen.
     * closes current stage
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "SchoolAdminScreen.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    /**
     * add teacher to database
     * checks for no inputs
     */
    public void onAddBtn(ActionEvent actionEvent) {
        // checks for no inputs
        if (txtFName.getText().equals("") || txtLName.getText().equals("") || txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
            displayMessage("Der mangler infomation");
            //checks for selection of school
        } else {
            try {

                // generate salt
                String salt = BCrypt.gensalt(10);
                //hash + salt one liner
                String hashed = BCrypt.hashpw(txtPassword.getText(), salt);

                // creates teacher object & creates it
                Teacher teacher = new Teacher(-1,singletonUser.getSchoolAdmin().getSchoolId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
                updateStatus(teacher);
                teacherModel.createTeacher(teacher);
            }catch(Exception e){
                e.printStackTrace();
                displayError(e);
            }
        }
    }

    /**
     * updates ui for feeling of successful creation of teacher object
     */
    public void updateStatus(Teacher teacher){
        lblStatus.setText("tilføjet ny lære: " + teacher.getFName() + " " + teacher.getLName() );
        txtPassword.setText("");
        txtUsername.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }

}
