package model;

import java.util.UUID;

public abstract class Employee implements DbWritable {
    private String name;
    private String address;
    private int ssn;
    private String id;
    private String gender;

    public Employee(String name, String address, int ssn, String gender) {
        this.name = name;
        this.address = address;
        this.ssn = ssn;
        this.gender = gender;
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

    public void readFields(String[] args) {
        if (args.length == 1){
            return;
        }
        this.id = args[0];
        this.name = args[1];
        this.address = args[2];
        this.ssn = Integer.parseInt(args[3]);
        this.gender = args[4];
    }

    public abstract void write();


}