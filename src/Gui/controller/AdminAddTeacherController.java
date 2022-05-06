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

public class AdminAddTeacherController implements Initializable {
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
    TeacherModel teacherModel;
    private ObservableList<School> allSchools;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolModel = new SchoolModel();
        sceneSwapper  = new SceneSwapper();
        teacherModel = new TeacherModel();

        allSchools = FXCollections.observableArrayList();
        try {
            fillComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * fills combobox with all schools
     */
    public void fillComboBox() throws SQLException {
        try {
            allSchools.clear();
            allSchools = schoolModel.getSchools();

            for(School school: allSchools){
                cbSchool.getItems().add(school.getName());
            }

        }catch (SQLException sqlException){
            displayError(sqlException);
            sqlException.printStackTrace();
        }
    }

    /**
     * switching scene and stage back to admin screen.
     * closes current stage
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminScreen.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    /**
     * add teacher to database
     * checks for no inputs
     * checks for school teacher should belong to
     */
    public void onAddBtn(ActionEvent actionEvent) {
        // checks for no inputs
        if (txtFName.getText().equals("") || txtLName.getText().equals("") || txtUsername.getText().equals("") || txtPassword.getText().equals("")){
           displayMessage("Der mangler infomation");
           //checks for selection of school
        }else if(cbSchool.getSelectionModel().isEmpty()){
            displayMessage("vælg en skole for læren");
        }else {
            try {
                //finds school that user has selected
                for (School school: allSchools){
                    if (cbSchool.getSelectionModel().getSelectedItem().equals(school.getName())){

                        // generate salt
                        String salt = BCrypt.gensalt(10);
                        //hash + salt one liner
                        String hashed = BCrypt.hashpw(txtPassword.getText(), salt);

                        // creates teacher object & creates it
                        Teacher teacher = new Teacher(-1,school.getId(), txtFName.getText(), txtLName.getText(), txtUsername.getText(), hashed);
                        updateStatus(teacher);
                        teacherModel.createTeacher(teacher);
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
