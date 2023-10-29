package com.example.avengersatm;

import java.io.*;

public class BankDataManager {
    private static BankDataManager instance;

    private BankDataManager(){}

    public static BankDataManager getInstance(){
        if (instance == null) {
            instance = new BankDataManager();
        }
        return instance;
    }

    public Bank initBankData(){
        Bank bank = readBankDataFromFile();
        if (bank == null) {
            bank = new Bank("Avengers Bank");
            Customer customer1 = new Customer(12345, "Alice", new DebitCard("1111222233334444", "1234", null));
            Account account1 = new Account("00001", customer1);
            Account account2 = new Account("00002", customer1);
            customer1.addAccount(account1);
            customer1.addAccount(account2);

            Customer customer2 = new Customer(67890, "Bob", new DebitCard("1234123412341234", "4321", null));
            Account account3 = new Account("00003", customer2);
            Account account4 = new Account("00004", customer2);
            customer2.addAccount(account3);
            customer2.addAccount(account4);

            bank.addCustomer(customer1);
            bank.addCustomer(customer2);

            account1.deposit(1000.0);
            account2.deposit(10000.0);
            account3.deposit(2000.0);
            account4.deposit(5000.0);

            saveBankDataToFile(bank);
        }
        return bank;
    }
    void saveBankDataToFile(Bank bank) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bankData.ser"))) {
            oos.writeObject(bank);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Bank readBankDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bankData.ser"))) {
            return (Bank) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
