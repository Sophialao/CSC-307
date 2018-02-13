package model;

import java.util.Date;

public class Timecard {
    private int id;
    private int employeeId;
    private Date timeIn;
    private Date timeOut;

    public Timecard(int id, int employeeId, Date timeIn, Date timeOut) {
        this.id = id;
        this.employeeId = employeeId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }
}
