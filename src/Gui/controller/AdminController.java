package Gui.controller;

import Gui.model.SchoolModel;
import Gui.utill.SceneSwapper;
import be.School;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TableView<School> tvSchool;
    @FXML
    private TableColumn<School, String> tcSchoolname;
    @FXML
    private TableColumn<School, String> tcSchoolId;

    SchoolModel schoolModel;
    SceneSwapper sceneSwapper;
    private ObservableList<School> allSchools;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolModel = new SchoolModel();
        sceneSwapper = new SceneSwapper();

        allSchools = FXCollections.observableArrayList();

        prepareTableview();
    }

    public void prepareTableview(){
        // setup for the tableview School
        tcSchoolname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcSchoolId.setCellValueFactory(cellData -> cellData.getValue().getIdPropertyAsStringProperty()  );

        setTableview();
    }

    public void setTableview(){
        allSchools.clear();
        allSchools.addAll(schoolModel.getSchools());
        tvSchool.setItems(allSchools);
    }


    public void onAddSchoolBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminAddSchool.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

    }

    public void onRemoveSchoolBtn(ActionEvent actionEvent) {
        if (tvSchool.getSelectionModel().getSelectedItem() == null) {
            displayMessage("Ingen Skole er valgt");
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Ville du gerne fjerne denne skole, fra systemet, indholder også alle lærer,elever og borger");
            a.setTitle("Fjern Skole");
            a.setHeaderText("Fjern skole: " + tvSchool.getSelectionModel().getSelectedItem().getName() + " fra systemet");
            a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                try {
                    schoolModel.removeSchool(tvSchool.getSelectionModel().getSelectedItem());
                    prepareTableview();
                } catch (SQLException e) {
                    e.printStackTrace();
                    displayError(e);
                }
            });
        }

    }

    public void onEditSchoolBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminEditSchool.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
