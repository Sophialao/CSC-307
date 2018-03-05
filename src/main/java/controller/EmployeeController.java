package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.DbWritable;
import model.Employee;
import model.SalaryEmployee;
import model.HourlyEmployee;
import javafx.scene.control.ListView;
import view.EmployeeView;

import javax.swing.text.html.parser.Entity;
import java.util.*;

import static javafx.application.Application.launch;

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

    @FXML
    ListView sLV;
    @FXML
    ListView hLV;
    public void setData(){

        Map<String, DbWritable> allS = SalaryEmployee.getAll();
        List<SalaryEmployee> salaryEmployees = new ArrayList<SalaryEmployee>();
        List<String> salaryEmployeeNames = new ArrayList<String>();

        Iterator<String> it = allS.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            salaryEmployees.add((SalaryEmployee) allS.get(key));
        }
        for(int i =0;i<salaryEmployees.size();i++){

            SalaryEmployee obj=salaryEmployees.get(i);
            salaryEmployeeNames.add(obj.getName());
        }

        System.out.println(salaryEmployeeNames.toString());
        ObservableList<String> items = FXCollections.observableArrayList (salaryEmployeeNames);
        sLV.setItems(items);


        Map<String, DbWritable> allH = HourlyEmployee.getAll();
        List<HourlyEmployee> hourlyEmployees = new ArrayList<HourlyEmployee>();
        List<String> hourlyEmployeeNames = new ArrayList<String>();

        Iterator<String> it2 = allH.keySet().iterator();
        while(it2.hasNext()){
            String key2 = it2.next();
            hourlyEmployees.add((HourlyEmployee) allH.get(key2));
        }
        for(int i =0;i<hourlyEmployees.size();i++){

            HourlyEmployee obj2=hourlyEmployees.get(i);
            hourlyEmployeeNames.add(obj2.getName());
        }

        System.out.println(hourlyEmployeeNames.toString());
        ObservableList<String> items2 = FXCollections.observableArrayList (hourlyEmployeeNames);
        hLV.setItems(items2);
    }


    public static void main(String [] args) {

    }

}
