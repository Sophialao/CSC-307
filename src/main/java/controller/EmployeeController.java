package controller;
import model.Employee;
import model.SalaryEmployee;
import model.HourlyEmployee;

import java.util.ArrayList;
import java.util.*;
import util.Utils;

public class EmployeeController {

    // So each element represents an ArrayList of Timecards for each eid (employee)
    public HashMap<Integer, SalaryEmployee> allSEmployees = new HashMap<Integer, SalaryEmployee>();
    public HashMap<Integer, HourlyEmployee> allHEmployees = new HashMap<Integer, HourlyEmployee>();


    public EmployeeController() {
    }

    //add Employee
    //edit Employee
    //delete Employee


    public Employee addSalaryEmployee(String name, String address, int number, int eid, String method, double salary,double commission, double sales) {

        SalaryEmployee s = new SalaryEmployee(name, address, number, eid, method, salary, commission, sales);


        if (allSEmployees.get(eid) == null) { // new employee
            allSEmployees.put(eid, s);
        }
        else{
            System.out.println("Employee already exists");
        }

       return (allSEmployees.get(eid));
    }

    public Employee addHourlyEmployee(String name, String address, int ssn, int eid, String method, double rate) {

        HourlyEmployee h = new HourlyEmployee(name, address, ssn, eid, method, rate);


        if (allHEmployees.get(eid) == null) { // new employee
            allHEmployees.put(eid, h);
        }
        else{
            System.out.println("Employee already exists");
        }

        return (allHEmployees.get(eid));
    }

    public void deleteEmployee(int eid){
        if(allSEmployees.get(eid) != null){
            allSEmployees.remove(eid);
        }
        else if(allHEmployees.get(eid) != null){
            allHEmployees.remove(eid);
        }
        else{
            System.out.println("employee doesn't exist");
        }

    }

    public void  editSalaryEmployee(String name, String address, int number, int eid, String method, double salary,double commission, double sales){
        if(allSEmployees.get(eid) == null){
            //employee does not exist
            System.out.println("employee does not exist");
        }
        else{
            SalaryEmployee currEmp = allSEmployees.get(eid);
            currEmp.setAddress(address);
            currEmp.setMethod(method);
            currEmp.setName(name);
            currEmp.setCommission(commission);
            currEmp.setSalary(salary);
            currEmp.setSales(sales);
            allSEmployees.put(eid,currEmp);
        }
    }

    public void editHourlyEmployee(String name, String address, int ssn, int eid, String method, double rate){
        if(allHEmployees.get(eid) == null){
            //employee does not exist
            System.out.println("employee does not exist");
        }
        else{
            HourlyEmployee currEmp = allHEmployees.get(eid);
            currEmp.setAddress(address);
            currEmp.setMethod(method);
            currEmp.setName(name);
            currEmp.setRate(rate);
            allHEmployees.put(eid,currEmp);
        }
    }

    public HashMap<Integer, SalaryEmployee> getAllSEmployee(){
        System.out.println(allSEmployees.keySet());
        return allSEmployees;
    }

    public HashMap<Integer, HourlyEmployee> getAllHEmployee(){
        System.out.println(allHEmployees.keySet());
        return allHEmployees;
    }

    public Employee getSEmployee(int eid){
        return allSEmployees.get(eid);
    }

    public Employee getHEmployee(int eid){
        return allHEmployees.get(eid);
    }
/*
    public void writeToFile(String filename) {
        TreeMap<Integer, String> tm = new TreeMap<Integer, String>();
        Set<Integer> keys = timecards.keySet();
        Iterator<Integer> keyIter = keys.iterator();

        while (keyIter.hasNext()) {
            int eid = keyIter.next();
            ArrayList<Timecard> employeeTc = getTimecardList(eid);

            for (Timecard tc : employeeTc) {
                tm.put(tc.getId(), tc.timecardToString());
            }
        }
        Utils.writeToFile(tm, filename);
    }
*/


    public static void main(String [] args) {




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

    }

}
