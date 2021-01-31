package com.example.raansalatpak;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.example.raansalatpak.Model.Cart;
import com.example.raansalatpak.Model.ProductCart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";

    private ArrayList<ProductCart> items;
    private RecyclerView mRecyclerView;
    private ArrayList<Cart> carts = new ArrayList<>();
    private TextView tv_food_price_total;
    private TextView tvprice;
    private int Count = 1;
    private int Food_Price;
    private TextView tv_number_counter;
    private String mTotal_Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tv_food_price_total = (TextView) findViewById(R.id.tv_food_price_total);
        tvprice = (TextView) findViewById(R.id.tv_price);
        tv_number_counter = (TextView) findViewById(R.id.tv_number_counter);

      //  tv_food_price_total = String.format("%d", Food_Price * Count);
        mTotal_Price = String.format("%d", Food_Price * Count);
        tv_food_price_total.setText(mTotal_Price);
        // key productCarts
        carts = getIntent().getParcelableArrayListExtra("carts");


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewCart);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        fetchProduct();
    }

    private void fetchProduct() {
        // SELECT * FROM food WHERE Food_ID IN (1,3,5)

        String query = "SELECT * FROM food WHERE Food_ID IN (";
        for (Cart item : carts) {
            query += "" + item.getFoodId() + ",";
        }
        query = query.substring(0, query.length() - 1);
        query += ")";

        Log.d(TAG, "fetchProduct: " + query);

        Dru.connection(ConnectDB.getConnection())
                .execute(query)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            items = new ArrayList<>();

                            int index = -1;

                            while (resultSet.next()) {
                                index++;


                                int count = carts.get(index).getCount();

                                ProductCart productCart = new ProductCart(
                                        resultSet.getInt(1),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4),
                                        resultSet.getInt(5),
                                        resultSet.getInt(6),
                                        resultSet.getInt(7),
                                        count
                                );
                                items.add(productCart);

                                Log.d(TAG, "onComplete: " + resultSet.getString(2));
                            }

                            mRecyclerView.setAdapter(new CartAdapter());

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });
    }

    private class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
            return new CartViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            ProductCart product = items.get(position);
            holder.tvnamefoodth.setText(product.getFood_name());
            holder.tv_number_counter.setText(String.valueOf(product.getCount()));
            holder.tvprice.setText(product.getPrice() + "");
            Dru.loadImageCircle(holder.iv_imagefood,  product.getImagefood());

            holder.btn_decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart cart = new Cart(
                            carts.get(position).getFoodId(),
                            carts.get(position).getCount() -1
                    );
                    carts.set(position, cart);
                    fetchProduct();
                }
            });

            holder.btn_increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart cart = new Cart(
                            carts.get(position).getFoodId(),
                            carts.get(position).getCount() + 1
                    );
                    carts.set(position, cart);
                    fetchProduct();
                }
            });

            holder.iv_imagedelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    carts.remove(position);
                    fetchProduct();
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class CartViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvnamefoodth;
        private final TextView tv_number_counter;
        private final Button btn_decrement;
        private final Button btn_increment;
        private final ImageView iv_imagedelete;
        private final TextView tvprice;
        private final TextView tvfoodcount;
        private final ImageView iv_imagefood;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnamefoodth = (TextView) itemView.findViewById(R.id.tv_namefoodth);
            tv_number_counter = (TextView) itemView.findViewById(R.id.tv_number_counter);
            btn_decrement = (Button) itemView.findViewById(R.id.btn_decrement);
            btn_increment = (Button) itemView.findViewById(R.id.btn_increment);
            iv_imagedelete = (ImageView) itemView.findViewById(R.id.iv_imagedelete);
            tvprice = (TextView)itemView.findViewById(R.id.tv_price);
            tvfoodcount = (TextView)itemView.findViewById(R.id.tv_food_count);
            iv_imagefood = (ImageView) itemView.findViewById(R.id.iv_imagefood);

        }
    }

}
