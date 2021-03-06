package model;

import util.Constants;
import util.DbSqlite;
import util.DbSqlite;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class HourlyEmployee extends Employee {
    private double rate;   // Annual salary

    public HourlyEmployee() {
        super();
    }
    public HourlyEmployee(String name, String address, int ssn, double rate, String gender, int sickDays) {
        super(name, address, ssn, gender, sickDays);
        this.rate = rate;
    }

    public static HourlyEmployee getInstance(String id) {
        if (id == null)
            return new HourlyEmployee();
        else {
            return (HourlyEmployee) DbSqlite.getObject(HourlyEmployee.class, id, Constants.HOURLY_EMPLOYEE_DB);
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = DbSqlite.getAll(HourlyEmployee.class, Constants.HOURLY_EMPLOYEE_DB);
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

    public void readFields(ResultSet res) throws SQLException {
        super.readFields(res);
        this.setRate(res.getDouble("rate"));

    }

    public void update() {
        String stmt = "UPDATE " + Constants.HOURLY_EMPLOYEE_DB + " SET ";
        stmt += "name = '" + this.getName() + "', ";
        stmt += "address = '" + this.getAddress() + "', ";
        stmt += "ssn = " + this.getSsn() + ", ";
        stmt += "gender = '" + this.getGender() + "', ";
        stmt += "sickDays = " + this.getSickDays() + ", ";
        stmt += "rate = " + this.getRate();
        stmt += " WHERE id = '" + this.getId() +"';";
        DbSqlite.insertOrDelete(stmt);
    }

    public void write() {
        String stmt = "INSERT INTO " + Constants.HOURLY_EMPLOYEE_DB + "(id, name, type, address, ssn, gender, sickDays, " +
                "rate) VALUES (";
        stmt += "'" + this.getId() + "', '" + this.getName() + "', '" + "Hourly" + "', '" + this.getAddress() + "', " + this.getSsn() + ", '" +
                this.getGender() + "', " + this.getSickDays() + ", " + this.getRate() + ");";
        DbSqlite.insertOrDelete(stmt);
    }

    public void remove() {
        String stmt = "DELETE FROM " + Constants.HOURLY_EMPLOYEE_DB + " WHERE id = '" + this.getId() + "';";
        DbSqlite.insertOrDelete(stmt);
    }


}