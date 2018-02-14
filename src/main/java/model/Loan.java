package model;

import util.Constants;
import util.Utils;

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

    public static Loan getInstance(String id) {
        if (id == null)
            return new Loan();
        else {
            String[] db = Utils.readLine(Constants.LOAN_DB, id);
            if (db != null) {
                Loan loan = new Loan();
                loan.readFields(db);
                return loan;
            }
            return new Loan();
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = Utils.parseFile(Constants.LOAN_DB, HourlyEmployee.class);

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

    public void readFields(String[] args) {
        this.id = args[0];
        this.employeeId = args[1];
        this.amount = Double.parseDouble(args[2]);
        this.interestRate = Double.parseDouble(args[3]);
        this.duration = Integer.parseInt(args[4]);
    }

    public void write() {
        Utils.removeLine(Constants.LOAN_DB, this.getId());
        String toWrite = this.id + " " + this.employeeId + " " + this.amount + " " + this.interestRate +
                " " + this.duration;
        Utils.appendLine(Constants.LOAN_DB, toWrite);
    }
}
