package com.example.javajunction.ModelClass;

public class Add_To_cart_model {
    int Image;
    String Title, Description, Price;

    public Add_To_cart_model(int image, String title, String description, String price) {
        Image = image;
        Title = title;
        Description = description;
        Price = price;
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
