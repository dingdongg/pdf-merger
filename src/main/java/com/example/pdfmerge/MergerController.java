package com.example.pdfmerge;

import com.example.pdfmerge.exceptions.NoFilesException;
import com.example.pdfmerge.model.PDFMerger;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class MergerController {
    public Button btnChooseFiles;
    public ListView<String> selectedFilesPane = new ListView<>();
    public Button btnMerge;
    private static Stage baseStage;

    private final PDFMerger merger = new PDFMerger();
    public Label outputLabel;
    public Button btnEditSaveLocation;
    public Button btnResetSelection;

    public static void initStage(Stage stage) {
        baseStage = stage;
    }

    public void onChooseButtonClick(ActionEvent actionEvent) {
        this.merger.openFileChooser(baseStage);
        updateSelectedFilesPane();
        int num = this.merger.getPDFs().size();
        updateOutputLabel(String.format("%d PDFs selected", num));
    }

    private void updateSelectedFilesPane() {
        List<File> files = this.merger.getPDFs();
        for (File f : files) {
            this.selectedFilesPane.getItems().add(f.getName());
        }
    }

    public void onMergeButtonClick(ActionEvent actionEvent) {
        try {
            this.merger.mergeFiles();
        } catch (NoFilesException e) {
            updateOutputLabel("No PDFs selected.");
        }
    }

    private void updateOutputLabel(String s) {
        this.outputLabel.setText(s);
    }

    public void onResetButtonClick(ActionEvent actionEvent) {
    }

    public void onLocationButtonClick(ActionEvent actionEvent) {
    }
}