package controller;
import model.*;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class IntegrationTest {
    Date d = new Date();
    int currDate = d.getDate();
    int currMonth = d.getMonth();

    @Test
    public void testHourlyMultTimecardsLoanAndSalaryCommissionLoan(){
        EmployeeController ec = new EmployeeController();
        TimecardController tc = new TimecardController();

        // Add hourly employee
        HourlyEmployee kelly = (HourlyEmployee) ec.addHourlyEmployee(null,"Kelly", "SLO", 1234, 10.0, "F", 6);

        // Check gets back same employee
        String kelly_id = kelly.getId();
        HourlyEmployee k = HourlyEmployee.getInstance(kelly_id);
        assertThat(k).isEqualToComparingFieldByField(kelly);

        // Edit employee
        k.setAddress("SAC");
        ec.addHourlyEmployee(k.getId(), k.getName(), "SAC", k.getSsn(), k.getRate(), k.getGender(), k.getSickDays());

        // Check gets back same employee with updated info
        HourlyEmployee k2 = HourlyEmployee.getInstance(kelly_id);
        assertEquals(k2.getAddress(), "SAC");

        // Adds timecard with employee id
        Date dIn = new Date(2018, currMonth, currDate, 9, 0);
        Date dOut = new Date(2018, currMonth, currDate, 17, 0);
        Date dIn2 = new Date(2018, currMonth, currDate, 9, 0);
        Date dOut2 = new Date(2018, currMonth, currDate, 17, 0);
        Timecard t1 = tc.addTimecard(kelly.getId(), dIn, dOut);
        Timecard t2 = tc.addTimecard(kelly.getId(), dIn2, dOut2);

        // Checks gets back right timecards
        Timecard t3 = Timecard.getInstance(t1.getId());
        Timecard t4 = Timecard.getInstance(t2.getId());
        assertThat(t1).isEqualToComparingFieldByField(t3);
        assertThat(t2).isEqualToComparingFieldByField(t4);

        // Adds loan with employee id
        Loan l = new Loan(kelly.getId(), 12000, .01, 12);
        l.write();

        // Checks gets back same loan
        Loan l3 = Loan.getInstance(l.getId());
        assertThat(l).isEqualToComparingFieldByField(l3);

        // Calculates payment using all this info, checks if payment was correct
        PaymentController pc = new PaymentController(true, true);
        pc.calculatePayment();
        Payment p = null;
        Map<String, DbWritable> payments = Payment.getAll();
        double pay1 = 0.0;
        for (String key : payments.keySet()){
            p = (Payment)payments.get(key);
            if (p.getEmployeeId().equals(kelly.getId())){
                pay1 = p.getAmount();
                break;
            }
        }
        assertEquals(-856.4, pay1);

        // Checks gets right payment back
        Payment p2 = Payment.getInstance(p.getId());
        assertThat(p).isEqualToComparingFieldByField(p2);


        // Add salary employee
        SalaryEmployee kelly2 = (SalaryEmployee) ec.addSalaryEmployee(null,"Kelly", "SLO", 1234, 100000.0, .1, 100.0, "F", 6);

        // Checks gets back same employee
        String kelly_id2 = kelly2.getId();
        SalaryEmployee k3 = SalaryEmployee.getInstance(kelly_id2);
        assertThat(kelly2).isEqualToComparingFieldByField(k3);

        // Edit employee
        k3.setAddress("SAC");
        ec.addSalaryEmployee(k3.getId(), k3.getName(), "SAC", k3.getSsn(), k3.getSalary(), k3.getCommission(), k3.getSales(), k3.getGender(), k3.getSickDays());

        // Check gets back same employee with updated info
        SalaryEmployee k4 = SalaryEmployee.getInstance(kelly_id2);
        assertEquals(k4.getAddress(), "SAC");

        // Adds loan with employee id
        Loan l2 = new Loan(kelly2.getId(), 12000, .01, 12);
        l2.write();

        // Check gets back same loan
        Loan l4 = Loan.getInstance(l2.getId());
        assertThat(l2).isEqualToComparingFieldByField(l4);

        // Calculates payment with all this info, checks if payment is correct
        pc.calculatePayment();
        Payment p3 = null;
        Map<String, DbWritable> payments2 = Payment.getAll();
        double pay2 = 0.0;
        for (String key : payments2.keySet()){
            p3 = (Payment)payments2.get(key);
            if (p3.getEmployeeId().equals(kelly2.getId())){
                pay2 = p3.getAmount();
                break;
            }
        }
        assertEquals(6557.4, pay2, .01);

        // Checks gets right payment back
        Payment p4 = Payment.getInstance(p3.getId());
        assertThat(p3).isEqualToComparingFieldByField(p4);
    }

}