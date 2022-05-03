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


    // used  for alle tabs
    @FXML
    private Label lblTemplate;
    @FXML
    private Label lblStatus;


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





    private ObservableList<TPLGeneralInfo> tplGeneralInfos;

    TPLModel tplModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tplModel = new TPLModel();
        tplGeneralInfos = FXCollections.observableArrayList();

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

}
