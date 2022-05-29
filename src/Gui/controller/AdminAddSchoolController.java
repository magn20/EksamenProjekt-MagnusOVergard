package Gui.controller;

import Gui.model.SchoolModel;
import Gui.utill.SceneSwapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayMessage;


public class AdminAddSchoolController implements Initializable {
    @FXML
    private TextField txtSchoolName;

    SceneSwapper sceneSwapper;
    SchoolModel schoolModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper = new SceneSwapper();
        schoolModel = new SchoolModel();
    }


    /**
     * adds a school to Database
     *
     * @param actionEvent
     */
    public void onAddBtn(ActionEvent actionEvent) {
        //checks for no input for school name.
        if (txtSchoolName.getText().equals("")) {
            displayMessage("du har ikke skrevet noget navn");
        } else {
            // creates school
            try {
                schoolModel.createSchool(txtSchoolName.getText());
                txtSchoolName.setText("");
            } catch (Exception e) {
                displayMessage("Der skete en Fejl prøv igen");
                e.printStackTrace();
            }
        }


    }

    /**
     * switches back to AdminScreen
     */
    public void onBackBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminScreen.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


}
