package com.example.pdfmerge;

import com.example.pdfmerge.model.PDFMerger;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class HelloController {
    public Button btnChooseFiles;
    public ListView<String> selectedFilesPane = new ListView<>();
    public Button btnMerge;
    private static Stage baseStage;

    private final PDFMerger merger = new PDFMerger();

    public static void initStage(Stage stage) {
        baseStage = stage;
    }

    public void onChooseButtonClick(ActionEvent actionEvent) {
        this.merger.openFileChooser(baseStage);
        updateSelectedFilesPane();
    }

    private void updateSelectedFilesPane() {
        List<File> files = this.merger.getPDFs();
        for (File f : files) {
            this.selectedFilesPane.getItems().add(f.getName());
        }
    }

    public void onMergeButtonClick(ActionEvent actionEvent) {

    }
}