package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Employee;
import model.Timecard;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class TimecardController {

    public TimecardController() {
    }
    Date tIn;
    Date tOut;

    @FXML private Text actiontarget;
    String eId;
    @FXML private TextField timeInF;
    @FXML private TextField timeOutF;

    @FXML private DatePicker dp;
    @FXML private DatePicker dp2;


    @FXML protected void employeeForTimecard(Employee emp){
        actiontarget.setText("Add Timecard For " + emp.getName());
        eId = emp.getId();
    }

    @FXML protected void exampleButtonAction(ActionEvent event) {

        if(timeInF.getText() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter time in");
            return;
        }
        if(timeOutF.getText() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter time out");
            return;
        }
        try {
            LocalDate ld = dp.getValue();
            String date = localDatetoString(ld);
            String full = date + timeInF.getText();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            tIn = df.parse(full);
            System.out.println("tIn " + tIn);
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Time in date not formatted correctly");
            return;
        }
        try {
            LocalDate ld2 = dp2.getValue();
            String date2 = localDatetoString(ld2);
            String full2 = date2 + timeOutF.getText();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            tOut = df.parse(full2);
            System.out.println("tOut " + tOut);
            addTimecard(eId,tIn,tOut);
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Time out date not formatted correctly");
            return;
        }

        //addTimecard(eId.getText(),tIn,tOut);
        showAlert(Alert.AlertType.CONFIRMATION, "New timecard submitted!",
                "Timecard submitted: \n" + "timeIn: " + tIn + "\ntimeOut: " + tOut);

        timeInF.clear();
        timeOutF.clear();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }


    public String localDatetoString(LocalDate ld){
        try{
            int yr = ld.getYear();
            int month = ld.getMonthValue();
            int date = ld.getDayOfMonth();
            return (Integer.toString(yr) + "-" + Integer.toString(month) +  "-" + Integer.toString(date) + " ");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Timecard addTimecard(String eid, Date timeIn, Date timeOut) {
        Timecard tc = Timecard.getInstance(null);
        tc.setEId(eid);
        System.out.println("timeIn " + timeIn);
        System.out.println("timeOut " + timeOut);
        tc.setTimeIn(timeIn);
        tc.setTimeOut(timeOut);
        tc.write();
        return tc;
    }
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }


}
