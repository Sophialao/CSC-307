package model;

import util.Constants;
import util.DbUtils;
import util.DbSqlite;

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
            return (Payment) DbSqlite.getObject(Payment.class, id, Constants.PAYMENT_DB);
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = DbSqlite.getAll(Payment.class, Constants.PAYMENT_DB);
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


    public Date parseDateString(String d){
        String[] date_time = d.split(" ");
        String[] date_split = date_time[0].split("-");
        String[] time_split = date_time[1].split(":");
        //System.out.print("STRING: " + d);
        //System.out.print("DATE_SPLIT: " + date_split[0] + date_split[1]);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        Date date = new Date(Integer.parseInt(date_split[0]), Integer.parseInt(date_split[1]), Integer.parseInt(date_split[2]), Integer.parseInt(time_split[0]), Integer.parseInt(time_split[1]));
        //System.out.println(date);
        Date date2 = null;
        try{
            date2 = df.parse(d);
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }

        return date2;
    }

    public void readFields(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setEmployeeId(res.getString("employeeId"));
        this.setAmount(res.getDouble("amount"));
        this.setDate(parseDateString(res.getString("date")));
        //this.setDate(res.getDate("date"));
    }

    public void update() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:00");
        String dateFormat = df.format(this.getDate());

        String stmt = "UPDATE " + Constants.PAYMENT_DB + " SET ";
        stmt += "employeeId = '" + this.getEmployeeId() + "', ";
        stmt += "amount = " + this.getAmount() + ", ";
        stmt += "date = '" + dateFormat + "'";
        stmt += " WHERE id = '" + this.getId() +"';";
        DbSqlite.insertOrDelete(stmt);
    }

    public void write() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:00");
        String dateFormat = df.format(this.getDate());


        String stmt = "INSERT INTO " + Constants.PAYMENT_DB + "(id, employeeId, amount, date) VALUES (";
        stmt += "'" + this.getId() + "', '" + this.getEmployeeId() + "', " + this.getAmount() + ", '" + dateFormat + "');";
        DbSqlite.insertOrDelete(stmt);
    }

    public void remove() {
        String stmt = "DELETE FROM " + Constants.PAYMENT_DB + " WHERE id = '" + this.getId() + "';";
        DbSqlite.insertOrDelete(stmt);
    }
}
