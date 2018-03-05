package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Timecard;
import javafx.stage.Window;
import util.Utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimecardController {

    public TimecardController() {
    }
    Date tIn;
    Date tOut;

    @FXML private Text actiontarget;
    @FXML private TextField eId;
    @FXML private TextField timeInF;
    @FXML private TextField timeOutF;

    @FXML protected void exampleButtonAction(ActionEvent event) {
        if(eId.getText() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter employee Id");
            return;
        }
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
            Date tIn = df.parse(timeInF.getText());
            System.out.println("tIn " + tIn);
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Time in date not formatted correctly");
            return;
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            Date tOut = df.parse(timeOutF.getText());
            System.out.println("tOut " + tOut);
            addTimecard(eId.getText(),tIn,tOut);
        }
        catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Time out date not formatted correctly");
            return;
        }
        //addTimecard(eId.getText(),tIn,tOut);
        showAlert(Alert.AlertType.CONFIRMATION,"New timecard submitted!",
                "Timecard submitted ");
    }
    public void addTimecard(String eid, Date timeIn, Date timeOut) {
        Timecard tc = Timecard.getInstance(null);
        tc.setEId(eid);
        System.out.println("timeIn " + timeIn);
        System.out.println("timeOut " + timeOut);
        tc.setTimeIn(timeIn);
        tc.setTimeOut(timeOut);
        tc.write();
    }
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }


}
