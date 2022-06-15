package com.example.pdfmerge.model;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * Class that handles the merging of the PDFs selected.
 * As an initial implementation, only allow 2 PDFs to be merged at once.
 */
public class PDFMerger {

    private FileChooser fileChooser;
    private File fileOne;
    private File fileTwo;
    private final String DEFAULT_DIRECTORY = "";
    private final int NUM_FILES = 2;

    public PDFMerger() {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle("Selected 2 PDFs for merging");
    }

    // bring up file chooser window
    // initializes fileOne and fileTwo
    public void openFileChooser(Stage stage) {
        List<File> selectedFiles = this.fileChooser.showOpenMultipleDialog(stage);
        if (selectedFiles.size() == NUM_FILES) {
            this.fileOne = selectedFiles.get(0);
            this.fileTwo = selectedFiles.get(1);
        }
    }

    // check that only PDFs have been selected

    // merge the two selected files
}
