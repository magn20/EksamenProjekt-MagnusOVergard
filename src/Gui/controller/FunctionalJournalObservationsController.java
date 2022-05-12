package Gui.controller;

import Gui.model.CitizenModel;
import Gui.utill.SingletonUser;
import be.Citizen;
import be.FunctionJournalObservation;
import be.HealthJournalObservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FunctionalJournalObservationsController implements Initializable {
    @FXML
    private TextArea tctObservation;
    private SingletonUser singletonUser;
    private Citizen citizen;
    private CitizenModel citizenModel;

    public void onSaveCloseBtn(ActionEvent actionEvent) throws SQLException {
        if (citizenModel.getFunctionJournalObservation(citizen.getId()) == null){
            citizenModel.createFunctionJournalObservation(new FunctionJournalObservation(-1, citizen.getId(),tctObservation.getText()));
        }else{
            FunctionJournalObservation functionJournalObservation = citizenModel.getFunctionJournalObservation(citizen.getId());
            functionJournalObservation.setObservation(tctObservation.getText());
            citizenModel.updateFunctionJournalObservation(functionJournalObservation);
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    singletonUser = SingletonUser.getInstance();
    citizen = singletonUser.getCitizen();
    citizenModel = new CitizenModel();

        try {
            if (citizenModel.getFunctionJournalObservation(citizen.getId()) != null){
                tctObservation.setText(citizenModel.getFunctionJournalObservation(citizen.getId()).getObservation());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}