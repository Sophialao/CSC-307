package controller;
import java.text.DateFormat;
import java.text.ParseException;
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
        System.out.println("'h': edit hourly employee");
        System.out.println("'s': edit salary employee");
        System.out.println("'i': input hours");
        System.out.println("'ph': pay hourly employees");
        System.out.println("'ps': pay salary employees");
        System.out.println("'q': quit");
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
        else if (action.equals("q")) {
            return false;
        }
        else if (action.equals("ph")){
            return pay("h");
        }
        else if (action.equals("ps")) {
            return pay("s");
        }
        else if (action.equals("d")) {
            return deleteEmployee();
        }
        else if (action.equals("h")) {
            return setEmployeeData(Constants.HOURLY);
        }
        else if (action.equals("s")) {
            return setEmployeeData(Constants.SALARIED);
        }
        else {
            return returnError();
        }
    }

    public static boolean createEmployee(){
        boolean result = true;
        Scanner reader = new Scanner(System.in);
        System.out.println("Hourly? (y/n)");
        String action = reader.nextLine();

        if (action.equals("y")) {
            System.out.println("-- Creating Hourly Employee --");
            setEmployeeData(Constants.HOURLY);
            return result;
        }
        else if(action.equals("n")) {
            System.out.println("-- Creating Salary Employee --");
            setEmployeeData(Constants.SALARIED);
            return result;
        }

        else {
            return returnError();
        }
    }

    public static boolean setEmployeeData(String emplType) {
        Scanner reader = new Scanner(System.in);
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

        else {
            System.out.println("Salary: ");
            double salary = reader.nextDouble();
            System.out.println("Commission: ");
            double commission = reader.nextDouble();
            System.out.println("Sales: ");
            double sales = reader.nextDouble();
            EmployeeController ec = new EmployeeController();
            ec.addSalaryEmployee(name, address, ssn, salary, commission, sales);
            System.out.println("Employee created successfully!");
            return true;
        }
    }

    public static boolean deleteEmployee(){
        Scanner reader = new Scanner(System.in);
        System.out.println("ID: ");
        String eid = reader.nextLine();
        System.out.println("eid: " + eid);
        SalaryEmployee.getInstance(eid).remove();
        //HourlyEmployee.getInstance(eid).remove();
        return true;
    }

    public static boolean createTimecard() {
        Scanner reader = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd kk:mm");
        System.out.println("Employee ID: ");
        String eid = reader.nextLine();
        System.out.println("Time In (YYYY-MM-DD HH:MM 24hr): ");
        String dateTimeIn = reader.nextLine();
        System.out.println("Time Out (YYYY-MM-DD HH:MM 24hr): ");
        String dateTimeOut = reader.nextLine();

        try {
            Date timeIn = df.parse(dateTimeIn);
            Date timeOut = df.parse(dateTimeOut);
            TimecardController tc = new TimecardController();
            tc.addTimecard(eid, timeIn, timeOut);
            System.out.println("Timecard created successfully!");
            return true;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return returnError();
        }
    }

    public static boolean pay(String s) {

        if (s == "m"){
            PaymentController pc = new PaymentController(true, false);
            pc.calculatePayment();
        }
        else{
            PaymentController pc = new PaymentController(false, true);
            pc.calculatePayment();
        }

        System.out.println("Paid successfully!");
        return true;

    }

    public static boolean returnError() {
        System.out.println("that's not a fucking option");
        return false;
    }

}
