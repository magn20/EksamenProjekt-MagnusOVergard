package Gui.controller;

import Gui.model.SchoolModel;
import Gui.model.StudentModel;
import Gui.model.TeacherModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.School;
import be.Student;
import be.Teacher;
import bll.utill.BCrypt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class SchoolAdminAddStudentController implements Initializable {
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
    StudentModel studentModel;
    private ObservableList<School> allSchools;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper  = new SceneSwapper();
        studentModel = new StudentModel();



    }



    /**
     * switches stage and scene back to admin screen.
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "SchoolAdminScreen.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Add student to Database
     * checks for no inputs
     * checks for no school selection
     */
    public void onAddBtn(ActionEvent actionEvent) {
        // checks for no inputs
        if (txtFName.getText().equals("") || txtLName.getText().equals("") || txtUsername.getText().equals("") || txtPassword.getText().equals("")){
           displayMessage("Der mangler infomation");
           //checks for school selection
        }else {
            try {
                        // generate salt
                        String salt = BCrypt.gensalt(10);
                        //hash + salt one liner
                        String hashed = BCrypt.hashpw(txtPassword.getText(), salt);

                        // creates new Student object
                        Student student = new Student(-1,singletonUser.getSchoolAdmin().getSchoolId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);

                        // adds the student to database
                        studentModel.createStudent(student);

                        //updates ui
                        updateStatus(student);
                    } catch (SQLException | IOException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }




    /**
     * updates the ui for user feeling of succesful creation of Student.
     * @param student
     */
    public void updateStatus(Student student){
        lblStatus.setText("tilføjet ny Elev: " + student.getFName() + " " + student.getLName() );
        txtPassword.setText("");
        txtUsername.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }

}
