package Gui.controller;

import Gui.model.TPLModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Citizen;
import be.Template;
import bll.CitizenFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class TeacherAddCitizenController implements Initializable {


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
     * adds a citizen.
     * @param actionEvent
     */
    public void onAddBtn(ActionEvent actionEvent) {
        // checks for no inputs
        if (txtFName.getText().equals("") || txtLName.getText().equals("") || txtAge.getText().equals("")){
            displayMessage("Der mangler infomation");
        }else {
            try {
                 // creates new Student object
                 Citizen citizen = new Citizen(-1,singletonUser.getTeacher().getSchoolId(), txtFName.getText(), txtLName.getText(), txtAge.getText());
                 // adds the student to database
                 citizenFacade.createCitizen(citizen);
                 //updates ui
                 updateStatus(citizen);


                TeacherController controller = new SceneSwapper().getTeacherController();
                controller.setTableview();
            }catch (Exception e){
                e.printStackTrace();
                displayError(e);
            }
        }

    }

    /**
     * closses the stage.
     * @param actionEvent
     */
    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * updates the ui for user feeling of succesful creation of Template.
     * @param citizen that has ben created
     */
    public void updateStatus(Citizen citizen){
        lblStatus.setText("tilføjet ny Template: " + citizen.getfName() + " " + citizen.getlName());
        txtAge.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }

}
