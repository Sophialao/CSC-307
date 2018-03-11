package model;

import util.Constants;
import util.DbUtils;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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
            return (Timecard) DbUtils.getObject(Timecard.class, id, Constants.TIMECARD_DB);
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = DbUtils.getAll(Timecard.class, Constants.TIMECARD_DB);
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

    public void readFields(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setEId(res.getString("employeeId"));
        this.setTimeIn(res.getDate("timeIn"));
        this.setTimeOut(res.getDate("timeOut"));

    }

    public void update() {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd kk:mm:00");
        String timeInFormat = df.format(this.getTimeIn());
        String timeOutFormat = df.format(this.getTimeOut());

        String stmt = "UPDATE " + Constants.TIMECARD_DB + " SET ";
        stmt += "timeIn = '" + timeInFormat + "', ";
        stmt += "timeOut = '" + timeOutFormat + "', ";
        stmt += "employeeId = '" + this.getEId() + "'";
        stmt += " WHERE id = '" + this.getId() +"';";
        DbUtils.insertOrDelete(stmt);
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
        DbUtils.insertOrDelete(stmt);
    }

    public void remove() {
        String stmt = "DELETE FROM " + Constants.TIMECARD_DB + " WHERE id = '" + this.getId() + "';";
        DbUtils.insertOrDelete(stmt);
    }


}
