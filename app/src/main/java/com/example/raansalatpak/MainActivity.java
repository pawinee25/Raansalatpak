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

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewrecomment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mRecyclerView.setAdapter(new RecommentAdapter());
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

    @Override
    protected void onResume() {
        super.onResume();

        fetchProduct();
    }

    private void fetchProduct() {
        String sql = "SELECT * FROM `food`";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            items = new ArrayList<Product>();
                            while (resultSet.next()) {
                                Product product = new Product(
                                        resultSet.getInt(1),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4),
                                        resultSet.getInt(5),
                                        resultSet.getInt(6),
                                        resultSet.getInt(7)
                                );
                                items.add(product);
                            }

                            mRecyclerView.setAdapter(new RecommentAdapter());

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
    }

    private class RecommentAdapter extends RecyclerView.Adapter<ProducrtHolder> {
        @NonNull
        @Override
        public ProducrtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false);
            return new ProducrtHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProducrtHolder holder, int position) {
            Product product = items.get(position);
            holder.tvnamefoodth.setText(product.getFood_name());
            holder.tvnamefoodus.setText(product.getFood_nameus());
            holder.tvprice.setText(product.getPrice() + "");
            Dru.loadImageCircle(holder.ivimagefood, ConnectDB.BASE_IMAGE + product.getImagefood());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class ProducrtHolder extends RecyclerView.ViewHolder {
        private final ImageView ivimagefood;
        private final TextView tvnamefoodth;
        private final TextView tvnamefoodus;
        private final TextView tvprice;

        public ProducrtHolder(@NonNull View itemView) {
            super(itemView);
            ivimagefood = (ImageView) itemView.findViewById(R.id.iv_imagefood);
            tvnamefoodth = (TextView) itemView.findViewById(R.id.tv_namefoodth);
            tvnamefoodus = (TextView) itemView.findViewById(R.id.tv_namefoodus);
            tvprice = (TextView) itemView.findViewById(R.id.tv_price);

        }
    }
}



