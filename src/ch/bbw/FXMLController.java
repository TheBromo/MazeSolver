package ch.bbw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    public void close(MouseEvent evt) {
        ((Label)evt.getSource()).getScene().getWindow().hide();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
