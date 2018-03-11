package model;

import util.Constants;
import util.DbUtils;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Payment implements DbWritable {
    private String id;
    private String employeeId;
    private double amount;
    private Date date;

    public Payment() {
        this.id = UUID.randomUUID().toString();
    }
    public Payment(String employeeId, double amount, Date date) {
        this.employeeId = employeeId;
        this.amount = amount;
        this.date = date;
        this.id = UUID.randomUUID().toString();
    }

    public static Payment getInstance(String id) {
        if (id == null)
            return new Payment();
        else {
            return (Payment) DbUtils.getObject(Payment.class, id, Constants.PAYMENT_DB);
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = DbUtils.getAll(Payment.class, Constants.PAYMENT_DB);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void readFields(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setEmployeeId(res.getString("employeeId"));
        this.setAmount(res.getDouble("amount"));
        this.setDate(res.getDate("date"));
    }

    public void update() {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd kk:mm:00");
        String dateFormat = df.format(this.getDate());

        String stmt = "UPDATE " + Constants.PAYMENT_DB + " SET ";
        stmt += "employeeId = '" + this.getEmployeeId() + "', ";
        stmt += "amount = " + this.getAmount() + ", ";
        stmt += "date = '" + dateFormat + "'";
        stmt += " WHERE id = '" + this.getId() +"';";
        DbUtils.insertOrDelete(stmt);
    }

    public void write() {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd kk:mm:00");
        String dateFormat = df.format(this.getDate());


        String stmt = "INSERT INTO " + Constants.PAYMENT_DB + "(id, employeeId, amount, date) VALUES (";
        stmt += "'" + this.getId() + "', '" + this.getEmployeeId() + "', " + this.getAmount() + ", '" + dateFormat + "');";
        DbUtils.insertOrDelete(stmt);
    }

    public void remove() {
        String stmt = "DELETE FROM " + Constants.PAYMENT_DB + " WHERE id = '" + this.getId() + "';";
        DbUtils.insertOrDelete(stmt);
    }
}
