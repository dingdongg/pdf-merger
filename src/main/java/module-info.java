module com.example.pdfmerge {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires java.prefs;


    opens com.example.pdfmerge to javafx.fxml;
    exports com.example.pdfmerge;
}