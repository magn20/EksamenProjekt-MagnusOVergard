package Gui.controller;

import Gui.utill.SceneSwapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static bll.utill.DisplayMessage.displayMessage;

public class LoginController implements Initializable {

    @FXML
    private PasswordField lblPassword;
    @FXML
    private TextField lblUsername;

    SceneSwapper sceneSwapper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneSwapper = new SceneSwapper();
    }


    /**
     * login functions and will switch to stage
     */
    public void onLoginBtn(ActionEvent actionEvent) throws IOException {
        //checks for admin
        if (lblPassword.getText().equals("admin") & lblUsername.getText().equals("admin")){
            sceneSwapper.sceneSwitch(new Stage(), "AdminScreen.fxml");
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }else{
            // display wrong Login combination
            displayMessage("Brugernavn eller Kodeord var Forkert");
        }
    }

}
