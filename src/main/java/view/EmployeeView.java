package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class DatabaseEmployee
{
    String id;
    String name;
    String address;
    String SSN;
    String salary;
    String commission;
    String sales;


    DatabaseEmployee(String id, String name, String address, String SSN, String salary, String commission,  String sales)
    {
    }
}

public class EmployeeView extends Application{

// Java Program to illustrate calling a
// no-argument constructor
    
    @FXML TableView<DatabaseEmployee> table;
    public  void initialize() {
         ArrayList<DatabaseEmployee> list = new ArrayList<DatabaseEmployee>();
        try {
            Scanner s = new Scanner(new File("mock_db/salaryEmployees.txt"));
            while (s.hasNextLine()) {
                String [] argsparsed = s.nextLine().split(",");

                String a = argsparsed[0];
                String b = argsparsed[1];
                String c = argsparsed[2];
                String d = argsparsed[3];
                String e = argsparsed[4];
                String f = argsparsed[5];
                String g = argsparsed[6];

                DatabaseEmployee newEmp = new DatabaseEmployee(a,b,c,d,e,f,g);
                list.add(newEmp);

            }
            s.close();
        }
        catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        ObservableList<DatabaseEmployee> data = FXCollections.observableArrayList(list);
        if(table == null){
            System.out.println("nulllll");
        }
        else{
            table.setItems(data);
        }

        //id.setCellValueFactory( new PropertyValueFactory<DatabaseEmployee,String>("ID") );



    }



    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/EmployeeView.fxml"));

        Scene scene = new Scene(root, 300, 275);

        stage.setTitle("Employees");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        EmployeeView ev = new EmployeeView();
        ev.initialize();
        launch(args);
    }
}
