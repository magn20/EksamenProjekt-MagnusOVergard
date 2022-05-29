package Gui.controller;

import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Citizen;
import bll.CitizenFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class TeacherEditCitizenController implements Initializable {


    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtFName;

    CitizenFacade citizenFacade;
    SingletonUser singletonUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        citizenFacade = new CitizenFacade();
        singletonUser = SingletonUser.getInstance();
    }

    /**
     * edit a citizen
     * checks for no inputs
     */
    public void onEditBtn(ActionEvent actionEvent) {


        // checks for no inputs
        if (txtFName.getText().equals("") || txtLName.getText().equals("") || txtAge.getText().equals("")) {
            displayMessage("Der mangler infomation");
        } else {
            try {


                TeacherController controller = new SceneSwapper().getTeacherController();
                Citizen citizen = controller.getCitizenForEdit();
                citizen.setlName(txtLName.getText());
                citizen.setFName(txtFName.getText());
                citizen.setAge(txtAge.getText());


                // adds the student to database
                citizenFacade.updateCitizen(citizen);


                //updates ui
                updateStatus(citizen);

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                e.printStackTrace();
                displayError(e);
            }
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
     * updates the ui for user feeling of succesful Update of Template.
     *
     * @param citizen that has ben created
     */
    public void updateStatus(Citizen citizen) {
        lblStatus.setText("Redigeret i Template: " + citizen.getfName() + " " + citizen.getlName());
        txtAge.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }

}
