package ch.bbw.controller;

import ch.bbw.model.Fields.Empty;
import ch.bbw.model.Fields.Field;
import ch.bbw.model.Fields.Robot;
import ch.bbw.model.Fields.Wall;
import ch.bbw.model.Maze;
import ch.bbw.model.MazeSolver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class FXMLController implements Initializable {

    private static Image robot, wall, grass, flag;
    @FXML
    VBox vbox;
    @FXML
    HBox header;
    @FXML
    Canvas canvas;
    private Thread thread;
    private GraphicsContext gc;
    private Stage primaryStage;
    private MazeSolver solver;


    //size of each block in pixels
    private int size;
    private double oldX, oldY, lastDragX, lastDragY, offsetX, offsetY;
    private boolean dragInProgress;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void close(MouseEvent evt) {
        if (solver != null) {
            solver.setSolved(true);
        }
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

    private void moveCanvas(MouseEvent event, boolean moving) {
    /*    if (moving) {
            canvas.setTranslateX(canvas.getTranslateX() + (event.getX() - oldCanvasX));
            canvas.setTranslateY(canvas.getTranslateY() + (event.getY() - oldCanvasY));
        }
        oldCanvasX = event.getX();
        oldCanvasY = event.getY();
    */
    }

    public void externaldraw(Maze maze) {
        Platform.runLater(() -> {
            draw(maze);
        });
    }

    private void draw(Maze maze) {

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.translate(offsetX, offsetY);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, maze.getSize() * size, maze.getSize() * size);
        for (int x = 0; x < maze.getSize(); x++) {
            for (int y = 0; y < maze.getSize(); y++) {

                Field field = maze.getField(x, y);
                if (field instanceof Empty) {
                    gc.drawImage(grass, x * size, y * size, size, size);
                } else if (field instanceof Wall) {
                    gc.drawImage(wall, x * size, y * size, size, size);
                } else if (field instanceof Robot) {
                    gc.drawImage(grass, field.getX() * size, field.getY() * size, size, size);
                    gc.drawImage(robot, field.getX() * size, field.getY() * size, size, size);
                } else {
                    gc.drawImage(grass, x * size, y * size, size, size);
                    gc.drawImage(flag, x * size, y * size, size, size);
                }
            }
        }
        Robot robotObj = maze.getRobot();
        gc.drawImage(robot, robotObj.getX() * size, robotObj.getY() * size, size, size);
        //gc.restore();
        gc.translate(-offsetX, -offsetY);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dragInProgress = false;
        header.setOnMouseDragged(event -> moveWindow(event, true));
        header.setOnMouseMoved(event -> moveWindow(event, false));


        size = 150;
        gc = canvas.getGraphicsContext2D();
        robot = new Image(getClass().getResourceAsStream("/robot.png"));
        wall = new Image(getClass().getResourceAsStream("/wall.png"));
        grass = new Image(getClass().getResourceAsStream("/grass.png"));
        flag = new Image(getClass().getResourceAsStream("/flag.png"));

    }

    public void setSolver(MazeSolver solver) {
        this.solver = solver;
    }

    public void handleStart(ActionEvent actionEvent) {
        if (thread == null) {
            thread = new Thread(new MazeSolver(this));
            thread.start();
        }
    }

    public void handleReset(ActionEvent actionEvent) {
        if (solver != null) {
            solver.setSolved(true);
            thread = null;
        }
    }

    public void handlePause(ActionEvent actionEvent) {
        if (solver != null) {
            solver.setPaused(false);
        }
    }


    public void onScroll(ScrollEvent scrollEvent) {
        System.out.println("scrolling");
    }


    public void onMousePressed(MouseEvent mouseEvent) {
        lastDragY = mouseEvent.getY();
        lastDragX = mouseEvent.getX();
    }

    public void handleMouseDrag(MouseEvent mouseEvent) {
        offsetX += mouseEvent.getX() - lastDragX;
        offsetY += mouseEvent.getY() - lastDragY;
        lastDragX = mouseEvent.getX();
        lastDragY = mouseEvent.getY();
        externaldraw(solver.getMaze());
    }
}
