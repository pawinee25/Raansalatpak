package com.example.raansalatpak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raansalatpak.Model.Product;
import com.example.raansalatpak.Model.Cart;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int REQUEST_CODE_CART = 101;
    private Button mTvrecommend;
    private Button mTvsalat;
    private Button mTvspaghetti;
    private Button mTvsteak;
    private Button mTvdrinks;
    private static final String TAG = "MainActivity";
    private ArrayList<Product> items;
    private RecyclerView mRecyclerView;
    private TextView mTvOrder;
    private ImageView mIvCart;

    private ArrayList<Cart> carts = new ArrayList<>();

    private Recommend_Fragment.SetOnOrderListener recommendListener = new Recommend_Fragment.SetOnOrderListener() {
        @Override
        public void onOrder(int foodId, int count) {
//            String text = mTvOrder.getText().toString();
//            int countLast = Integer.parseInt(text);
//            String show = String.valueOf(countLast + count);
//            mTvOrder.setText(show);

            carts.add(new Cart(foodId, count));
            int countAll = 0;
            for (Cart productCart : carts) {
                countAll += productCart.getCount();
            }
            mTvOrder.setText(String.valueOf(countAll));
        }
    };

    private Spaghetti_Fragment.SetOnOrderListener spaghettiListener = new Spaghetti_Fragment.SetOnOrderListener() {
        @Override
        public void onOrder(int foodId, int count) {
//            String text = mTvOrder.getText().toString();
//            int countLast = Integer.parseInt(text);
//            String show = String.valueOf(countLast + count);
//            mTvOrder.setText(show);

            carts.add(new Cart(foodId, count));
            int countAll = 0;
            for (Cart productCart : carts) {
                countAll += productCart.getCount();
            }
            mTvOrder.setText(String.valueOf(countAll));
        }
    };

    private Steak_Fragment.SetOnOrderListener steakListener = new Steak_Fragment.SetOnOrderListener() {
        @Override
        public void onOrder(int foodId, int count) {
//            String text = mTvOrder.getText().toString();
//            int countLast = Integer.parseInt(text);
//            String show = String.valueOf(countLast + count);
//            mTvOrder.setText(show);

            carts.add(new Cart(foodId, count));
            int countAll = 0;
            for (Cart productCart : carts) {
                countAll += productCart.getCount();
            }
            mTvOrder.setText(String.valueOf(countAll));
        }
    };

    private Salat_Fragment.SetOnOrderListener salatListener = new Salat_Fragment.SetOnOrderListener() {
        @Override
        public void onOrder(int foodId, int count) {
//            String text = mTvOrder.getText().toString();
//            int countLast = Integer.parseInt(text);
//            String show = String.valueOf(countLast + count);
//            mTvOrder.setText(show);

            carts.add(new Cart(foodId, count));
            int countAll = 0;
            for (Cart productCart : carts) {
                countAll += productCart.getCount();
            }
            mTvOrder.setText(String.valueOf(countAll));
        }
    };

    private Drinks_Fragment.SetOnOrderListener drinkListener = new Drinks_Fragment.SetOnOrderListener() {
        @Override
        public void onOrder(int foodId, int count) {
//            String text = mTvOrder.getText().toString();
//            int countLast = Integer.parseInt(text);
//            String show = String.valueOf(countLast + count);
//            mTvOrder.setText(show);

            carts.add(new Cart(foodId, count));
            int countAll = 0;
            for (Cart productCart : carts) {
                countAll += productCart.getCount();
            }
            mTvOrder.setText(String.valueOf(countAll));
        }
    };


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
        toolbar.setTitle("Halal Food Delivery");
        setSupportActionBar(toolbar);

        mTvrecommend = (Button) findViewById(R.id.tv_recommend);
        mTvsalat = (Button) findViewById(R.id.tv_salat);
        mTvspaghetti = (Button) findViewById(R.id.tv_spaghetti);
        mTvsteak = (Button) findViewById(R.id.tv_steak);
        mTvdrinks = (Button) findViewById(R.id.tv_drinks);
        mTvOrder = (TextView) findViewById(R.id.tvOrder);
        mIvCart = (ImageView) findViewById(R.id.iv_imageCart);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, new Recommend_Fragment(recommendListener))
                    .commit();
        }

        mTvrecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Recommend_Fragment(recommendListener))
                        .commit();

//                Toast.makeText(getBaseContext(),"mTvrecommend",Toast.LENGTH_SHORT).show();
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, new Salat_Fragment(salatListener))
                    .commit();
        }

        mTvsalat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Salat_Fragment(salatListener))
                        .commit();

//                Toast.makeText(getBaseContext(),"mTvsalat",Toast.LENGTH_SHORT).show();
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, new Spaghetti_Fragment(spaghettiListener))
                    .commit();
        }

        mTvspaghetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Spaghetti_Fragment(spaghettiListener))
                        .commit();
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, new Steak_Fragment(steakListener))
                    .commit();
        }

        mTvsteak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Steak_Fragment(steakListener))
                        .commit();
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, new Drinks_Fragment(drinkListener))
                    .commit();
        }

        mTvdrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, new Drinks_Fragment(drinkListener))
                        .commit();

            }
        });
        mIvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!carts.isEmpty()) {
                    Intent intent = new Intent(getBaseContext(), CartActivity.class);
                    intent.putParcelableArrayListExtra("carts", carts);
                    startActivityForResult(intent, REQUEST_CODE_CART);
                }
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
                startActivity(new Intent(getBaseContext(), ShopInformationActivity.class));
                break;
            case R.id.nav_orders:
                startActivity(new Intent(getBaseContext(), OrderActivity.class));
                break;
            case R.id.nav_history:
                startActivity(new Intent(getBaseContext(), HistoryActivity.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(getBaseContext(), UserInfoActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CART && resultCode == Activity.RESULT_OK) {
            carts.clear();
            mTvOrder.setText(String.valueOf(carts.size()));
            startActivity(new Intent(getBaseContext(), OrderActivity.class));
        }
    }
}



