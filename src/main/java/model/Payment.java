package model;

import util.Constants;
import util.Utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Payment implements DbWritable {
    private String id;
    private String employeeId;
    private int amount;
    private Date date;

    public Payment() {
        this.id = UUID.randomUUID().toString();
    }
    public Payment(String employeeId, int amount, Date date) {
        this.employeeId = employeeId;
        this.amount = amount;
        this.date = date;
        this.id = UUID.randomUUID().toString();
    }

    public static Payment getInstance(String id) {
        if (id == null)
            return new Payment();
        else {
            String[] db = Utils.readLine(Constants.PAYMENT_DB, id);
            if (db != null) {
                Payment pay = new Payment();
                pay.readFields(db);
                return pay;
            }
            return new Payment();
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = Utils.parseFile(Constants.PAYMENT_DB, HourlyEmployee.class);

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void readFields(String[] line) {
        this.id = line[0];
        this.employeeId = line[1];
        this.amount = Integer.parseInt(line[2]);
        this.date = new Date(line[3]);
    }

    public void write() {
        Utils.removeLine(Constants.PAYMENT_DB, this.id);
        String toWrite = this.id + " " + this.employeeId + " " + this.amount + " " + this.date.toString();
        Utils.appendLine(Constants.PAYMENT_DB, toWrite);
    }
}
