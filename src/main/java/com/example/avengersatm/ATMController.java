package com.example.avengersatm;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ATMController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}