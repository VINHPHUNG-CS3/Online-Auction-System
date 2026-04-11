package com.bt.shared;

import java.util.UUID;

/**
 * The base abstract class for all objects in the system.
 */
public abstract class Entity {

    // Encapsulation: Private field, only accessible via getter
    private String id;

    public Entity() {
        // Automatically generate a unique random ID for every new entity created
        this.id = UUID.randomUUID().toString();
    }
    
    public Entity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    // Abstraction: Any class that inherits Entity MUST create its own version of
    // this method
    public abstract void displayInfo();
}