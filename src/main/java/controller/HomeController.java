package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.DbWritable;
import model.Employee;
import model.HourlyEmployee;
import model.SalaryEmployee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HomeController {
    @FXML ListView sLV;
    @FXML ListView hLV;

    public void editSalaryEmployeeClicked(ActionEvent event) {
        this.initializeEmployee(event, (Employee) sLV.getSelectionModel().getSelectedItem());
    }

    public void editHourlyEmployeeClicked(ActionEvent event) {
        this.initializeEmployee(event, (Employee) hLV.getSelectionModel().getSelectedItem());
    }

    public void addEmployeeClicked(ActionEvent event) {
        this.initializeEmployee(event, null);
    }

    public void initializeEmployee(ActionEvent event, Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Employee");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();

            if (employee != null) {
                EmployeeController controller = loader.getController();
                controller.setFields(employee);
            }
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(){

        sLV.setCellFactory(param -> new ListCell<SalaryEmployee>() {
            @Override
            protected void updateItem(SalaryEmployee empl, boolean empty) {
                super.updateItem(empl, empty);
                if (empty || empl == null || empl.getName() == null) {
                    setText(null);
                } else {
                    setText(empl.getName());
                }
            }
        });
        hLV.setCellFactory(param -> new ListCell<HourlyEmployee>() {
            @Override
            protected void updateItem(HourlyEmployee empl, boolean empty) {
                super.updateItem(empl, empty);
                if (empty || empl == null || empl.getName() == null) {
                    setText(null);
                } else {
                    setText(empl.getName());
                }
            }
        });

        Map<String, DbWritable> allS = SalaryEmployee.getAll();
        List<SalaryEmployee> salaryEmployees = new ArrayList<SalaryEmployee>();

        Iterator<String> it = allS.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            salaryEmployees.add((SalaryEmployee) allS.get(key));
        }

        ObservableList<SalaryEmployee> items = FXCollections.observableArrayList (salaryEmployees);
        sLV.setItems(items);


        Map<String, DbWritable> allH = HourlyEmployee.getAll();
        List<HourlyEmployee> hourlyEmployees = new ArrayList<HourlyEmployee>();

        Iterator<String> it2 = allH.keySet().iterator();
        while(it2.hasNext()){
            String key2 = it2.next();
            hourlyEmployees.add((HourlyEmployee) allH.get(key2));
        }

        ObservableList<HourlyEmployee> items2 = FXCollections.observableArrayList (hourlyEmployees);
        hLV.setItems(items2);
    }
}
