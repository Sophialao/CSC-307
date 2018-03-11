package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.util.*;

public class ReportController {

    @FXML private TableView salaryTable;
    @FXML private TableColumn sName;
    @FXML private TableColumn sRate;
    @FXML private TableColumn sCommission;
    @FXML private TableColumn sLoan;
    @FXML private TableColumn sCommissionAmt;
    @FXML private TableColumn sTaxes;
    @FXML private TableColumn sPayment;

    @FXML private TableView hourlyTable;
    @FXML private TableColumn hName;
    @FXML private TableColumn hRate;
    @FXML private TableColumn hLoan;
    @FXML private TableColumn hTaxes;
    @FXML private TableColumn hHours;
    @FXML private TableColumn hPayment;

    public ReportController() {

    }

    public void generateReport() {
        sName.setCellValueFactory(new PropertyValueFactory<Report, String>("name"));
        sRate.setCellValueFactory(new PropertyValueFactory<Report, String>("rate"));
        sCommission.setCellValueFactory(new PropertyValueFactory<Report, String>("commission"));
        sLoan.setCellValueFactory(new PropertyValueFactory<Report, String>("loan"));
        sCommissionAmt.setCellValueFactory(new PropertyValueFactory<Report, String>("commissionAmt"));
        sTaxes.setCellValueFactory(new PropertyValueFactory<Report, String>("taxes"));
        sPayment.setCellValueFactory(new PropertyValueFactory<Report, String>("payment"));
        salaryTable.setItems(getAllSalaryEmpl());

        hName.setCellValueFactory(new PropertyValueFactory<Report, String>("name"));
        hRate.setCellValueFactory(new PropertyValueFactory<Report, String>("rate"));
        hLoan.setCellValueFactory(new PropertyValueFactory<Report, String>("loan"));
        hTaxes.setCellValueFactory(new PropertyValueFactory<Report, String>("taxes"));
        hTaxes.setCellValueFactory(new PropertyValueFactory<Report, String>("hours"));
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
            double loans = Math.floor(calculateLoans(se) * 100)/100;
            double commissionAmt = Math.floor(PaymentController.calculateCommission(se) * 100)/100;
            double taxes = PaymentController.calculateTaxes((salary/12.0), se);
            double payment = Math.floor(calculateSalaryPayment(se, loans, commissionAmt, salary) * 100)/100;
            Report report = new Report(se.getName(), salary, se.getCommission(), loans, commissionAmt, taxes, 0.0, payment);
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
            double loans = Math.floor(calculateLoans(he) * 100)/100;
            double taxes = PaymentController.calcRestTaxes(wage*2080);
            double hours = calculateHoursWorked(he);
            double payment = Math.floor(PaymentController.calculateTime(he)*100)/100;
            Report report = new Report(he.getName(), wage, 0.0, loans, 0.0, taxes, hours, payment);
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
        return after_loans;
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
                each_month_pay_total += each_month_pay;
            }
        }

        return each_month_pay_total;
    }
}