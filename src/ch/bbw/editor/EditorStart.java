package ch.bbw.editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditorStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/editor.fxml"));
        primaryStage.setTitle("Maze Editor");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}