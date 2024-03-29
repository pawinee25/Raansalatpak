package com.example.raansalatpak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.example.raansalatpak.Model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Recommend_Fragment extends Fragment {

    //private static final String TAG = "Recommend_Fragment";
    private static final String TAG = "TestPutExtra";
    private RecyclerView mRecyclerView;
    private ArrayList<Product> items;

    private SetOnOrderListener listener;

    public Recommend_Fragment(SetOnOrderListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewrecomment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new RecommentAdapter());
    }


    public void onResume() {
        super.onResume();
        fetchProduct();
    }

    private void fetchProduct() {
        String sql = "SELECT f.Food_ID,f.Food_Name,f.Food_NameUS,f.Food_Image,f.Food_Price,f.Food_Detail_ID,f.Food_Type_ID, orderdetail.Food_ID,orderdetail.Qty  FROM food f INNER JOIN orderdetail  ON orderdetail.Food_ID = f.Food_ID AND orderdetail.Qty>3 GROUP BY f.Food_ID DESC LIMIT 10";
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reccommend2, parent, false);
            return new ProducrtHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProducrtHolder holder, int position) {
            Product product = items.get(position);
            holder.tvnamefoodth.setText(product.getFood_name());
            holder.tvnamefoodus.setText(product.getFood_nameus());
            holder.tvprice.setText(product.getPrice() + "");
            Dru.loadImageCircle(holder.ivimagefood, product.getImagefood());
            Log.d(TAG, "onBindViewHolder: ");

        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    public class ProducrtHolder extends RecyclerView.ViewHolder {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = items.get(getAdapterPosition());
                    Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                    intent.putExtra("Food_ID", product.getFood_id());
                    intent.putExtra("Food_Name", product.getFood_name());
                    intent.putExtra("Food_Image", product.getImagefood());
                    intent.putExtra("Food_NameUS", product.getFood_nameus());
                    intent.putExtra("Food_Detail_ID", product.getFood_detail_id());
                    intent.putExtra("Food_Price", product.getPrice());
                    Log.d(TAG, "ocClick: " + product.getImagefood());
//                    startActivity(intent);

                    startActivityForResult(intent, 999);
                }
            });


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999 && resultCode == Activity.RESULT_OK && data != null) {
            int count = data.getIntExtra("Count", 0);
            int foodId = data.getIntExtra("Food_ID", 0);
            listener.onOrder(foodId, count);
//            Toast.makeText(getContext(), "" + count + " " + Food_ID, Toast.LENGTH_SHORT).show();
        }
    }

    interface SetOnOrderListener {
        void onOrder(int foodId, int count);
    }

}


