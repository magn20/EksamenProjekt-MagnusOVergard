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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class TeacherEditStudentController implements Initializable {


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

    StudentModel studentModel;
    SingletonUser singletonUser;
    Student student;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentModel = new StudentModel();
        singletonUser = SingletonUser.getInstance();
        TeacherController controller = new SceneSwapper().getTeacherController();
        student = controller.getStudentForEdit();

        setupTextFields();

    }

    /**
     * puts the selected students for changes data into textfields.
     */
    private void setupTextFields() {
        txtFName.setText(student.getFName());
        txtLName.setText(student.getLName());
        txtUsername.setText(student.getUsername());
    }

    /**
     * updates a student
     * checks for no inputs
     */
    public void onEditBtn(ActionEvent actionEvent) throws SQLException, IOException {

        // checks for no inputs
        if (txtFName.getText().equals("") || txtLName.getText().equals("") || txtUsername.getText().equals("")) {
            displayMessage("Der mangler infomation");
        } else {
            try {

                if (txtPassword.getText().equals("")) {
                    student.setFName(txtFName.getText());
                    student.setLName(txtLName.getText());
                    student.setUsername(txtUsername.getText());

                    studentModel.updateStudent(student);
                    updateStatus(student);

                } else {

                    String salt = BCrypt.gensalt(10);
                    //hash + salt one liner
                    String hashed = BCrypt.hashpw(txtPassword.getText(), salt);
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "du overskriver kodeordet for denne elev");
                    a.setTitle("Rediger i Elev ");
                    a.setHeaderText("Ville du gerne redigere denne elev");
                    a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                        Student student = new Student(this.student.getId(), this.student.getSchoolId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
                        try {
                            studentModel.updateStudent(student);
                        } catch (SQLException | IOException e) {
                            e.printStackTrace();
                            displayError(e);
                        }
                        updateStatus(student);
                    });
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }


            //updates ui
            // updateStatus(student);
            TeacherController controller = new SceneSwapper().getTeacherController();
            controller.setTableview();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        }
    }


    /**
     * closes the stage
     */
    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * updates the ui for user feeling of successful Update of Student.
     *
     * @param student that has ben created
     */
    public void updateStatus(Student student) {
        lblStatus.setText("Redigeret i Template: " + student.getFName() + " " + student.getLName());
        txtUsername.setText("");
        txtPassword.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }

}
