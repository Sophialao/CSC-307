package controller;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PaymentController {
    public static boolean invoke_hourly;
    public static boolean invoke_month;


    public PaymentController(){
    	this.invoke_hourly = false;
    	this.invoke_month = false;
	}

    public PaymentController(boolean invoke_month, boolean invoke_hourly){
    	this.invoke_month = invoke_month;
    	this.invoke_hourly = invoke_hourly;
	}

	@FXML private TableView salaryTable;
	@FXML private TableColumn sName;
	@FXML private TableColumn sRate;
	@FXML private TableColumn sCommission;
	@FXML private TableColumn sLoan;
	@FXML private TableColumn sCommissionAmt;
	@FXML private TableColumn sTaxes;
	@FXML private TableColumn sSickDays;

	@FXML private TableColumn sPayment;

	@FXML private TableView hourlyTable;
	@FXML private TableColumn hName;
	@FXML private TableColumn hRate;
	@FXML private TableColumn hLoan;
	@FXML private TableColumn hTaxes;
	@FXML private TableColumn hHours;
	@FXML private TableColumn hSickDays;
	@FXML private TableColumn hPayment;

	@FXML private Button payButton;


	public void generatePayments() {
		sName.setCellValueFactory(new PropertyValueFactory<Report, String>("name"));
		sRate.setCellValueFactory(new PropertyValueFactory<Report, String>("rate"));
		sCommission.setCellValueFactory(new PropertyValueFactory<Report, String>("commission"));
		sLoan.setCellValueFactory(new PropertyValueFactory<Report, String>("loan"));
		sCommissionAmt.setCellValueFactory(new PropertyValueFactory<Report, String>("commissionAmt"));
		sTaxes.setCellValueFactory(new PropertyValueFactory<Report, String>("taxes"));
		sSickDays.setCellValueFactory(new PropertyValueFactory<Report, String>("sickDays"));
		sPayment.setCellValueFactory(new PropertyValueFactory<Report, String>("payment"));
		salaryTable.setItems(getAllSalaryEmpl());

		hName.setCellValueFactory(new PropertyValueFactory<Report, String>("name"));
		hRate.setCellValueFactory(new PropertyValueFactory<Report, String>("rate"));
		hLoan.setCellValueFactory(new PropertyValueFactory<Report, String>("loan"));
		hTaxes.setCellValueFactory(new PropertyValueFactory<Report, String>("taxes"));
		hHours.setCellValueFactory(new PropertyValueFactory<Report, String>("hours"));
		hSickDays.setCellValueFactory(new PropertyValueFactory<Report, String>("sickDays"));
		hPayment.setCellValueFactory(new PropertyValueFactory<Report, String>("payment"));
		hourlyTable.setItems(getAllHourlyEmpl());
	}

	public ObservableList<Report> getAllSalaryEmpl() {
		Map<String, DbWritable> allS = SalaryEmployee.getAll();
		ObservableList<Report> salaryReports = FXCollections.observableArrayList();

		Iterator<String> it = allS.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			SalaryEmployee se = (SalaryEmployee)allS.get(key);
			double salary = se.getSalary();
			double loans = Math.floor(calculateLoansForShow(se) * 100)/100;
			double commissionAmt = Math.floor(PaymentController.calculateCommission(se) * 100)/100;
			double taxes = PaymentController.calculateTaxes((salary/12.0), se);
			double sickDays = Math.floor(PaymentController.calcSickDaysWithoutSet(calculatePayBeforeSickDays(se, loans, commissionAmt, salary), se)* 100)/100;
			double payment = Math.floor(calculateSalaryPayment(se, loans, commissionAmt, salary) * 100)/100;
			Report report = new Report(se.getName(), salary, se.getCommission(), loans, commissionAmt, taxes, 0.0, sickDays, payment);
			salaryReports.add(report);
		}

		return salaryReports;
	}

	public ObservableList<Report> getAllHourlyEmpl() {
		Map<String, DbWritable> allH = HourlyEmployee.getAll();
		ObservableList<Report> hourlyReports = FXCollections.observableArrayList();

		Iterator<String> it = allH.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			HourlyEmployee he = (HourlyEmployee) allH.get(key);
			double wage = he.getRate();
			double loans = Math.floor(calculateLoansForShow(he) * 100)/100;
			double taxes = PaymentController.calcRestTaxes(wage*2080);
			double hours = calculateHoursWorked(he);
			double sickDays = Math.floor(PaymentController.calcSickDaysWithoutSet(calculatePayBeforeHourlySick(he, loans), he)*100)/100;
			double payment = Math.floor(PaymentController.calculateHourlyPayment(he, loans)*100)/100;
			Report report = new Report(he.getName(), wage, 0.0, loans, 0.0, taxes, hours, sickDays, payment);
			hourlyReports.add(report);
		}

		return hourlyReports;
	}

	public static double calculateHoursWorked(HourlyEmployee e) {
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
		return hours;
	}

	public static double calculateSalaryPayment(SalaryEmployee se, double loans, double commissionAmt, double salary) {
		double before_tax = salary/12.0;
		double after_commission = before_tax + commissionAmt;
		double after_tax = after_commission - (after_commission*PaymentController.calculateTaxes(before_tax, se));
		double after_loans = after_tax - loans;
		double after_sickDays = after_loans - calcSickDaysWithoutSet(after_loans, se);
		return after_sickDays;
	}

	public static double calculateHourlyPayment(HourlyEmployee he, double loans) {
		double before_tax = calculateTime(he);
		double after_tax = before_tax - calculateTaxes(before_tax, he);
		double after_loans = after_tax - loans;
		double after_sickDays = after_loans - calcSickDaysWithoutSet(after_loans, he);
		return after_sickDays;
	}

	public static double calculatePayBeforeSickDays(SalaryEmployee se, double loans, double commissionAmt, double salary){
		double before_tax = salary/12.0;
		double after_commission = before_tax + commissionAmt;
		double after_tax = after_commission - (after_commission*PaymentController.calculateTaxes(before_tax, se));
		double after_loans = after_tax - loans;
		return after_loans;
	}

	public static double calculatePayBeforeHourlySick(HourlyEmployee he, double loans){
		double before_tax = calculateTime(he);
		double after_tax = before_tax - calculateTaxes(before_tax, he);
		double after_loans = after_tax - loans;
		return after_loans;
	}


	@FXML protected String handleSubmitButtonAction(ActionEvent event) {
		if (!checkLastDateofMonth() && !checkMonday()){
			return "Not Payday";
		}
		else if (checkPaid()){
			return "Already Paid";
		}
		else{
			calculatePayment();
			return "Paid";
		}
	}


	public ObservableList<Loan> getAllLoans(Employee e) {
		Map<String, DbWritable> allL = Loan.getAll();
		ObservableList<Loan> loans = FXCollections.observableArrayList();

		Iterator<String> it = allL.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			Loan l = (Loan)allL.get(key);
			if (l.getEmployeeId().equals(e.getId())){
				loans.add(l);
			}
		}

		return loans;
	}


	public static boolean checkPaid(){
		Map<String, DbWritable> payments = Payment.getAll();
		for (String key : payments.keySet()){
			Payment p = (Payment) payments.get(key);
			Date today = new Date();
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(p.getDate());
			cal2.setTime(today);
			boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
					cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
			if (sameDay){
				return true;
			}
		}
		return false;
	}


	public void payEmployee(ActionEvent event) {
		String result = this.handleSubmitButtonAction(event);
		if (result.equals("Paid")){
			showAlert(Alert.AlertType.CONFIRMATION, "Success", "Paid Employees!");
		}
		else if (result.equals("Not Payday")){
			showAlert(Alert.AlertType.ERROR, "Error",
					"Not payday for any employees!");
		}
		else{
			showAlert(Alert.AlertType.ERROR, "Error",
					"Already paid employees!");
		}
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
			double after_sickDays = after_loans - calcSickDays(after_loans, e);
			Payment p = new Payment(key, after_sickDays, getDate());
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
			double after_sickDays = after_loans - calcSickDays(after_loans, e);
			Payment p = new Payment(key, after_sickDays, getDate());
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
		double sales = e.getSales();
		e.setSales(0.0);
		return sales * e.getCommission();
	}

	public static double calculateLoans(Employee e) {
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

		double each_month_pay_total = 0.0;

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
				e_loan.update();
				each_month_pay_total += each_month_pay;
				//System.out.println("LOAN PAY:" + each_month_pay);
			}
		}

		return each_month_pay_total;
	}

	public static double calculateLoansForShow(Employee e) {
		String e_id = e.getId();
        /*try {
            Map<String, DbWritable> e_loans = Loan.getAll();
        }
        catch (Exception ex){
            return 0.0;
        }*/

		Map<String, DbWritable> e_loans = Loan.getAll();
		if (e_loans.isEmpty()){
			return 0.0;
		}

		double each_month_pay_total = 0.0;

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
				each_month_pay_total += each_month_pay;
			}
		}

		return each_month_pay_total;
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

	public static double calcSickDays(double money, Employee se){
		// deducts a tenth of pay for each extra sick day taken
		int sickDays = se.getSickDays();
		se.setSickDays(3);
		se.update();
		if (sickDays < 0){
			return Math.abs(sickDays)*.1*money;
		}
		return 0.0;
	}

	public static double calcSickDaysWithoutSet(double money, Employee se){
		// deducts a tenth of pay for each extra sick day taken
		int sickDays = se.getSickDays();
		//se.setSickDays(3);s
		//se.update();
		if (sickDays < 0){
			return Math.abs(sickDays)*.1*money;
		}
		return 0.0;
	}

	public static void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}


}
