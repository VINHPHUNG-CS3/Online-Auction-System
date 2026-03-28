package com.bt.shared;

public class Bidder extends User {

    // Specific attribute just for Bidders
    private double accountBalance;

    public Bidder(String username, String email, String password, double startingBalance) {
        super(username, email, password); // Pass basic info up to the User class
        this.accountBalance = startingBalance;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    // Fulfilling the Abstraction requirement from User
    @Override
    public String getRole() {
        return "BIDDER";
    }

    // Polymorphism: Adding balance info to the standard display
    @Override
    public void displayInfo() {
        super.displayInfo(); // Prints the standard ID, Username, Email
        System.out.println("Role: " + getRole() + " | Balance: $" + accountBalance);
    }
}