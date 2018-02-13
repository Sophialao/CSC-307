package model;

public class SalaryEmployee extends Employee {
    private double salary;
    private double commissionRate;

    public SalaryEmployee(String id, String firstName, String lastName, int sickDays, int vacationDays, double salary, double commissionRate) {
        super(id, firstName, lastName, sickDays, vacationDays);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }
}
