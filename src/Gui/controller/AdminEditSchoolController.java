package Gui.controller;

import Gui.model.SchoolModel;
import Gui.utill.SceneSwapper;
import be.School;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class AdminEditSchoolController implements Initializable {

    @FXML
    private TextField txtSchoolName;
    @FXML
    private ComboBox cbSchool;

    SceneSwapper sceneSwapper;
    SchoolModel schoolModel;
    private ObservableList<School> allSchools;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper = new SceneSwapper();
        schoolModel = new SchoolModel();

        allSchools = FXCollections.observableArrayList();
        try {
            fillComboBox();
        } catch (SQLException | IOException e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * fills combobox with all schools
     */
    public void fillComboBox() throws SQLException, IOException {
        allSchools.clear();
        allSchools = schoolModel.getSchools();

        for (School school : allSchools) {
            cbSchool.getItems().add(school.getName());
        }

    }

    /**
     * goes to admin screen and closes current stage.
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminScreen.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * edit a school and check for wrong values
     */
    public void onEditBtn(ActionEvent actionEvent) throws SQLException {
        if (cbSchool.getSelectionModel().isEmpty()) {
            displayMessage("vælg en skole at rediger i");
        } else if (txtSchoolName.getText().equals("")) {
            displayMessage("Skriv skolen navn");
        } else {
            try {
                for (School school : allSchools) {
                    if (cbSchool.getSelectionModel().getSelectedItem().equals(school.getName())) {
                        School school1 = new School(school.getId(), txtSchoolName.getText());
                        schoolModel.updateSchool(school1);
                        txtSchoolName.setText("");
                        fillComboBox();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                displayError(e);
            }

        }
    }


}
