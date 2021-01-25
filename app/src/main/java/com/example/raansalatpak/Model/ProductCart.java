package com.example.raansalatpak.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductCart implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(foodId);
        dest.writeInt(count);
    }

    public static final Parcelable.Creator<ProductCart> CREATOR
            = new Parcelable.Creator<ProductCart>() {
        public ProductCart createFromParcel(Parcel in) {
            return new ProductCart(in);
        }

        public ProductCart[] newArray(int size) {
            return new ProductCart[size];
        }
    };

    private ProductCart(Parcel in) {
        foodId = in.readInt();
        count = in.readInt();
    }

}
