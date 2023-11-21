package com.example.javajunction.ModelClass;

public class Add_To_cart_model {
    private String key; // Add a private field for the key
    int Image;
    String Title, Description, Price;

    // Constructor with key parameter
    public Add_To_cart_model(int image, String title, String description, String price) {
        Image = image;
        Title = title;
        Description = description;
        Price = price;
        this.key = key;
    }

    // Getter and setter for the key
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    // Other getters and setters for your model class

    // Default constructor required for Firebase
    public Add_To_cart_model() {
        // Required empty public constructor
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
