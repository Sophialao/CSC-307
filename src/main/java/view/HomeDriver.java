package view;

import controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeDriver extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeView.fxml"));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setData();

        Scene scene = new Scene(root, 700, 700);
        stage.setTitle("Payroll");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
