package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Employee;
import model.Timecard;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            tIn = df.parse(timeInF.getText());
            System.out.println("tIn " + tIn);
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Time in date not formatted correctly");
            return;
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            tOut = df.parse(timeOutF.getText());
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
