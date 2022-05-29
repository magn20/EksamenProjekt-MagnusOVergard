package Gui.controller;

import Gui.model.StudentModel;
import Gui.model.TeacherModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Student;
import be.Teacher;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class SchoolAdminController implements Initializable {

    // tableview for Student
    @FXML
    private TableView<Student> tvStudent;
    @FXML
    private TableColumn<Student, String> tcStudentFName;
    @FXML
    private TableColumn<Student, String> tcStudentLName;
    @FXML
    private TableColumn<Student, Integer> tcStudentSchoolId;
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


    TeacherModel teacherModel;
    StudentModel studentModel;

    SceneSwapper sceneSwapper;


    private ObservableList<Teacher> allTeachers;
    private ObservableList<Student> allStudents;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teacherModel = new TeacherModel();
        studentModel = new StudentModel();
        sceneSwapper = new SceneSwapper();

        allTeachers = FXCollections.observableArrayList();
        allStudents = FXCollections.observableArrayList();

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

        // setup for tableview Teacher
        tcTeacherFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcTeacherLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        tcTeacherSchoolID.setCellValueFactory(cellData -> cellData.getValue().schoolIdProperty().asObject());

        // setup for tableview Student
        tcStudentFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcStudentLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcStudentSchoolId.setCellValueFactory(cellData -> cellData.getValue().schoolIdProperty().asObject());

        setTableview();
    }

    /**
     * sets the data into all the TableViews
     */
    public void setTableview() throws SQLException, IOException {
        allTeachers.clear();
        allTeachers.addAll(teacherModel.getTeachers());
        tvTeacher.setItems(allTeachers);

        allStudents.clear();
        allStudents.addAll(studentModel.getStudents());
        tvStudent.setItems(allStudents);
    }


    /**
     * Removes a Teacher
     * Checks for no selection
     * proceed after confirmation
     */
    public void OnRemoveTeacherBtn(ActionEvent actionEvent) {
        if (tvTeacher.getSelectionModel().getSelectedItem() == null) {
            displayMessage("Ingen lærer er valgt");
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Ville du gerne fjerne denne lærer.");
            a.setTitle("Fjern lære");
            a.setHeaderText("Fjern lære: " + tvTeacher.getSelectionModel().getSelectedItem().getFName() + tvTeacher.getSelectionModel().getSelectedItem().getLName() + " fra systemet");
            a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                try {
                    teacherModel.removeTeacher(tvTeacher.getSelectionModel().getSelectedItem());
                    prepareTableview();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                    displayError(e);
                }
            });
        }

    }

    /**
     * Removes a Student
     * Checks for no selection
     * proceed after confirmation
     */
    public void onRemoveStudentBtn(ActionEvent actionEvent) {
        if (tvStudent.getSelectionModel().getSelectedItem() == null) {
            displayMessage("Ingen elev er valgt");
        } else {
            // waiting for confirmation.
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Ville du gerne fjerne denne elev.");
            a.setTitle("Fjern elev");
            a.setHeaderText("Fjern elev: " + tvStudent.getSelectionModel().getSelectedItem().getFName() + " " + tvStudent.getSelectionModel().getSelectedItem().getLName() + " fra systemet");
            a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                try {
                    studentModel.removeStudent(tvStudent.getSelectionModel().getSelectedItem());
                    prepareTableview();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                    displayError(e);
                }
            });
        }
    }


    /**
     * opens AdminEditTeacher scene on new stage, Closes current stage
     */
    public void onEditTeacherBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "SchoolAdminEditTeacher.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * opens AdminAddTeacher scene on new stage, Closes current stage
     */
    public void onAddTeacherBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "SchoolAdminAddTeacher.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * opens AdminAddStudent scene on new stage, Closes current stage
     */
    public void onAddStudentBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "SchoolAdminAddStudent.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * opens AdminEditStudent scene on new stage, Closes current stage
     */
    public void onEditStudentBtn(ActionEvent actionEvent) throws IOException {
        if (tvStudent.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("Vælg en elev");
        } else {
            //sets up student for edit
            SingletonUser singletonUser = SingletonUser.getInstance();
            singletonUser.setStudent(tvStudent.getSelectionModel().getSelectedItem());
            //change scene
            sceneSwapper.sceneSwitch(new Stage(), "SchoolAdminEditStudent.fxml");
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        }
    }

    /**
     * goes back to login screen.
     */
    public void onLogoutBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "Login.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
