package com.example.raansalatpak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShopInformationActivity extends AppCompatActivity {

    private TextView mTvnameraan;
    private TextView mTvprovince;
    private TextView mTvsubdistrict;
    private TextView mTvdistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);

        mTvnameraan = (TextView) findViewById(R.id.tv_nameraan);
        mTvprovince = (TextView) findViewById(R.id.tv_province);
        mTvsubdistrict = (TextView) findViewById(R.id.tv_subdistrict);
        mTvdistrict = (TextView) findViewById(R.id.tv_district);

        String sql = "SELECT `Shop_Name`,`Shop_Province`,`Shop_Subdistrict`,`Shop_District` FROM `shop` ";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            while (resultSet.next()) {
                                mTvnameraan.setText(resultSet.getString("Shop_Name"));
                                mTvprovince.setText(resultSet.getString("Shop_Province"));
                                mTvsubdistrict.setText(resultSet.getString("Shop_Subdistrict"));
                                mTvdistrict.setText(resultSet.getString("Shop_District"));

                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}