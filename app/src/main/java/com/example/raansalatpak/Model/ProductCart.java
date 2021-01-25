package com.example.raansalatpak.Model;

public class ProductCart {

    private int Food_id;
    private String Food_name;
    private String Food_nameus;
    private String imagefood;
    private int price;
    private int Food_detail_id;
    private int Food_type_id;
    private int count;

    public ProductCart() {
    }

    public ProductCart(int food_id, String food_name, String food_nameus, String imagefood, int price, int food_detail_id, int food_type_id, int count) {
        Food_id = food_id;
        Food_name = food_name;
        Food_nameus = food_nameus;
        this.imagefood = imagefood;
        this.price = price;
        Food_detail_id = food_detail_id;
        Food_type_id = food_type_id;
        this.count = count;
    }

    public int getFood_id() {
        return Food_id;
    }

    public void setFood_id(int food_id) {
        Food_id = food_id;
    }

    public String getFood_name() {
        return Food_name;
    }

    public void setFood_name(String food_name) {
        Food_name = food_name;
    }

    public String getFood_nameus() {
        return Food_nameus;
    }

    public void setFood_nameus(String food_nameus) {
        Food_nameus = food_nameus;
    }

    public String getImagefood() {
        return imagefood;
    }

    public void setImagefood(String imagefood) {
        this.imagefood = imagefood;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFood_detail_id() {
        return Food_detail_id;
    }

    public void setFood_detail_id(int food_detail_id) {
        Food_detail_id = food_detail_id;
    }

    public int getFood_type_id() {
        return Food_type_id;
    }

    public void setFood_type_id(int food_type_id) {
        Food_type_id = food_type_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

