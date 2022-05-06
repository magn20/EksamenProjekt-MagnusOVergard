package Gui.controller;

import Gui.model.CitizenModel;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {



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


    SceneSwapper sceneSwapper;
    TPLModel tplModel;
    CitizenModel citizenModel;
    SingletonUser singletonUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singletonUser = SingletonUser.getInstance();
        sceneSwapper = new SceneSwapper();
        tplModel = new TPLModel();
        citizenModel = new CitizenModel();

        templatesForSchool = FXCollections.observableArrayList();
        citizensForSchool = FXCollections.observableArrayList();


        try {
            prepareTableview();
        } catch (SQLException e) {
            e.printStackTrace();
            DisplayMessage.displayError(e);
        }
    }

    /**
     * sets up the tableview
     */
    public void prepareTableview() throws SQLException {
        tcTemplateID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcAge.setCellValueFactory(cellData -> cellData.getValue().ageProperty());

        tcCitizenID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcCitizenFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcCitizenLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcCitizenAge.setCellValueFactory(cellData -> cellData.getValue().ageProperty());


        setTableview();
    }

    public void setTableview() throws SQLException {
        tvTemplate.setItems(tplModel.getTemplate(singletonUser.getTeacher().getSchoolId()));

        tvCitizen.setItems(citizenModel.getCitizen(singletonUser.getTeacher().getSchoolId()));
    }


    /**
     * removes a template
     */
    public void onRemoveTemplate(ActionEvent actionEvent) throws SQLException {

        if (tvTemplate.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Vælg en Template og fjerne.");
        }else{
            try {
                tplModel.removeTemplate(tvTemplate.getSelectionModel().getSelectedItem());
            }catch (Exception ex){
                DisplayMessage.displayError(ex);
                ex.printStackTrace();
            }
            setTableview();
        }
    }


    /**
     * Removes a Citizen
     */
    public void onRemoveCitizenBtn(ActionEvent actionEvent) throws SQLException {
        if (tvCitizen.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Vælg en Template og fjerne.");
        }else{
            try {
                citizenModel.removeCitizen(tvCitizen.getSelectionModel().getSelectedItem());
            }catch (Exception ex){
                DisplayMessage.displayError(ex);
                ex.printStackTrace();
            }
            setTableview();
        }
    }

    public Template getTemplateForEdit(){
        return tvTemplate.getSelectionModel().getSelectedItem();
    }

    public Citizen getCitizenForEdit(){
        return tvCitizen.getSelectionModel().getSelectedItem();
    }



















    public void onAddTemplate(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "TeacherAddTemplate.fxml");
    }


    public void onEditTemplate(ActionEvent actionEvent) throws IOException {
        if (tvTemplate.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Vælg en Template");
        }else{
            sceneSwapper.sceneSwitch(new Stage(), "TeacherEditTemplate.fxml");
        }
    }

    public void onOpenJournalBtn(ActionEvent actionEvent) throws IOException {
        if (tvTemplate.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Vælg en Template");
        }else{
            sceneSwapper.sceneSwitch(new Stage(), "TeacherTPLJournalCreation.fxml");
        }
    }

    public void onOpenCitizenJournalBtn(ActionEvent actionEvent) throws IOException {

        if (tvCitizen.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Ingen Borger er Valgt");
        }else{
            singletonUser.setCitizen(tvCitizen.getSelectionModel().getSelectedItem());
            sceneSwapper.sceneSwitch(new Stage(), "TeacherJournalCreation.fxml");
        }

    }

    public void onAddCitizen(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "TeacherAddCitizen.fxml");
    }

    public void onEditCitizen(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "TeacherEditCitizen.fxml");
    }

    public void onCreateCitizenOfTemplateBtn(ActionEvent actionEvent) throws SQLException {
        if (tvTemplate.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("vælg en template");
        }else {

            final Runnable runnable = () -> {
               try {
                   Template template = tvTemplate.getSelectionModel().getSelectedItem();

                   Citizen citizen = new Citizen(-1, template.getSchoolId(), template.getfName(), template.getlName(),template.getAge());
                   citizenModel.createCitizen(citizen);

                   ObservableList<Citizen> citizens = citizenModel.getCitizen(citizen.getSchoolId());

                   citizen =  citizens.get(citizens.size() -1);

                   for (TPLGeneralInfo tplGeneralInfo: tplModel.getTPLGeneralInfo(template.getId())){
                       citizenModel.createGeneralInfo(new GeneralInfo(-1, citizen.getId(), tplGeneralInfo.getCoping(), tplGeneralInfo.getMotivation(), tplGeneralInfo.getResources(), tplGeneralInfo.getRoles(), tplGeneralInfo.getHabits(), tplGeneralInfo.getEducationAndJob(), tplGeneralInfo.getLifeStory(), tplGeneralInfo.getHealthInformation(), tplGeneralInfo.getEquipmentAids(), tplGeneralInfo.getHomeLayout(), tplGeneralInfo.getNetwork()));
                   }
                   for (TPLFunctionalJournal tplFunctionalJournal: tplModel.getTPLFunctionalJournal(template.getId())){
                       citizenModel.createFunctionalJournal(new FunctionalJournal(-1, citizen.getId(), tplFunctionalJournal.getCondition(), tplFunctionalJournal.getLastUpdate(), tplFunctionalJournal.getNiveau(), tplFunctionalJournal.getRelevancy(), tplFunctionalJournal.getNote(), tplFunctionalJournal.getExpectation(), tplFunctionalJournal.getExecution(), tplFunctionalJournal.getExecutionLimits(), tplFunctionalJournal.getCitizenExpectation()));
                   }
                   for (TPLHealthJournal tplHealthJournal: tplModel.getTPLHealthJournal(template.getId())){
                       citizenModel.createHealthJournal(new HealthJournal(-1, citizen.getId(), tplHealthJournal.getCondition(), tplHealthJournal.getLastUpdate(), tplHealthJournal.getEvaluation(), tplHealthJournal.getRelevancy(), tplHealthJournal.getNote(), tplHealthJournal.getExpectation()));
                   }
                   Platform.runLater(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               setTableview();
                           } catch (SQLException e) {
                               e.printStackTrace();
                           }
                       }
                   });
                   DisplayMessage.displayMessage("Udført: ny Borger: " + citizen.getfName() + " " + citizen.getlName());


               }catch (Exception e){
                   DisplayMessage.displayMessage("Der Gik noget galt");
                   e.printStackTrace();
               }

            };


            Thread thread = new Thread(runnable);
            thread.start();



        }




    }

    public void onAsignStudentForCitzen(ActionEvent actionEvent) throws IOException {
        if (tvCitizen.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Vælg en Borger");
        }else{
            singletonUser.setCitizen(tvCitizen.getSelectionModel().getSelectedItem());
            sceneSwapper.sceneSwitch(new Stage(), "TeacherAddStudentWorksOn.fxml");

        }

    }

    public void onLogOutBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "Login.fxml");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
