package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Date;

public class EmployeeTimecardsController {
    @FXML public Label timecards;
    Employee e;


    @FXML private TableView timecardTable;
    @FXML private TableColumn StimeIn;
    @FXML private TableColumn StimeOut;


    public void generateTimecards(Employee e) {
        this.e = e;

        StimeIn.setCellValueFactory(new PropertyValueFactory<Timecard, Date>("timeIn"));
        StimeOut.setCellValueFactory(new PropertyValueFactory<Timecard, Date>("timeOut"));
        timecardTable.setItems(getAllTimecards(e));
    }

    public ObservableList<Timecard> getAllTimecards(Employee e) {
        Map<String, DbWritable> allT = Timecard.getAll();
        ObservableList<Timecard> timecards = FXCollections.observableArrayList();

        Iterator<String> it = allT.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            Timecard t = (Timecard)allT.get(key);
            //Date dIn = t.getTimeIn();
            //Date dOut = t.getTimeOut();
            if (t.getEId().equals(e.getId())){
                timecards.add(t);
            }
        }

        return timecards;
    }

    @FXML public void initializeTC2(Employee e){
        Map<String, DbWritable> tc = Timecard.getAll();
        String all_timecards = "\n\n\n";
        this.e = e;
        for (String key : tc.keySet()) {
            Timecard t = (Timecard) tc.get(key);

            if (t.getEId().equals(e.getId())) {
                all_timecards += ("\tTime In: " + t.getTimeIn() + " Time Out: " + t.getTimeOut() + "\n");
            }
        }
        if (all_timecards == "\n\n\n"){
            timecards.setText("\n\n\n\tNo timecards");
        }
        else {
            timecards.setText(all_timecards);
        }
    }

    public void initializeTimecardButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeView.fxml"));
            Parent root = loader.load();
            EmployeeController ec = loader.getController();
            Employee employee = e;
            this.initializeTimecard2(event, employee);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initializeTimecard2(ActionEvent event, Employee employee){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TimecardView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Timecard");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();

            if (employee != null) {
                TimecardController tc_controller = loader.getController();
                tc_controller.employeeForTimecard(employee);
            }
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
