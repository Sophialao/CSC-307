package view;

import controller.EmployeeController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import model.DbWritable;
import model.SalaryEmployee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class EmployeeView extends Application{

// Java Program to illustrate calling a
// no-argument constructor


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeView.fxml"));
        Parent root = loader.load();
        EmployeeController controller = loader.getController();
        controller.setData();

        Scene scene = new Scene(root, 700, 700);

        stage.setTitle("Employees");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        EmployeeView ev = new EmployeeView();
        launch(args);
    }
}
