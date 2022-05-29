package Gui.controller;

import Gui.model.StudentModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Student;
import bll.utill.BCrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class TeacherAddStudentController implements Initializable {


    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtFName;

    SingletonUser singletonUser;
    StudentModel studentModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singletonUser = SingletonUser.getInstance();
        studentModel = new StudentModel();
    }

    /**
     * adds a Student,
     */
    public void onAddBtn(ActionEvent actionEvent) {
        // checks for no inputs
        if (txtFName.getText().equals("") || txtLName.getText().equals("") || txtUsername.getText().equals("") || txtPassword.getText().isEmpty()) {
            displayMessage("Der mangler infomation");
        } else {
            try {

                String salt = BCrypt.gensalt(10);
                //hash + salt one liner
                String hashed = BCrypt.hashpw(txtPassword.getText(), salt);
                // creates new Student object
                Student student = new Student(-1, singletonUser.getTeacher().getSchoolId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);

                // adds the student to database
                studentModel.createStudent(student);

                //updates ui
                updateStatus(student);


                TeacherController controller = new SceneSwapper().getTeacherController();
                controller.setTableview();
            } catch (Exception e) {
                e.printStackTrace();
                displayError(e);
            }
        }

    }

    /**
     * Closes the stage
     */
    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * updates the ui for user feeling of succesful creation of student.
     *
     * @param student that has ben created
     */
    public void updateStatus(Student student) {
        lblStatus.setText("tilføjet ny elev: " + student.getFName() + " " + student.getLName());
        txtUsername.setText("");
        txtPassword.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }

}
