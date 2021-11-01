package com.example.raansalatpak;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.adedom.library.ExecuteUpdate;
import com.example.raansalatpak.Model.Cart;
import com.example.raansalatpak.Model.ProductCart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";

    private RecyclerView mRecyclerView;
    private ArrayList<Cart> carts = new ArrayList<>();
    private TextView tv_food_price_total;
    private TextView tvprice;
    private int Count = 1;
    private int Food_Price;
    private TextView tv_number_counter;
    private String mTotal_Price;
    private TextView mCartCountSum;
    private Button mBtn_Submit_Cart;
    private ArrayList<ProductCart> productCarts = new ArrayList<>();
    private CartAdapter mAdapter = new CartAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tv_food_price_total = (TextView) findViewById(R.id.tv_food_price_total);
        tvprice = (TextView) findViewById(R.id.tv_price);
        tv_number_counter = (TextView) findViewById(R.id.tv_number_counter);
        mCartCountSum = (TextView) findViewById(R.id.cartCountSum);

        //  tv_food_price_total = String.format("%d", Food_Price * Count);
        mTotal_Price = String.format("%d", Food_Price * Count);
        tv_food_price_total.setText(mTotal_Price);
        // key productCarts
        carts = getIntent().getParcelableArrayListExtra("carts");
        mBtn_Submit_Cart = (Button) findViewById(R.id.btn_Submit_Cart);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewCart);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mRecyclerView.setAdapter(mAdapter);

        mBtn_Submit_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOrderConfirm();
            }
        });

        fetchProduct();
    }

    private void dialogOrderConfirm() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("ยืนยันคำสั่งซื้อ");
        dialog.setMessage("คุณต้องการสั่งอาหารใช่หรือไม่?   กรุณามารับอาหารหลังจากที่สั่งในอีก45นาที");
        dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                insertOrder();
            }
        });
        dialog.show();
    }

    private void insertOrder() {
        String uuid = UUID.randomUUID().toString().replace("-","");
        String customerId = getSharedPreferences("file",MODE_PRIVATE).getString("customerId","");
        String sqlOrder = "INSERT INTO `order`(`Order_ID`, `Customer_ID`, `Status`, `Created`) " +
                "VALUES ('"+uuid+"' ,"+customerId+" ,'0' ,CURRENT_TIMESTAMP)";
        Dru.connection(ConnectDB.getConnection())
                .execute(sqlOrder)
                .commit(new ExecuteUpdate() {
                    @Override
                    public void onComplete() {
                        for (ProductCart productCart : productCarts) {
                            String sqlOrderDetail = "INSERT INTO `orderdetail`( `Order_ID`, `Food_ID`, `Qty`) " +
                                    "VALUES ('"+uuid+"',"+productCart.getFood_id()+" ,"+productCart.getCount()+")";
                            Dru.connection(ConnectDB.getConnection())
                                    .execute(sqlOrderDetail)
                                    .commit(new ExecuteUpdate() {
                                        @Override
                                        public void onComplete() {
                                            Toast.makeText(CartActivity.this, "Insert order success", Toast.LENGTH_SHORT).show();
                                            setResult(Activity.RESULT_OK);
                                            finish();
                                        }
                                    });
                        }
                    }
                });
    }

    private void cartSum() {
        int count = 0;
        int price = 0;
        for (ProductCart productCart : productCarts) {
            count += productCart.getCount();
            price += productCart.getCount() * productCart.getPrice();
        }
        mCartCountSum.setText(String.valueOf(count));
        tv_food_price_total.setText(String.valueOf(price));
    }

    private void fetchProduct() {
        // SELECT * FROM food WHERE Food_ID IN (1,3,5)

        String query = "SELECT * FROM food WHERE Food_ID IN (";
        for (Cart item : carts) {
            query += "" + item.getFoodId() + ",";
        }
        query = query.substring(0, query.length() - 1);
        query += ")";

        Dru.connection(ConnectDB.getConnection())
                .execute(query)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            int count = 0;
                            while (resultSet.next()) {
                                for (Cart cart : carts) {
                                    if (cart.getFoodId() == resultSet.getInt(1)) {
                                        count = cart.getCount();
                                    }
                                }

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
                                productCarts.add(productCart);
                            }

                            mAdapter.setList(productCarts);
                            cartSum();

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });
    }

    private class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

        private ArrayList<ProductCart> items = new ArrayList<>();

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
            Dru.loadImageCircle(holder.iv_imagefood, product.getImagefood());

            holder.btn_decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = productCarts.indexOf(product);
                    ProductCart element = new ProductCart(
                            product.getFood_id(),
                            product.getFood_name(),
                            product.getFood_nameus(),
                            product.getImagefood(),
                            product.getPrice(),
                            product.getFood_detail_id(),
                            product.getFood_type_id(),
                            product.getCount() + 1
                    );
                    productCarts.set(index, element);

                    setList(productCarts);
                    cartSum();
                }
            });

            holder.btn_increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = productCarts.indexOf(product);
                    ProductCart element = new ProductCart(
                            product.getFood_id(),
                            product.getFood_name(),
                            product.getFood_nameus(),
                            product.getImagefood(),
                            product.getPrice(),
                            product.getFood_detail_id(),
                            product.getFood_type_id(),
                            product.getCount() - 1
                    );
                    if (element.getCount() <= 0) {
                        return;
                    }
                    productCarts.set(index, element);

                    setList(productCarts);
                    cartSum();
                }
            });

            holder.iv_imagedelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productCarts.remove(position);
                    setList(productCarts);
                    cartSum();
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        void setList(ArrayList<ProductCart> items) {
            this.items = items;
            notifyDataSetChanged();
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
            tvprice = (TextView) itemView.findViewById(R.id.tv_price);
            tvfoodcount = (TextView) itemView.findViewById(R.id.tv_food_count);
            iv_imagefood = (ImageView) itemView.findViewById(R.id.iv_imagefood);

        }
    }

}
