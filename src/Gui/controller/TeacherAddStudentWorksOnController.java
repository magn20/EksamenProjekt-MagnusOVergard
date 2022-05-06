package Gui.controller;

import Gui.model.StudentModel;
import Gui.utill.SingletonUser;
import be.Citizen;
import be.Student;
import bll.CitizenFacade;
import bll.utill.DisplayMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.awt.desktop.AppReopenedEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeacherAddStudentWorksOnController implements Initializable {


    @FXML
    private TableView<Student> tvStudent;
    @FXML
    private TableColumn<Student, Integer> tcStudentId;
    @FXML
    private TableColumn<Student, String> tcFName;
    @FXML
    private TableColumn<Student, String> tcLName;

    @FXML
    private Label lblStatus;
    @FXML
    private Label lblCitizen;

    private ObservableList<Student> students;
    StudentModel studentModel;
    SingletonUser singletonUser;
    CitizenFacade citizenFacade;
    Citizen citizen;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentModel = new StudentModel();
        students = FXCollections.observableArrayList();
        singletonUser = SingletonUser.getInstance();
        citizen = singletonUser.getCitizen();
        citizenFacade = new CitizenFacade();

        try {
            students = studentModel.getStudentsFromSchool(singletonUser.getTeacher().getSchoolId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lblCitizen.setText(citizen.getfName() + " " + citizen.getlName());
        setupTableview();


    }

    private void setupTableview() {
        tcStudentId.setCellValueFactory(cellData -> cellData.getValue().schoolIdProperty().asObject());
        tcFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());

        tvStudent.setItems(students);

    }

    public void onAddBtn(ActionEvent actionEvent) throws SQLException {
        if (tvStudent.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("vælg en elev, til borgeren");
        }else {
            citizenFacade.setStudentWorksOnCitizen(citizen, tvStudent.getSelectionModel().getSelectedItem());
            lblStatus.setText(tvStudent.getSelectionModel().getSelectedItem().getFName() + " " + tvStudent.getSelectionModel().getSelectedItem().getLName() + " arbejder nu på borgeren: " + citizen.getfName() + " "+ citizen.getlName());
        }

    }

    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


}
