package com.example.raansalatpak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.example.raansalatpak.Model.Order;
import com.example.raansalatpak.Model.OrderDetail;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ReceiptActivity extends AppCompatActivity {

    private String orderId;
    private ArrayList<OrderDetail> orderDetails = new ArrayList<>();
    private ReceiptAdapter mAdapter = new ReceiptAdapter();

    private TextView tvOrderId;
    private TextView tvOrderDateTime;
    private TextView tvOrderQty;
    private TextView tvOrderTotalPrice;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        orderId = getIntent().getStringExtra("orderId");

        tvOrderId = (TextView) findViewById(R.id.tvOrderId);
        tvOrderDateTime = (TextView) findViewById(R.id.tvOrderDateTime);
        tvOrderQty = (TextView) findViewById(R.id.tvOrderQty);
        tvOrderTotalPrice = (TextView) findViewById(R.id.tvOrderTotalPrice);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(mAdapter);

        fetchReceipt();
    }

    private void fetchReceipt() {
        // order
        String sqlOrder = "SELECT * FROM `order` WHERE Order_ID = '" + orderId + "'";
        Dru.connection(ConnectDB.getConnection())
                .execute(sqlOrder)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            while (resultSet.next()) {
                                Order order = new Order(
                                        resultSet.getString(1),
                                        resultSet.getInt(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4)
                                );
                                tvOrderId.setText(order.getOrderId());
                                tvOrderDateTime.setText(order.getCreated());
                            }
                        } catch (Exception e) {
                        }
                    }
                });

        // order detail
        String sqlOrderDetail = "SELECT * FROM orderdetail INNER JOIN food ON orderdetail.Food_ID = food.Food_ID WHERE Order_ID = '" + orderId + "'";
        Dru.connection(ConnectDB.getConnection())
                .execute(sqlOrderDetail)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            while (resultSet.next()) {
                                OrderDetail orderDetail = new OrderDetail(
                                        resultSet.getInt(1),
                                        resultSet.getString(2),
                                        resultSet.getInt(3),
                                        resultSet.getInt(4),
                                        resultSet.getString(6),
                                        resultSet.getString(7),
                                        resultSet.getString(8),
                                        resultSet.getInt(9),
                                        resultSet.getInt(10),
                                        resultSet.getInt(11)
                                );
                                orderDetails.add(orderDetail);
                            }

                            mAdapter.setList(orderDetails);
                            calSumOrder();
                        } catch (Exception e) {
                        }
                    }
                });
    }

    private void calSumOrder() {
        int count = 0;
        int price = 0;
        for (OrderDetail orderDetail : orderDetails) {
            count += orderDetail.getQty();
            price += orderDetail.getQty() * orderDetail.getFoodPrice();
        }
        tvOrderQty.setText(String.valueOf(count));
        tvOrderTotalPrice.setText(String.valueOf(price));
    }

    private class ReceiptAdapter extends RecyclerView.Adapter<ReceiptViewHolder> {
        private ArrayList<OrderDetail> list = new ArrayList<>();

        @NonNull
        @Override
        public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false);
            return new ReceiptViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
            OrderDetail item = list.get(position);

            holder.tvFoodName.setText(item.getFoodName());
            holder.tvFoodQty.setText(String.valueOf(item.getQty()));
            holder.tvFoodPrice.setText(String.valueOf(item.getFoodPrice()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        void setList(ArrayList<OrderDetail> list) {
            this.list = list;
            notifyDataSetChanged();
        }

    }

    private class ReceiptViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFoodName;
        private final TextView tvFoodQty;
        private final TextView tvFoodPrice;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = (TextView) itemView.findViewById(R.id.tvFoodName);
            tvFoodQty = (TextView) itemView.findViewById(R.id.tvFoodQty);
            tvFoodPrice = (TextView) itemView.findViewById(R.id.tvFoodPrice);
        }
    }

}
