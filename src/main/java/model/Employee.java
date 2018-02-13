abstract class Employee
{
    private String name;
    private Date DOB;
    private double curPay; //for current pay period

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public double getCurPay() {
        return curPay;
    }

    public void setCurPay(double curPay) {
        this.curPay = curPay;
    }

    public abstract void addEmplyee();
    public void deleteEmployee();
    public void updateEmployee();
    public Employee getEmployee();
}