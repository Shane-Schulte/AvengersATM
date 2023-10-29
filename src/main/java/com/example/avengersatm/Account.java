package com.example.avengersatm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    final String accountNumber;
    private double balance;
    final Customer owner;
    final List<Transaction> transactions;

    // Constructor to initialize the account
    public Account(String accountNumber, Customer owner) {
        this.accountNumber = accountNumber;
        this.balance = 0; // Starting balance is 0
        this.owner = owner;
        this.transactions = new ArrayList<>();
    }

    // Method to deposit an amount
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }
        this.balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    // Method to withdraw an amount
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
            return;
        }
        if (amount > this.balance) {
            System.out.println("Insufficient funds!");
            return;
        }
        this.balance -= amount;
        transactions.add(new Transaction("Withdraw", amount));
    }

    // Getters and setters for the fields
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account [accountNumber=" + accountNumber + ", balance=" + balance + ", owner=" + owner.getName() + "]";
    }
}
