package com.example.avengersatm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bank implements Serializable {
    final String name;
    final List<Customer> customers;

    public Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
    }

    // Adds a new customer to the bank's list of customers
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Optional<Customer> getCustomerByCardNumber(String cardNumber) {
        return customers.stream()
                .filter(customer -> customer.getDebitCard() != null &&
                        customer.getDebitCard().getCardNumber().equals(cardNumber))
                .findFirst();
    }
    public List<Customer> getCustomers() {
        return customers;
    }
}


