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
import javafx.scene.image.Image;
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
    private Image image;


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
                    gc.drawImage(image, x, y, size, size);
                } else {
                    gc.setFill(Color.GREEN);
                    gc.fillOval(x, y, size, size);
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
        image = new Image(getClass().getResourceAsStream("/robot.jpeg"));
        Maze maze = new Maze(5);

        maze.setField(0, 0, new Wall());
        maze.setField(1, 0, new Wall());
        maze.setField(2, 0, new Wall());
        maze.setField(3, 0, new Wall());
        maze.setField(4, 0, new Wall());
        maze.setField(0, 1, new Wall());
        maze.setField(1, 1, new Robot('u'));
        maze.setField(2, 1, new Empty());
        maze.setField(3, 1, new Empty());
        maze.setField(4, 1, new Wall());
        maze.setField(0, 2, new Wall());
        maze.setField(1, 2, new Empty());
        maze.setField(2, 2, new Empty());
        maze.setField(3, 2, new Empty());
        maze.setField(4, 2, new Wall());
        maze.setField(0, 3, new Wall());
        maze.setField(1, 3, new Empty());
        maze.setField(2, 3, new Empty());
        maze.setField(3, 3, new Empty());
        maze.setField(4, 3, new Wall());
        maze.setField(0, 4, new Wall());
        maze.setField(1, 4, new Wall());
        maze.setField(2, 4, new Wall());
        maze.setField(3, 4, new Empty());
        maze.setField(4, 4, new Wall());
        draw(maze);

    }
}
