package com.example.pdfmerge.model;

import com.example.pdfmerge.exceptions.NoFilesException;
import javafx.stage.DirectoryChooser;
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
 */
public class PDFMerger {

    private FileChooser fileChooser;
    private List<File> PDFfiles;
    private SavePathSelector pathSelector;
    private final int MAX_NUM_FILES = 5;

    public PDFMerger() {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle(String.format("Selected up to %d PDFs for merging", MAX_NUM_FILES));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF files (.pdf)", "*.pdf");
        this.fileChooser.getExtensionFilters().add(filter);
        this.pathSelector = new SavePathSelector();
        this.PDFfiles = new LinkedList<>();
    }

    // bring up file chooser window
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
            merger.setDestinationFileName(pathSelector.getFileFullPath());

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


    // move the two function below into SavePathSelector.java
    public void openSaveLocationChooser(Stage stage) {
        DirectoryChooser folderChooser = new DirectoryChooser();
        File newPath = folderChooser.showDialog(stage);
        this.pathSelector.setSavePath(newPath.getAbsolutePath());
    }

    public String getSaveLocation() {
        return this.pathSelector.getSavePath();
    }
}
