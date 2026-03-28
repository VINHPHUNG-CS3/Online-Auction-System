package com.bt.shared;

/**
 * Abstract class representing an item up for auction.
 * Inherits from Entity to automatically get an ID.
 */
public abstract class Item extends Entity {

    private String name;
    private String description;
    private double startingPrice;

    public Item(String name, String description, double startingPrice) {
        super(); // Calls the Entity constructor to generate the unique ID
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    // Polymorphism: We are defining how displayInfo() works for an Item
    @Override
    public void displayInfo() {
        System.out.println("Item ID: " + getId() + " | Name: " + name + " | Starting Price: $" + startingPrice);
    }

    // Abstraction: Force child classes (Electronics/Art) to specify their category
    public abstract String getCategory();
}