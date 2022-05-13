package Gui.controller;

import Gui.model.SchoolModel;
import Gui.model.StudentModel;
import Gui.model.TeacherModel;
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

public class AdminAddStudentController implements Initializable {
    @FXML
    private Label lblStatus;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolModel = new SchoolModel();
        sceneSwapper  = new SceneSwapper();
        studentModel = new StudentModel();

        allSchools = FXCollections.observableArrayList();

        try {
            fillComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * fills all schools into combobox
     */
    public void fillComboBox() throws SQLException {
        try {
            allSchools.clear();
            allSchools = schoolModel.getSchools();

            for(School school: allSchools){
                cbSchool.getItems().add(school.getName());
            }
        }catch (SQLException | IOException sqlException){
            displayError(sqlException);
            sqlException.printStackTrace();
        }
    }

    /**
     * switches stage and scene back to admin screen.
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminScreen.fxml");
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
        }else if(cbSchool.getSelectionModel().isEmpty()){
            displayMessage("vælg en skole for Eleven");
        }else {
            try {
                // finds school that is selected
                for (School school: allSchools){
                    if (cbSchool.getSelectionModel().getSelectedItem().equals(school.getName())){

                        // generate salt
                        String salt = BCrypt.gensalt(10);
                        //hash + salt one liner
                        String hashed = BCrypt.hashpw(txtPassword.getText(), salt);

                        // creates new Student object
                        Student student = new Student(-1,school.getId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);

                        // adds the student to database
                        studentModel.createStudent(student);

                        //updates ui
                        updateStatus(student);
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                displayError(e);
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
