package com.example.raansalatpak.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {

    private int foodId;
    private int count;

    public Cart() {
    }

    public Cart(int foodId, int count) {
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

    public static final Parcelable.Creator<Cart> CREATOR
            = new Parcelable.Creator<Cart>() {
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    private Cart(Parcel in) {
        foodId = in.readInt();
        count = in.readInt();
    }

}
