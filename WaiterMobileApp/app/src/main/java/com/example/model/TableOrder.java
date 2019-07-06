package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;

public class TableOrder implements Serializable {
    private static int orderNumber = 0;
    private int tableNumber;
    private ArrayList<String> dishes;
    private ArrayList<Double> prices;
    private String order;
    private double total;

    public TableOrder(int tableNumber){
        this.tableNumber = tableNumber;
        dishes = new ArrayList<>();
        prices = new ArrayList<>();
        order = "";
        total = 0.0;
        orderNumber++;
    }

    public static int getOrderNumber() {
        return orderNumber;
    }

    public static void setOrderNumber(int orderNumber) {
        TableOrder.orderNumber = orderNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public ArrayList<String> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<String> dishes) {
        this.dishes = dishes;
    }

    public ArrayList<Double> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Double> prices) {
        this.prices = prices;
    }

    public String getOrder() {
        for (String dish: dishes){
            order+=dish+",";
        }
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}