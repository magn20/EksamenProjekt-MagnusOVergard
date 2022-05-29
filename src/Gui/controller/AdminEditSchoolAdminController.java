package Gui.controller;

import Gui.model.SchoolModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
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

public class AdminEditSchoolAdminController implements Initializable {
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
    private SingletonUser singletonUser;
    private SchoolAdmin selectedSchoolAdmin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper = new SceneSwapper();
        schoolAdminManager = new SchoolAdminManager();
        schoolModel = new SchoolModel();
        singletonUser = SingletonUser.getInstance();
        selectedSchoolAdmin = singletonUser.getSchoolAdmin();

        try {
            setupComboBox();
            viewSelectedSchoolAdmin();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * updates the view with selected SchoolAdmin information
     */
    private void viewSelectedSchoolAdmin() throws SQLException, IOException {
        String schoolName = "";
        for (School school : schoolModel.getSchools()) {
            if (school.getId() == selectedSchoolAdmin.getSchoolId()) {
                schoolName = school.getName();
            }
        }

        cbSchool.getSelectionModel().select(schoolName);
        txtFName.setText(selectedSchoolAdmin.getFName());
        txtLName.setText(selectedSchoolAdmin.getLName());
        txtUsername.setText(selectedSchoolAdmin.getUsername());
    }

    /**
     * setup combobox for schools
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
     * add SchoolAdmin to database
     * checks for no inputs
     */
    public void onAddBtn(ActionEvent actionEvent) throws SQLException, IOException {
        // checks for no inputs
        if (txtFName.getText().equals("") || cbSchool.getSelectionModel().isEmpty() || txtLName.getText().equals("") || txtUsername.getText().equals("")) {
            displayMessage("Der mangler infomation");
            //checks for selection of school
        } else if (txtPassword.getText().isEmpty()) {
            for (School school : schoolModel.getSchools()) {
                if (school.getName().equals(cbSchool.getSelectionModel().getSelectedItem())) {
                    selectedSchoolAdmin.setSchoolId(school.getId());
                }
                selectedSchoolAdmin.setFName(txtFName.getText());
                selectedSchoolAdmin.setLName(txtLName.getText());
                selectedSchoolAdmin.setUsername(txtUsername.getText());

                schoolAdminManager.updateSchoolAdmin(selectedSchoolAdmin);
            }
        } else {
            // generate salt
            String salt = BCrypt.gensalt(10);
            //hash + salt one liner
            String hashed = BCrypt.hashpw(txtPassword.getText(), salt);

            // confirmation box to ensure user knows that Teacher Password will be changed
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "du overskriver kodeordet");
            a.setTitle("Redigere skole admin");
            a.setHeaderText("Ville du gerne færdigøre denne redigering i skole admin");
            a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {

                try {
                    for (School school : schoolModel.getSchools()) {
                        if (school.getName().equals(cbSchool.getSelectionModel().getSelectedItem())) {
                            selectedSchoolAdmin.setSchoolId(school.getId());
                        }
                        selectedSchoolAdmin.setFName(txtFName.getText());
                        selectedSchoolAdmin.setLName(txtLName.getText());
                        selectedSchoolAdmin.setUsername(txtUsername.getText());
                        selectedSchoolAdmin.setPassword(hashed);
                        schoolAdminManager.updateSchoolAdmin(selectedSchoolAdmin);
                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }


    /**
     * updates ui for feeling of successful creation of schoolAdmin object
     */
    public void updateStatus(SchoolAdmin schoolAdmin) {
        lblStatus.setText("Redigeret skole admin: " + schoolAdmin.getFName() + " " + schoolAdmin.getLName());
        txtPassword.setText("");
        txtUsername.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }

}
