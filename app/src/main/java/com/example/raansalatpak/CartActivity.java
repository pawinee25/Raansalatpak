package com.example.raansalatpak;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.example.raansalatpak.Model.Order;
import com.example.raansalatpak.Model.Product;
import com.example.raansalatpak.Model.ProductCart;

import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";

    private ArrayList<Order> items;
    private RecyclerView mRecyclerView;
    private ArrayList<ProductCart> productCarts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // key productCarts
        productCarts = getIntent().getParcelableArrayListExtra("productCarts");

//        String test = productCarts.toString();
//        Toast.makeText(getBaseContext(),""+test,Toast.LENGTH_SHORT).show();

        for (ProductCart productCart : productCarts) {
            Log.d(TAG, "onCreate: " + productCart.getFoodId() + ", " + productCart.getCount());
        }

    }
}
