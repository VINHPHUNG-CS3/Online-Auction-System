package com.bt.shared;

public class ItemFactory {
    
    //Các tham số chung và mảng Varargs extraInfo cho phép nhập vào nhiều chuỗi extra tùy theo loại sản phẩm
    public static Item createItem(String itemType, String name, String description, double startingPrice, String... extraInfo) {
        if (itemType == null) {
            return null;
        }
        
        switch (itemType.toUpperCase()) {
            case "ELECTRONICS":
                // Electronics cần 2 extraInfo: brand (Vị trí 0) và warrantyMonths (Vị trí 1)
                String brand = extraInfo[0];
                int warranty = Integer.parseInt(extraInfo[1]); // Ép kiểu từ String sang số nguyên
                
                return new Electronics(name, description, startingPrice, brand, warranty); 
                
            case "ART":
                // Art cần 2 extraInfo: artist (Vị trí 0) và yearCreated (Vị trí 1)
                String artist = extraInfo[0];
                int year = Integer.parseInt(extraInfo[1]);
                
                return new Art(name, description, startingPrice, artist, year);
                
            case "VEHICLE":
                // Vehicle cần 3 extraInfo: make (Vị trí 0), model (Vị trí 1) và mileage (Vị trí 2)
                String make = extraInfo[0];
                String model = extraInfo[1];
                int mileage = Integer.parseInt(extraInfo[2]); 
                
                return new Vehicle(name, description, startingPrice, make, model, mileage);
                
            default:
                throw new IllegalArgumentException("Loại sản phẩm không hợp lệ: " + itemType);
        }
    }
}