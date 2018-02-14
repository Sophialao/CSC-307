import java.util.Calendar;
import util.Utils;
import model.Employee;
import model.SalaryEmployee;
import model.HourlyEmployee;
import model.Payment;
import model.Loan;
import model.Timecard;
import java.util.*;

class PaymentController{
	//public int total_payments = 0;
	//public HashMap<Integer, Payment> payments = new HashMap<Integer, Payment>();

	public static void calculatePayment(String[] args) {
		boolean monthly_payday = checkLastDateofMonth();
		boolean weekly_payday = checkMonday();
		if (!monthly_payday && !weekly_payday){
			return;
		}

		if (monthly_payday){
			newMonthlyPay();
		}

		if (weekly_payday){
			newWeeklyPay();
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
		HashMap<String, DbWriteable> es = SalaryEmployee.getAll();  // GET INSTANCE ALL
		for (String key : es){
			SalaryEmployee e = (SalaryEmployee) es[key];
			double before_tax = e.salary/12;
			double after_commission = before_tax + calculateCommission(e);
			double after_tax = after_commission - calculateTaxes(before_tax, e);
			double after_loans = after_tax - calculateLoans(e);
			Payment p = new Payment(key, after_loans, getDate());
			p.write();
		}
	}

	public static void newWeeklyPay(){
		//HourlyEmployee he = HourlyEmployee.getInstance();
		Map<String, DbWriteable> es = HourlyEmployee.getAll();
		for (String key : es){
			HourlyEmployee e = (HourlyEmployee) es[key];
			int before_tax = calculateTime(e.id);
			int after_tax = before_tax - calculateTaxes(before_tax, e);
			int after_loans = after_commission - calculateLoans(e.id);
			Payment p = new Payment(key, after_loans, getDate()); //GET INSTANCE WRITE
			p.write();
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

	public int calculateTime(HourlyEmp e){
		//Timecard tc = Timecard.getInstance();
		Map<String, DbWritable> e_timecards = Timecard.getAll();   //GET INSTANCE ALL
		float total_timeWorked = 0;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date dateBefore7Days = cal.getTime();
		for (Timecard e_timecard : e_timecards){
			if (e_timecard.timeIn.comparesTo(dateBefore7Days) > 0){
				total_timeWorked += Math.abs(e_timecard.timeOut - e_timecard.timeIn);
			}
		}
		float hours = ((total_timeWorked / (1000*60*60)) % 24);
		return e.rate * hours;
	}

	public int calculateCommission(SalaryEmployee e){
		return e.sales * e.commisionRate;
	}

	public double calculateLoans(Employee e){
		String e_id = e.id;
		
		Loan e_loan = Loan.getInstance(e_id);
		//Loan e_loan = e_l.getLoan(e_id);
		double interest_decimal = e_loan.interest;
		int time = e_loan.duration;
		double each_month = e_loan.amount/time;
		double each_month_pay = each_month * (1+interest_decimal);
		e_loan.amount -= each_month;
		e_loan.duration -= 1;
		e_loan.write();

		return each_month * (1+interest_decimal);
	}

	public double calculateTaxes(double before_tax_pay, Employee e){
		int annual;
		double first_per_month = 7850/12;
		double first_per_month_one = .01 * first_per_month;

		if (e instanceof HourlyEmployee){
			annual = e.rate * 2080;
		}
		else{
			annual = e.salary;
			if (annual <= 7850){
				return .01 * before_tax_pay;
			}
			else{
				int rest_sal = annual-7850;
				return first_per_month_one + (rest_sal*calcRestTaxes(rest_sal));
			}
		}

		if (annual <= 7850){
			return .01 * before_tax_pay;
		}

		double rest_hourly = annual - 7850;
		return first_per_month_one + (rest_hourly*(calcRestTaxes(rest_hourly)));
	}

	public double calcRestTaxes(double rest){
		if (7851 <= rest <= 18610){
			return .02;
		}
		else if (18611 <= rest <= 29372){
			return .04;
		}
		else if (29373 <= rest <= 40773){
			return .06;
		}
		else if (40774 <= rest <= 51530){
			return .08;
		}
		else if (51531 <= rest <= 263222){
			return .093;
		}
		else if (263223 <= rest <= 315866){
			return .103;
		}
		else if (315867 <= rest <= 526443){
			return .113;
		}
		else{
			return .123;
		}
	}


}