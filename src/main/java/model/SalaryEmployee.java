package model;

import util.Constants;
import util.Utils;

import java.util.Map;

public class SalaryEmployee extends Employee {
    private double salary;
    private double commission;
    private double sales;
    // Annual salary

    public SalaryEmployee() {
        super();
    }
    public SalaryEmployee(String name, String address, int ssn, double salary,
                          double commission, double sales) {
        super(name, address, ssn);
        this.salary = salary;
        this.commission = commission;
        this.sales = sales;
    }

    public static SalaryEmployee getInstance(String id) {
        if (id == null)
            return new SalaryEmployee();
        else {
            String[] db = Utils.readLine(Constants.SALARY_EMPLOYEE_DB, id);
            if (db != null) {
                SalaryEmployee empl = new SalaryEmployee();
                empl.readFields(db);
                empl.setId(id);
                return empl;
            }
            return new SalaryEmployee();
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = Utils.parseFile(Constants.SALARY_EMPLOYEE_DB, SalaryEmployee.class);

        return db;
    }

    public void mailCheck() {
        System.out.println("Within mailCheck of Salary class ");
        System.out.println("Mailing check to " + getName() + " with salary " + computePay());
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double newSalary) {
        if(newSalary >= 0.0) {
            salary = newSalary;
        }
    }
    public double getCommission(){
        return commission;
    }
    public void setCommission(double newCommission){
        commission = newCommission;
    }

    public void setSales(double newSales){
        sales = newSales;
    }

    public double getSales(){
        return sales;
    }

    public double computePay() {
        System.out.println("Computing salary pay for " + getName());
        return salary/52 + sales * commission;
    }

    public void readFields(String[] line) {
        super.readFields(line);
        this.salary = Double.parseDouble(line[4]);
        this.commission = Double.parseDouble(line[5]);
        this.sales = Double.parseDouble(line[6]);
    }

    public void remove() {
        Utils.removeLine(Constants.SALARY_EMPLOYEE_DB, this.getId());
    }

    public void write() {
        Utils.removeLine(Constants.SALARY_EMPLOYEE_DB, this.getId());
        String toWrite = this.getId() + "," + this.getName() + "," + this.getAddress() + "," +
                this.getSsn() + "," + this.salary + "," + this.commission + "," + this.sales;
        Utils.appendLine(Constants.SALARY_EMPLOYEE_DB, toWrite);
    }
}