package com.bt.shared;

public class Seller extends User {

    // Specific attribute just for Sellers
    private double sellerRating;

    public Seller(String username, String email, String password, double initialRating) {
        super(username, email, password);
        this.sellerRating = initialRating;
    }

    public double getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(double sellerRating) {
        this.sellerRating = sellerRating;
    }

    @Override
    public String getRole() {
        return "SELLER";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Role: " + getRole() + " | Rating: " + sellerRating + "/5.0");
    }
}
