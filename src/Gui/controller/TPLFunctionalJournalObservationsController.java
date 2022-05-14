package Gui.controller;

import Gui.model.TPLModel;
import Gui.utill.SceneSwapper;
import be.TPLFunctionJournalObservation;
import be.Template;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TPLFunctionalJournalObservationsController implements Initializable {
    @FXML
    private TextArea tctObservation;
    private TeacherController controller = new SceneSwapper().getTeacherController();
    private Template template;
    private TPLModel tplModel;

    public void onSaveCloseBtn(ActionEvent actionEvent) throws SQLException, IOException {
        if (tplModel.getTPLFunctionJournalObservation(template.getId()) == null){
            tplModel.createTPLFunctionJournalObservation(new TPLFunctionJournalObservation(-1, template.getId(),tctObservation.getText()));
        }else{
            TPLFunctionJournalObservation tplFunctionJournalObservation = tplModel.getTPLFunctionJournalObservation(template.getId());
            tplFunctionJournalObservation.setObservation(tctObservation.getText());
            tplModel.updateTPLFunctionJournalObservation(tplFunctionJournalObservation);
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    template = controller.getTemplateForEdit();
    tplModel = new TPLModel();

        try {
            if (tplModel.getTPLFunctionJournalObservation(template.getId()) != null){
                tctObservation.setText(tplModel.getTPLFunctionJournalObservation(template.getId()).getObservation());
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
