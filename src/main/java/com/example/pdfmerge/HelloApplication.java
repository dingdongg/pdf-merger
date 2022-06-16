package com.example.pdfmerge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("PDF Merger");
        stage.setScene(scene);
        HelloController.initStage(stage); // passes in this stage for use in HelloController
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}