package com.example.project3;

import com.example.project3.clinic.*;
import com.example.project3.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClinicManagerMain extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("clinic-view.fxml"));

            // Set up the Scene and the Stage
            Scene scene = new Scene(root);
            primaryStage.setTitle("RU Clinic Manager - Scheduling Office / Imaging Appointments");
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