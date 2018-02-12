
public class Payment
{
    private int id;
    private int employeeId;
    private int amount;
    private Date payDay;

    public Payment(Employee employeeId,int amount)
    {
        this.employeeId = employeeId;
	this.amount = amount;
    }

    public int getEmployee() {return employeeId;}
    public double getAmount() {return Amount;}

}
