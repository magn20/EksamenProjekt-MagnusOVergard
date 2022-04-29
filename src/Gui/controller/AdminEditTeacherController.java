package Gui.controller;

import Gui.model.SchoolModel;
import Gui.model.TeacherModel;
import Gui.utill.SceneSwapper;
import be.School;
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

public class AdminEditTeacherController implements Initializable {
    @FXML
    private Label lblStatus;
    @FXML
    private ComboBox cbTeacher;
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
    TeacherModel teacherModel;
    private ObservableList<School> allSchools;
    private ObservableList<Teacher> allTeachers;
    private Teacher selectedTeacher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolModel = new SchoolModel();
        sceneSwapper  = new SceneSwapper();
        teacherModel = new TeacherModel();


        allSchools = FXCollections.observableArrayList();
        allTeachers = FXCollections.observableArrayList();
        fillComboBox();
    }

    /**
     * fills the comboboxs for schools and teachers
     */
    public void fillComboBox(){
        allSchools.clear();
        cbSchool.getItems().clear();
        allSchools = schoolModel.getSchools();

        for(School school: allSchools){
            cbSchool.getItems().add(school.getName());
        }

        allTeachers.clear();
        cbTeacher.getItems().clear();
        allTeachers = teacherModel.getTeachers();
        for (Teacher teacher: allTeachers){
            cbTeacher.getItems().add(teacher.getFName() + " " + teacher.getLName());
        }
    }

    /**
     * switches back to Admin screen
     * and closes the stage
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminScreen.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    /**
     * Edits a teacher
     * @param actionEvent
     */
    public void onEditBtn(ActionEvent actionEvent) {
            try {
                // checks for no selected teacher
                if (cbTeacher.getSelectionModel().isEmpty()){
                    displayMessage("vælg en lærer");
                }else{
                    // runs thru schools and finds school
                    for (School school: allSchools){
                        if (cbSchool.getSelectionModel().getSelectedItem().equals(school.getName())){


                            // checks if there have been entered new password
                            //if not just updates Teacher
                            if (txtPassword.getText().equals("")) {
                                String hashed = selectedTeacher.getPassword();
                                Teacher teacher = new Teacher(selectedTeacher.getId(),school.getId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
                                teacherModel.updateTeacher(teacher);
                                updateStatus(teacher);
                                break;
                                // if password needs to be changes we hash it with salt.
                            }else {
                                // generate salt
                                String salt = BCrypt.gensalt(10);
                                //hash + salt one liner
                                String hashed = BCrypt.hashpw(txtPassword.getText(), salt);

                                // confirmation box to ensure user knows that Teacher Password will be changed
                                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "du overskriver kodeordet");
                                a.setTitle("Redigere læren");
                                a.setHeaderText("Ville du gerne færdigøre denne redigering i læren");
                                a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                                    Teacher teacher = new Teacher(selectedTeacher.getId(),school.getId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
                                    try {
                                        teacherModel.updateTeacher(teacher);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        displayError(e);
                                    }
                                    updateStatus(teacher);
                                });
                                break;
                            }
                        }
                    }


                }  }catch (Exception e){
                e.printStackTrace();
                displayError(e);
            }


        }


    /**
     * inputs all the teachers data into the diffrent txtfields and combobox.
     * @param actionEvent runs when an action is performed on combobox
     */
    public void onTeacherCb(ActionEvent actionEvent) {

        // finds selected teacher
        for (Teacher teacher : allTeachers){
            if (cbTeacher.getSelectionModel().getSelectedItem().equals(teacher.getFName() + " " + teacher.getLName())){
               //sets up txtfields and input teacher data in
                txtFName.setText(teacher.getFName());
                txtLName.setText(teacher.getLName());
                txtUsername.setText(teacher.getUsername());
                selectedTeacher = teacher;

                // gets the teachers school and automatic picks it.
                for (School school: allSchools){
                    if (teacher.getSchoolId() == school.getId()){
                        cbSchool.getSelectionModel().select(school.getName());
                    }
                }
            }

        }

    }

    /**
     * tells the user that the change happend, and removes the data from textfields for visual feeling of succesfull changes
     * @param teacher sends the teacher that has been changed
     */
    public void updateStatus(Teacher teacher){
        lblStatus.setText("Redigeret i læreren: " + teacher.getFName() + " " + teacher.getLName() );
        txtPassword.setText("");
        txtUsername.setText("");
        txtFName.setText("");
        txtLName.setText("");
        fillComboBox();
    }
}
