module com.example.pdfmerge {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pdfmerge to javafx.fxml;
    exports com.example.pdfmerge;
}