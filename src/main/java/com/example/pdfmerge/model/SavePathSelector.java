package com.example.pdfmerge.model;

import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 * Class that handles the configuration and storage of
 * merged PDF save path.
 */
public class SavePathSelector {

    private final Preferences userPrefs;
    private final String DEFAULT_DIRECTORY = "C:/merged-pdfs";
    private final String PATH_KEY = "savePath";
    private final String DEFAULT_FILE_NAME = "/merged-";
    private final String PDF_EXT = ".pdf";
    private String fileName;

    public SavePathSelector() {
        this.userPrefs = Preferences.userRoot();
        if (getSavePath().equals(this.DEFAULT_DIRECTORY)) {
            this.userPrefs.put(this.PATH_KEY, this.DEFAULT_DIRECTORY);
        } else {
            this.setSavePath(getSavePath());
        }
        this.fileName = updateFileName(); // set to "/merged-YYYYMMDDHHMMSS.pdf"
    }

    private String updateFileName() {
        Calendar dateTime = Calendar.getInstance();
        return this.DEFAULT_FILE_NAME + getDateIdentifier(dateTime) + this.PDF_EXT;
    }

    private String getDateIdentifier(Calendar timeInfo) {
        String year = Integer.toString(timeInfo.get(Calendar.YEAR));
        String month = Integer.toString(timeInfo.get(Calendar.MONTH) + 1); // MONTH is zero-indexed, increment by 1
        String day = Integer.toString(timeInfo.get(Calendar.DAY_OF_MONTH));
        String hour = Integer.toString(timeInfo.get(Calendar.HOUR_OF_DAY));
        String min = Integer.toString(timeInfo.get(Calendar.MINUTE));
        String sec = Integer.toString(timeInfo.get(Calendar.SECOND));
//        System.out.println(String.format("%s %s %s, %s:%s:%sPM", year, month, day, hour, min, sec));
        return year + month + day + hour + min + sec;
    }

    public void setSavePath(String absPath) {
        this.userPrefs.put(this.PATH_KEY, absPath);
    }

    public String getSavePath() {
        return this.userPrefs.get(this.PATH_KEY, this.DEFAULT_DIRECTORY);
    }

    public String getFileFullPath() {
        return this.userPrefs.get(this.PATH_KEY, this.DEFAULT_DIRECTORY) + this.fileName;
    }
}
