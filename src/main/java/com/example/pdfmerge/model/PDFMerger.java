package com.example.pdfmerge.model;

import com.example.pdfmerge.exceptions.NoFilesException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private final int MAX_NUM_FILES = 5;

    public PDFMerger() {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle(String.format("Selected up to %d PDFs for merging", MAX_NUM_FILES));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF files (.pdf)", "*.pdf");
        this.fileChooser.getExtensionFilters().add(filter);
        this.PDFfiles = new LinkedList<>();
    }

    // bring up file chooser window
    // initializes fileOne and fileTwo
    // ** MUST BE CALLED BEFORE THE mergeFiles() METHOD
    public void openFileChooser(Stage stage) {
        List<File> selectedFiles = this.fileChooser.showOpenMultipleDialog(stage);
        if (selectedFiles == null) {
            System.out.println("no files selected");
            return;
        }
        if (selectedFiles.size() <= MAX_NUM_FILES) {
            this.PDFfiles.addAll(selectedFiles);
        }
    }

    public List<File> getPDFs() {
        return this.PDFfiles;
    }

    // merge the selected files
    public void mergeFiles() throws NoFilesException {
        if (this.PDFfiles.size() == 0) {
            throw new NoFilesException("No files were selected");
        } else {
            // merge
            PDFMergerUtility merger = new PDFMergerUtility();
            for (File f : getPDFs()) {
                try {
                    merger.addSource(f);
                } catch (FileNotFoundException e) {
                    System.out.println("FILE NOT FOUND");
                }
            }
            merger.setDestinationFileName(DEFAULT_DIRECTORY);

            try {
                merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
    }

    public void resetSelection() {
        this.PDFfiles = new LinkedList<>();
    }
}
