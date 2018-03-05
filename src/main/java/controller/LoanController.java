package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import model.Employee;
import model.Loan;

public class LoanController {

    @FXML
    private Text actiontarget;
    String eId;
    @FXML private TextField LoanAmount;
    @FXML private TextField InterestRate;
    @FXML private TextField Duration;


    @FXML protected void employeeForLoan(Employee emp){
        actiontarget.setText("Add Loan For " + emp.getName());
        eId=emp.getId();
    }

    @FXML protected void exampleButtonAction(ActionEvent event) {
        double l;
        double interest;
        int duration;


        if(LoanAmount.getText() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter loan amount");
            return;
        }
        if(InterestRate.getText() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter interest rate");
            return;
        }
        if(Duration.getText() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter duration");
            return;
        }

        try {
            l = Double.parseDouble(LoanAmount.getText());
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Loan amount not formatted correctly");
            return;
        }
        try {
            interest = Double.parseDouble(InterestRate.getText());
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Interest not formatted correctly");
            return;
        }
        try {
            duration = Integer.parseInt(Duration.getText());
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Duration not formatted correctly");
            return;
        }
        Loan new_loan = new Loan(eId, l, interest, duration);
        new_loan.write();

        showAlert(Alert.AlertType.CONFIRMATION, "New loan added!",
                "Loan added ");
        LoanAmount.clear();
        InterestRate.clear();
        Duration.clear();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
