package ch.bbw.controller;

import ch.bbw.model.Fields.*;
import ch.bbw.model.Maze;
import ch.bbw.model.MazeSolver;
import ch.bbw.model.Position;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable  {
    private static Image robot, wall, grass, flag;
    @FXML
    private CheckBox kidsMode;
    @FXML
    VBox vbox;
    @FXML
    HBox header;
    @FXML
    Canvas canvas;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resetButton;
    //size of each block in pixels
    private GraphicsContext gc;
    private Stage primaryStage;
    private int size;
    private double oldX, oldY, lastDragX, lastDragY, offsetX, offsetY;

    private MazeSolver solver;
    private Thread calculatingThread;


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

    public void externalDraw(Maze maze) {
        Platform.runLater(() -> {
            if (kidsMode.isSelected()) drawKids(maze);
            else draw(maze);
        });
    }

    private void drawKids(Maze maze) {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.translate(offsetX, offsetY);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, maze.getSize() * size, maze.getSize() * size);
        for (int x = 0; x < maze.getSize(); x++)
        {
            for (int y = 0; y < maze.getSize(); y++)
            {

                Field field = maze.getField(new Position(x, y));
                if (field instanceof Empty)
                {
                    gc.drawImage(grass, x * size, y * size, size, size);
                }
                else if (field instanceof Wall)
                {
                    gc.drawImage(wall, x * size, y * size, size, size);
                }
                else if (field instanceof Robot)
                {
                    gc.drawImage(grass, field.getPosition().getX() * size, field.getPosition().getY() * size, size, size);
                }
                else
                {
                    gc.drawImage(grass, x * size, y * size, size, size);
                    gc.drawImage(flag, x * size, y * size, size, size);
                }
            }
        }

        Robot robotObj = maze.getRobot();
        gc.drawImage(robot, robotObj.getPosition().getX() * size, robotObj.getPosition().getY() * size, size, size);
        gc.translate(-offsetX, -offsetY);
    }

    private void draw(Maze maze) {
        gc.setFill(Color.web("#eceff1"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.translate(offsetX, offsetY);
        for (int x = 0; x < maze.getSize(); x++) {
            for (int y = 0; y < maze.getSize(); y++) {
                Field field = maze.getField(new Position(x, y));
                if (field instanceof Wall) {
                    gc.setFill(Color.web("#263238"));
                    gc.fillRect(x * size, y * size, size, size);
                } else if (field instanceof Goal) {
                    gc.setFill(Color.web("#00e676"));
                    gc.fillRect(x * size + 40, y * size + 40, size - 80, size - 80);
                }
                gc.setStroke(Color.web("#37474f"));
                gc.strokeRect(x * size, y * size, size, size);

            }
        }
        Robot robotObj = maze.getRobot();
        gc.setFill(Color.web("#536dfe"));
        gc.fillRect(robotObj.getPosition().getX() * size + 20, robotObj.getPosition().getY() * size + 20, size - 40, size - 40);
        gc.translate(-offsetX, -offsetY);
        gc.restore();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    public void handleStart() {
        if (calculatingThread == null) {
            calculatingThread = new Thread(new MazeSolver(this));
            calculatingThread.start();
        }
    }

    public void handleReset() {
        if (solver != null) {
            solver.setSolved(true);
            calculatingThread = null;
        }
    }

    public void handlePause() {
        if (solver != null) {
            solver.setPaused(!solver.isPaused());
            System.out.println(solver.isPaused());
            startButton.setDisable(!startButton.isDisabled());
            resetButton.setDisable(!resetButton.isDisabled());
            if (pauseButton.getText().equals("Continue")) {
                pauseButton.setText("Pause");
            } else {
                pauseButton.setText("Continue");
            }
        }
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
        externalDraw(solver.getMaze());
    }
}