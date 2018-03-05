package controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Employee;
import model.SalaryEmployee;
import model.HourlyEmployee;


public class EmployeeController {

    @FXML TextField firstName;
    @FXML TextField lastName;
    @FXML TextField email;
    @FXML TextField ssn;
    @FXML RadioButton maleButton;
    @FXML RadioButton femaleButton;
    @FXML TextField commission;
    @FXML TextField rate;
    @FXML CheckBox hourlyCheck;
    @FXML CheckBox salaryCheck;

    public EmployeeController() {
    }

    public void addEmployee() {
        if (salaryCheck.isSelected()) {
            this.addSalaryEmployee(firstName.getText() + " " + lastName.getText(),
                    "23424342w", Integer.parseInt(ssn.getText()),
                    Double.parseDouble(rate.getText()), Double.parseDouble(commission.getText()),
                    0.0);
        } else {
            this.addHourlyEmployee(firstName.getText() + " " + lastName.getText(),
                    "23424342w", Integer.parseInt(ssn.getText()),
                    Double.parseDouble(rate.getText()));
        }

    }

    public void setFields(Employee employee) {
        System.out.println(employee.getName());
        String[] name = employee.getName().split(" ");
        firstName.setText(name[0]);
        lastName.setText(name[1]);
        ssn.setText(Integer.toString(employee.getSsn()));
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
        commission.setDisable(true);
        salaryCheck.setSelected(false);
    }

    public void salaryClicked() {
        commission.setDisable(false);
        hourlyCheck.setSelected(false);
    }

    public void maleClicked() {
        femaleButton.setSelected(false);
    }
    public void femaleClicked() {
        maleButton.setSelected(false)   ;
    }


    public Employee addSalaryEmployee(String name, String address, int number, double salary,double commission, double sales) {

        SalaryEmployee s = SalaryEmployee.getInstance(null);

        s.setName(name);
        s.setAddress(address);
        s.setSsn(number);
        s.setSalary(salary);
        s.setCommission(commission);
        s.setSales(sales);

        s.write();

        return s;
    }

    public Employee addHourlyEmployee(String name, String address, int ssn, double rate) {

        HourlyEmployee h = HourlyEmployee.getInstance(null);

        h.setName(name);
        h.setAddress(address);
        h.setSsn(ssn);
        h.setRate(rate);

        h.write();
        return h;
    }

    public void deleteEmployee(String eid){
        SalaryEmployee.getInstance(eid).remove();
    }

    public void  editSalaryEmployee(String name, String address, int number, String eid, double salary,double commission, double sales){
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
