package com.example.raansalatpak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.example.raansalatpak.Model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTvrecommend;
    private TextView mTvsalat;
    private TextView mTvspaghetti;
    private TextView mTvsteak;
    private TextView mTvdrinks;
    private static final String TAG = "MainActivity";
    private ArrayList<Product> items;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ConnectDB.getConnection() == null) {
            Toast.makeText(getBaseContext(), "NULL", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Main");
        setSupportActionBar(toolbar);

        mTvrecommend = (TextView) findViewById(R.id.tv_recommend);
        mTvsalat = (TextView) findViewById(R.id.tv_salat);
        mTvspaghetti = (TextView) findViewById(R.id.tv_spaghetti);
        mTvsteak = (TextView) findViewById(R.id.tv_steak);
        mTvdrinks = (TextView) findViewById(R.id.tv_drinks);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, new Recommend_Fragment())
                    .commit();
        }

        mTvrecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Recommend_Fragment())
                        .commit();

//                Toast.makeText(getBaseContext(),"mTvrecommend",Toast.LENGTH_SHORT).show();
            }
        });

        mTvsalat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Salat_Fragment())
                        .commit();

//                Toast.makeText(getBaseContext(),"mTvsalat",Toast.LENGTH_SHORT).show();
            }
        });

        mTvspaghetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Spaghetti_Fragment())
                        .commit();
            }
        });

        mTvsteak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Steak_Fragment())
                        .commit();
            }
        });

        mTvdrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Drinks_Fragment())
                        .commit();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main:
                startActivity(new Intent(getBaseContext(),ShopInformationActivity.class));
                break;
            case R.id.nav_orders:
                Toast.makeText(getBaseContext(), "nav_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_history:
                Toast.makeText(getBaseContext(), "nav_history", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile:
                startActivity(new Intent(getBaseContext(), UserInfoActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}



