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

public class Timecard implements DbWritable {
    private String id;
    private String eId;
    private Date timeIn; // YYYY-MM-DD HH:MM 24-hour (military time)
    private Date timeOut;

    public Timecard() {
        this.id = UUID.randomUUID().toString();
    }

    public Timecard(String eId, Date timeIn, Date timeOut) {
        this.eId = eId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.id = UUID.randomUUID().toString();
    }

    public static Timecard getInstance(String id) {
        if (id == null)
            return new Timecard();
        else {
            return (Timecard) DbSqlite.getObject(Timecard.class, id, Constants.TIMECARD_DB);
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = DbSqlite.getAll(Timecard.class, Constants.TIMECARD_DB);
        return db;
    }

    public String getId() {
        return this.id;
    }

    public String getEId(){
        return this.eId;
    }
    
    private void setId(String id) { this.id = id;}

    public Date getTimeIn() {
        return this.timeIn;
    }

    public Date getTimeOut() {
        return this.timeOut;
    }

    public void setEId(String eid) { this.eId = eid; }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public String timecardToString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        return this.eId + " " + df.format(timeIn) + " " + df.format(timeOut);
    }

    public void printTimecard() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        System.out.println("Time in: " + df.format(timeIn) + "  Time out: " + df.format(timeOut));
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
        this.setEId(res.getString("employeeId"));
        this.setTimeIn(parseDateString(res.getString("timeIn")));
        this.setTimeOut(parseDateString(res.getString("timeOut")));
        //this.setTimeIn(res.getDate("timeIn"));
        //this.setTimeOut(res.getDate("timeOut"));

    }

    public void update() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:00");
        String timeInFormat = df.format(this.getTimeIn());
        String timeOutFormat = df.format(this.getTimeOut());

        String stmt = "UPDATE " + Constants.TIMECARD_DB + " SET ";
        stmt += "timeIn = '" + timeInFormat + "', ";
        stmt += "timeOut = '" + timeOutFormat + "', ";
        stmt += "employeeId = '" + this.getEId() + "'";
        stmt += " WHERE id = '" + this.getId() +"';";
        DbSqlite.insertOrDelete(stmt);
    }

    public void write() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:00");
        String timeInFormat = df.format(this.getTimeIn());
        String timeOutFormat = df.format(this.getTimeOut());

        String stmt = "INSERT INTO " + Constants.TIMECARD_DB + "(id, timeIn, timeOut, employeeId) VALUES (";
        stmt += "'" + this.getId() + "', " +
                "'" + timeInFormat + "', " +
                "'" + timeOutFormat + "', '" +
                this.getEId() + "');";
        DbSqlite.insertOrDelete(stmt);
    }

    public void remove() {
        String stmt = "DELETE FROM " + Constants.TIMECARD_DB + " WHERE id = '" + this.getId() + "';";
        DbSqlite.insertOrDelete(stmt);
    }


}
