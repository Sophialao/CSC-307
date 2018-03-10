package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DbWritable;
import model.HourlyEmployee;
import model.Report;
import model.SalaryEmployee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReportController {

    private TableView salaryTable = new TableView();
    private TableView hourlyTable = new TableView();

    public ReportController() {

    }

    public void generateReports(ActionEvent event) {
        //Salary Employees
        TableColumn<Report, String> sNameColumn = new TableColumn<>("Name");
        sNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        salaryTable.setItems(getAllSalaryEmpl());
        salaryTable.getColumns().addAll(sNameColumn);
    }

    public ObservableList<Report> getAllSalaryEmpl() {
        Map<String, DbWritable> allS = SalaryEmployee.getAll();
        List<SalaryEmployee> salaryEmployees = new ArrayList<SalaryEmployee>();
        ObservableList<Report> salaryReports = FXCollections.observableArrayList();

        Iterator<String> it = allS.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            SalaryEmployee se = (SalaryEmployee)allS.get(key);
            Report report = new Report(se.getName());
            salaryReports.add(report);
        }

        return salaryReports;
    }

    public ObservableList<HourlyEmployee> getAllHourlyEmpl() {
        Map<String, DbWritable> allH = HourlyEmployee.getAll();
        List<HourlyEmployee> hourlyEmployees = new ArrayList<HourlyEmployee>();

        Iterator<String> it = allH.keySet().iterator();
        while(it.hasNext()){
            String key2 = it.next();
            hourlyEmployees.add((HourlyEmployee) allH.get(key2));
        }

        ObservableList<HourlyEmployee> items = FXCollections.observableArrayList (hourlyEmployees);
        return items;
    }
}
