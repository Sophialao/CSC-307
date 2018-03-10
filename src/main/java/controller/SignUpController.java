package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

import javafx.scene.text.*;


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
       Login aLogin = new Login(eId,username, password);
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
