package com.example.javajunction;

public class Recipe_Model {
    int pic;
    String text;

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Recipe_Model(int pic, String text) {
        this.pic = pic;
        this.text = text;
    }
}
