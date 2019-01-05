package ch.bbw.solver.controller;

import ch.bbw.Map;
import ch.bbw.solver.model.Maze;
import ch.bbw.solver.model.MazeSolver;
import ch.bbw.solver.model.Position;
import ch.bbw.solver.model.fields.*;
import ch.bbw.solver.model.fields.Robot;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    VBox vbox;

    @FXML
    HBox header;

    @FXML
    Canvas canvas;

    @FXML
    private RadioButton pledgeAlgorithm, rightHandAlgorithm, kidsMode;

    @FXML
    private Button startButton, pauseButton, resetButton, importButton;

    private GraphicsContext context;
    private Stage primaryStage;
    private static Image robot, wall, grass, flag, start, triangle;
    private double oldX, oldY, lastDragX, lastDragY, offsetX, offsetY;
    private Field[][] importedMap;
    private MazeSolver solver;
    private Thread calculatingThread;

    // size of a side each block in pixels
    private int size;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void close(MouseEvent event) {
        if (solver != null) {
            solver.setSolved(true);
        }
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    private void moveWindow(MouseEvent event, boolean moving) {
        if (moving) {
            primaryStage.setX(primaryStage.getX() + (event.getScreenX() - oldX));
            primaryStage.setY(primaryStage.getY() + (event.getScreenY() - oldY));
        }

        oldX = event.getScreenX();
        oldY = event.getScreenY();
    }

    public boolean isPledge()
    {
        return pledgeAlgorithm.isSelected();
    }

    public void externalDraw() {
        Platform.runLater(() -> {
            if (kidsMode.isSelected()) drawKids();
            else draw();
        });
    }

    private void toggleAlgorithmSelection()
    {
        rightHandAlgorithm.setDisable(!rightHandAlgorithm.isDisabled());
        pledgeAlgorithm.setDisable(!pledgeAlgorithm.isDisabled());
        importButton.setDisable(!importButton.isDisabled());
        pauseButton.setDisable(!pauseButton.isDisabled());
        resetButton.setDisable(!resetButton.isDisabled());
        startButton.setDisable(!startButton.isDisabled());
    }

    private void drawKids() {
        Maze maze = solver.getMaze();
        context.setFill(Color.DARKGRAY);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.translate(offsetX, offsetY);
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, maze.getSize() * size, maze.getSize() * size);
        for (int x = 0; x < maze.getSize(); x++) {
            for (int y = 0; y < maze.getSize(); y++) {
                Field field = maze.getField(new Position(x, y));
                if (field instanceof Empty) {
                    context.drawImage(grass, x * size, y * size, size, size);
                } else if (field instanceof Wall) {
                    context.drawImage(wall, x * size, y * size, size, size);
                } else if (field instanceof Robot) {
                    context.drawImage(grass, field.getPosition().getX() * size, field.getPosition().getY() * size, size, size);
                } else if (field instanceof Goal){
                    context.drawImage(grass, x * size, y * size, size, size);
                    context.drawImage(flag, x * size, y * size, size, size);
                }
                else if (field instanceof Start)
                {
                    context.drawImage(grass, x * size, y * size, size, size);
                    context.drawImage(start, x * size, y * size, size, size);
                }
            }
        }
        Robot robotObj = maze.getRobot();
        context.drawImage(getRotatedImage(robot, robotObj.getTurnedDegrees()), robotObj.getPosition().getX() * size, robotObj.getPosition().getY() * size, size, size);
        context.translate(-offsetX, -offsetY);
    }

    private void draw() {
        Maze maze = solver.getMaze();
        context.setFill(Color.web("#eceff1"));
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.translate(offsetX, offsetY);
        for (int x = 0; x < maze.getSize(); x++) {
            for (int y = 0; y < maze.getSize(); y++) {
                Field field = maze.getField(new Position(x, y));
                if (field instanceof Wall) {
                    context.setFill(Color.web("#263238"));
                    context.fillRect(x * size, y * size, size, size);
                } else if (field instanceof Goal) {
                    context.setFill(Color.web("#00e676"));
                    context.fillRect(x * size + 40, y * size + 40, size - 80, size - 80);
                } else if (field instanceof Start)
                {
                    context.setFill(Color.web("#e67600"));
                    context.fillRect(x * size + 40, y * size + 40, size - 80, size - 80);
                }
                context.setStroke(Color.web("#37474f"));
                context.strokeRect(x * size, y * size, size, size);

            }
        }
        Robot robotObj = maze.getRobot();
        context.drawImage(getRotatedImage(triangle, robotObj.getTurnedDegrees()), robotObj.getPosition().getX() * size, robotObj.getPosition().getY() * size, size, size);
        context.translate(-offsetX, -offsetY);
    }

    private Image getRotatedImage(Image image, int rotationDegrees)
    {
        ImageView iv = new ImageView(image);
        iv.setRotate(rotationDegrees);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return iv.snapshot(params, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        header.setOnMouseDragged(event -> moveWindow(event, true));
        header.setOnMouseMoved(event -> moveWindow(event, false));

        size = 150;
        context = canvas.getGraphicsContext2D();
        robot = new Image(getClass().getResourceAsStream("/robot.png"));
        wall = new Image(getClass().getResourceAsStream("/wall.png"));
        grass = new Image(getClass().getResourceAsStream("/grass.png"));
        flag = new Image(getClass().getResourceAsStream("/flag.png"));
        start = new Image(getClass().getResourceAsStream("/start.png"));
        triangle = new Image(getClass().getResourceAsStream("/triangle.png"));
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
    }

    public void setSolver(MazeSolver solver) {
        this.solver = solver;
    }

    public void handleStart() {
        if (calculatingThread == null) {
            calculatingThread = new Thread(new MazeSolver(this));
            toggleAlgorithmSelection();
            calculatingThread.start();
        }
    }

    public void handleReset() {
        if (solver != null) {
            solver.setSolved(true);
            toggleAlgorithmSelection();
            calculatingThread = null;
        }
    }

    public void handlePause() {
        if (solver != null) {
            solver.setPaused(!solver.isPaused());
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
        externalDraw();
    }

    public void handleImport(ActionEvent actionEvent) {
        try {
            importedMap = new Map().importMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Field[][] getImportedMap() {
        return importedMap;
    }
}