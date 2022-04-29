package Gui.controller;

import Gui.model.SchoolModel;
import Gui.model.StudentModel;
import Gui.model.TeacherModel;
import Gui.utill.SceneSwapper;
import be.School;
import be.Student;
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

    // tableview for School
    @FXML
    private TableView<School> tvSchool;
    @FXML
    private TableColumn<School, String> tcSchoolname;
    @FXML
    private TableColumn<School, String> tcSchoolId;

    SchoolModel schoolModel;
    TeacherModel teacherModel;
    StudentModel studentModel;

    SceneSwapper sceneSwapper;

    private ObservableList<School> allSchools;
    private ObservableList<Teacher> allTeachers;
    private ObservableList<Student> allStudents;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolModel = new SchoolModel();
        teacherModel = new TeacherModel();
        studentModel = new StudentModel();
        sceneSwapper = new SceneSwapper();

        allSchools = FXCollections.observableArrayList();
        allTeachers = FXCollections.observableArrayList();
        allStudents = FXCollections.observableArrayList();

        prepareTableview();
    }

    /**
     * setup for all tableviews
     */
    public void prepareTableview(){
        // setup for tableview School
        tcSchoolname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcSchoolId.setCellValueFactory(cellData -> cellData.getValue().getIdPropertyAsStringProperty()  );

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
    public void setTableview(){
        allSchools.clear();
        allSchools.addAll(schoolModel.getSchools());
        tvSchool.setItems(allSchools);

        allTeachers.clear();
        allTeachers.addAll(teacherModel.getTeachers());
        tvTeacher.setItems(allTeachers);

        allStudents.clear();
        allStudents.addAll(studentModel.getStudents());
        tvStudent.setItems(allStudents);
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
                } catch (SQLException e) {
                    e.printStackTrace();
                    displayError(e);
                }
            });
        }

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

    /**
     * Removes a Student
     * Checks for no selection
     * proceed after confirmation
     */
    public void onRemoveStudentBtn(ActionEvent actionEvent) {
        if (tvStudent.getSelectionModel().getSelectedItem() == null) {
            displayMessage("Ingen elev er valgt");
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Ville du gerne fjerne denne elev.");
            a.setTitle("Fjern elev");
            a.setHeaderText("Fjern elev: " + tvStudent.getSelectionModel().getSelectedItem().getFName() + " " + tvStudent.getSelectionModel().getSelectedItem().getLName()  + " fra systemet");
            a.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                try {
                    studentModel.removeStudent(tvStudent.getSelectionModel().getSelectedItem());
                    prepareTableview();
                } catch (SQLException e) {
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
     * opens AdminEditTeacher scene on new stage, Closes current stage
     */
    public void onEditTeacherBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminEditTeacher.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * opens AdminAddTeacher scene on new stage, Closes current stage
     */
    public void onAddTeacherBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminAddTeacher.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * opens AdminAddStudent scene on new stage, Closes current stage
     */
    public void onAddStudentBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminAddStudent.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    /**
     * opens AdminEditStudent scene on new stage, Closes current stage
     */
    public void onEditStudentBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "AdminEditStudent.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
