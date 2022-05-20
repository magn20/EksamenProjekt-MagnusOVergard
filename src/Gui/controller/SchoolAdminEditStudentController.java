package Gui.controller;

import Gui.model.SchoolModel;
import Gui.model.StudentModel;
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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class SchoolAdminEditStudentController implements Initializable {
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
    SchoolModel schoolModel;
    StudentModel studentModel;

    private Student selectedStudent = SingletonUser.getInstance().getStudent();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper  = new SceneSwapper();
        studentModel = new StudentModel();


    }


    /**
     * Set Admin Screen stage and closes currently stage
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "SchoolAdminScreen.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * edit a Student
     */
    public void onEditBtn(ActionEvent actionEvent) {
            try {
                // checks if a password change is needed if not just change the student
                  if (txtPassword.getText().equals("")) {
                                Student student = new Student(selectedStudent.getId(),selectedStudent.getSchoolId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), selectedStudent.getPassword());
                                studentModel.updateStudent(student);
                                updateStatus(student);
                                // if a password change is detected. ask for confirmation before proceeding with student change.
                            }else {
                                String salt = BCrypt.gensalt(10);
                                //hash + salt one liner
                                String hashed = BCrypt.hashpw(txtPassword.getText(), salt);

                                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "du overskriver kodeordet for denne elev");
                                a.setTitle("Rediger i Elev ");
                                a.setHeaderText("Ville du gerne redigere denne elev");
                                a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                                    Student student = new Student(selectedStudent.getId(),selectedStudent.getSchoolId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
                                    try {
                                        studentModel.updateStudent(student);
                                    } catch (SQLException | IOException e) {
                                        e.printStackTrace();
                                        displayError(e);
                                    }
                                    try {
                                        updateStatus(student);
                                    } catch (SQLException | IOException e) {
                                        e.printStackTrace();
                                    }

                                });
                            }
            }catch (Exception e){
                e.printStackTrace();
                displayError(e);
            }
        }


    /**
     * updates the view for visually effect of successful change to student
     */
    public void updateStatus(Student student) throws SQLException, IOException {
        lblStatus.setText("redigeret i: " + student.getFName() + " " + student.getLName() );
        txtPassword.setText("");
        txtUsername.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }
}
