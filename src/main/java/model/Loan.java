package model;

public class Loan {
    private int id;
    private int employeeId;
    private double amount;


    public Loan(int id, int employeeId, double amount) {
        this.id = id;
        this.employeeId = employeeId;
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
