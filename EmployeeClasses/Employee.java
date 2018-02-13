public abstract class Employee {
    private String name;
    private String address;
    private int ssn;
    private int id;
    private String method;



    public Employee(String name, String address, int ssn, int id, String method) {
        System.out.println("Constructing an Employee");
        this.name = name;
        this.address = address;
        this.ssn = ssn;
        this.id = id;
        this.method = method;
    }

    public double computePay() {
        System.out.println("Inside Employee computePay");
        return 0.0;
    }

    public void mailCheck() {
        System.out.println("Mailing a check to " + this.name + " " + this.address);
    }

    public String toString() {
        return name + " " + address + " " + ssn;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String newAddress) {
        address = newAddress;
    }

    public int getSsn() {
        return ssn;
    }

    public int getId(){ return id;}

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}