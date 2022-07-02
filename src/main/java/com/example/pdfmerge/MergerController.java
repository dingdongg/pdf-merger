package com.example.pdfmerge;

import com.example.pdfmerge.exceptions.NoFilesException;
import com.example.pdfmerge.model.PDFMerger;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.util.Callback;

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
        configureFileCells();

        this.merger.openFileChooser(baseStage);
        int num = this.merger.getPDFs().size();
        updateSelectedFilesPane();
        updateOutputLabel(String.format("%d PDFs selected", num));
    }

    // configure drag and drop functionality
    private void configureFileCells() {
        this.selectedFilesPane.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> listCell = new ListCell<String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };

                listCell.setOnDragDetected((MouseEvent event) -> {
                    System.out.println("listcell DRAG_DETECTED");
                    Dragboard db = listCell.startDragAndDrop(TransferMode.MOVE);

                    ClipboardContent content = new ClipboardContent();
                    content.putString(listCell.getItem());
                    db.setContent(content);

                    event.consume();
                });

                return listCell;
            }
        });
    }

    private void updateSelectedFilesPane() {
        List<File> files = this.merger.getPDFs();
        List<String> displayedFiles = this.selectedFilesPane.getItems();
        for (File f : files) {
            if (!displayedFiles.contains(f.getName())) {
                this.selectedFilesPane.getItems().add(f.getName());
            }
        }
    }

    public void onMergeButtonClick(ActionEvent actionEvent) {
        try {
            this.merger.mergeFiles();
            updateOutputLabel("Merged!");
            resetSelectionAndPane();
        } catch (NoFilesException e) {
            updateOutputLabel("No PDFs selected.");
        }
    }

    private void updateOutputLabel(String s) {
        this.outputLabel.setText(s);
    }

    public void onResetButtonClick(ActionEvent actionEvent) {
        resetSelectionAndPane();
        updateOutputLabel("Deselected all PDFs.");
    }

    private void resetSelectionAndPane() {
        this.merger.resetSelection();
        int size = this.selectedFilesPane.getItems().size();
        this.selectedFilesPane.getItems().remove(0, size);
    }

    public void onLocationButtonClick(ActionEvent actionEvent) {
        this.merger.openSaveLocationChooser(baseStage);
        updateOutputLabel(String.format("Files will be saved to %s", this.merger.getSaveLocation()));
    }
}