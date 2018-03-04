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

    @FXML private Text actiontarget;
    @FXML private TextField eId;
    @FXML private DatePicker timeIn;
    @FXML private DatePicker timeOut;

    @FXML protected void exampleButtonAction(ActionEvent event) {
        if(eId.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter employee Id");
            return;
        }
        if(timeIn.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter time in");
            return;
        }
        if(timeOut.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter time out");
            return;
        }
        addTimecard(eId.getText(), new Date(timeIn.getValue().toEpochDay()),
                new Date(timeOut.getValue().toEpochDay()));
        showAlert(Alert.AlertType.CONFIRMATION,"New timecard submitted!",
                "Timecard submitted ");
    }

    public void addTimecard(String eid, Date timeIn, Date timeOut) {
        Timecard tc = Timecard.getInstance(null);
        tc.setEId(eid);
        System.out.println(timeIn);
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
