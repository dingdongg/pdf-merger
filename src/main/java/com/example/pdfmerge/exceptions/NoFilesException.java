package com.example.pdfmerge.exceptions;

// exception thrown when no files are selected for merging
public class NoFilesException extends Exception {

    public NoFilesException(String msg) {
        super(msg);
    }
}
