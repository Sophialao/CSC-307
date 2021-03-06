package model;

import util.Constants;
import util.DbUtils;
import util.DbSqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class Loan implements DbWritable {
    private String id;
    private String employeeId;
    private double amount;
    private double interestRate;
    private int duration;


    public Loan() {
        this.id = UUID.randomUUID().toString();
    }

    public Loan(String employeeId, double amount, double interestRate, int duration) {
        this.employeeId = employeeId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.duration = duration;
        this.id = UUID.randomUUID().toString();
    }

    public static Loan getInstance(String id) {
        if (id == null)
            return new Loan();
        else {
            return (Loan) DbSqlite.getObject(Loan.class, id, Constants.LOAN_DB);
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = DbSqlite.getAll(Loan.class, Constants.LOAN_DB);
        return db;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void readFields(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setAmount(res.getDouble("amount"));
        this.setDuration(res.getInt("duration"));
        this.setEmployeeId(res.getString("employeeId"));
        this.setInterestRate(res.getDouble("interestRate"));
    }

    public void update() {
        String stmt = "UPDATE " + Constants.LOAN_DB + " SET ";
        stmt += "amount = " + this.getAmount() + ", ";
        stmt += "interestRate = " + this.getInterestRate() + ", ";
        stmt += "duration = " + this.getDuration() + ", ";
        stmt += "employeeId = '" + this.getEmployeeId() + "'";
        stmt += " WHERE id = '" + this.getId() +"';";
        DbSqlite.insertOrDelete(stmt);
    }

    public void write() {
        String stmt = "INSERT INTO " + Constants.LOAN_DB + "(id, amount, interestRate, duration, employeeId) VALUES (";
        stmt += "'" + this.getId() + "', " + this.getAmount() + ", " + this.getInterestRate() + ", " + this.getDuration() + ", '" +
                this.getEmployeeId() + "');";
        DbSqlite.insertOrDelete(stmt);
    }

    public void remove() {
        String stmt = "DELETE FROM " + Constants.LOAN_DB + " WHERE id = '" + this.getId() + "';";
        DbSqlite.insertOrDelete(stmt);
    }
}
