package Gui.controller;

import Gui.model.TPLModel;
import Gui.utill.SceneSwapper;
import Gui.utill.SingletonUser;
import be.Template;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static bll.utill.DisplayMessage.displayError;
import static bll.utill.DisplayMessage.displayMessage;

public class TeacherEditTemplateController implements Initializable {


    @FXML
    private Label lblStatus;
    @FXML
    private ComboBox cbSchool;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtFName;

    TPLModel tplModel;
    SingletonUser singletonUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tplModel = new TPLModel();
        singletonUser = SingletonUser.getInstance();
    }

    public void onEditBtn(ActionEvent actionEvent) {


        // checks for no inputs
        if (txtFName.getText().equals("") || txtLName.getText().equals("") || txtAge.getText().equals("")){
            displayMessage("Der mangler infomation");
        }else {
            try {


                TeacherController controller = new SceneSwapper().getTeacherController();
                Template template = controller.getTemplateForEdit();
                        template.setlName(txtLName.getText());
                        template.setFName(txtFName.getText());
                        template.setAge(txtAge.getText());


                        // adds the student to database
                        tplModel.updateTemplate(template);


                        //updates ui
                        updateStatus(template);

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

            }catch (Exception e){
                e.printStackTrace();
                displayError(e);
            }
        }

    }

    public void onCloseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * updates the ui for user feeling of succesful Update of Template.
     * @param template that has ben created
     */
    public void updateStatus(Template template){
        lblStatus.setText("Redigeret i Template: " + template.getfName() + " " + template.getlName());
        txtAge.setText("");
        txtFName.setText("");
        txtLName.setText("");
    }

}
