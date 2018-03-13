package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.DbWritable;
import model.Employee;
import model.Payment;
import model.HourlyEmployee;
import model.SalaryEmployee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import javafx.scene.text.*;


public class HomeController {
    @FXML ListView sLV;
    @FXML ListView hLV;
    Employee emp;

    public void editSalaryEmployeeClicked(ActionEvent event) {
        emp = (Employee) sLV.getSelectionModel().getSelectedItem();
        this.initializeEmployee(event, emp);
        //EmployeeController ec = new EmployeeController();
        //ec.initializeTab();
    }

    public void editHourlyEmployeeClicked(ActionEvent event) {
        emp = (Employee) hLV.getSelectionModel().getSelectedItem();
        this.initializeEmployee(event, emp);
    }

    public void generateReport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GenerateReport.fxml"));
            Parent root = loader.load();
            ReportController rc = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Report");
            stage.setScene(new Scene(root, 913, 652));

            rc.generateReport();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployeeClicked(ActionEvent event) {
        this.initializeEmployee(event, null);
    }

    public void refreshPage() {
        this.setData();
    }

    public void payEmployee(ActionEvent event) {
        PaymentController controller = new PaymentController();
        String result = controller.handleSubmitButtonAction(event);
        if (result.equals("Paid")){
            String pays = "";
            Map<String, DbWritable> payments = Payment.getAll();

            for (String key : payments.keySet()){
                Payment p = (Payment)payments.get(key);
                Date today = new Date();
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                System.out.println(p.getDate());
                cal1.setTime(p.getDate());
                cal2.setTime(today);
                boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
                if (sameDay){
                    Employee e = HourlyEmployee.getInstance(p.getEmployeeId());
                    if (e == null){
                        e = SalaryEmployee.getInstance(p.getEmployeeId());
                    }
                    pays += (e.getName()+ " : " + Double.toString(p.getAmount()) + "\n");
                }
            }
            showAlert(Alert.AlertType.CONFIRMATION, "Success: Paid Employees",
                    pays);
        }
        else if (result.equals("Not Payday")){
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Not payday for any employees!");
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Already paid employees!");
        }
    }


    public void initializeEmployee(ActionEvent event, Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeTotalView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Employee");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();

            if (employee != null) {
                EmployeeTotal controller = loader.getController();
                controller.empController.setFields(employee);
                //controller.setFields(employee);
            }
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

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        if (title.equals("Success: Paid Employees")) {
            alert.setHeaderText("Payments:");
            Text t = new Text(message);
            ScrollPane scrollPane = new ScrollPane();
            //scrollPane.setFitToHeight(true);
            //scrollPane.setFitToWidth(true);
            scrollPane.setContent(t);
            alert.getDialogPane().setContent(scrollPane);
            alert.getDialogPane().setPrefSize(480, 320);
        }
        else{
            alert.setContentText(message);
        }
        alert.show();
    }
}
