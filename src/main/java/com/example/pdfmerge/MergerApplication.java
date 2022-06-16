package com.example.pdfmerge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MergerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MergerApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("PDF Merger");
        stage.setScene(scene);
        MergerController.initStage(stage); // passes in this stage for use in HelloController
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}