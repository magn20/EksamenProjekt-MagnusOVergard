package Gui.controller;

import Gui.model.TPLModel;
import Gui.utill.SceneSwapper;
import be.TPLGeneralInfo;
import be.Template;
import dal.db.TPLGeneralInfoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.skin.TextAreaSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    TPLModel tplModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tplModel = new TPLModel();
        tplGeneralInfos = FXCollections.observableArrayList();
        MainCategory = FXCollections.observableArrayList();

        setComboboxMainHealth();

        TeacherController controller = new SceneSwapper().getTeacherController();
        Template template = controller.getTemplateForEdit();



        lblTemplate.setText("Template: "+ template.getfName() + " " + template.getlName() + " Template ID" + template.getId());
        if (!tplModel.getTPLGeneralInfo(template.getId()).isEmpty()){
            tplGeneralInfos.addAll(tplModel.getTPLGeneralInfo(template.getId()));
            setTextFieldsForGeneralInfo(tplGeneralInfos.get(0));
        }

    }

    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onSaveGeneralInfoBtn(ActionEvent actionEvent) throws SQLException {
        TeacherController controller = new SceneSwapper().getTeacherController();
        Template template = controller.getTemplateForEdit();

        if (tplModel.getTPLGeneralInfo(template.getId()).isEmpty()){
            TPLGeneralInfo tplGeneralInfo = new TPLGeneralInfo(-1, template.getId(), txtCoping.getText(), txtMovtivation.getText(), txtResources.getText(), txtRoles.getText(), txtHabits.getText(), txtEducationAndJob.getText(), txtLifestory.getText(), txtHealthInfo.getText(), txtEquitmentAids.getText(), txtHouseLayout.getText(), txtNetwork.getText());
            tplModel.createTPLGeneralInfo(tplGeneralInfo);
        }else{

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
}
