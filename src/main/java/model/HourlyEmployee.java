package model;

import controller.TimecardController;
import util.Constants;
import util.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HourlyEmployee extends Employee implements DbWritable {
    private double rate;   // Annual salary

    private HourlyEmployee() {
        super();
    }
    private HourlyEmployee(String id, String name, String address, int ssn, double rate) {
        super(name, address, ssn, id);
        this.rate = rate;
    }

    public static HourlyEmployee getInstance(String id) {
        if (id == null)
            return new HourlyEmployee();
        else {
            String[] db = Utils.readLine(Constants.HOURLY_EMPLOYEE_DB, id);
            if (db != null) {
                return new HourlyEmployee(db[0], db[1], db[2],
                        Integer.parseInt(db[3]), Double.parseDouble(db[4]));
            }
            return new HourlyEmployee();
        }
    }

    public static Map<String, DbWritable> getAll() {
        ArrayList<HourlyEmployee> employees = new ArrayList<HourlyEmployee>();
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
        String toWrite = this.getId() + " " + this.getName() + " " + this.getAddress() + " " +
                this.getSsn() + " " + this.rate ;
        Utils.appendLine(Constants.HOURLY_EMPLOYEE_DB, toWrite);
    }



}