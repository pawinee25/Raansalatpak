package com.example.raansalatpak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTvrecommend;
    private TextView mTvsalat;
    private TextView mTvspaghetti;
    private TextView mTvsteak;
    private TextView mTvdrinks;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ConnectDB.getConnection() == null){
            Toast.makeText(getBaseContext(),"NULL",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getBaseContext(),"OK",Toast.LENGTH_SHORT).show();
        }

        mTvrecommend = (TextView) findViewById(R.id.tv_recommend);
        mTvsalat = (TextView) findViewById(R.id.tv_salat);
        mTvspaghetti = (TextView) findViewById(R.id.tv_spaghetti);
        mTvsteak = (TextView) findViewById(R.id.tv_steak);
        mTvdrinks = (TextView) findViewById(R.id.tv_drinks);

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout,new Recommend_Fragment())
                    .commit();
        }

        mTvrecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout,new Recommend_Fragment())
                        .commit();
            }
        });

        mTvsalat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout,new Salat_Fragment())
                        .commit();
            }
        });

        mTvspaghetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout,new Spaghetti_Fragment())
                        .commit();
            }
        });

        mTvsteak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout,new Steak_Fragment())
                        .commit();
            }
        });

        mTvdrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout,new Drinks_Fragment())
                        .commit();
            }
        });
//        Log.d(TAG, "onCreate: ");
    }

}