package com.example.raansalatpak.Model;

public class ProductCart {

    private int foodId;
    private int count;

    public ProductCart() {
    }

    public ProductCart(int foodId, int count) {
        this.foodId = foodId;
        this.count = count;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
