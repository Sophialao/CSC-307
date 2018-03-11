package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.Employee;

public class EmployeeTotal {
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab timecard_tab;

    @FXML
    private Tab loan_tab;


    @FXML private EmployeeTimecardsController employeeTimecardsController;

    @FXML private EmployeeLoansController employeeLoansController;

    @FXML public EmployeeController empController;

    public void initializeTimecardAll1(Event e){
        this.initializeTimecardAll(e, empController.employee);
    }

    public void initializeTimecardAll(Event event, Employee employee) {
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeTimecards.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Timecards");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();
*/
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeTimecards.fxml"));
            Parent root = loader.load();
*/
        if (employee != null) {
            employeeTimecardsController.initializeTC2(employee);
        }
       /* } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    public void initializeLoanAll1(Event e){
        this.initializeLoanAll(e, empController.employee);
    }

    public void initializeLoanAll(Event event, Employee employee) {
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeLoans.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Loans");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();

            if (employee != null) {
                EmployeeLoansController controller = loader.getController();
                controller.initializeL2(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        if (employee != null) {
            employeeLoansController.initializeL2(employee);
        }
    }
}
