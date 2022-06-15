package com.example.pdfmerge;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    public Button btnHello;
    @FXML
    public Button btnMock;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onMockButtonClick() {
        Mock mock = new Mock(5);
        welcomeText.setText(mock.check());
    }
}