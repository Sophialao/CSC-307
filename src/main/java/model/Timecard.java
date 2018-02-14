package model;

import util.Constants;
import util.Utils;

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
            String[] db = Utils.readLine(Constants.TIMECARD_DB, id);
            if (db != null) {
                Timecard time = new Timecard();
                time.readFields(db);
                time.setId(id);
                return time;
            }
            return new Timecard();
        }
    }

    public static Map<String, DbWritable> getAll() {
        Map<String, DbWritable> db = Utils.parseFile(Constants.TIMECARD_DB, Timecard.class);

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
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd kk:mm");
        return this.eId + " " + df.format(timeIn) + " " + df.format(timeOut);
    }

    public void printTimecard() {
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd kk:mm");
        System.out.println("Time in: " + df.format(timeIn) + "  Time out: " + df.format(timeOut));
    }

    public void readFields(String[] line) {
        this.id = line[0];
        this.eId = line[1];
        this.timeIn = new Date(line[2]);
        this.timeOut = new Date(line[3]);
    }

    public void write() {
        Utils.removeLine(Constants.TIMECARD_DB, this.id);
        String toWrite = this.id + "," + this.eId + "," + this.timeIn.toString() + "," + this.timeOut.toString();
        Utils.appendLine(Constants.TIMECARD_DB, toWrite);
    }


}
