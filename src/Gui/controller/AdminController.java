package Gui.controller;

import Gui.model.SchoolModel;
import Gui.model.TeacherModel;
import Gui.utill.SceneSwapper;
import be.School;
import be.Teacher;
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

    // tableview for Teacher
    @FXML
    private TableView<Teacher> tvTeacher;
    @FXML
    private TableColumn<Teacher, String> tcTeacherFName;
    @FXML
    private TableColumn<Teacher, String> tcTeacherLName;
    @FXML
    private TableColumn<Teacher, Integer> tcTeacherSchoolID;
    @FXML
    private TableColumn<Teacher, String> tcUsername;

    // tableview for School
    @FXML
    private TableView<School> tvSchool;
    @FXML
    private TableColumn<School, String> tcSchoolname;
    @FXML
    private TableColumn<School, String> tcSchoolId;

    SchoolModel schoolModel;
    TeacherModel teacherModel;

    SceneSwapper sceneSwapper;

    private ObservableList<School> allSchools;
    private ObservableList<Teacher> allTeachers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolModel = new SchoolModel();
        teacherModel = new TeacherModel();
        sceneSwapper = new SceneSwapper();

        allSchools = FXCollections.observableArrayList();
        allTeachers = FXCollections.observableArrayList();

        prepareTableview();
    }

    public void prepareTableview(){
        // setup for the tableview School
        tcSchoolname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcSchoolId.setCellValueFactory(cellData -> cellData.getValue().getIdPropertyAsStringProperty()  );

        tcTeacherFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcTeacherLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        tcTeacherSchoolID.setCellValueFactory(cellData -> cellData.getValue().schoolIdProperty().asObject());

        setTableview();
    }

    public void setTableview(){
        allSchools.clear();
        allSchools.addAll(schoolModel.getSchools());
        tvSchool.setItems(allSchools);

        allTeachers.clear();
        allTeachers.addAll(teacherModel.getTeachers());
        tvTeacher.setItems(allTeachers);
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

    public void onEditTeacherBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminEditTeacher.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void OnRemoveTeacherBtn(ActionEvent actionEvent) {
        if (tvTeacher.getSelectionModel().getSelectedItem() == null) {
            displayMessage("Ingen lærer er valgt");
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Ville du gerne fjerne denne lærer.");
            a.setTitle("Fjern lære");
            a.setHeaderText("Fjern lære: " + tvTeacher.getSelectionModel().getSelectedItem().getFName() +tvTeacher.getSelectionModel().getSelectedItem().getLName()  + " fra systemet");
            a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                try {
                    teacherModel.removeTeacher(tvTeacher.getSelectionModel().getSelectedItem());
                    prepareTableview();
                } catch (SQLException e) {
                    e.printStackTrace();
                    displayError(e);
                }
            });
        }

    }

    public void onAddTeacherBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminAddTeacher.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
