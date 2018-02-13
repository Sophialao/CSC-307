package model;

public class Timecard {
    public final int id;
    public final int eid;
    public String timeIn; // YYYY-MM-DD HH:MM 24-hour (military time)
    public String timeOut;

    public Timecard (int id, int eid, String timeIn, String timeOut) {
        this.id = id;
        this.eid = eid;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public int getId() {
        return this.id;
    }

    public String getTimeIn() {
        return this.timeIn;
    }

    public String getTimeOut() {
        return this.timeOut;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public void printTimecard() {
        System.out.println("Time in: " + this.timeIn + "  Time out: " + this.timeOut);
    }
}
