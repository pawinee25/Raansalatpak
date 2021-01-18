package com.example.raansalatpak.Model;

import java.time.format.DateTimeFormatter;


public class Order {
    private int Order_ID;
    private int  Food_Price;
    private int  Price;
    private int  Qty;
    private DateTimeFormatter Order_Datetime;
    private int  Customer_ID;
    private int  Bill_NO;
    private int Food_ID;
    private int Shop_ID;

    public Order() {
    }

    public Order(int order_ID, int food_Price, int price, int qty, DateTimeFormatter order_Datetime, int customer_ID, int bill_NO, int food_ID, int shop_ID) {
        Order_ID = order_ID;
        Food_Price = food_Price;
        Price = price;
        Qty = qty;
        Order_Datetime = order_Datetime;
        Customer_ID = customer_ID;
        Bill_NO = bill_NO;
        Food_ID = food_ID;
        Shop_ID = shop_ID;
    }

    public int getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(int order_ID) {
        Order_ID = order_ID;
    }

    public int getFood_Price() {
        return Food_Price;
    }

    public void setFood_Price(int food_Price) {
        Food_Price = food_Price;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public DateTimeFormatter getOrder_Datetime() {
        return Order_Datetime;
    }

    public void setOrder_Datetime(DateTimeFormatter order_Datetime) {
        Order_Datetime = order_Datetime;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public int getBill_NO() {
        return Bill_NO;
    }

    public void setBill_NO(int bill_NO) {
        Bill_NO = bill_NO;
    }

    public int getFood_ID() {
        return Food_ID;
    }

    public void setFood_ID(int food_ID) {
        Food_ID = food_ID;
    }

    public int getShop_ID() {
        return Shop_ID;
    }

    public void setShop_ID(int shop_ID) {
        Shop_ID = shop_ID;
    }
}
