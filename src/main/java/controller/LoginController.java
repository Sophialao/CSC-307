package controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;

public class LoginController {
    @FXML private Text actionTarget;


    @FXML protected void signIn(ActionEvent event) {
        actionTarget.setText("Hello world!");
    }
}
