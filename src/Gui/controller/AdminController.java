package Gui.controller;

import Gui.model.SchoolModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.School;
import be.SchoolAdmin;
import bll.SchoolAdminManager;
import bll.utill.DisplayMessage;
import javafx.collections.FXCollections;
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
    private TableColumn<SchoolAdmin, Integer> tcSchoolAdminSchoolId;
    @FXML
    private TableColumn<SchoolAdmin, String> tcSchoolAdminLName;
    @FXML
    private TableColumn<SchoolAdmin, String> tcSchoolAdminFName;
    @FXML
    private TableView<SchoolAdmin> tvSchoolAdmin;
    // tableview for School
    @FXML
    private TableView<School> tvSchool;
    @FXML
    private TableColumn<School, String> tcSchoolname;
    @FXML
    private TableColumn<School, String> tcSchoolId;

    private SchoolModel schoolModel;
    private SchoolAdminManager schoolAdminManager;
    private SceneSwapper sceneSwapper;
    SingletonUser singletonUser = SingletonUser.getInstance();

    private ObservableList<School> allSchools;
    private ObservableList<SchoolAdmin> allSchoolAdmins;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolModel = new SchoolModel();
        sceneSwapper = new SceneSwapper();
        schoolAdminManager = new SchoolAdminManager();

        allSchools = FXCollections.observableArrayList();
        allSchoolAdmins = FXCollections.observableArrayList();

        try {
            prepareTableview();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * setup for all tableviews
     */
    public void prepareTableview() throws SQLException, IOException {
        // setup for tableview School
        tcSchoolname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcSchoolId.setCellValueFactory(cellData -> cellData.getValue().getIdPropertyAsStringProperty()  );

        tcSchoolAdminFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcSchoolAdminLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcSchoolAdminSchoolId.setCellValueFactory(cellData -> cellData.getValue().schoolIdProperty().asObject());

    setTableview();
    }

    /**
     * sets the data into all the TableViews
     */
    public void setTableview() throws SQLException, IOException {
        allSchools.clear();
        allSchools.addAll(schoolModel.getSchools());
        tvSchool.setItems(allSchools);

        allSchoolAdmins.clear();
        allSchoolAdmins.addAll(schoolAdminManager.getSchoolAdmins());
        tvSchoolAdmin.setItems(allSchoolAdmins);
    }




    /**
     * Removes a school
     * Checks for no selection
     * proceed after confirmation
     */
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
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                    displayError(e);
                }
            });
        }

    }


    /**
     * switches to AdminAddSchool. and closes current stage.
     */
    public void onAddSchoolBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminAddSchool.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

    }

    /**
     * opens AdminEditSchool scene on new stage, Closes current stage
     */
    public void onEditSchoolBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminEditSchool.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    /**
     * closes the stage and set the login screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onLogoutBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "Login.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * opens the screen for creating school admins
     * closses current screen.
     */
    public void onAddSchoolAdmin(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminAddSchoolAdmin.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * removes a schoolAdmin
     * checks for no selections and confirmation
     */
    public void onRemoveSchoolAdmin(ActionEvent actionEvent) {
        if (tvSchool.getSelectionModel().isEmpty()) {
            displayMessage("Ingen Skole er valgt");
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Ville du gerne fjerne denne skole admin, fra systemet, indholder også alle lærer,elever og borger");
            a.setTitle("Fjern Skole admin");
            a.setHeaderText("Fjern skole admin: " + tvSchoolAdmin.getSelectionModel().getSelectedItem().getFName()+ " " + tvSchoolAdmin.getSelectionModel().getSelectedItem().getLName() +  " fra systemet");
            a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                try {
                    schoolAdminManager.removeSchoolAdmin(tvSchoolAdmin.getSelectionModel().getSelectedItem());
                    prepareTableview();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                    displayError(e);
                }
            });
        }

    }

    /**
     * opens edit for schoolAdmin
     * Checks for no selection
     */
    public void onEditSchoolAdmin(ActionEvent actionEvent) throws IOException {
        if (tvSchoolAdmin.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Vælg en Skole Adminstrator");
        }else {
            singletonUser.setSchoolAdmin(tvSchoolAdmin.getSelectionModel().getSelectedItem());
            sceneSwapper.sceneSwitch(new Stage(), "AdminEditSchoolAdmin.fxml");
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
