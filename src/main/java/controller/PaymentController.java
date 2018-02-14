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
	//public int total_payments = 0;
	//public HashMap<Integer, Payment> payments = new HashMap<Integer, Payment>();
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
		//SalaryEmployee se = SalaryEmployee.getInstance();
		Map<String, DbWritable> es = SalaryEmployee.getAll();  // GET INSTANCE ALL
		for (String key : es.keySet()){
			SalaryEmployee e = (SalaryEmployee) es.get(key);
			double before_tax = e.getSalary()/12;
			double after_commission = before_tax + calculateCommission(e);
			double after_tax = after_commission - calculateTaxes(before_tax, e);
			double after_loans = after_tax - calculateLoans(e);
			if (after_loans>0) {
				Payment p = new Payment(key, after_loans, getDate());
				p.write();
			}
		}
	}

	public static void newWeeklyPay(){
		//HourlyEmployee he = HourlyEmployee.getInstance();
		Map<String, DbWritable> es = HourlyEmployee.getAll();
		for (String key : es.keySet()){
			HourlyEmployee e = (HourlyEmployee) es.get(key);
			//System.out.println(e.getName());
			double before_tax = calculateTime(e);
			//System.out.println("BEFORE TAX:" + before_tax);
			double after_tax = before_tax - calculateTaxes(before_tax, e);
			//System.out.println("AFTERTAX" + after_tax);
			double after_loans = after_tax - calculateLoans(e);
			//System.out.println("AfterLOANS:" + after_loans);
			if (after_loans > 0) {
				Payment p = new Payment(key, after_loans, getDate());
				p.write();
			}
		}
	}

	/*public static void addToPaymentFile(){
		HashMap<Integer, String> payments_forUtil = new HashMap<Integer, String>();
		for (Integer key : payments.keySet()){
			Payment pm = payments[key];
			String pm_str = Integer.toString(pm.employeeId) + Integer.toString(pm.amount) + Integer.toString(pm.date);
			payments_forUtil.add(key, pm_str);
		}
		Util.writeToFile(payments_forUtil, mock_db.Payments.txt);
	}*/

	public static double calculateTime(HourlyEmployee e){
		//Timecard tc = Timecard.getInstance();
		Map<String, DbWritable> e_timecards = Timecard.getAll();   //GET INSTANCE ALL
		double total_timeWorked = 0;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date dateBefore7Days = cal.getTime();
		for (String key : e_timecards.keySet()){
			//System.out.println("going through timecards");
			Timecard e_timecard = (Timecard) e_timecards.get(key);
			if (e_timecard.getEId().equals(e.getId())){
				//System.out.println(e_timecard.getEId());
				//System.out.println(e_timecard.getTimeIn());
				//System.out.println(dateBefore7Days);
				if (e_timecard.getTimeIn().after(dateBefore7Days)){
					total_timeWorked += Math.abs(e_timecard.getTimeOut().getTime() - e_timecard.getTimeIn().getTime());
					//System.out.println("Total time:" + total_timeWorked);
				}
			}
		}
		//double hours = ((total_timeWorked / ((double)((1000*60*60)) % 24)));
		double hours = ((total_timeWorked / (double)(1000*60*60)) % 24);
		//System.out.println("hours:" + hours);
		//System.out.println("Rate:" + e.getRate());
		return e.getRate() * hours;
	}

	public static double calculateCommission(SalaryEmployee e){
		return e.getSales() * e.getCommission();
	}

	public static double calculateLoans(Employee e){
		String e_id = e.getId();
		
		Loan e_loan = Loan.getInstance(e_id);
		if (e_loan.getAmount()==0){
			return 0.0;
		}
		//Loan e_loan = e_l.getLoan(e_id);
		double interest_decimal = e_loan.getInterestRate();
		int time = e_loan.getDuration();
		double each_month = e_loan.getAmount()/(double)time;
		double each_month_pay = each_month * (1+interest_decimal);
		e_loan.setAmount(e_loan.getAmount() - each_month);
		e_loan.setDuration(e_loan.getDuration() - 1);
		e_loan.write();

		return each_month * (1+interest_decimal);
	}

	public static double calculateTaxes(double before_tax_pay, Employee e){
		/*double annual;
		double first_per_month = 7850/12;
		double first_per_month_one = .01 * first_per_month;

		if (e instanceof HourlyEmployee){
			annual = ((HourlyEmployee)e).getRate() * 2080;
		}
		else{
			annual = ((SalaryEmployee)e).getSalary();
			if (annual <= 7850){
				return .01 * before_tax_pay;
			}
			else{
				double rest_sal = annual;//-7850;
				return first_per_month_one + ((rest_sal-7850)*calcRestTaxes(rest_sal));
			}
		}
		System.out.println("ANNUAL" + annual);

		if (annual <= 7850){
			//System.out.println("CALCULATED TAXES" + (.01*before_tax_pay));
			return .01 * before_tax_pay;
		}

		double rest_hourly = annual; //- 7850.0;

		/*System.out.println("CALCULATED" + first_per_month_one + (((rest_hourly-7850)/12)*(calcRestTaxes(rest_hourly))));
		System.out.println("RESTHOURLY" + rest_hourly);
		System.out.println(calcRestTaxes(rest_hourly));
		System.out.println(rest_hourly/12);*/
		//return first_per_month_one + (((rest_hourly-7850)/12)*(calcRestTaxes(rest_hourly)));
		return .2*before_tax_pay;
	}

	public static double calcRestTaxes(double rest){
		if ((7851 <= rest)  && (rest <= 18610)){
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
