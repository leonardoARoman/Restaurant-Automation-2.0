package com.example.model;

import java.util.ArrayList;

public class Dish {
    private String dish;
    private int menuNo;
    private int dishNo;
    private double price;
    private boolean isSelected;

    public Dish(int menuNo, int dishNo, String dish, double price, boolean isSelected) {
        this.dish = dish;
        this.menuNo = menuNo;
        this.dishNo = dishNo;
        this.price = price;
        this.isSelected = isSelected;
    }

    public String getDish() {
        return dish;
    }

    public int getMenuNo() {
        return menuNo;
    }

    public int getDishNo() {
        return dishNo;
    }

    public double getPrice() {
        return price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
