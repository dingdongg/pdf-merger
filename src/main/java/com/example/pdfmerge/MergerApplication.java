package com.example.pdfmerge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MergerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MergerApplication.class.getResource("hello-view.fxml"));
        Rectangle2D screenRes = Screen.getPrimary().getBounds();
        Scene scene = new Scene(fxmlLoader.load(), screenRes.getWidth() / 2, screenRes.getHeight() * 3 / 4);
        stage.setTitle("PDF Merger");
        stage.getIcons().add(new Image("file:images/icon.png"));
        stage.setScene(scene);
        MergerController.initStage(stage); // passes in this stage for use in HelloController
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}