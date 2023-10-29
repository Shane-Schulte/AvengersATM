package com.example.avengersatm;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ATM {

    final Bank bank;

    public ATM(Bank bank){
        this.bank = bank;
    }

    public Bank getBank(){
        return bank;
    }

    public void viewAccountBalanceFX(Customer currentCustomer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        List<Account> customerAccounts = currentCustomer.getAccounts();

        // Handle multiple accounts
        if (customerAccounts.size() == 1) {
            Account account = customerAccounts.get(0);
            alert.setContentText("Balance in your account (" + account.getAccountNumber() + "): $" + account.getBalance());
            alert.showAndWait();
        } else {
            StringBuilder balances = new StringBuilder();
            for (Account acc : customerAccounts) {
                balances.append("Account ").append(acc.getAccountNumber()).append(": $").append(acc.getBalance()).append("\n");
            }
            alert.setContentText(balances.toString());
            alert.showAndWait();
        }
    }

    public void performWithdrawalFX(Customer currentCustomer) {
        if (currentCustomer.getAccounts().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("No accounts available for the customer.");
            errorAlert.showAndWait();
            return;
        }

        // Let user choose which account to withdraw from
        List<String> choices = currentCustomer.getAccounts().stream()
                .map(account -> "Account Number: " + account.getAccountNumber() + " - Balance: $" + account.getBalance())
                .collect(Collectors.toList());

        ChoiceDialog<String> accountChoiceDialog = new ChoiceDialog<>(choices.get(0), choices);
        accountChoiceDialog.setTitle("Select Account");
        accountChoiceDialog.setHeaderText("Choose an account to withdraw from:");

        Optional<String> accountResult = accountChoiceDialog.showAndWait();

        if (!accountResult.isPresent()) {
            return;  // User cancelled the operation
        }

        // Find the selected account
        String selectedDetail = accountResult.get();
        Account selectedAccount = currentCustomer.getAccounts().stream()
                .filter(account -> selectedDetail.contains(account.getAccountNumber()))
                .findFirst()
                .orElse(null);

        if (selectedAccount == null) {
            return;  // Shouldn't happen but just in case
        }

        // Use TextInputDialog for getting withdrawal amount
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Withdrawal");
        dialog.setHeaderText("Enter amount to withdraw:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            double amount = Double.parseDouble(result.get());

            // Check if there's enough balance to withdraw
            if (selectedAccount.getBalance() >= amount) {
                selectedAccount.withdraw(amount);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully withdrew $" + amount + " from account " + selectedAccount.getAccountNumber() + ". New Balance: $" + selectedAccount.getBalance());
                alert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Insufficient funds in account " + selectedAccount.getAccountNumber());
                errorAlert.showAndWait();
            }
        }
    }



    public void performDepositFX(Customer currentCustomer) {
        if (currentCustomer.getAccounts().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("No accounts available for the customer.");
            errorAlert.showAndWait();
            return;
        }

        // Let user choose which account to deposit into
        List<String> choices = currentCustomer.getAccounts().stream()
                .map(account -> "Account Number: " + account.getAccountNumber() + " - Balance: $" + account.getBalance())
                .collect(Collectors.toList());

        ChoiceDialog<String> accountChoiceDialog = new ChoiceDialog<>(choices.get(0), choices);
        accountChoiceDialog.setTitle("Select Account");
        accountChoiceDialog.setHeaderText("Choose an account to deposit into:");

        Optional<String> accountResult = accountChoiceDialog.showAndWait();

        if (!accountResult.isPresent()) {
            return;  // User cancelled the operation
        }

        // Find the selected account
        String selectedDetail = accountResult.get();
        Account selectedAccount = currentCustomer.getAccounts().stream()
                .filter(account -> selectedDetail.contains(account.getAccountNumber()))
                .findFirst()
                .orElse(null);

        if (selectedAccount == null) {
            return;  // Shouldn't happen but just in case
        }

        // Use TextInputDialog to get the deposit amount
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter amount to deposit:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            double amount = Double.parseDouble(result.get());
            selectedAccount.deposit(amount);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Successfully deposited $" + amount + " to account " + selectedAccount.getAccountNumber() + ". New Balance: $" + selectedAccount.getBalance());
            alert.showAndWait();
        }
    }


    public void performTransferFX(Customer currentCustomer) {
        List<Account> accounts = currentCustomer.getAccounts();

        if (accounts.size() < 2) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Transfer requires at least two accounts. You only have one.");
            errorAlert.showAndWait();
            return;
        }

        // Choosing from account
        ChoiceDialog<Account> fromAccountDialog = new ChoiceDialog<>(accounts.get(0), accounts);
        fromAccountDialog.setTitle("Transfer");
        fromAccountDialog.setHeaderText("Choose the account to transfer FROM:");
        Optional<Account> fromResult = fromAccountDialog.showAndWait();

        if (fromResult.isPresent()) {
            Account fromAccount = fromResult.get();

            List<Account> otherAccounts = new ArrayList<>(accounts);
            otherAccounts.remove(fromAccount);

            // Choosing to account
            ChoiceDialog<Account> toAccountDialog = new ChoiceDialog<>(otherAccounts.get(0), otherAccounts);
            toAccountDialog.setTitle("Transfer");
            toAccountDialog.setHeaderText("Choose the account to transfer TO:");
            Optional<Account> toResult = toAccountDialog.showAndWait();

            if (toResult.isPresent()) {
                Account toAccount = toResult.get();

                // Choosing transfer amount
                TextInputDialog amountDialog = new TextInputDialog("0");
                amountDialog.setTitle("Transfer");
                amountDialog.setHeaderText("Enter amount to transfer:");
                Optional<String> amountResult = amountDialog.showAndWait();

                if (amountResult.isPresent()) {
                    double amount = Double.parseDouble(amountResult.get());

                    // Perform transfer logic here. For now, just a basic check
                    if (fromAccount.getBalance() >= amount) {
                        fromAccount.withdraw(amount);
                        toAccount.deposit(amount);

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setContentText("Successfully transferred $" + amount + " from account " + fromAccount.getAccountNumber() + " to " + toAccount.getAccountNumber());
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("Insufficient funds in account " + fromAccount.getAccountNumber());
                        errorAlert.showAndWait();
                    }
                }
            }
        }
    }
}
