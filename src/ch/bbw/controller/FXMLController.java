package ch.bbw.controller;

import ch.bbw.model.Fields.Empty;
import ch.bbw.model.Fields.Field;
import ch.bbw.model.Fields.Robot;
import ch.bbw.model.Fields.Wall;
import ch.bbw.model.Maze;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    VBox vbox;
    @FXML
    HBox header;
    @FXML
    Canvas canvas;
    private GraphicsContext gc;
    private double oldX, oldY;
    private Stage primaryStage;

    //size of each block in pixels
    private int size;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void close(MouseEvent evt) {
        ((Button) evt.getSource()).getScene().getWindow().hide();
    }

    private void moveWindow(MouseEvent event, boolean moving) {
        if (moving) {
            primaryStage.setX(primaryStage.getX() + (event.getScreenX() - oldX));
            primaryStage.setY(primaryStage.getY() + (event.getScreenY() - oldY));
        }

        oldX = event.getScreenX();
        oldY = event.getScreenY();
    }

    private void draw(Maze maze) {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, maze.getSize() * size, maze.getSize() * size);
        for (int x = 0; x < maze.getSize(); x++) {
            for (int y = 0; y < maze.getSize(); y++) {
                Field field = maze.getField(x, y);
                if (field instanceof Empty) {

                } else if (field instanceof Wall) {
                    gc.setFill(Color.GRAY);
                    gc.fillRect(x + 1, y + 1, 8, 8);
                } else if (field instanceof Robot) {

                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        header.setOnMouseDragged(event -> moveWindow(event, true));
        header.setOnMouseMoved(event -> moveWindow(event, false));
        size = 10;
        gc = canvas.getGraphicsContext2D();
    }
}
