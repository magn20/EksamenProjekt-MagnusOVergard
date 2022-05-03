package Gui.controller;

import Gui.model.TPLModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Student;
import be.Template;
import bll.utill.DisplayMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {


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

    private ObservableList<Template> allTemplate;

    SceneSwapper sceneSwapper;
    TPLModel tplModel;
    SingletonUser singletonUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singletonUser = SingletonUser.getInstance();
        sceneSwapper = new SceneSwapper();
        tplModel = new TPLModel();

        allTemplate = FXCollections.observableArrayList();

        prepareTableview();
    }

    public void prepareTableview(){
        tcTemplateID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tcFName.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        tcLName.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        tcAge.setCellValueFactory(cellData -> cellData.getValue().ageProperty());

        setTableview();
    }

    public void setTableview() {
        tvTemplate.setItems(tplModel.getTemplate(singletonUser.getTeacher().getSchoolId()));
    }




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

    public Template getTemplateForEdit(){
        return tvTemplate.getSelectionModel().getSelectedItem();
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
            sceneSwapper.sceneSwitch(new Stage(), "TeacherJounalCreation.fxml");
        }
    }
}
