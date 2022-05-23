import dal.db.BasicConnectionPool;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    private static FXMLLoader fxmlLoaderMain;
    public static void main(String[] args) {launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoaderMain = new FXMLLoader(getClass().getResource("/gui/view/Login.fxml"));
        primaryStage.centerOnScreen();
        Scene scene = new Scene(fxmlLoaderMain.load());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        Image image = new Image("/gui/img/logo.png");
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Sosu Esbjerg");
        primaryStage.show();

        Thread createConnection = new Thread(runnable);
        createConnection.start();
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                BasicConnectionPool.create();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
