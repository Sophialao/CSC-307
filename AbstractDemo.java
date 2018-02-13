public class AbstractDemo {

    public static void main(String [] args) {
        Salary s = new Salary("Mohd Mohtashim", "Ambehta, UP", 3, 3600, "salary", 100000.00,0.10,5000);
        //Salary e = new Salary("John Adams", "Boston, MA", 2, 2400, "hourly", 100000.00);
        Hourly h = new Hourly("Nuclear Program", "Boston, MA", 2, 2400, "hourly", 100000.00);
        System.out.println("Call mailCheck using Salary reference --");
        s.mailCheck();
       // System.out.println("\n Call mailCheck using Employee reference--");
       // e.mailCheck();
        System.out.println("\n Call hourlyCheck using Employee reference--");
        h.mailCheck();

    }
}