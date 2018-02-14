
package model;

public abstract class Employee {
    private String name;
    private String address;
    private int ssn;
    private int id;
    private String method;

    public Employee(String name, String address, int ssn, int id, String method) {
        this.name = name;
        this.address = address;
        this.ssn = ssn;
        this.id = id;
        this.method = method;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}