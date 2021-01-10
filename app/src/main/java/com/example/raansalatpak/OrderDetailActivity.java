package com.example.raansalatpak;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adedom.library.Dru;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView mTvNumberCounter;
    private int Count = 0 ;
    private ImageView mImg_Imgae;
    private  ImageView iv_imagefood;
    private TextView mTv_NameFoodTh;
    private TextView mTv_NameFoodUs;
    private TextView mTv_Detail_Food;
    private TextView mTv_Food_Price;
    private Object holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        mTvNumberCounter = (TextView) findViewById(R.id.tv_number_counter);

        String Food_ID = getIntent().getStringExtra("Food_ID");
        String Food_Name = getIntent().getStringExtra("Food_Name");
        String Food_NameUS = getIntent().getStringExtra("Food_NameUS");
        String Food_Detail_ID = getIntent().getStringExtra("Food_Detail_ID");
        String Food_Image = getIntent().getStringExtra("Food_Image");
        int Food_Price = getIntent().getIntExtra("Food_Price",0);

        mImg_Imgae = (ImageView) findViewById(R.id.img_imgae);
        mTv_NameFoodTh = (TextView) findViewById(R.id.tv_namefoodth);
        mTv_NameFoodUs = (TextView) findViewById(R.id.tv_namefoodus);
        mTv_Detail_Food = (TextView) findViewById(R.id.tv_detail_food);
        mTv_Food_Price = (TextView)findViewById(R.id.tv_food_price);


        mTv_NameFoodTh.setText(Food_Name);
        mTv_NameFoodUs.setText(Food_NameUS);
        mTv_Detail_Food.setText(Food_Detail_ID);
        mTv_Food_Price.setText(Food_Price+"");
        mImg_Imgae.setImageDrawable(Drawable.createFromPath(Food_Image));
        //Dru.loadImageCircle(holder.iv_imagefood,  product.getImagefood());


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