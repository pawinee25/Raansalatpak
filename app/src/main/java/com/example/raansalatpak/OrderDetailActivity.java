package com.example.raansalatpak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView mTvNumberCounter;
    private int Count = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        mTvNumberCounter = (TextView) findViewById(R.id.tv_number_counter);


    }
    public  void increment(View v){
        Count++;
        mTvNumberCounter.setText(""+Count);


    }
    public void decrement(View v){
        if(Count <= 0) Count = 0;
        else Count--;
        mTvNumberCounter.setText(""+Count);
    }
}