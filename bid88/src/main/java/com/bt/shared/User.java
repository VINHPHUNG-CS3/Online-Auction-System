package com.bt.shared;

/**
 * Abstract class representing a generic user.
 * Inherits from Entity to automatically get an ID.
 */
public abstract class User extends Entity {

    // Encapsulation: Keeping user data safe
    private String username;
    private String email;
    private String password;
    private String role;

    public User() { } // Constructor rỗng

    public User(String username, String email, String password) {
        super(); // Calls the Entity constructor to generate the unique ID
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role; 
    }

    // Polymorphism: We are defining how displayInfo() works for a User
    @Override
    public void displayInfo() {
        System.out.println("User ID: " + getId() + " | Username: " + username + " | Email: " + email);
    }

    // Abstraction: Force child classes (Bidder/Seller) to declare their role
    public abstract String getRole();
}