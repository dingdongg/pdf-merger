package com.example.pdfmerge.model;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that handles the merging of the PDFs selected.
 * As an initial implementation, only allow 2 PDFs to be merged at once.
 */
public class PDFMerger {

    private FileChooser fileChooser;
    private List<File> PDFfiles;
    private final String DEFAULT_DIRECTORY = "";
    private final int NUM_FILES = 2;

    public PDFMerger() {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle(String.format("Selected %d PDFs for merging", NUM_FILES));
        this.PDFfiles = new LinkedList<>();
    }

    // bring up file chooser window
    // initializes fileOne and fileTwo
    // ** MUST BE CALLED BEFORE THE mergeFiles() METHOD
    public void openFileChooser(Stage stage) {
        List<File> selectedFiles = this.fileChooser.showOpenMultipleDialog(stage);
        if (selectedFiles.size() == NUM_FILES) {
            this.PDFfiles.addAll(selectedFiles);
        }
    }

    // check that only NUM_FILES PDFs have been selected
    public boolean selectedPDFs() {
        return false; // stub
    }

    // merge the two selected files
    public File mergeFiles() {
        return new File("STUB"); // stub
    }
}
