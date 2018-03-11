package controller;
import javafx.event.ActionEvent;
import javafx.event.Event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import javafx.geometry.Pos;
import javafx.scene.text.*;


import java.io.IOException;
import java.util.Map;


public class EmployeeController {

    @FXML TextField name;
    @FXML TextField address;
    @FXML TextField ssn;
    @FXML RadioButton maleButton;
    @FXML RadioButton femaleButton;
    @FXML TextField commission;
    @FXML TextField sales;
    @FXML TextField rate;
    @FXML CheckBox hourlyCheck;
    @FXML CheckBox salaryCheck;
    @FXML Button deleteButton;
    @FXML TextField sickdays;

    @FXML Text loans;
    @FXML Text timecards;

    public Employee employee;

    public EmployeeController() {
    }

    public void addEmployee(ActionEvent event) {
        String gender = "M";
        if (femaleButton.isSelected()) {
            gender = "F";
        }
        if (salaryCheck.isSelected()) {
            if (this.employee == null) {
                this.employee = this.addSalaryEmployee(null, name.getText(),
                        address.getText(), Integer.parseInt(ssn.getText()),
                        Double.parseDouble(rate.getText()), Double.parseDouble(commission.getText()),
                        Double.parseDouble(sales.getText()), gender);
            } else {
                this.addSalaryEmployee(this.employee.getId(), name.getText(),
                        address.getText(), Integer.parseInt(ssn.getText()),
                        Double.parseDouble(rate.getText()), Double.parseDouble(commission.getText()),
                        Double.parseDouble(sales.getText()), gender);
            }
        } else {
            if (this.employee == null) {
                this.employee = this.addHourlyEmployee(null, name.getText(),
                        address.getText(), Integer.parseInt(ssn.getText()),
                        Double.parseDouble(rate.getText()), gender);
            } else {
                this.addHourlyEmployee(this.employee.getId(), name.getText(),
                        address.getText(), Integer.parseInt(ssn.getText()),
                        Double.parseDouble(rate.getText()), gender);
            }
        }
        showAlert(Alert.AlertType.CONFIRMATION, "Success",
                "Employee " + this.employee.getName() + " added!");
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void removeEmployee(ActionEvent event) {
        this.deleteEmployee(this.employee.getId());
        showAlert(Alert.AlertType.CONFIRMATION, "Success",
                "Employee " + this.employee.getName() + " removed!");
        ((Node) event.getSource()).getScene().getWindow().hide();

    }


    public void setFields(Employee employee) {
        this.employee = employee;
        ssn.setText(Integer.toString(employee.getSsn()));
        name.setText(employee.getName());
        address.setText(employee.getAddress());
        if (employee.getGender().equals("M")) {
            maleButton.setSelected(true);
        } else {
            femaleButton.setSelected(true);
        }
        if (employee instanceof SalaryEmployee) {
            SalaryEmployee salary = (SalaryEmployee) employee;
            salaryCheck.setSelected(true);
            // shows commission and sales
            commission.setEditable(true);
            commission.setDisable(false);
            sales.setEditable(true);
            sales.setDisable(false);
            commission.setText(Double.toString(salary.getCommission()));
            sales.setText(Double.toString(salary.getSales()));
            rate.setText(Double.toString(salary.getSalary()));
        } else {
            HourlyEmployee hourly = (HourlyEmployee) employee;
            hourlyCheck.setSelected(true);
            rate.setText(Double.toString(hourly.getRate()));
        }

    }

    public void hourlyClicked() {
        commission.setEditable(false);
        commission.setDisable(true);
        sales.setEditable(false);
        sales.setDisable(true);
        salaryCheck.setSelected(false);
    }

    public void salaryClicked() {
        commission.setEditable(true);
        commission.setDisable(false);
        sales.setEditable(true);
        sales.setDisable(false);
        hourlyCheck.setSelected(false);
    }

    public void maleClicked() {
        femaleButton.setSelected(false);
    }
    public void femaleClicked() {
        maleButton.setSelected(false)   ;
    }


    public Employee addSalaryEmployee(String eid, String name, String address, int number, double salary,double commission, double sales, String gender) {

        SalaryEmployee s = SalaryEmployee.getInstance(eid);

        s.setName(name);
        s.setAddress(address);
        s.setSsn(number);
        s.setSalary(salary);
        s.setCommission(commission);
        s.setSales(sales);
        s.setGender(gender);

        s.write();
        return s;
    }

    public Employee addHourlyEmployee(String eid, String name, String address, int ssn, double rate, String gender) {

        HourlyEmployee h = HourlyEmployee.getInstance(eid);

        h.setName(name);
        h.setAddress(address);
        h.setSsn(ssn);
        h.setRate(rate);
        h.setGender(gender);

        h.write();
        return h;
    }

    public void deleteEmployee(String eid){
        if (this.employee instanceof HourlyEmployee){
            HourlyEmployee.getInstance(eid).remove();
        }
        else{
            SalaryEmployee.getInstance(eid).remove();
        }
    }

    public void editSalaryEmployee(String name, String address, int number, String eid, double salary,double commission, double sales){
            SalaryEmployee aSP= SalaryEmployee.getInstance(eid);
            aSP.setName(name);
            aSP.setAddress(address);
            aSP.setSsn(number);
            aSP.setSalary(salary);
            aSP.setCommission(commission);
            aSP.setSales(sales);

            aSP.write();
    }

    public void editHourlyEmployee(String name, String address, int ssn, String eid, double rate){
        HourlyEmployee aHP= HourlyEmployee.getInstance(eid);
        aHP.setName(name);
        aHP.setAddress(address);
        aHP.setSsn(ssn);
        aHP.setRate(rate);


        aHP.write();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }




}
