package Gui.controller;

import Gui.model.SchoolModel;
import Gui.utill.SceneSwapper;
import be.School;
import be.SchoolAdmin;
import bll.SchoolAdminManager;
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

import static bll.utill.DisplayMessage.displayMessage;

public class AdminAddSchoolAdminController implements Initializable {
    @FXML
    private ComboBox cbSchool;
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
    private SchoolAdminManager schoolAdminManager;
    private SchoolModel schoolModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper = new SceneSwapper();
        schoolAdminManager = new SchoolAdminManager();
        schoolModel = new SchoolModel();

        try {
            setupComboBox();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * setup the combobox for schools with all schools
     */
    private void setupComboBox() throws SQLException, IOException {

        for (School school : schoolModel.getSchools()) {
            cbSchool.getItems().add(school.getName());
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
     * a
     * add SchoolAdmin to database
     * checks for no inputs
     */
    public void onAddBtn(ActionEvent actionEvent) throws SQLException, IOException {
        // checks for no inputs
        if (txtFName.getText().equals("") || cbSchool.getSelectionModel().isEmpty() || txtLName.getText().equals("") || txtUsername.getText().equals("") || txtPassword.getText().isEmpty()) {
            displayMessage("Der mangler infomation");
            //checks for selection of school
        } else {
            int schoolId = -1;
            for (School school : schoolModel.getSchools()) {
                if (school.getName().equals(cbSchool.getSelectionModel().getSelectedItem())) {
                    schoolId = school.getId();
                }
                String fName = txtFName.getText();
                String lName = txtLName.getText();
                String username = txtUsername.getText();

                // generate salt
                String salt = BCrypt.gensalt(10);
                //hash + salt one liner
                String hashed = BCrypt.hashpw(txtPassword.getText(), salt);

                updateStatus(schoolAdminManager.createSchoolAdmin(new SchoolAdmin(-1, schoolId, fName, lName, username, hashed)));
            }
        }
    }


    /**
     * updates ui for feeling of successful creation of schoolAdmin object
     */
    public void updateStatus(SchoolAdmin schoolAdmin) {
        lblStatus.setText("tilføjet ny skole admin: " + schoolAdmin.getFName() + " " + schoolAdmin.getLName());
        txtPassword.setText("");
        txtUsername.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }

}
