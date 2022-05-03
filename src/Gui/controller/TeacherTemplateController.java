package Gui.controller;

import Gui.model.TPLModel;
import Gui.utill.SceneSwapper;
import be.TPLGeneralInfo;
import be.TPLHealthJournal;
import be.Template;
import bll.utill.DisplayMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.spi.CalendarDataProvider;

public class TeacherTemplateController implements Initializable {



    // used  for all tabs
    @FXML
    private Label lblTemplate;
    @FXML
    private Label lblStatus;


    // Only for HealthFunction
    @FXML
    private ComboBox cbMainHealthCategory;
    @FXML
    private ComboBox cbUnderHealthCategory;
    @FXML
    private ComboBox cbExpectation;


    @FXML
    private TextArea txtNote;
    @FXML
    private TextArea txtEvaluation;
    @FXML
    private Label lblLastUpdate;

    @FXML
    private Label txtCondition;
    // checkbox
    @FXML
    private CheckBox checkboxMaybe;
    @FXML
    private CheckBox checkboxActive;
    @FXML
    private CheckBox checkboxNotRelavant;

    //tableview
    @FXML
    private TableView<TPLHealthJournal> tvTPLHealthJournal;
    @FXML
    private TableColumn<TPLHealthJournal, String> tcCondition;
    @FXML
    private TableColumn<TPLHealthJournal, String> tcRelanacy;
    @FXML
    private TableColumn<TPLHealthJournal, String> tcEvaluation;
    @FXML
    private TableColumn<TPLHealthJournal, String> tcExpactation;
    @FXML
    private TableColumn<TPLHealthJournal, String> tcNote;
    @FXML
    private TableColumn<TPLHealthJournal, String> tcLastUpdate;


    // only for General Information tab
    @FXML
    private TextArea txtCoping;
    @FXML
    private TextArea txtLifestory;
    @FXML
    private TextArea txtMovtivation;
    @FXML
    private TextArea txtHouseLayout;
    @FXML
    private TextArea txtEquitmentAids;
    @FXML
    private TextArea txtHabits;
    @FXML
    private TextArea txtRoles;
    @FXML
    private TextArea txtNetwork;
    @FXML
    private TextArea txtHealthInfo;
    @FXML
    private TextArea txtResources;
    @FXML
    private TextArea txtEducationAndJob;




    private ObservableList<String> MainCategory;
    private ObservableList<TPLGeneralInfo> tplGeneralInfos;
    private ObservableList<TPLHealthJournal> tplHealthJournals;
    TeacherController controller = new SceneSwapper().getTeacherController();

    TPLModel tplModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tplModel = new TPLModel();
        tplGeneralInfos = FXCollections.observableArrayList();
        MainCategory = FXCollections.observableArrayList();
        tplHealthJournals = FXCollections.observableArrayList();

        // sets up the comboboxs for TPLHealthJournal.
        setComboboxMainHealth();
        setComboboxExpectation();


        // gets the Templated that is selected from teacher screen.

        Template template = controller.getTemplateForEdit();


        updatetplHealthJournals();

        lblTemplate.setText("Template: "+ template.getfName() + " " + template.getlName() + " Template ID" + template.getId());

        if (!tplModel.getTPLGeneralInfo(template.getId()).isEmpty()){
            tplGeneralInfos.addAll(tplModel.getTPLGeneralInfo(template.getId()));
            setTextFieldsForGeneralInfo(tplGeneralInfos.get(0));
        }



    }

    /**
     * closes the stage
     * @param actionEvent
     */
    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * saves the general information to database.
     * @param actionEvent
     * @throws SQLException
     */
    public void onSaveGeneralInfoBtn(ActionEvent actionEvent) throws SQLException {
        TeacherController controller = new SceneSwapper().getTeacherController();
        Template template = controller.getTemplateForEdit();

        if (tplModel.getTPLGeneralInfo(template.getId()).isEmpty()){
            TPLGeneralInfo tplGeneralInfo = new TPLGeneralInfo(-1, template.getId(), txtCoping.getText(), txtMovtivation.getText(), txtResources.getText(), txtRoles.getText(), txtHabits.getText(), txtEducationAndJob.getText(), txtLifestory.getText(), txtHealthInfo.getText(), txtEquitmentAids.getText(), txtHouseLayout.getText(), txtNetwork.getText());
            tplModel.createTPLGeneralInfo(tplGeneralInfo);
        }else{

            tplGeneralInfos.addAll(tplModel.getTPLGeneralInfo(template.getId()));
            TPLGeneralInfo tplGeneralInfo = tplGeneralInfos.get(0);

            tplGeneralInfo.setTplCitizenId(template.getId());
            tplGeneralInfo.setNetwork(txtNetwork.getText());
            tplGeneralInfo.setHomeLayout(txtHouseLayout.getText());
            tplGeneralInfo.setHealthInformation(txtHealthInfo.getText());
            tplGeneralInfo.setEquipmentAids(txtEquitmentAids.getText());
            tplGeneralInfo.setLifeStory(txtLifestory.getText());
            tplGeneralInfo.setHabits(txtHabits.getText());
            tplGeneralInfo.setRoles(txtRoles.getText());
            tplGeneralInfo.setResources(txtResources.getText());
            tplGeneralInfo.setEducationAndJob(txtEducationAndJob.getText());
            tplGeneralInfo.setMotivation(txtMovtivation.getText());
            tplGeneralInfo.setCoping(txtCoping.getText());

            tplModel.updateTPLGeneralInfo(tplGeneralInfo);
            setTextFieldsForGeneralInfo(tplGeneralInfo);
        }

        lblStatus.setText("Status: " + "Updated Generelle Informationer");
    }

    /**
     * sets the textfields up with data from template general information
     * @param tplGeneralInfo the object.
     */
    public void setTextFieldsForGeneralInfo(TPLGeneralInfo tplGeneralInfo){
        txtCoping.setText(tplGeneralInfo.getCoping());
        txtEducationAndJob.setText(tplGeneralInfo.getEducationAndJob());
        txtEquitmentAids.setText(tplGeneralInfo.getCoping());
        txtHabits.setText(tplGeneralInfo.getHabits());
        txtHealthInfo.setText(tplGeneralInfo.getHealthInformation());
        txtHouseLayout.setText(tplGeneralInfo.getHomeLayout());
        txtLifestory.setText(tplGeneralInfo.getLifeStory());
        txtMovtivation.setText(tplGeneralInfo.getMotivation());
        txtNetwork.setText(tplGeneralInfo.getNetwork());
        txtResources.setText(tplGeneralInfo.getResources());
        txtRoles.setText(tplGeneralInfo.getRoles());
    }


    /**
     * imitates a shift og control on a textarea, by hitting tab or enter key.
     * @param event
     */
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.TAB && !event.isShiftDown() && !event.isControlDown() || code == KeyCode.ENTER && !event.isShiftDown() && !event.isControlDown()) {
            event.consume();
            Node node = (Node) event.getSource();
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyCode.CONTROL.getCode());
                robot.keyPress(KeyCode.TAB.getCode());
                robot.delay(10);
                robot.keyRelease(KeyCode.TAB.getCode());
                robot.keyRelease(KeyCode.CONTROL.getCode());
            }
            catch (AWTException e) { }
        }
    }


    /**
     * sets up main Health journal combobox
     */
    public void setComboboxMainHealth(){

        MainCategory.add("Funktionsniveau");
        MainCategory.add("Bevægeapperat");
        MainCategory.add("Ernæring");
        MainCategory.add("Hud og Slimhinder");
        MainCategory.add("Kommunikation");
        MainCategory.add("Psykosociale Forhold");
        MainCategory.add("Respiration og Cirkulation");
        MainCategory.add("Seksualitet");
        MainCategory.add("Smerter og Sanseindtryk");
        MainCategory.add("Søvn og Hvile");
        MainCategory.add("Viden og Udvikling");
        MainCategory.add("Udskillelse af Affaldsstoffer");

        cbMainHealthCategory.setItems(MainCategory);
    }


    /**
     * puts the rightful information into the Combobox underCategoryForHealth
     * depending on the selection off MainHealthCategory combobox
     */
    public void selectUnderCategoryForHealth(){

        int selectMainHealthCombobox = cbMainHealthCategory.getSelectionModel().getSelectedIndex();

        cbUnderHealthCategory.getItems().clear();

        switch (selectMainHealthCombobox){

            case 0:
                cbUnderHealthCategory.getItems().add("Problemer med personlig pjele");
                cbUnderHealthCategory.getItems().add("Problemer med daglige aktiviteter");
                break;
            case 1:
                cbUnderHealthCategory.getItems().add("Problemer med mobilitet og bevægelse");
                break;
            case 2:
                cbUnderHealthCategory.getItems().add("Problemer med væskeindtag");
                cbUnderHealthCategory.getItems().add("Problemer med fødeindtag");
                cbUnderHealthCategory.getItems().add("Uhensigtsmæssig vægtændring");
                cbUnderHealthCategory.getItems().add("Problemer med overvægt");
                cbUnderHealthCategory.getItems().add("Problemer med undervægt");
                break;
            case 3:
                cbUnderHealthCategory.getItems().add("Problemer med kirurgisk sår");
                cbUnderHealthCategory.getItems().add("Problemer med Diabetisk sår");
                cbUnderHealthCategory.getItems().add("Problemer med cancer sår");
                cbUnderHealthCategory.getItems().add("Problemer med tryk sår");
                cbUnderHealthCategory.getItems().add("Problemer med arterielt sår");
                cbUnderHealthCategory.getItems().add("Problemer med venøst sår");
                cbUnderHealthCategory.getItems().add("Problemer med blandings sår");
                cbUnderHealthCategory.getItems().add("Problemer med traume sår");
                cbUnderHealthCategory.getItems().add("Andre problemer med hud og slimhinder");
                break;
            case 4:
                cbUnderHealthCategory.getItems().add("Problemer med kommunikation");
                break;
            case 5:
                cbUnderHealthCategory.getItems().add("Problemer med socialt samvær");
                cbUnderHealthCategory.getItems().add("Emotionelle problemer");
                cbUnderHealthCategory.getItems().add("Problemer med misbrug");
                cbUnderHealthCategory.getItems().add("Mentale problemer");
                break;
            case 6:
                cbUnderHealthCategory.getItems().add("Respirationsproblemer");
                cbUnderHealthCategory.getItems().add("Cirkulationsproblemer");
                break;
            case 7:
                cbUnderHealthCategory.getItems().add("Problemer med seksualitet");
                break;
            case 8:
                cbUnderHealthCategory.getItems().add("Akutte smerter");
                cbUnderHealthCategory.getItems().add("Periodevise smerter");
                cbUnderHealthCategory.getItems().add("Kroniske smerter");
                cbUnderHealthCategory.getItems().add("Problemer med synssans");
                cbUnderHealthCategory.getItems().add("Problemer med lugtesans");
                cbUnderHealthCategory.getItems().add("Problemer med hørelse");
                cbUnderHealthCategory.getItems().add("Problemer med smagssans");
                cbUnderHealthCategory.getItems().add("Problemer med følesans");
                break;
            case 9:
                cbUnderHealthCategory.getItems().add("Døgnrytmeproblemer");
                cbUnderHealthCategory.getItems().add("SøvnProblemer");
                break;
            case 10:
                cbUnderHealthCategory.getItems().add("Problemer med hukommelse");
                cbUnderHealthCategory.getItems().add("Problemer med indsigt i Behandlingsformål");
                cbUnderHealthCategory.getItems().add("Problemer med sygdomindsigt");
                cbUnderHealthCategory.getItems().add("Kognitive problemer");
                break;
            case 11:
                cbUnderHealthCategory.getItems().add("Problemer med vandladning");
                cbUnderHealthCategory.getItems().add("Problemer med urininkontinens");
                cbUnderHealthCategory.getItems().add("Problemer med afføringsinkontinens");
                cbUnderHealthCategory.getItems().add("Problemer problemer med mave og tarm");
                cbUnderHealthCategory.getItems().add("Problemer med væske fra dræn");
                break;

            default: break;
        }

    }


    public void onMainCategoryForHeatlhCb(ActionEvent actionEvent) {
        selectUnderCategoryForHealth();
    }

    public void onSaveHealthjournalBtn(ActionEvent actionEvent) throws SQLException {
        boolean hasSaved = false;
        Date date = new Date();
        Template template = controller.getTemplateForEdit();

        if (cbUnderHealthCategory.getSelectionModel().isEmpty()){
            DisplayMessage.displayMessage("Vælg en helbredstilstand først");
        }else {



            updatetplHealthJournals();

        for (TPLHealthJournal tplHealthJournal : tplHealthJournals){
            if (cbUnderHealthCategory.getSelectionModel().getSelectedItem().equals(tplHealthJournal.getCondition())){

                tplHealthJournal.setNote(txtNote.getText());
                tplHealthJournal.setExpectation((String) cbExpectation.getSelectionModel().getSelectedItem());
                tplHealthJournal.setEvaluation(txtEvaluation.getText());
                tplHealthJournal.setLastUpdate(date.toString());

                if (checkboxActive.isSelected()){
                    tplHealthJournal.setRelevancy("Aktiv");
                }else if (checkboxMaybe.isSelected()){
                    tplHealthJournal.setRelevancy("potentiel");
                }else{
                    tplHealthJournal.setRelevancy("Ikke relavant");
                }

                tplModel.updateTPLHealthJournal(tplHealthJournal);
                hasSaved = true;
                break;
            }
        }
        if (!hasSaved){
            String relavancy = "";
            if (checkboxActive.isSelected()){
                relavancy = "Aktiv";
            }else if (checkboxMaybe.isSelected()){
                relavancy = "potentiel";
            }else{
                relavancy = "Ikke relavant";
            }

            TPLHealthJournal tplHealthJournal = new TPLHealthJournal(-1, template.getId(), (String) cbUnderHealthCategory.getSelectionModel().getSelectedItem(), date.toString(), txtEvaluation.getText(), "Ikke relavant", txtNote.getText(), (String) cbExpectation.getSelectionModel().getSelectedItem());
            tplModel.createTPLHealthJournal(tplHealthJournal);
            }
        }
    }


    public void onSelectedHealthConditionCb(ActionEvent actionEvent) {


        updatetplHealthJournals();
        boolean hasupdated = false;

        if (!tplHealthJournals.isEmpty()){
            for (TPLHealthJournal tplHealthJournal: tplHealthJournals){
                if(cbUnderHealthCategory.getSelectionModel().isEmpty()){

                } else if (cbUnderHealthCategory.getSelectionModel().getSelectedItem().equals(tplHealthJournal.getCondition())){
                    updateHealthScreen(tplHealthJournal);
                    hasupdated = true;
                    break;
                }
            }
        }
        if (!hasupdated){
            if(!cbUnderHealthCategory.getSelectionModel().isEmpty()){

                txtNote.setText("");
                txtEvaluation.setText("");
                cbExpectation.getSelectionModel().clearSelection();
                lblLastUpdate.setText("Sidst Opdateret den:");
                txtCondition.setText("Problem:" + cbUnderHealthCategory.getSelectionModel().getSelectedItem());
                checkboxActive.setSelected(false);
                checkboxNotRelavant.setSelected(false);
                checkboxMaybe.setSelected(false);
            }


        }
    }

    private void updatetplHealthJournals(){

        if (!tplModel.getTPLHealthJournal(controller.getTemplateForEdit().getId()).isEmpty()){
            tplHealthJournals.clear();
            tplHealthJournals.addAll(tplModel.getTPLHealthJournal(controller.getTemplateForEdit().getId()));
        }
    }

    private void updateHealthScreen(TPLHealthJournal tplHealthJournal){
        txtNote.setText("");
        txtEvaluation.setText("");
        cbExpectation.getSelectionModel().clearSelection();
        lblLastUpdate.setText("Sidst Opdateret den:");
        txtCondition.setText("Problem:");

        txtNote.setText(tplHealthJournal.getNote());
        txtEvaluation.setText(tplHealthJournal.getEvaluation());
        cbExpectation.getSelectionModel().select(tplHealthJournal.getExpectation());
        lblLastUpdate.setText("Sidst Opdateret den: " + tplHealthJournal.getLastUpdate());
        txtCondition.setText("Problem: " + tplHealthJournal.getCondition());

        if (tplHealthJournal.getRelevancy().equals("Aktiv")){
            checkboxActive.setSelected(true);
            checkboxNotRelavant.setSelected(false);
            checkboxMaybe.setSelected(false);
        }else if (tplHealthJournal.getRelevancy().equals("Ikke relavant")){
            checkboxActive.setSelected(false);
            checkboxNotRelavant.setSelected(true);
            checkboxMaybe.setSelected(false);
        }else{
            checkboxActive.setSelected(false);
            checkboxNotRelavant.setSelected(false);
            checkboxMaybe.setSelected(true);
        }

    }

    public void setComboboxExpectation(){
        cbExpectation.getItems().add("Mindskes");
        cbExpectation.getItems().add("Forbliver uændret");
        cbExpectation.getItems().add("Forsvinder");
    }

}
