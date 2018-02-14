package controller;
import java.util.Scanner;

import model.HourlyEmployee;
import model.SalaryEmployee;
import util.Constants;

public class Driver {

    public static void main (String[] args) {
        boolean stillFuckingGoing = true;
        while (stillFuckingGoing) {
            String action = promptUserAction();
            stillFuckingGoing = parseAction(action);
        }

        return;
    }

    public static String promptUserAction() {
        Scanner reader = new Scanner(System.in);
        System.out.println("'a': add employee");
        System.out.println("'d': delete employee");
        System.out.println("'e': edit employee");
        System.out.println("'i': input hours");
        System.out.println("Action: ");
        String action = reader.nextLine();
        return action;
    }

    public static boolean parseAction(String action) {
        if (action.equals("a")) {
            return createEmployee();
        }
        else if (action.equals("i")) {
            return createTimecard();
        }
        return false;
    }

    public static boolean createEmployee() {
        boolean result = true;
        Scanner reader = new Scanner(System.in);
        System.out.println("Hourly? (y/n)");
        String action = reader.nextLine();

        if (action.equals("y")) {
            System.out.println("-- Creating Hourly Employee --");
            setEmployeeData(Constants.HOURLY);
            return result;
        }
        else if (action.equals("n")) {
            System.out.println("Commissioned? (y/n)");
            String commissionConfirm = reader.nextLine();

            if (commissionConfirm.equals("y")) {
                System.out.println("-- Creating Commissioned Employee --");
                return setEmployeeData(Constants.COMMISSIONED);
            }
            else if (commissionConfirm.equals("n")) {
                System.out.println("-- Creating Salaried Employee --");
                return setEmployeeData(Constants.SALARIED);
            }
            else {
                return returnError();
            }
        }
        else {
            return returnError();
        }
    }

    public static boolean setEmployeeData(String emplType) {
        Scanner reader = new Scanner();
        System.out.println("Name: ");
        String name = reader.nextLine();
        System.out.println("Address: " );
        String address = reader.nextLine();
        System.out.println("SSN: " );
        int ssn = Integer.parseInt(reader.nextLine());
        System.out.println("ID: ");
        int id = Integer.parseInt(reader.nextLine());

        if (emplType.equals(Constants.HOURLY)) {
            System.out.println("Wage: ");
            double rate = reader.nextDouble();
            HourlyEmployee e = HourlyEmployee.getInstance(null);
            e.setRate(rate);
            e.setAddress(address);
            e.setId(id);
            e.setName(name);
            e.setSsn(ssn);
            e.write();
            System.out.println("Employee created successfully!");
            return true;
        }

        else if (emplType.equals(Constants.SALARIED)) {
            System.out.println("Salary: ");
            double salary = reader.nextDouble();
            SalaryEmployee e = SalaryEmployee.getInstance(null);
            e.setName(name);
            e.setAddress(address);
            e.setSsn(ssn);
            e.setId(id);
            e.setSalary(salary);
            e.setCommission(0.0);
            e.setSales(0.0);
            e.write();
            System.out.println("Employee created successfully!");
            return true;
        }

        else {
            System.out.println("Salary: ");
            double salary = reader.nextDouble();
            System.out.println("Commission Rate: ");
            double rate = reader.nextDouble();
            EmployeeController ec = new EmployeeController();
            ec.addSalaryEmployee(name, address, )Employee(name, address, ssn, id, )
            System.out.println("Employee created successfully!");
            return true;
        }
        return false;
    }

    public static boolean createTimecard() {

    }

    public static boolean returnError() {
        System.out.println("that's not a fucking option");
        return false;
    }

}
