package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.*;
import model.*;

import java.util.*;

public class SignUpController {
    @FXML Button signUp;
    @FXML TextField createUsername;
    @FXML PasswordField createPassword;
    String eId;


    public void addAccount(ActionEvent event){
        String username;
        String password;

        if(createUsername.getText() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter a username");
            return;
        }

        if(createPassword.getText() == null){
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter a password");
            return;
        }

        try {
            username = (createUsername.getText());
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Username not formatted correctly");
            return;
        }
        try {
            password = (createPassword.getText());
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Password not formatted correctly");
            return;
        }
       eId = UUID.randomUUID().toString();
       Login aLogin = new Login(username, password);
       aLogin.write();

        showAlert(Alert.AlertType.CONFIRMATION, "New account added!",
                "account added: \n" + "Username: " + createUsername.getText() + "\nPassword: " + createPassword.getText());
        createUsername.clear();
        createPassword.clear();

        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }



}
