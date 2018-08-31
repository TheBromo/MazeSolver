package ch.bbw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    VBox vbox;

    private double startMoveX = -1, startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;

    @FXML
    public void close(MouseEvent evt) {
        ((Label)evt.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        dragging = true;

        moveTrackingRect = new Rectangle();
        moveTrackingRect.setWidth(vbox.getWidth());
        moveTrackingRect.setHeight(vbox.getHeight());
        moveTrackingRect.getStyleClass().add( "tracking-rect" );

        moveTrackingPopup = new Popup();
        moveTrackingPopup.getContent().add(moveTrackingRect);
        moveTrackingPopup.show(vbox.getScene().getWindow());
        moveTrackingPopup.setOnHidden( (e) -> resetMoveOperation());
    }

    private void resetMoveOperation() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }

    @FXML
    public void moveWindow(MouseEvent evt) {

        if (dragging) {

            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = vbox.getScene().getWindow();

            double stageX = w.getX();
            double stageY = w.getY();

            moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
            moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
        }
    }

    @FXML
    public void endMoveWindow(MouseEvent evt) {

        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = vbox.getScene().getWindow();

            double stageX = w.getX();
            double stageY = w.getY();

            w.setX(stageX + (endMoveX - startMoveX));
            w.setY(stageY + (endMoveY - startMoveY));

            if (moveTrackingPopup != null) {
                moveTrackingPopup.hide();
                moveTrackingPopup = null;
            }
        }

        resetMoveOperation();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
