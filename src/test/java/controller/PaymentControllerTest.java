package controller;
import model.*;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentControllerTest {
    Date d = new Date();
    int currDate = d.getDate();
    int currMonth = d.getMonth();

   @Test
    public void testHourlyEmpOneTimecardPayment() {
        EmployeeController ec = new EmployeeController();
        TimecardController tc = new TimecardController();
        HourlyEmployee kelly = (HourlyEmployee) ec.addHourlyEmployee("Kelly", "SLO", 1234, 10.0);
        Date dIn = new Date(2018, currMonth, currDate, 9, 0);
        Date dOut = new Date(2018, currMonth, currDate, 17, 0);
        tc.addTimecard(kelly.getId(), dIn, dOut);
        PaymentController pc = new PaymentController(true, true);
        pc.calculatePayment();
        Map<String, DbWritable> payments = Payment.getAll();
        double pay = 0.0;
        for (String key : payments.keySet()){
            Payment p = (Payment)payments.get(key);
            if (p.getEmployeeId().equals(kelly.getId())){
                pay = p.getAmount();
                break;
            }
        }
        assertEquals(76.8, pay);
    }

    @Test
    public void testHourlyEmpMultTimecardPayment(){
        EmployeeController ec = new EmployeeController();
        TimecardController tc = new TimecardController();
        HourlyEmployee kelly = (HourlyEmployee) ec.addHourlyEmployee("Kelly", "SLO", 1234, 10.0);
        Date dIn = new Date(2018, currMonth, currDate, 9, 0);
        Date dOut = new Date(2018, currMonth, currDate, 17, 0);
        Date dIn2 = new Date(2018, currMonth, currDate, 9, 0);
        Date dOut2 = new Date(2018, currMonth, currDate, 17, 0);
        tc.addTimecard(kelly.getId(), dIn, dOut);
        tc.addTimecard(kelly.getId(), dIn2, dOut2);
        PaymentController pc = new PaymentController(true, true);
        pc.calculatePayment();
        Map<String, DbWritable> payments = Payment.getAll();
        double pay = 0.0;
        for (String key : payments.keySet()){
            Payment p = (Payment)payments.get(key);
            if (p.getEmployeeId().equals(kelly.getId())){
                pay = p.getAmount();
                break;
            }
        }
        assertEquals(153.6, pay);
    }

    @Test
    public void testHourlyEmpMultTimecardOvertimePayment(){
        EmployeeController ec = new EmployeeController();
        TimecardController tc = new TimecardController();
        HourlyEmployee kelly = (HourlyEmployee) ec.addHourlyEmployee("Kelly", "SLO", 1234, 10.0);
        for (int i = 0; i < 10; i++){
            Date dIn = new Date(2018, currMonth, currDate, 9, 0);
            Date dOut = new Date(2018, currMonth, currDate, 17, 0);
            tc.addTimecard(kelly.getId(), dIn, dOut);
        }
        PaymentController pc = new PaymentController(true, true);
        pc.calculatePayment();
        Map<String, DbWritable> payments = Payment.getAll();
        double pay = 0.0;
        for (String key : payments.keySet()){
            Payment p = (Payment)payments.get(key);
            if (p.getEmployeeId().equals(kelly.getId())){
                pay = p.getAmount();
                break;
            }
        }
        assertEquals(960.0, pay);
    }

    @Test
    public void testHourlyEmpMultTimecardLoanPayment(){
        EmployeeController ec = new EmployeeController();
        TimecardController tc = new TimecardController();
        HourlyEmployee kelly = (HourlyEmployee) ec.addHourlyEmployee("Kelly", "SLO", 1234, 10.0);

        Date dIn = new Date(2018, currMonth, currDate, 9, 0);
        Date dOut = new Date(2018, currMonth, currDate, 17, 0);
        Date dIn2 = new Date(2018, currMonth, currDate, 9, 0);
        Date dOut2 = new Date(2018, currMonth, currDate, 17, 0);
        tc.addTimecard(kelly.getId(), dIn, dOut);
        tc.addTimecard(kelly.getId(), dIn2, dOut2);

        Loan l = new Loan(kelly.getId(), 12000, .01, 12);
        l.write();

        PaymentController pc = new PaymentController(true, true);
        pc.calculatePayment();
        Map<String, DbWritable> payments = Payment.getAll();
        double pay = 0.0;
        for (String key : payments.keySet()){
            Payment p = (Payment)payments.get(key);
            if (p.getEmployeeId().equals(kelly.getId())){
                pay = p.getAmount();
                break;
            }
        }
        assertEquals(-856.4, pay);
    }

    @Test
    public void testSalaryEmpPayment(){
        EmployeeController ec = new EmployeeController();
        SalaryEmployee kelly = (SalaryEmployee) ec.addSalaryEmployee("Kelly", "SLO", 1234, 100000.0, .1, 0.0);

        PaymentController pc = new PaymentController(true, true);
        pc.calculatePayment();
        Map<String, DbWritable> payments = Payment.getAll();
        double pay = 0.0;
        for (String key : payments.keySet()){
            Payment p = (Payment)payments.get(key);
            if (p.getEmployeeId().equals(kelly.getId())){
                pay = p.getAmount();
                break;
            }
        }
        assertEquals(7558.33, pay, .01);
    }

    @Test
    public void testSalaryEmpCommissionPayment(){
        EmployeeController ec = new EmployeeController();
        SalaryEmployee kelly = (SalaryEmployee) ec.addSalaryEmployee("Kelly", "SLO", 1234, 100000.0, .1, 100.0);

        PaymentController pc = new PaymentController(true, true);
        pc.calculatePayment();
        Map<String, DbWritable> payments = Payment.getAll();
        double pay = 0.0;
        for (String key : payments.keySet()){
            Payment p = (Payment)payments.get(key);
            if (p.getEmployeeId().equals(kelly.getId())){
                pay = p.getAmount();
                break;
            }
        }
        assertEquals(7567.4, pay, .01);
    }

    @Test
    public void testSalaryEmpCommissionLoanPayment(){
        EmployeeController ec = new EmployeeController();
        SalaryEmployee kelly = (SalaryEmployee) ec.addSalaryEmployee("Kelly", "SLO", 1234, 100000.0, .1, 100.0);

        Loan l = new Loan(kelly.getId(), 12000, .01, 12);
        l.write();

        PaymentController pc = new PaymentController(true, true);
        pc.calculatePayment();
        Map<String, DbWritable> payments = Payment.getAll();
        double pay = 0.0;
        for (String key : payments.keySet()){
            Payment p = (Payment)payments.get(key);
            if (p.getEmployeeId().equals(kelly.getId())){
                pay = p.getAmount();
                break;
            }
        }
        assertEquals(6557.4, pay, .01);
    }

    @Test
    public void testHourlyAndSalaryEmp(){
        //Hourly
        EmployeeController ec = new EmployeeController();
        TimecardController tc = new TimecardController();
        HourlyEmployee kelly = (HourlyEmployee) ec.addHourlyEmployee("Kelly", "SLO", 1234, 10.0);
        Date dIn = new Date(2018, currMonth, currDate, 9, 0);
        Date dOut = new Date(2018, currMonth, currDate, 17, 0);
        tc.addTimecard(kelly.getId(), dIn, dOut);
        PaymentController pc = new PaymentController(true, true);
        pc.calculatePayment();
        Map<String, DbWritable> payments = Payment.getAll();
        double pay1 = 0.0;
        for (String key : payments.keySet()){
            Payment p = (Payment)payments.get(key);
            if (p.getEmployeeId().equals(kelly.getId())){
                pay1 = p.getAmount();
                break;
            }
        }

        //Salary
        SalaryEmployee kelly2 = (SalaryEmployee) ec.addSalaryEmployee("Kelly", "SLO", 1234, 100000.0, .1, 0.0);
        PaymentController pc2 = new PaymentController(true, true);
        pc2.calculatePayment();
        Map<String, DbWritable> payments2 = Payment.getAll();
        double pay2 = 0.0;
        for (String key : payments2.keySet()){
            Payment p2 = (Payment)payments2.get(key);
            if (p2.getEmployeeId().equals(kelly2.getId())){
                pay2 = p2.getAmount();
                break;
            }
        }

        assertEquals(76.8, pay1);
        assertEquals(7558.33, pay2, .01);
    }

}