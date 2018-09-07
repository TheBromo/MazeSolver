package ch.bbw;

import ch.bbw.controller.FXMLController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        Parent root = loader.load();
        FXMLController controller = (FXMLController) loader.getController();

        Scene scene = null;

        String osName = System.getProperty("os.name");
        if( osName != null && osName.startsWith("Windows") ) {

            //
            // Windows hack b/c unlike Mac and Linux, UNDECORATED doesn't include a shadow
            //
            scene = (new WindowsHack()).getShadowScene(root);
            stage.initStyle(StageStyle.TRANSPARENT);

        } else {
            scene = new Scene( root );
            stage.initStyle(StageStyle.UNDECORATED);
        }

        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("MazeSolver");
        stage.setScene( scene );
        stage.setMinHeight(200.0d);
        stage.setMinWidth(300.0d);
        stage.show();
        controller.setPrimaryStage(stage);
        controller.setRoot(root);

    }


    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
