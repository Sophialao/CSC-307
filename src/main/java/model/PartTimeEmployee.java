

public class PartTimeEmpolyee implments Employee
{
    private int id;
    private String name;
    private Date DOB;
    private double hoursWorked;
    private double salesCur; //amount of sales made for this pay hour
    private double payPerHour;
    private int sickDays;
    private Payments[] payments;
    

    public PartTimeEmployee(String name,Date DOB,Double payPerHours)
    {
	this.name = name;
	this.DOB = DOB;
	this.payPerHour = payPerHour;
	

    }
    
    public double getcurPay() {return curPay;}
    public String getName() {return name;}
    public Date getDOB() {return DOB;}
    public double getpayPerHour() {return getPayPerHour;}

}