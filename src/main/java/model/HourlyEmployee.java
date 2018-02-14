package model;

import controller.TimecardController;

public class HourlyEmployee extends Employee {
    private double rate;   // Annual salary

    public HourlyEmployee(String name, String address, int ssn, int id, String method, double rate) {
        super(name, address, ssn, id, method);
        setRate(rate);
    }


    public double getRate() {
        return rate;
    }

    public void setRate(double newRate) {
        if(newRate >= 0.0) {
            rate = newRate;
        }
    }

    public void paymentComp(){
        TimecardController timecard = new TimecardController();
        timecard.getTimecardList(this.getId());
    }

    public double computePay() {
        System.out.println("Computing salary pay for " + getName());
        return rate/52;
    }
}