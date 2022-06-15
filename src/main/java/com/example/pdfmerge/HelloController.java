package com.example.pdfmerge;

import com.example.pdfmerge.model.PDFMerger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class HelloController {
    public Button btnChooseFiles;
    public ScrollPane selectedFilesPane;
    public Button btnMerge;
    private static Stage baseStage;

    private PDFMerger merger = new PDFMerger();

    public static void initStage(Stage stage) {
        baseStage = stage;
    }

    public void onChooseButtonClick(ActionEvent actionEvent) {
        this.merger.openFileChooser(baseStage);
    }

    public void onMergeButtonClick(ActionEvent actionEvent) {

    }
}