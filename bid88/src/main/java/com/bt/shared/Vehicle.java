package com.bt.shared;

public class Vehicle extends Item {

    // Specific attributes just for Vehicles
    private String make;
    private String model;
    private int mileage;

    public Vehicle(String name, String description, double startingPrice, String make, String model, int mileage) {
        super(name, description, startingPrice); // Pass basic info up to the Item class
        this.make = make;
        this.model = model;
        this.mileage = mileage;
    }

    // Getters and Setters
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    // Fulfilling the Abstraction requirement from Item
    @Override
    public String getCategory() {
        return "VEHICLE";
    }

    // Polymorphism: Adding vehicle info to the standard display
    @Override
    public void displayInfo() {
        super.displayInfo(); // Prints the standard ID, Name, Starting Price
        System.out.println(
                "Category: " + getCategory() + " | Vehicle: " + make + " " + model + " | Mileage: " + mileage + " km");
    }
}