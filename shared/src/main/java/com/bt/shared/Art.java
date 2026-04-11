package com.bt.shared;

public class Art extends Item {

    private String artist;
    private int yearCreated;

    public Art(String name, String description, double startingPrice, String artist, int yearCreated) {
        super(name, description, startingPrice);
        this.artist = artist;
        this.yearCreated = yearCreated;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(int yearCreated) {
        this.yearCreated = yearCreated;
    }

    @Override
    public String getCategory() {
        return "ART";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Category: " + getCategory() + " | Artist: " + artist + " | Year: " + yearCreated);
    }
}