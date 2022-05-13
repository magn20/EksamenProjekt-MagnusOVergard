package Gui.controller;

import Gui.model.CitizenModel;
import Gui.utill.SingletonUser;
import be.Citizen;
import be.HealthJournalObservation;
import dal.db.HealthJournalObservationDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HealthJournalObservationsController implements Initializable {
    @FXML
    private TextArea tctObservation;
    private SingletonUser singletonUser;
    private Citizen citizen;
    private CitizenModel citizenModel;

    public void onSaveCloseBtn(ActionEvent actionEvent) throws SQLException, IOException {
        if (citizenModel.getHealthJournalObservation(citizen.getId()) == null){
            citizenModel.createHealthJournalObservation(new HealthJournalObservation(-1, citizen.getId(),tctObservation.getText()));
        }else{
            HealthJournalObservation healthJournalObservation = citizenModel.getHealthJournalObservation(citizen.getId());
            healthJournalObservation.setObservation(tctObservation.getText());
            citizenModel.updateHealthJournalObservation(healthJournalObservation);
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
            if (citizenModel.getHealthJournalObservation(citizen.getId()) != null){
                tctObservation.setText(citizenModel.getHealthJournalObservation(citizen.getId()).getObservation());
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
