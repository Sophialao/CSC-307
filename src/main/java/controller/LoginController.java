package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginController {
    @FXML Button signUpButton;
    @FXML Button loginButton;

    public void goToSignUp(ActionEvent event) {
        this.initializeSignUp(event);
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void goToHome(ActionEvent event){
        this.initializeHomeView();
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

}
