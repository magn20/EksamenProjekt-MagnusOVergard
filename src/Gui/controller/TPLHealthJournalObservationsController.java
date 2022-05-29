package Gui.controller;

import Gui.model.TPLModel;
import Gui.utill.SceneSwapper;
import be.TPLHealthJournalObservation;
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

public class TPLHealthJournalObservationsController implements Initializable {
    @FXML
    private TextArea tctObservation;
    private Template template;
    private TPLModel tplModel;
    private TeacherController controller = new SceneSwapper().getTeacherController();

    public void onSaveCloseBtn(ActionEvent actionEvent) throws SQLException, IOException {
        if (tplModel.getTPLHealthJournalObservation(template.getId()) == null) {
            tplModel.createTPLHealthJournalObservation(new TPLHealthJournalObservation(-1, template.getId(), tctObservation.getText()));
        } else {
            TPLHealthJournalObservation tplHealthJournalObservation = tplModel.getTPLHealthJournalObservation(template.getId());
            tplHealthJournalObservation.setObservation(tctObservation.getText());
            tplModel.updateTPLHealthJournalObservation(tplHealthJournalObservation);
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        template = controller.getTemplateForEdit();
        tplModel = new TPLModel();

        try {
            if (tplModel.getTPLHealthJournalObservation(template.getId()) != null) {
                tctObservation.setText(tplModel.getTPLHealthJournalObservation(template.getId()).getObservation());
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
