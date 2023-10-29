package com.example.avengersatm;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    final String type;             // Type of transaction (e.g., Deposit, Withdrawal, FXTransfer, etc.)
    final double amount;           // Amount involved in the transaction
    final LocalDateTime timestamp; // Date and time of transaction

    // Constructor
    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now(); // Set the timestamp to the current date and time
    }

    @Override
    public String toString() {
        return "Transaction [type=" + type + ", amount=" + amount + ", timestamp=" + timestamp + "]";
    }
}
