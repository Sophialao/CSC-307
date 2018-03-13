package model;

import util.Constants;
import util.DbUtils;
import util.DbSqlite;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                          double commission, double sales, String gender, int sickDays) {
        super(name, address, ssn, gender, sickDays);
        this.salary = salary;
        this.commission = commission;
        this.sales = sales;
    }

    public static SalaryEmployee getInstance(String id) {
        if (id == null)
            return new SalaryEmployee();
        else {
            return (SalaryEmployee) DbSqlite.getObject(SalaryEmployee.class, id, Constants.SALARY_EMPLOYEE_DB);
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = DbSqlite.getAll(SalaryEmployee.class, Constants.SALARY_EMPLOYEE_DB);
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

    public void readFields(ResultSet res) throws SQLException {
        super.readFields(res);
        this.setSalary(res.getDouble("rate"));
        this.setCommission(res.getDouble("commission"));
        this.setSales(res.getDouble("sales"));
    }

    public void update() {
        String stmt = "UPDATE " + Constants.SALARY_EMPLOYEE_DB + " SET ";
        stmt += "name = '" + this.getName() + "', ";
        stmt += "address = '" + this.getAddress() + "', ";
        stmt += "ssn = " + this.getSsn() + ", ";
        stmt += "gender = '" + this.getGender() + "', ";
        stmt += "sickDays = " + this.getSickDays() + ", ";
        stmt += "rate = " + this.getSalary() + ", ";
        stmt += "commission = " + this.getCommission() + ", ";
        stmt += "sales = " + this.getSales() + ", ";
        stmt += " WHERE id = '" + this.getId() +"';";
        DbSqlite.insertOrDelete(stmt);
    }

    public void write() {
        String stmt = "INSERT INTO " + Constants.SALARY_EMPLOYEE_DB + "(id, name, type, address, ssn, gender, sickDays, " +
                "rate, commission, sales) VALUES (";
        stmt += "'" + this.getId() + "', '" + this.getName() + "', '" + "Salary" + "', '" + this.getAddress() + "', " + this.getSsn() +
                ", '" + this.getGender() + "', " +
                this.getSickDays() + ", " + this.getSalary() + ", " + this.getCommission() + ", " +
                this.getSales() + ");";
        DbSqlite.insertOrDelete(stmt);
    }

    public void remove() {
        String stmt = "DELETE FROM " + Constants.SALARY_EMPLOYEE_DB + " WHERE id = '" + this.getId() + "';";
        DbSqlite.insertOrDelete(stmt);
    }
}