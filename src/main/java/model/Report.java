package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Report {

    private final ObjectProperty<String> name = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> rate = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> commission = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> loan = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> commissionAmt = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> taxes = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> hours = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> sickDays = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> payment = new SimpleObjectProperty<>();

    public Report() {
        this.name.set("");
        this.rate.set(0.0);
        this.commission.set(0.0);
    }

    public Report(String name, double rate, double commission, double loan, double commissionAmt, double taxes, double hours, double sickDays, double payment) {
        this.name.set(name);
        this.rate.set(rate);
        this.commission.set(commission);
        this.loan.set(loan);
        this.commissionAmt.set(commissionAmt);
        this.taxes.set(taxes);
        this.hours.set(hours);
        this.sickDays.set(sickDays);
        this.payment.set(payment);
    }

    public String toSalaryString() {
        //name, rate, commission, loan, commAmt, taxes, hours, sickDays, payment
        return String.format("|%-15s|%-15.2f|%-15.2f|%-15.2f|%-20.2f|%-15.2f|%-15.2f|%-15.2f|",
                getName(),getRate(),getCommission(),getLoan(),getCommissionAmt(),getTaxesAmt(),getSickDays(),getPayment());
    }

    public String toHourlyString() {
        //name, rate, commission, loan, commAmt, taxes, hours, sickDays, payment
        return String.format("|%-15s|%-15.2f|%-15.2f|%-15.2f|%-15.2f|%-15.2f|%-15.2f|",
                getName(),getRate(),getLoan(),getTaxesAmt(),getHours(),getSickDays(),getPayment());
    }

    public double truncateDouble(double value) {
        double res = Math.floor(value * 100) / 100;
        return (res);
    }

    public final String getName() {
        return this.name.get();
    }
    public final void setName(String value) {
        this.name.set(value);
    }
    public final ObjectProperty<String> nameProperty() {
        return this.name;
    }

    public final double getRate() {
        return truncateDouble(this.rate.get());
    }
    public final void setRate(double value) {
        this.rate.set(value);
    }
    public final ObjectProperty<Double> rateProperty() {
        return this.rate;
    }

    public final Double getCommission() {
        return truncateDouble(this.commission.get());
    }
    public final void setName(double value) {
        this.commission.set(value);
    }
    public final ObjectProperty<Double> comissionProperty() {
        return this.commission;
    }

    public final Double getLoan() {
        return truncateDouble(this.loan.get());
    }
    public final void setLoan(double value) {
        this.loan.set(value);
    }
    public final ObjectProperty<Double> loanProperty() {
        return this.loan;
    }

    public final Double getCommissionAmt() {
        return truncateDouble(this.commissionAmt.get());
    }
    public final void setCommissionAmt(double value) {
        this.commissionAmt.set(value);
    }
    public final ObjectProperty<Double> commissionAmtProperty() {
        return this.commissionAmt;
    }

    public final Double getTaxesAmt() {
        return truncateDouble(this.taxes.get());
    }
    public final void setTaxes(double value) {
        this.taxes.set(value);
    }
    public final ObjectProperty<Double> taxesProperty() {
        return this.taxes;
    }

    public final Double getSickDays() { return truncateDouble(this.sickDays.get()); }
    public final void setSickDays(double value) { this.sickDays.set(value); }
    public final ObjectProperty<Double> sickDaysProperty() {
        return this.sickDays;
    }

    public final Double getHours() {
        return truncateDouble(this.hours.get());
    }
    public final void setHours(double value) {
        this.hours.set(value);
    }
    public final ObjectProperty<Double> hoursProperty() {
        return this.hours;
    }


    public final Double getPayment() {
        return truncateDouble(this.payment.get());
    }
    public final void setPayment(double value) {
        this.payment.set(value);
    }
    public final ObjectProperty<Double> paymentProperty() {
        return this.payment;
    }




}