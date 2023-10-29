package com.example.avengersatm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {
    final int id;
    final String name;
    final List<Account> accounts;
    final DebitCard debitCard;

    // Constructor to initialize the customer details
    public Customer(int id, String name, DebitCard card) {
        this.id = id;
        this.name = name;
        this.accounts = new ArrayList<>();
        this.debitCard = card;
    }

    // Getters and setters for the fields
    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", accounts=" + accounts + "]";
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }
}
