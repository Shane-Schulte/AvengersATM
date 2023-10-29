package com.example.avengersatm;

import javafx.application.Application;
import javafx.stage.Stage;

public class ATMApplication extends Application {

    private final BankDataManager dataManager = BankDataManager.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Bank bank = dataManager.initBankData();
        UserInterface ui = new UserInterface(bank);
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}