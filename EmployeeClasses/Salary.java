public class Salary extends Employee {
    private double salary;
    private double commission;
    private double sales;
    // Annual salary

    public Salary(String name, String address, int number, int id, String method, double salary,double commission, double sales) {
        super(name, address, number, id, method);
        setSalary(salary);
    }

    public void mailCheck() {
        System.out.println("Within mailCheck of Salary class ");
        System.out.println("Mailing check to " + getName() + " with salary " + computePay());
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double newSalary) {
        if(newSalary >= 0.0) {
            salary = newSalary;
        }
    }
    public double getCommission(){
        return commission;
    }
    public void setCommission(double newCommission){
        commission = newCommission;
    }

    public void setSales(double newSales){
        sales = newSales;
    }

    public double getSales(){
        return sales;
    }

    public double computePay() {
        System.out.println("Computing salary pay for " + getName());
        return salary/52 + sales * commission;
    }
}