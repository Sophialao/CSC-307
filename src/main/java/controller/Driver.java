package controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        else {
            return returnError();
        }
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

        if (emplType.equals(Constants.HOURLY)) {
            System.out.println("Wage: ");
            double rate = reader.nextDouble();
            EmployeeController ec = new EmployeeController();
            ec.addHourlyEmployee(name, address, ssn, rate);
            System.out.println("Employee created successfully!");
            return true;
        }

        else if (emplType.equals(Constants.SALARIED)) {
            System.out.println("Salary: ");
            double salary = reader.nextDouble();
            EmployeeController ec = new EmployeeController();
            ec.addSalaryEmployee(name, address, ssn, salary, 0.0,0.0);
            System.out.println("Employee created successfully!");
            return true;
        }

        else {
            System.out.println("Salary: ");
            double salary = reader.nextDouble();
            System.out.println("Commission Rate: ");
            double rate = reader.nextDouble();
            EmployeeController ec = new EmployeeController();
            ec.addSalaryEmployee(name, address, ssn, salary, rate,0.0);
            System.out.println("Employee created successfully!");
            return true;
        }
        return false;
    }

    public static boolean createTimecard() {
        Scanner reader = new Scanner();
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd kk:mm");
        System.out.println("Employee ID: ");
        String eid = reader.nextLine();
        System.out.println("Time In (YYYY-MM-DD HH:MM 24hr): ");
        String datetime = reader.nextLine();
        Date timeIn = df.parse(datetime);
        System.out.println("Time Out (YYYY-MM-DD HH:MM 24hr): ");
        String datetime = reader.nextLine();
        Date timeOut = df.parse(datetime);
        TimecardController tc = new TimecardController();
        tc.addTimecard(eid, timeIn, timeOut);
        System.out.println("Timecard created successfully!");
        return true;
    }

    public static boolean returnError() {
        System.out.println("that's not a fucking option");
        return false;
    }

}
