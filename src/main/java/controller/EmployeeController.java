package controller;
import model.Employee;
import model.SalaryEmployee;
import model.HourlyEmployee;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeController {



    public EmployeeController() {
    }

    //add Employee
    //edit Employee
    //delete Employee

    public void addEmployee() {
        

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



    public static void main(String [] args) {


/*

        EmployeeController ec = new EmployeeController();

        ec.addSalaryEmployee("Pradeep Sangli", "Cal Poly", 123456, 1, "salary", 100000.00,0.10,5000);
        ec.addSalaryEmployee("Sam Rastovich", "Cal Poly", 234567, 2, "salary", 100000.00,0.10,5000);
        ec.addSalaryEmployee("Kelly Chen", "Cal Poly", 345678, 3, "salary", 100000.00,0.10,5000);
        ec.addSalaryEmployee("Daniel Kim", "Cal Poly", 456789, 4, "salary", 100000.00,0.10,5000);
        ec.getAllSEmployee();
        ec.deleteEmployee(2);
        ec.editSalaryEmployee("Pradeep Sangli", "LOCKHAVEN WAY", 123456, 1, "salary", 100000.00,0.10,5000);
        ec.getSEmployee(1);
        ec.getAllSEmployee();


        ec.addHourlyEmployee("Pradeep Sangli", "CalPoly", 123456, 5, "hourly", 10.00);
        ec.addHourlyEmployee("Sam Rastovich", "CalPoly", 123456, 6, "hourly", 10.00);
        ec.addHourlyEmployee("Kelly Chen", "CalPoly", 123456, 7, "hourly", 10.00);
        ec.addHourlyEmployee("Dankiel Kim", "CalPoly", 123456, 8, "hourly", 10.00);
        ec.getAllHEmployee();
        ec.deleteEmployee(5);
        ec.getAllHEmployee();
        */

    }

}
