package com.example.pdfmerge;

import com.example.pdfmerge.exceptions.NoFilesException;
import com.example.pdfmerge.model.PDFMerger;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.*;
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
    public TextField mergedFileName;

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
        this.selectedFilesPane.setCellFactory(new Callback<>() {

            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> listCell = new ListCell<>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };

                allowDragAndDrop(listCell);

                return listCell;
            }
        });
    }

    private void allowDragAndDrop(ListCell<String> listCell) {
        configDragDetected(listCell);
        configDragOver(listCell);
        configDragEntered(listCell);
        configDragExited(listCell);
        configDragDropped(listCell);
        configDragDone(listCell);
    }

    private void configDragDone(ListCell<String> listCell) {
        listCell.setOnDragDone((DragEvent event) -> {
//            System.out.println("DRAG_DONE");

            if (event.getTransferMode() == TransferMode.MOVE) {
                swapFileOrder(listCell.getIndex(), Integer.parseInt(event.getDragboard().getString()));
            }

            event.consume();
        });
    }

    private void configDragDropped(ListCell<String> listCell) {
        listCell.setOnDragDropped((DragEvent event) -> {
//            System.out.println("DRAG_DROPPED");

            Dragboard db = event.getDragboard();
            boolean dragSuccessful = false;
            if (db.hasString()) {
                ClipboardContent content = new ClipboardContent();
                content.putString(String.valueOf(listCell.getIndex()));
                db.setContent(content);
                dragSuccessful = true;
            }

            event.setDropCompleted(dragSuccessful);
            event.consume();
        });
    }

    private void configDragExited(ListCell<String> listCell) {
        listCell.setOnDragExited((DragEvent event) -> {
//            System.out.println("DRAG_EXITED");

            listCell.setStyle("");

            event.consume();
        });
    }

    private void configDragEntered(ListCell<String> listCell) {
        listCell.setOnDragEntered((DragEvent event) -> {
//            System.out.println("DRAG_ENTERED");

            if (event.getGestureSource() != listCell && event.getDragboard().hasString()) {
                listCell.setStyle("color: green;");
            }

            event.consume();
        });
    }

    private void configDragOver(ListCell<String> listCell) {
        listCell.setOnDragOver((DragEvent event) -> {
//            System.out.println("DRAG_OVER");

            if (event.getGestureSource() != listCell && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });
    }

    private void configDragDetected(ListCell<String> listCell) {
        listCell.setOnDragDetected((MouseEvent event) -> {
//            System.out.println("DRAG_DETECTED");
            Dragboard db = listCell.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(listCell.getIndex())); // paste index into dragboard
            db.setContent(content);

            event.consume();
        });
    }

    private void swapFileOrder(int indexA, int indexB) {

        List<File> files = this.merger.getPDFs();
        File temp = files.get(indexA);
        files.set(indexA, files.get(indexB));
        files.set(indexB, temp);

        List<String> displayedFiles = this.selectedFilesPane.getItems();
        String tempName = displayedFiles.get(indexA);
        displayedFiles.set(indexA, displayedFiles.get(indexB));
        displayedFiles.set(indexB, tempName);
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
            String customFileName = this.mergedFileName.getText();
            if (isValidFileName(customFileName)) {
                this.merger.mergeFiles(customFileName);
                updateOutputLabel("Merged!");
                resetSelectionAndPane();
            } else {
                updateOutputLabel("Please only use numbers, alphabet letters, '-', and '_'.");
                this.mergedFileName.setText("");
            }
        } catch (NoFilesException e) {
            updateOutputLabel("No PDFs selected.");
        }
    }

    private boolean isValidFileName(String customFileName) {
        return customFileName.matches("[\\w_-]*");
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