package controller;
import java.util.Calendar;
import util.Utils;
import model.Employee;
import model.SalaryEmployee;
import model.HourlyEmployee;
import model.Payment;
import model.Loan;
import model.Timecard;
import model.DbWritable;
import java.util.*;

class PaymentController{
    public static boolean invoke_hourly;
    public static boolean invoke_month;

    public PaymentController(boolean invoke_month, boolean invoke_hourly){
    	this.invoke_month = invoke_month;
    	this.invoke_hourly = invoke_hourly;
	}

	public static void calculatePayment() {
		boolean monthly_payday = checkLastDateofMonth();
		boolean weekly_payday = checkMonday();

        if (invoke_hourly == true){
            newWeeklyPay();
        }
        if (invoke_month == true){
            newMonthlyPay();
        }

		if (!monthly_payday && !weekly_payday){
			return;
		}

        if (invoke_month == false && invoke_hourly == false){
            if (monthly_payday){
                newMonthlyPay();
            }

            if (weekly_payday){
                newWeeklyPay();
            }
        }
	}

	public static boolean checkLastDateofMonth(){
		Calendar cal = Calendar.getInstance();
		int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int currDate = cal.get(Calendar.DAY_OF_MONTH);
		return (lastDate == currDate);
	}

	public static boolean checkMonday(){
		Calendar cal = Calendar.getInstance();
		int currDay = cal.get(Calendar.DAY_OF_WEEK);
		return (currDay == 2);
	}

	public static Date getDate(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}


	public static void newMonthlyPay(){
		Map<String, DbWritable> es = SalaryEmployee.getAll();
		for (String key : es.keySet()){
			SalaryEmployee e = (SalaryEmployee) es.get(key);
			if (key.equals("")){
				continue;
			}
			double before_tax = e.getSalary()/12.0;
			double after_commission = before_tax + calculateCommission(e);
			double after_tax = after_commission - (after_commission*calculateTaxes(before_tax, e));
			double after_loans = after_tax - calculateLoans(e);
			//System.out.println(after_loans);
			Payment p = new Payment(key, after_loans, getDate());
			p.write();
		}
	}

	public static void newWeeklyPay(){
		Map<String, DbWritable> es = HourlyEmployee.getAll();
		for (String key : es.keySet()){
			HourlyEmployee e = (HourlyEmployee) es.get(key);
			double before_tax = calculateTime(e);
			if (before_tax == 0.0){
				continue;
			}
			System.out.println("Original: " + before_tax);
			double after_tax = before_tax - (before_tax*calculateTaxes(before_tax, e));
			System.out.println("After taxes" + after_tax);
			double after_loans = after_tax - calculateLoans(e);
			System.out.println("After loans, final payment" + after_loans);
			Payment p = new Payment(key, after_loans, getDate());
			p.write();
		}
	}

	public static double calculateTime(HourlyEmployee e){
		Map<String, DbWritable> e_timecards = Timecard.getAll();
		double total_timeWorked = 0;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date dateBefore7Days = cal.getTime();
		for (String key : e_timecards.keySet()){
			Timecard e_timecard = (Timecard) e_timecards.get(key);
			if (e_timecard.getEId().equals(e.getId())){
				if (e_timecard.getTimeIn().after(dateBefore7Days)){
					total_timeWorked += Math.abs(e_timecard.getTimeOut().getTime() - e_timecard.getTimeIn().getTime());
				}
			}
		}
		double seconds = total_timeWorked / 1000.0;
		double minutes = seconds / 60.0;
		double hours = minutes / 60.0;

		if (Double.compare(hours, 40.0) > 0){
			double otRate = 1.5 * e.getRate();
			double otHours = hours-40.0;
			return (e.getRate()*40.0) + (otRate*otHours);
		}
		return e.getRate() * hours;
	}

	public static double calculateCommission(SalaryEmployee e){
		return e.getSales() * e.getCommission();
	}

	public static double calculateLoans(Employee e){
		String e_id = e.getId();
		try {
			Map<String, DbWritable> e_loans = Loan.getAll();
		}
		catch (Exception ex){
			return 0.0;
		}

		Map<String, DbWritable> e_loans = Loan.getAll();
		if (e_loans.isEmpty()){
			return 0.0;
		}

		for (String key : e_loans.keySet()) {
			Loan e_loan = (Loan) e_loans.get(key);
			if (e_loan.getEmployeeId() == null){
				continue;
			}
			else if (e_loan.getEmployeeId().equals(e_id)) {
				double interest_decimal = e_loan.getInterestRate();
				int time = e_loan.getDuration();
				double each_month = e_loan.getAmount() / (double) time;
				double each_month_pay = each_month * (1 + interest_decimal);
				e_loan.setAmount(e_loan.getAmount() - each_month);
				e_loan.setDuration(e_loan.getDuration() - 1);
				e_loan.write();
				//System.out.println("LOAN PAY:" + each_month_pay);
				return each_month_pay;
			}
		}
		return 0.0;
	}

	public static double calculateTaxes(double before_tax_pay, Employee e){
		double annual;

		if (e instanceof HourlyEmployee){
			annual = ((HourlyEmployee)e).getRate() * 2080;
		}
		else{
			annual = ((SalaryEmployee)e).getSalary();
		}


		return calcRestTaxes(annual);
	}

	public static double calcRestTaxes(double rest){
		if (rest <= 18610){
			return .02;
		}
		else if ((18611 <= rest) && (rest <= 29372)){
			return .04;
		}
		else if ((29373 <= rest) && (rest <= 40773)){
			return .06;
		}
		else if ((40774 <= rest) && (rest <= 51530)){
			return .08;
		}
		else if ((51531 <= rest) && (rest <= 263222)){
			return .093;
		}
		else if ((263223 <= rest) && (rest <= 315866)){
			return .103;
		}
		else if ((315867 <= rest) && (rest <= 526443)){
			return .113;
		}
		else{
			return .123;
		}
	}


}
