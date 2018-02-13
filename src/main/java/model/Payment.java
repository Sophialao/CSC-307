package model;

import java.util.Date;

public class Payment {
    private int id;
    private int employeeId;
    private int amount;
    private Date date;

    public Payment(int id, int employeeId, int amount, Date date) {
        this.id = id;
        this.employeeId = employeeId;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
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
}
