package Gui.controller;

import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Citizen;
import be.Student;
import bll.CitizenFacade;
import bll.utill.DisplayMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private TableView<Citizen> tvCitizen;
    @FXML
    private TableColumn<Citizen, Integer> tcCitizenID;
    @FXML
    private TableColumn<Citizen, String> tcCitizenFName;
    @FXML
    private TableColumn<Citizen, String> tcCitizenLName;
    @FXML
    private TableColumn<Citizen, String> tcCitizenAge;

    private SceneSwapper sceneSwapper;
    private SingletonUser singletonUser;
    private CitizenFacade citizenFacade;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singletonUser = SingletonUser.getInstance();
        sceneSwapper = new SceneSwapper();
        citizenFacade = new CitizenFacade();
        try {
            setupTableview();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    /**
     * sets up Tableview
     */
    public void setupTableview() throws SQLException {
        // setup Citizen Tableview
        tcCitizenID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcCitizenFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcCitizenLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcCitizenAge.setCellValueFactory(cellData -> cellData.getValue().ageProperty());

        try {
            tvCitizen.setItems(citizenFacade.getCitizenForStudent(singletonUser.getStudent().getId()));
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    /**
     * opens Citizen journal Containing "GeneralInfo, HealthJournal & FunctionalJournal"
     */
    public void onOpenCitizenJournalBtn(ActionEvent actionEvent) throws IOException {
        if (tvCitizen.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Ingen borger valgt");
        }else{
            singletonUser.setCitizen(tvCitizen.getSelectionModel().getSelectedItem());
            sceneSwapper.sceneSwitch(new Stage(), "TeacherJournalCreation.fxml");
        }
    }

    /**
     * closses the stage and goes back to login screen
     */
    public void onLogOutBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "Login.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
