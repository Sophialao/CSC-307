package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.DbWritable;
import model.Employee;
import model.Payment;
import model.HourlyEmployee;
import model.SalaryEmployee;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import javafx.scene.text.*;


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
