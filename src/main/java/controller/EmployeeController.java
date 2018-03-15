package controller;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import javafx.scene.text.*;

import java.io.IOException;


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

    int numberSickDays = 3;

    @FXML Spinner<Integer> sickdaysSpinner;

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
                        Double.parseDouble(sales.getText()), gender,
                        sickdaysSpinner.getValue());
                        //Integer.parseInt(sickdays.getText()));
            } else {
                this.editEmployee(event);
            }
        } else {
            if (this.employee == null) {
               // sickdaysSpinner.setEditable(true);

                this.employee = this.addHourlyEmployee(null, name.getText(),
                        address.getText(), Integer.parseInt(ssn.getText()),
                        Double.parseDouble(rate.getText()), gender,
                        sickdaysSpinner.getValue());
                        // Integer.parseInt(sickdays.getText()));
            } else {
                this.editEmployee(event);
            }
        }
        showAlert(Alert.AlertType.CONFIRMATION, "Success",
                "Employee " + this.employee.getName() + " added!");
        ((Node) event.getSource()).getScene().getWindow().hide();
        //this.setHomeData();
    }

    public void removeEmployee(ActionEvent event) {
        this.deleteEmployee(this.employee.getId());
        showAlert(Alert.AlertType.CONFIRMATION, "Success",
                "Employee " + this.employee.getName() + " removed!");
        //this.setData();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void setHomeData() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeView.fxml"));
            Parent root = loader.load();
            HomeController hc = loader.getController();
            hc.refreshPage();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setFields(Employee employee) {
        this.employee = employee;
        ssn.setText(Integer.toString(employee.getSsn()));
        name.setText(employee.getName());
        address.setText(employee.getAddress());
        //sickdays.setText(Integer.toString(employee.getSickDays()));
        //sickdaysSpinner.
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

            SpinnerValueFactory<Integer> sickdaysValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-30,30, employee.getSickDays());

            this.sickdaysSpinner.setValueFactory(sickdaysValueFactory);
            sickdaysSpinner.setEditable(true);
        } else {
            HourlyEmployee hourly = (HourlyEmployee) employee;
            hourlyCheck.setSelected(true);
            rate.setText(Double.toString(hourly.getRate()));


            SpinnerValueFactory<Integer> sickdaysValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-30,30,employee.getSickDays());
            this.sickdaysSpinner.setValueFactory(sickdaysValueFactory);
            sickdaysSpinner.setEditable(true);

        }
        //this.setData();

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


    public Employee addSalaryEmployee(String eid, String name, String address, int number, double salary,double commission, double sales, String gender, int sickDays) {

        SalaryEmployee s = SalaryEmployee.getInstance(eid);

        s.setName(name);
        s.setAddress(address);
        s.setSsn(number);
        s.setSalary(salary);
        s.setCommission(commission);
        s.setSales(sales);
        s.setGender(gender);
        s.setSickDays(sickDays);

        s.write();
        return s;
    }

    public Employee addHourlyEmployee(String eid, String name, String address, int ssn, double rate, String gender, int sickDays) {

        HourlyEmployee h = HourlyEmployee.getInstance(eid);

        h.setName(name);
        h.setAddress(address);
        h.setSsn(ssn);
        h.setRate(rate);
        h.setGender(gender);
        h.setSickDays(sickDays);

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
    public void instantiate(){
        SpinnerValueFactory<Integer> sickdaysValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-30,30, 0);

        this.sickdaysSpinner.setValueFactory(sickdaysValueFactory);
        sickdaysSpinner.setEditable(true);
        sickdaysSpinner.setEditable(true);
        sickdaysSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                sickdaysSpinner.increment(0); // won't change value, but will commit editor
            }
        });
    }

    public void editSalaryEmployee(String name, String address, int number, String eid, double salary,double commission, double sales, String gender, int sickDays){
            SalaryEmployee aSP= SalaryEmployee.getInstance(eid);
            aSP.setName(name);
            aSP.setAddress(address);
            aSP.setSsn(number);
            aSP.setSalary(salary);
            aSP.setGender(gender);
            aSP.setCommission(commission);
            aSP.setSales(sales);
            aSP.setSickDays(sickDays);

            aSP.update();
    }

    public void editHourlyEmployee(String name, String address, int ssn, String eid, double rate, String gender, int sickDays){
        HourlyEmployee aHP= HourlyEmployee.getInstance(eid);
        aHP.setName(name);
        aHP.setAddress(address);
        aHP.setSsn(ssn);
        aHP.setRate(rate);
        aHP.setGender(gender);
        aHP.setSickDays(sickDays);


        aHP.update();
    }

    public void editEmployee(ActionEvent event){
        String gender = "M";
        if (femaleButton.isSelected()) {
            gender = "F";
        }
        if (salaryCheck.isSelected()) {
            this.editSalaryEmployee(name.getText(),
                    address.getText(), Integer.parseInt(ssn.getText()), this.employee.getId(),
                    Double.parseDouble(rate.getText()), Double.parseDouble(commission.getText()),
                    Double.parseDouble(sales.getText()), gender,
                    sickdaysSpinner.getValue());
                    //Integer.parseInt(sickdays.getText()));
        } else {
            this.editHourlyEmployee(name.getText(),
                    address.getText(), Integer.parseInt(ssn.getText()), this.employee.getId(),
                    Double.parseDouble(rate.getText()), gender,
                    sickdaysSpinner.getValue());
                    //Integer.parseInt(sickdays.getText()));
        }
        showAlert(Alert.AlertType.CONFIRMATION, "Success",
                "Employee " + this.employee.getName() + " added!");
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
