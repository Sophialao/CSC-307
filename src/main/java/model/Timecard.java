package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timecard {
    public final int id;
    public final int eid;
    public Date timeIn; // YYYY-MM-DD HH:MM 24-hour (military time)
    public Date timeOut;

    public Timecard (int id, int eid, Date timeIn, Date timeOut) {
        this.id = id;
        this.eid = eid;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public int getId() {
        return this.id;
    }

    public Date getTimeIn() {
        return this.timeIn;
    }

    public Date getTimeOut() {
        return this.timeOut;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public String timecardToString() {
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd kk:mm");
        return Integer.toString(eid) + " " + df.format(timeIn) + " " + df.format(timeOut);
    }

    public void printTimecard() {
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd kk:mm");
        System.out.println("Time in: " + df.format(timeIn) + "  Time out: " + df.format(timeOut));
    }
}
