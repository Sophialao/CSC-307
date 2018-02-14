package model;

import controller.TimecardController;
import util.Constants;
import util.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HourlyEmployee extends Employee {
    private double rate;   // Annual salary

    public HourlyEmployee() {
        super();
    }
    public HourlyEmployee(String name, String address, int ssn, double rate) {
        super(name, address, ssn);
        this.rate = rate;
    }

    public static HourlyEmployee getInstance(String id) {
        if (id == null)
            return new HourlyEmployee();
        else {
            String[] db = Utils.readLine(Constants.HOURLY_EMPLOYEE_DB, id);
            if (db != null) {
                HourlyEmployee empl = new HourlyEmployee();
                empl.setId(id);
                empl.readFields(db);
                return empl;
            }
            return new HourlyEmployee();
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = Utils.parseFile(Constants.HOURLY_EMPLOYEE_DB, HourlyEmployee.class);

        return db;
    }


    public double getRate() {
        return rate;
    }

    public void setRate(double newRate) {
        if(newRate >= 0.0) {
            rate = newRate;
        }
    }


    public double computePay() {
        System.out.println("Computing salary pay for " + getName());
        return rate/52;
    }

    public void readFields(String[] line) {
        super.readFields(line);
        this.rate = Double.parseDouble(line[4]);
    }

    public void write() {
        Utils.removeLine(Constants.HOURLY_EMPLOYEE_DB, this.getId());
        String toWrite = this.getId() + "," + this.getName() + "," + this.getAddress() + "," +
                this.getSsn() + "," + this.rate ;
        Utils.appendLine(Constants.HOURLY_EMPLOYEE_DB, toWrite);
    }

    public void remove() {
        Utils.removeLine(Constants.HOURLY_EMPLOYEE_DB, this.getId());
    }

    public static void main(String[] args) {
        //SalaryEmployee emp = new SalaryEmployee("sam", "dkfja", 234, 10.30, 10.09, 80.0);
        //emp.write();

        SalaryEmployee emp = SalaryEmployee.getInstance("77a999fd-987a-4a5d-936a-f3ceade5b6d9");
        emp.remove();

    }



}