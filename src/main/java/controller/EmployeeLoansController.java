package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.stage.Stage;
import model.DbWritable;
import model.Employee;
import model.Loan;
import javafx.event.Event;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Map;

public class EmployeeLoansController {
    @FXML Label loans;
    Employee e;

    public void initializeL2(Employee e){
        Map<String, DbWritable> lc = Loan.getAll();
        String all_loans = "\n\n\n";
        this.e = e;
        for (String key : lc.keySet()) {
            Loan l = (Loan) lc.get(key);
            if (l.getEmployeeId().equals(e.getId())) {
                all_loans += ("\tAmount: " + l.getAmount() + " Interest Rate: " + l.getInterestRate() + " Duration: " + l.getDuration() + "\n");
            }
        }
        if (all_loans == "\n\n\n"){
            loans.setText("\n\n\n\tNo Loans");
        }
        else {
            loans.setText(all_loans);
        }
    }

    public void initializeLoanButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeView.fxml"));
            Parent root = loader.load();
            EmployeeController ec = loader.getController();
            Employee employee = this.e;
            this.initializeLoan2(event, employee);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initializeLoan2(ActionEvent event, Employee employee){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoanView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Loan");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();

            if (employee != null) {
                LoanController tc_controller = loader.getController();
                tc_controller.employeeForLoan(employee);
            }
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
