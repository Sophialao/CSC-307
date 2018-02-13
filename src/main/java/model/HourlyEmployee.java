package model;

public class HourlyEmployee extends Employee {
    private double rate;

    public HourlyEmployee(String id, String firstName, String lastName, int sickDays, int vacationDays, double rate) {
        super(id, firstName, lastName, sickDays, vacationDays);
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
