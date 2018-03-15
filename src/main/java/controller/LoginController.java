package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Login;

import java.io.IOException;
import java.util.UUID;

import static model.Login.valueExistsInDb;


public class LoginController {
    @FXML Button signUpButton;
    @FXML Button loginButton;

    @FXML
    TextField aUsername;
    @FXML
    PasswordField aPassword;

    public void goToSignUp(ActionEvent event) {
        this.initializeSignUp(event);
        //((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void goToHome(ActionEvent event){
        this.verifyAccount();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void initializeHomeView() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Pay Roll");
            stage.setScene(new Scene(root, 800, 460));
            stage.show();

            HomeController controller = loader.getController();
            controller.setData();



        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initializeSignUp(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUpView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(root, 800, 460));
            stage.show();


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verifyAccount(){
        String username;
        String password;

        if(aUsername.getText() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter a username");
            return;
        }

        if(aPassword.getText() == null){
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter a password");
            return;
        }

        try {
            username = (aUsername.getText());
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Username not formatted correctly");
            return;
        }
        try {
            password = (aPassword.getText());
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Password not formatted correctly");
            return;
        }

         if(valueExistsInDb(username,password)){
            initializeHomeView();
         }
         else{
             showAlert(Alert.AlertType.ERROR, "Bad Boy!",
                     "Passwords wrong");
         }
    }

    private void showAlert(Alert.AlertType alertType,String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
