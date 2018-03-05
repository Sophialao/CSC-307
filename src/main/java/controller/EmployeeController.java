package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Employee;
import model.SalaryEmployee;
import model.HourlyEmployee;

import java.io.IOException;


public class EmployeeController {

    @FXML TextField name;
    @FXML TextField address;
    @FXML TextField ssn;
    @FXML RadioButton maleButton;
    @FXML RadioButton femaleButton;
    @FXML TextField commission;
    @FXML TextField rate;
    @FXML CheckBox hourlyCheck;
    @FXML CheckBox salaryCheck;
    private Employee employee;

    public EmployeeController() {
    }

    public void addEmployee(ActionEvent event) {
        String gender = "M";
        if (femaleButton.isSelected()) {
            gender = "F";
        }
        if (salaryCheck.isSelected()) {
            this.addSalaryEmployee(this.employee.getId(), name.getText(),
                    address.getText(), Integer.parseInt(ssn.getText()),
                    Double.parseDouble(rate.getText()), Double.parseDouble(commission.getText()),
                    0.0, gender);
        } else {
            this.addHourlyEmployee(this.employee.getId(), name.getText(),
                    address.getText(), Integer.parseInt(ssn.getText()),
                    Double.parseDouble(rate.getText()), gender);
        }
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
            commission.setText(Double.toString(salary.getCommission()));
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
        salaryCheck.setSelected(false);
    }

    public void salaryClicked() {
        commission.setEditable(true);
        commission.setDisable(false);
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
        SalaryEmployee.getInstance(eid).remove();
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

}
