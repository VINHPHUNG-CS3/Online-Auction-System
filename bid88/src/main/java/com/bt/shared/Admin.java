package com.bt.shared;

public class Admin extends User {

    // Specific attribute just for Admins (e.g., 1 = Moderator, 5 = Super Admin)
    private int accessLevel;

    public Admin(String username, String email, String password, int accessLevel) {
        super(username, email, password); // Pass basic info up to the User class
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    // Fulfilling the Abstraction requirement from User
    @Override
    public String getRole() {
        return "ADMIN";
    }

    // Polymorphism: Adding access level info to the standard display
    @Override
    public void displayInfo() {
        super.displayInfo(); // Prints the standard ID, Username, Email
        System.out.println("Role: " + getRole() + " | Access Level: " + accessLevel);
    }

    // Example of an Admin-specific action
    public void banUser(User user) {
        System.out.println("ADMIN ACTION: " + this.getUsername() + " has banned user " + user.getUsername());
        // In the future, you could add a 'boolean isActive' to the User class and set
        // it to false here.
    }
}