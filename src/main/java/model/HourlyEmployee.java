package model;

public class HourlyEmployee extends Employee {
    private double salary;   // Annual salary

    public HourlyEmployee(String name, String address, int ssn, int id, String method, double salary) {
        super(name, address, ssn, id, method);
        setSalary(salary);
    }

    public void mailCheck() {
        System.out.println("Within mailCheck of Salary class ");
        System.out.println("Mailing check to " + getName() + " with salary " + salary);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double newSalary) {
        if(newSalary >= 0.0) {
            salary = newSalary;
        }
    }

    public double computePay() {
        System.out.println("Computing salary pay for " + getName());
        return salary/52;
    }
}