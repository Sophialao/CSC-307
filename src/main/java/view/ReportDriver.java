package view;

import controller.ReportController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class ReportDriver extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GenerateReport.fxml"));
            Parent root = loader.load();
            ReportController rc = loader.getController();

            stage.setTitle("Report");
            stage.setScene(new Scene(root, 913, 652));

            rc.generateReport();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
