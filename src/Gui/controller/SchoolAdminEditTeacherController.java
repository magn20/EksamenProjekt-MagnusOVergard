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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;

public class SchoolAdminEditTeacherController implements Initializable {
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

    private SceneSwapper sceneSwapper;
    private SingletonUser singletonUser = SingletonUser.getInstance();
    private TeacherModel teacherModel;
    private Teacher selectedTeacher = singletonUser.getTeacher();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper = new SceneSwapper();
        teacherModel = new TeacherModel();
    }

    /**
     * switches back to Admin screen
     * and closes the stage
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "SchoolAdminScreen.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    /**
     * Edits a teacher
     *
     * @param actionEvent
     */
    public void onEditBtn(ActionEvent actionEvent) {
        try {
            // checks if there have been entered new password
            //if not just updates Teacher
            if (txtPassword.getText().equals("")) {
                String hashed = selectedTeacher.getPassword();
                Teacher teacher = new Teacher(selectedTeacher.getId(), selectedTeacher.getSchoolId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
                teacherModel.updateTeacher(teacher);
                updateStatus(teacher);
                // if password needs to be changes we hash it with salt.
            } else {
                // generate salt
                String salt = BCrypt.gensalt(10);
                //hash + salt one liner
                String hashed = BCrypt.hashpw(txtPassword.getText(), salt);

                // confirmation box to ensure user knows that Teacher Password will be changed
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "du overskriver kodeordet");
                a.setTitle("Redigere læren");
                a.setHeaderText("Ville du gerne færdigøre denne redigering i læren");
                a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                    Teacher teacher = new Teacher(selectedTeacher.getId(), selectedTeacher.getSchoolId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
                    try {
                        teacherModel.updateTeacher(teacher);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                        displayError(e);
                    }
                    try {
                        updateStatus(teacher);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                        displayError(e);
                    }
                });
            }
        } catch (SQLException | IOException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * tells the user that the change happend, and removes the data from textfields for visual feeling of succesfull changes
     *
     * @param teacher sends the teacher that has been changed
     */
    public void updateStatus(Teacher teacher) throws SQLException, IOException {
        lblStatus.setText("Redigeret i læreren: " + teacher.getFName() + " " + teacher.getLName());
        txtPassword.setText("");
        txtUsername.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }
}
