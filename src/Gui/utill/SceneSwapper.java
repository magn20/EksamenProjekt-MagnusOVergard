package Gui.utill;

import Gui.controller.TeacherController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SceneSwapper {

    private static FXMLLoader fxmlLoaderCoordinatorScreen;

        /**
         * switches the stage to a certain fxml file.
         * @param stage of stage wanted to be shown.
         * @param fxmlClassName the file that wanted to be shown
         */
        public void sceneSwitch(Stage stage, String fxmlClassName) throws IOException {
            URL url = new File("src/gui/view/" + fxmlClassName).toURI().toURL();
            Parent scene = FXMLLoader.load(url);

            Scene ViewScene = new Scene(scene);
            //stage.setResizable(false);
            stage.setTitle("Sosu Esbjerg");

            Image image = new Image("/gui/img/logo.png");
            stage.getIcons().add(image);
            stage.setScene(ViewScene);

            stage.setScene(ViewScene);
            stage.show();

        }

    public void TeacherScreen(Stage primaryStage) throws IOException {
        fxmlLoaderCoordinatorScreen = new FXMLLoader(getClass().getResource("/gui/view/TeacherScreen.fxml"));
        Scene scene = new Scene(fxmlLoaderCoordinatorScreen.load());
        primaryStage.centerOnScreen();
        //primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        Image image = new Image("/gui/img/logo.png");
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sosu Esbjerg");
        primaryStage.show();
    }

    public TeacherController getTeacherController() {
        return fxmlLoaderCoordinatorScreen.getController();
    }




}

