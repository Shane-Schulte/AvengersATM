package com.example.avengersatm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class UserInterface {

    final ATM atm;
    private Customer currentCustomer;
    private BankDataManager dataManager;
    private Bank bank;

    public UserInterface(Bank bank){
        this.atm = new ATM(bank);
        this.dataManager = dataManager;
        this.bank = bank;
    }

    public void start(Stage primaryStage) {
        Bank bank = new Bank("Avengers Bank");
        List<Customer> initBankData = bank.getCustomers();

        Scene scene = createLoginScene(primaryStage);
        primaryStage.setTitle("ATM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene createLoginScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label cardNumberLabel = new Label("Enter Card Number:");
        TextField cardNumberField = new TextField();
        Label pinLabel = new Label("Enter PIN:");
        PasswordField pinField = new PasswordField();

        Button loginBtn = new Button("Login");
        Label statusLabel = new Label();

        grid.add(cardNumberLabel, 0, 0);
        grid.add(cardNumberField, 1, 0);
        grid.add(pinLabel, 0, 1);
        grid.add(pinField, 1, 1);
        grid.add(loginBtn, 1, 2);
        grid.add(statusLabel, 1, 3);

        loginBtn.setOnAction(e -> {
            String cardNumber = cardNumberField.getText().trim();
            String pin = pinField.getText().trim();

            Optional<Customer> potentialCustomer = atm.getBank().getCustomerByCardNumber(cardNumber);
            if (potentialCustomer.isPresent() && potentialCustomer.get().getDebitCard().verifyPin(pin)) {
                currentCustomer = potentialCustomer.get();
                Scene mainMenuScene = createMainMenuScene(stage);
                stage.setScene(mainMenuScene);
            } else {
                statusLabel.setText("Invalid card number or PIN.");
            }
        });

        return new Scene(grid, 500, 300);
    }

    private Scene createMainMenuScene(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Button quickWithdrawBtn =  new Button("Quick Withdraw");
        Button viewBalanceBtn = new Button("View Balance");
        Button withdrawBtn = new Button("Withdraw");
        Button depositBtn = new Button("Deposit");
        Button transferBtn = new Button("Transfer");
        Button logoutBtn = new Button("Logout");

        vbox.getChildren().addAll(quickWithdrawBtn, withdrawBtn, depositBtn, transferBtn, logoutBtn);

        quickWithdrawBtn.setOnAction(e -> {
            atm.performQuickWithdrawFX(currentCustomer);
            BankDataManager.getInstance().saveBankDataToFile(bank);
            stage.setScene(createLoginScene(stage));
        });
        viewBalanceBtn.setOnAction(e -> {
            atm.viewAccountBalanceFX(currentCustomer);
        });
        withdrawBtn.setOnAction(e -> {
            atm.performWithdrawalFX(currentCustomer);
        });
        depositBtn.setOnAction(e -> {
            atm.performDepositFX(currentCustomer);
        });
        transferBtn.setOnAction(e -> {
            atm.performTransferFX(currentCustomer);
        });
        logoutBtn.setOnAction(e -> {
            BankDataManager.getInstance().saveBankDataToFile(bank);
            stage.setScene(createLoginScene(stage));
        });

        return new Scene(vbox, 500, 300);
    }
}
