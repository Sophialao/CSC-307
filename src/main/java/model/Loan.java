
public class Loan
{
    private int id;
    private double totalAmountLoaned;
    private double interest;
    private double amountPaid;
    

    public PartTimeEmployee(double totalAmountLoaned,double interest)
    {
	this.totalAmountLoaned = totalAmountLoaned;
        this.interest = interest;
    }
    public double getTotalAmountLoaned() {return totalAmountLoaned;}
    public double getInterest() {return interest;}
}
