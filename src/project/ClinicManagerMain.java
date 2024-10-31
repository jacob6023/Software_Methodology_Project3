package com.test.project3;
import com.test.project3.util.Date;
import com.test.project3.util.Sort;
import com.test.project3.util.List;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClinicManagerMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("clinic-view.fxml"));

            // Set up the Scene and the Stage
            Scene scene = new Scene(root);
            primaryStage.setTitle("Clinic Manager - Scheduling Office / Imaging Appointments");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
