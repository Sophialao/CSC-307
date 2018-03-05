package controller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.Employee;
import model.HourlyEmployee;
import model.SalaryEmployee;
import model.*;
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
        System.out.println("'l': add loan");
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
            return editEmployee(Constants.HOURLY);
        }
        else if (action.equals("s")) {
            return editEmployee(Constants.SALARIED);
        }
        else if (action.equals("l")){
            return setLoan();
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

        if (emplType.equals(Constants.HOURLY)) {

            System.out.println("Name: ");
            String name = reader.nextLine();
            System.out.println("address: ");
            String address = reader.nextLine();
            System.out.println("ssn: ");
            int ssn = reader.nextInt();
            System.out.println("rate: ");
            double rate = reader.nextDouble();
            HourlyEmployee h = new HourlyEmployee(name, address, ssn, rate, "M");
            h.write();
            System.out.println("Employee created successfully!");
            return true;
        }

        else {
            System.out.println("Name: ");
            String name = reader.nextLine();
            System.out.println("address: ");
            String address = reader.nextLine();
            System.out.println("ssn: ");
            int ssn = reader.nextInt();
            System.out.println("salary");
            double salary = reader.nextInt();
            System.out.println("commission");
            double commission = reader.nextDouble();
            System.out.println("sales");
            double sales = reader.nextDouble();
            SalaryEmployee s = new SalaryEmployee(name, address, ssn, salary, commission, sales, "M");
            s.write();
            System.out.println("Employee created successfully!");
            return true;
        }
    }

    public static boolean editEmployee(String emplType){
        Scanner reader = new Scanner(System.in);
        System.out.println("ID: ");
        String eid = reader.nextLine();
        if(emplType.equals(Constants.HOURLY)){
            System.out.println("which field:\nname\naddress\nssn\nrate");
            String choice = reader.nextLine();
            if(choice.equals("name")){
                System.out.println("Enter name:");
                String newName = reader.nextLine();
                HourlyEmployee he =  HourlyEmployee.getInstance(eid);
                he.setName(newName);
                he.write();
            }
            else if(choice.equals("address")){
                System.out.println("Enter address:");
                String newAddress = reader.nextLine();
                HourlyEmployee he =  HourlyEmployee.getInstance(eid);
                he.setAddress(newAddress);
                he.write();
            }
            else if(choice.equals("ssn")){
                System.out.println("Enter ssn:");
                int newSsn = reader.nextInt();
                HourlyEmployee he =  HourlyEmployee.getInstance(eid);
                he.setSsn(newSsn);
                he.write();
            }
            else if(choice.equals("rate")){
                System.out.println("Enter rate:");
                double newRate = reader.nextDouble();
                HourlyEmployee he =  HourlyEmployee.getInstance(eid);
                he.setRate(newRate);
                he.write();
            }
            else{
                return false;
            }
        }
        else{
            System.out.println("which field:\nname\naddress\nssn\nsalary\ncommission\nsales");
            String choice = reader.nextLine();
            if(choice.equals("name")){
                System.out.println("Enter name:");
                String newName = reader.nextLine();
                SalaryEmployee se =  SalaryEmployee.getInstance(eid);
                se.setName(newName);
                se.write();
            }
            else if(choice.equals("address")){
                System.out.println("Enter address:");
                String newAddress = reader.nextLine();
                SalaryEmployee se =  SalaryEmployee.getInstance(eid);
                se.setAddress(newAddress);
                se.write();
            }
            else if(choice.equals("ssn")){
                System.out.println("Enter ssn:");
                int newSsn = reader.nextInt();
                SalaryEmployee se =  SalaryEmployee.getInstance(eid);
                se.setSsn(newSsn);
                se.write();
            }
            else if(choice.equals("salary")){
                System.out.println("Enter ssn:");
                int newSalary = reader.nextInt();
                SalaryEmployee se =  SalaryEmployee.getInstance(eid);
                se.setSalary(newSalary);
                se.write();
            }
            else if(choice.equals("commission")){
                System.out.println("Enter commission:");
                double newCommission = reader.nextDouble();
                SalaryEmployee se =  SalaryEmployee.getInstance(eid);
                se.setCommission(newCommission);
                se.write();
            }
            else if(choice.equals("sales")){
                System.out.println("Enter sales:");
                double newSales = reader.nextDouble();
                SalaryEmployee se =  SalaryEmployee.getInstance(eid);
                se.setSales(newSales);
                se.write();
            }
            else{
                return false;
            }
        }


        return true;
    }

    public static boolean deleteEmployee(){
        Scanner reader = new Scanner(System.in);
        System.out.println("ID: ");
        String eid = reader.nextLine();
        System.out.println("eid: " + eid);
        SalaryEmployee.getInstance(eid).remove();
        HourlyEmployee.getInstance(eid).remove();
        return true;
    }


    public static boolean createTimecard() {
        Scanner reader = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        System.out.println("Employee ID: ");
        String eid = reader.nextLine();
        System.out.println("Time In (YYYY-MM-DD HH:MM 24hr): ");
        String dateTimeIn = reader.nextLine();
        System.out.println(dateTimeIn);
        System.out.println("Time Out (YYYY-MM-DD HH:MM 24hr): ");
        String dateTimeOut = reader.nextLine();

        try {
            Date timeIn = df.parse(dateTimeIn);
            Date timeOut = df.parse(dateTimeOut);
            System.out.println(timeIn);
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

    public static boolean setLoan(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Employee ID: ");
        String eid = reader.nextLine();
        System.out.println("Amount: ");
        double amt = Double.parseDouble(reader.nextLine());
        System.out.println("Interest Rate: ");
        double interest = Double.parseDouble(reader.nextLine());
        System.out.println("Duration (months): ");
        int dur = Integer.parseInt(reader.nextLine());
        Loan l = new Loan(eid, amt, interest, dur);
        l.write();
        System.out.println("Set up loan successfully!");
        return true;
    }

    public static boolean returnError() {
        System.out.println("that's not a fucking option");
        return false;
    }

}
