package Gui.controller;

import Gui.model.CitizenModel;
import Gui.model.StudentModel;
import Gui.model.TPLModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.*;
import bll.utill.DisplayMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;

public class TeacherController implements Initializable {


    @FXML
    private Label lblTeacherInfo;
    // Tableview for Student
    @FXML
    private TableView<Student> tvStudent;
    @FXML
    private TableColumn<Student, Integer> tcStudentId;
    @FXML
    private TableColumn<Student, String> tcStudentFName;
    @FXML
    private TableColumn<Student, String> tcStudentUsername;
    @FXML
    private TableColumn<Student, String> tcStudentLName;
    //Tableview for citizen
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

    // Tableview for Template
    @FXML
    private TableView<Template> tvTemplate;
    @FXML
    private TableColumn<Template, Integer> tcTemplateID;
    @FXML
    private TableColumn<Template, String> tcFName;
    @FXML
    private TableColumn<Template, String> tcLName;
    @FXML
    private TableColumn<Template, String> tcAge;

    private ObservableList<Template> templatesForSchool;
    private ObservableList<Citizen> citizensForSchool;
    private ObservableList<Student> allStudentsForSchool;


    SceneSwapper sceneSwapper;
    TPLModel tplModel;
    CitizenModel citizenModel;
    SingletonUser singletonUser;
    StudentModel studentModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singletonUser = SingletonUser.getInstance();
        sceneSwapper = new SceneSwapper();
        tplModel = new TPLModel();
        citizenModel = new CitizenModel();
        studentModel = new StudentModel();

        templatesForSchool = FXCollections.observableArrayList();
        citizensForSchool = FXCollections.observableArrayList();
        allStudentsForSchool = FXCollections.observableArrayList();

        lblTeacherInfo.setText(singletonUser.getTeacher().getFName() + " " + singletonUser.getTeacher().getLName());

        try {
            prepareTableview();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            DisplayMessage.displayError(e);
        }

    }

    /**
     * sets up the tableview
     */
    public void prepareTableview() throws SQLException, IOException {
        tcTemplateID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcAge.setCellValueFactory(cellData -> cellData.getValue().ageProperty());

        tcCitizenID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcCitizenFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcCitizenLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcCitizenAge.setCellValueFactory(cellData -> cellData.getValue().ageProperty());

        tcStudentId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcStudentFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcStudentLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcStudentUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());

        setTableview();
    }

    /**
     * puts the data into the tableviews for (Templates, Citizens & Students)
     *
     * @throws SQLException
     */
    public void setTableview() throws SQLException, IOException {
        tvTemplate.setItems(tplModel.getTemplate(singletonUser.getTeacher().getSchoolId()));

        tvCitizen.setItems(citizenModel.getCitizen(singletonUser.getTeacher().getSchoolId()));

        tvStudent.setItems(studentModel.getStudentsFromSchool(singletonUser.getTeacher().getSchoolId()));
    }


    /**
     * removes a template
     */
    public void onRemoveTemplate(ActionEvent actionEvent) throws SQLException, IOException {

        if (tvTemplate.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("Vælg en Template og fjerne.");
        } else {
            try {
                tplModel.removeTemplate(tvTemplate.getSelectionModel().getSelectedItem());
            } catch (Exception ex) {
                DisplayMessage.displayError(ex);
                ex.printStackTrace();
            }
            setTableview();
        }
    }


    /**
     * Removes a Citizen
     */
    public void onRemoveCitizenBtn(ActionEvent actionEvent) throws SQLException, IOException {
        if (tvCitizen.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("Vælg en Template og fjerne.");
        } else {
            try {
                citizenModel.removeCitizen(tvCitizen.getSelectionModel().getSelectedItem());
            } catch (Exception ex) {
                DisplayMessage.displayError(ex);
                ex.printStackTrace();
            }
            setTableview();
        }
    }

    /**
     * gets selected Template on Tableview for Templates
     *
     * @return selected Template object
     */
    public Template getTemplateForEdit() {
        return tvTemplate.getSelectionModel().getSelectedItem();
    }

    /**
     * gets selected citizen on Tableview for Citizens
     *
     * @return selected Citizen Object
     */
    public Citizen getCitizenForEdit() {
        return tvCitizen.getSelectionModel().getSelectedItem();
    }


    /**
     * opens the add template screen
     *
     * @param actionEvent on action button
     */
    public void onAddTemplate(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "TeacherAddTemplate.fxml");
    }

    /**
     * open edit Template screen
     * Checks for no selection oon Tableview for templates
     */
    public void onEditTemplate(ActionEvent actionEvent) throws IOException {
        if (tvTemplate.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("Vælg en Template");
        } else {
            sceneSwapper.sceneSwitch(new Stage(), "TeacherEditTemplate.fxml");
        }
    }

    /**
     * opens a journal for templates
     * checks for no selection of template
     */
    public void onOpenJournalBtn(ActionEvent actionEvent) throws IOException {
        if (tvTemplate.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("Vælg en Template");
        } else {
            sceneSwapper.sceneSwitch(new Stage(), "TeacherTPLJournalCreation.fxml");
        }
    }

    /**
     * opens journal for citizens.
     * Checks for no selection for Citizen on Tableview Citizen
     */
    public void onOpenCitizenJournalBtn(ActionEvent actionEvent) throws IOException {

        if (tvCitizen.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("Ingen Borger er Valgt");
        } else {
            singletonUser.setCitizen(tvCitizen.getSelectionModel().getSelectedItem());
            sceneSwapper.sceneSwitch(new Stage(), "Journal.fxml");
        }

    }

    /**
     * opens add citizen screen
     */
    public void onAddCitizen(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "TeacherAddCitizen.fxml");
    }

    /**
     * opens scene for edits of  citizen
     */
    public void onEditCitizen(ActionEvent actionEvent) throws IOException {
        if (tvCitizen.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("ingen Borger valgt");
        } else {
            sceneSwapper.sceneSwitch(new Stage(), "TeacherEditCitizen.fxml");
        }

    }

    /**
     * Copies Selected Template into Citizens.
     * Gets the Citizen, General information, Health Journal & Functional journal. And add them to new citizen.
     *
     * @param actionEvent
     * @throws SQLException
     */
    public void onCreateCitizenOfTemplateBtn(ActionEvent actionEvent) throws SQLException {
        if (tvTemplate.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("vælg en template");
        } else {
            // copy a template into a citizen
            final Runnable runnable = () -> {
                try {
                    Template template = tvTemplate.getSelectionModel().getSelectedItem();

                    Citizen citizen = new Citizen(-1, template.getSchoolId(), template.getfName(), template.getlName(), template.getAge());
                    citizenModel.createCitizen(citizen);

                    ObservableList<Citizen> citizens = citizenModel.getCitizen(citizen.getSchoolId());

                    citizen = citizens.get(citizens.size() - 1);

                    // copies all TPLGeneralInfo into GeneralInfo for new citizen
                    for (TPLGeneralInfo tplGeneralInfo : tplModel.getTPLGeneralInfo(template.getId())) {
                        citizenModel.createGeneralInfo(new GeneralInfo(-1, citizen.getId(), tplGeneralInfo.getCoping(), tplGeneralInfo.getMotivation(), tplGeneralInfo.getResources(), tplGeneralInfo.getRoles(), tplGeneralInfo.getHabits(), tplGeneralInfo.getEducationAndJob(), tplGeneralInfo.getLifeStory(), tplGeneralInfo.getHealthInformation(), tplGeneralInfo.getEquipmentAids(), tplGeneralInfo.getHomeLayout(), tplGeneralInfo.getNetwork()));
                    }
                    // copies all TPLFunctionalJournal into FunctionalJournal for new Citizen
                    for (TPLFunctionalJournal tplFunctionalJournal : tplModel.getTPLFunctionalJournal(template.getId())) {
                        citizenModel.createFunctionalJournal(new FunctionalJournal(-1, citizen.getId(), tplFunctionalJournal.getCondition(), tplFunctionalJournal.getLastUpdate(), tplFunctionalJournal.getNiveau(), tplFunctionalJournal.getRelevancy(), tplFunctionalJournal.getNote(), tplFunctionalJournal.getExpectation(), tplFunctionalJournal.getExecution(), tplFunctionalJournal.getExecutionLimits(), tplFunctionalJournal.getCitizenExpectation()));
                    }
                    // Copies all TPLHealthJournal into HealthJournal for new Citizen
                    for (TPLHealthJournal tplHealthJournal : tplModel.getTPLHealthJournal(template.getId())) {
                        citizenModel.createHealthJournal(new HealthJournal(-1, citizen.getId(), tplHealthJournal.getCondition(), tplHealthJournal.getLastUpdate(), tplHealthJournal.getEvaluation(), tplHealthJournal.getRelevancy(), tplHealthJournal.getNote(), tplHealthJournal.getExpectation()));
                    }
                    // updates the tableview.
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                setTableview();
                            } catch (SQLException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    DisplayMessage.displayMessage("Udført: ny Borger: " + citizen.getfName() + " " + citizen.getlName());


                } catch (Exception e) {
                    DisplayMessage.displayMessage("Der Gik noget galt");
                    e.printStackTrace();
                }

            };

            // Starts the runnable in new thread.
            Thread thread = new Thread(runnable);
            thread.start();


        }


    }

    /**
     * opens new stage for assign student to work on Citizen
     * Checks for no citizen is selected
     */
    public void onAsignStudentForCitzen(ActionEvent actionEvent) throws IOException {
        if (tvCitizen.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("Vælg en Borger");
        } else {
            singletonUser.setCitizen(tvCitizen.getSelectionModel().getSelectedItem());
            sceneSwapper.sceneSwitch(new Stage(), "TeacherAddStudentWorksOn.fxml");

        }

    }

    /**
     * closes stage and opens new stage for login screen.
     */
    public void onLogOutBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "Login.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * updates tableview citizen with citizens a selected student is working on.
     *
     * @param mouseEvent when clicked on tableview Student
     * @throws SQLException
     */
    public void onSelectedStudentTV(MouseEvent mouseEvent) throws SQLException, IOException {

        if (!tvStudent.getSelectionModel().isEmpty()) {
            tvCitizen.getItems().clear();
            tvCitizen.setItems(citizenModel.getCitizenForStudent(tvStudent.getSelectionModel().getSelectedItem().getId()));
        }

    }

    /**
     * reset the data in the tableview to show all.
     */
    public void onShowAllCitizensBtn(ActionEvent actionEvent) throws SQLException, IOException {
        setTableview();
    }

    /**
     * opens new stage for add student screen
     */
    public void onAddStudentBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "TeacherAddStudent.fxml");
    }

    /**
     * opens new stage for edit in student
     * checks for no selection in tableview student
     */
    public void onEditStudentBtn(ActionEvent actionEvent) throws IOException {
        if (tvStudent.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("Der er ikke valgt nogen elev");
        } else {
            sceneSwapper.sceneSwitch(new Stage(), "TeacherEditStudent.fxml");
        }
    }

    /**
     * gets selected student on tableview student
     *
     * @return selected Student object
     */
    public Student getStudentForEdit() {

        return tvStudent.getSelectionModel().getSelectedItem();
    }

    /**
     * removes a student.
     * Checks for selections and confirmation
     */
    public void onRemoveStudent(ActionEvent actionEvent) {
        if (tvStudent.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("Der er ikke nogen elev valgt.");
        } else {
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
     * updates Tableview student to show all students that works on a Citizen
     * checks for no selection on Citizen TableView
     *
     * @param mouseEvent when clicked is performed on tableview citizen
     */
    public void onSelectedCitizenTv(MouseEvent mouseEvent) throws SQLException, IOException {
        if (!tvCitizen.getSelectionModel().isEmpty()) {
            tvStudent.getItems().clear();
            tvStudent.setItems(studentModel.getStudentsFromCitizen(tvCitizen.getSelectionModel().getSelectedItem().getId()));
        }
    }

    /**
     * opens new stage for Teacher new username.
     */
    public void onTeacherNewUsername(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "TeacherNewUsername.fxml");
    }

    /**
     * opens new stage for Teacher new password
     */
    public void onTeacherNewPassword(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "TeacherNewPassword.fxml");
    }

    /**
     * copies a selected Template into a new template
     */
    public void onCopyTemplateBtn(ActionEvent actionEvent) {

        if (tvTemplate.getSelectionModel().isEmpty()) {
            DisplayMessage.displayMessage("vælg en template");
        } else {

            final Runnable templateCopy = () -> {
                try {
                    Template template = tvTemplate.getSelectionModel().getSelectedItem();

                    tplModel.createTemplate(template);

                    ObservableList<Template> templates = tplModel.getTemplate(template.getSchoolId());

                    Template newTemplate = templates.get(templates.size() - 1);

                    for (TPLGeneralInfo tplGeneralInfo : tplModel.getTPLGeneralInfo(template.getId())) {
                        tplModel.createTPLGeneralInfo(new TPLGeneralInfo(-1, newTemplate.getId(), tplGeneralInfo.getCoping(), tplGeneralInfo.getMotivation(), tplGeneralInfo.getResources(), tplGeneralInfo.getRoles(), tplGeneralInfo.getHabits(), tplGeneralInfo.getEducationAndJob(), tplGeneralInfo.getLifeStory(), tplGeneralInfo.getHealthInformation(), tplGeneralInfo.getEquipmentAids(), tplGeneralInfo.getHomeLayout(), tplGeneralInfo.getNetwork()));
                    }
                    for (TPLFunctionalJournal tplFunctionalJournal : tplModel.getTPLFunctionalJournal(template.getId())) {
                        tplModel.createTPLFunctionalJournal(new TPLFunctionalJournal(-1, newTemplate.getId(), tplFunctionalJournal.getCondition(), tplFunctionalJournal.getLastUpdate(), tplFunctionalJournal.getNiveau(), tplFunctionalJournal.getRelevancy(), tplFunctionalJournal.getNote(), tplFunctionalJournal.getExpectation(), tplFunctionalJournal.getExecution(), tplFunctionalJournal.getExecutionLimits(), tplFunctionalJournal.getCitizenExpectation()));
                    }
                    for (TPLHealthJournal tplHealthJournal : tplModel.getTPLHealthJournal(template.getId())) {
                        tplModel.createTPLHealthJournal(new TPLHealthJournal(-1, newTemplate.getId(), tplHealthJournal.getCondition(), tplHealthJournal.getLastUpdate(), tplHealthJournal.getEvaluation(), tplHealthJournal.getRelevancy(), tplHealthJournal.getNote(), tplHealthJournal.getExpectation()));
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                setTableview();
                            } catch (SQLException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    DisplayMessage.displayMessage("Udført: Kopieret Template: " + newTemplate.getfName() + " " + newTemplate.getlName());


                } catch (Exception e) {
                    DisplayMessage.displayMessage("Der Gik noget galt");
                    e.printStackTrace();
                }

            };


            Thread thread = new Thread(templateCopy);
            thread.start();


        }
    }
}
