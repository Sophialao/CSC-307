package controller;
import java.util.Scanner;

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
            System.out.println("hi");
            createEmployee();
            return false;
        }
        return false;
    }

    public static void createEmployee() {

        Scanner reader = new Scanner(System.in);
        System.out.println("Hourly? (y/n)");
        String action = reader.nextLine();

        if (action.equals("y")) {
            System.out.println("-- Creating Hourly Employee --");
            System.out.println("Name: ");
            String name = reader.nextLine();
            System.out.println("Address: " );
            String address = reader.nextLine();
            System.out.println("SSN: " );
            int ssn = Integer.parseInt(reader.nextLine());
            System.out.println("name: " + name + " address: " + address + " ssn: " + ssn);
            return;
        }
        else if (action.equals("n")) {
            System.out.println("-- Creating Salaried Employee --");
            System.out.println("Commissioned? (y/n)");
            String commissionConfirm = reader.nextLine();

            if (commissionConfirm.equals(("y"))) {

            }
        }
    }
}
