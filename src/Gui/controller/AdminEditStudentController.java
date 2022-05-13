package Gui.controller;

import Gui.model.SchoolModel;
import Gui.model.StudentModel;
import Gui.utill.SceneSwapper;
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

public class AdminEditStudentController implements Initializable {
    @FXML
    private Label lblStatus;
    @FXML
    private ComboBox cbStudent;
    @FXML
    private TextField txtFName;
    @FXML
    private ComboBox cbSchool;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    SceneSwapper sceneSwapper;
    SchoolModel schoolModel;
    StudentModel studentModel;
    private ObservableList<School> allSchools;
    private ObservableList<Student> allStudents;
    private Student selectedStudent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolModel = new SchoolModel();
        sceneSwapper  = new SceneSwapper();
        studentModel = new StudentModel();


        allSchools = FXCollections.observableArrayList();
        allStudents = FXCollections.observableArrayList();
        try {
            fillComboBox();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            displayError(e);
        }
    }

    /**
     * fills combobox for Schools & Students
     */
    public void fillComboBox() throws SQLException, IOException {
        allSchools.clear();
        cbSchool.getItems().clear();
        allSchools = schoolModel.getSchools();

        for(School school: allSchools){
            cbSchool.getItems().add(school.getName());
        }

        allStudents.clear();
        cbStudent.getItems().clear();
        allStudents = studentModel.getStudents();
        for (Student student: allStudents){
            cbStudent.getItems().add(student.getFName() + " " + student.getLName());
        }
    }

    /**
     * Set Admin Screen stage and closes currently stage
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminScreen.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * edit a Student
     */
    public void onEditBtn(ActionEvent actionEvent) {
            try {
                //checks if student is selected
                if (cbStudent.getSelectionModel().isEmpty()){
                    displayMessage("vælg en Elev");
                }else{

                    //finds school selected
                    for (School school: allSchools){
                        if (cbSchool.getSelectionModel().getSelectedItem().equals(school.getName())){

                            // checks if a password change is needed if not just change the student
                            if (txtPassword.getText().equals("")) {
                                String hashed = selectedStudent.getPassword();
                                Student student = new Student(selectedStudent.getId(),school.getId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
                                studentModel.updateStudent(student);
                                updateStatus(student);
                                break;
                                // if a password change is detected. ask for confirmation before proceeding with student change.
                            }else {
                                String salt = BCrypt.gensalt(10);
                                //hash + salt one liner
                                String hashed = BCrypt.hashpw(txtPassword.getText(), salt);


                                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "du overskriver kodeordet for denne elev");
                                a.setTitle("Rediger i Elev ");
                                a.setHeaderText("Ville du gerne redigere denne elev");
                                a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                                    Student student = new Student(selectedStudent.getId(),school.getId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
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
                                        displayError(e);
                                    }
                                });
                                break;
                            }
                        }
                    }
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
        fillComboBox();
    }

    /**
     * shows the data of the selected student.
     * @param actionEvent runs on an action on ComboBox
     */
    public void onStudentCb(ActionEvent actionEvent) {
        for (Student student : allStudents){
            if (cbStudent.getSelectionModel().getSelectedItem().equals(student.getFName() + " " + student.getLName())){
                txtFName.setText(student.getFName());
                txtLName.setText(student.getLName());
                txtUsername.setText(student.getUsername());
                selectedStudent = student;
                for (School school: allSchools){
                    if (student.getSchoolId() == school.getId()){
                        cbSchool.getSelectionModel().select(school.getName());
                    }
                }
            }

        }
    }
}
