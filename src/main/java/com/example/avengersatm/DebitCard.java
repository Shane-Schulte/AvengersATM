package com.example.avengersatm;

import java.io.Serializable;

public class DebitCard implements Serializable {
    final String cardNumber;
    final String pin;
    final Customer cardHolder;

    public DebitCard(String cardNumber, String pin, Customer cardHolder) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean verifyPin(String inputPin) {
        return this.pin.equals(inputPin);
    }
}

