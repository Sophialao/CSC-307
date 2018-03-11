package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public abstract class Employee implements DbWritable {
    private String name;
    private String address;
    private int ssn;
    private String id;
    private String gender;
    private int sickDays;

    public Employee(String name, String address, int ssn, String gender, int sickDays) {
        this.name = name;
        this.address = address;
        this.ssn = ssn;
        this.gender = gender;
        this.sickDays = sickDays;
        this.id = UUID.randomUUID().toString();
    }
    public Employee () {
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public int getSickDays() {
        return sickDays;
    }

    public void setSickDays(int sickDays) {
        this.sickDays = sickDays;
    }

    public void readFields(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setName(res.getString("name"));
        this.setAddress(res.getString("address"));
        this.setSsn(res.getInt("ssn"));
        this.setGender(res.getString("gender"));
        this.setSickDays(res.getInt("sickDays"));
    }

    public abstract void write();


}