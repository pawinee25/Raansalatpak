package com.example.raansalatpak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.example.raansalatpak.Model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView mTvNumberCounter;
    private static final String TAG = "OrderDetailActivity";
    private int Count = 0;
    private ImageView mImg_Imgae;
    private ImageView iv_imagefood;
    private TextView mTv_NameFoodTh;
    private TextView mTv_NameFoodUs;
    private TextView mTv_Detail_Food;
    private TextView mTv_Food_Price;
    private Object holder;
    private Product product;
    private Button mBtnDecrement;
    private Button mBtnIncrement;
    private TextView mTv_Detail_Food_Name;
    private String mTotal_Price;
    private int Food_Price;
    private int tv_food_price;
    private RelativeLayout mSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);


        mTvNumberCounter = (TextView) findViewById(R.id.tv_number_counter);
        mBtnDecrement = (Button) findViewById(R.id.btn_decrement);
        mBtnIncrement = (Button) findViewById(R.id.btn_increment);
        mImg_Imgae = (ImageView) findViewById(R.id.img_imgae);
        mTv_NameFoodTh = (TextView) findViewById(R.id.tv_namefoodth);
        mTv_NameFoodUs = (TextView) findViewById(R.id.tv_namefoodus);
        mTv_Detail_Food = (TextView) findViewById(R.id.tv_detail_food);
        mTv_Food_Price = (TextView) findViewById(R.id.tv_food_price);
        mSubmit = (RelativeLayout) findViewById(R.id.submit);


        int Food_ID = getIntent().getIntExtra("Food_ID", 0);
        String Food_Name = getIntent().getStringExtra("Food_Name");
        String Food_NameUS = getIntent().getStringExtra("Food_NameUS");
        int Food_Detail_ID = getIntent().getIntExtra("Food_Detail_ID", 0);
        String Food_Image = getIntent().getStringExtra("Food_Image");
        Food_Price = getIntent().getIntExtra("Food_Price", 0);
        mTv_NameFoodTh.setText(Food_Name);
        mTv_NameFoodUs.setText(Food_NameUS);
        //mTv_Detail_Food.setText(Food_Detail_ID);
        mTv_Food_Price.setText(Food_Price + "");
        Dru.loadImageCircle(mImg_Imgae, Food_Image);
        String sql = "SELECT Food_Detail_Properties FROM food  INNER JOIN fooddetail ON fooddetail.Food_Detail_ID & food.Food_Detail_ID = '" + Food_Detail_ID + "'";
        //String sql = "SELECT Food_Detail_Properties FROM food  INNER JOIN fooddetail ON food.Food_Detail_ID = '"+Food_Detail_ID+"' and fooddetail.Food_Detail_ID = '"+Food_Detail_ID+"'";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            while (resultSet.next()) {
                                //Log.d(TAG, "onComplete: "+Food_Detail_ID );
                                mTv_Detail_Food.setText(resultSet.getString("Food_Detail_Properties"));


                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });


        mBtnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
            }
        });
        mBtnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Food_Price * Count

                Intent intent = new Intent();
                intent.putExtra("Count", Count);
                intent.putExtra("Food_ID", Food_ID);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void decrement() {
        if (Count <= 0) Count = 0;
        else Count--;
        mTvNumberCounter.setText("" + Count);
    }

    private void increment() {
        Count++;
        mTvNumberCounter.setText("" + Count);
        //tv_food_price.setText(tv_food_price * mTvNumberCounter+""Count);
        mTotal_Price = String.format("%d", Food_Price * Count);
        mTv_Food_Price.setText(mTotal_Price);
        Log.d(TAG, "onComplete: " + Food_Price);

    }


}