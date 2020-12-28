package com.example.raansalatpak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";
    private TextView mTvusername;
    private TextView mTvname;
    private TextView mTvsurname;
    private TextView mTvdateofbirth;
    private TextView mTvgender;
    private TextView mTvtel;
    private TextView mTvemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mTvusername = (TextView) findViewById(R.id.tv_username);
        mTvname = (TextView) findViewById(R.id.tv_name);
        mTvsurname = (TextView) findViewById(R.id.tv_surname);
        mTvdateofbirth = (TextView) findViewById(R.id.tv_dateofbirth);
        mTvgender = (TextView) findViewById(R.id.tv_gender);
        mTvtel = (TextView) findViewById(R.id.tv_tel);
        mTvemail = (TextView) findViewById(R.id.tv_email);

        String customerId = getSharedPreferences("file", MODE_PRIVATE).getString("customerId", "");
        String sql = "SELECT `UserName` ,`Name` ,`Surname`, `DateOfBirth`, `Gender`, `Tel` ,`Email` FROM `customer` WHERE `Customer_ID` = '" + customerId + "'";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            while (resultSet.next()) {
                                Log.d(TAG, "onComplete: ");
                                mTvusername.setText(resultSet.getString("UserName"));
                                mTvname.setText(resultSet.getString("Name"));
                                mTvsurname.setText(resultSet.getString("Surname"));
                                mTvdateofbirth.setText(resultSet.getString("DateOfBirth"));
                                mTvgender.setText(resultSet.getString("Gender"));
                                mTvtel.setText(resultSet.getString("Tel"));
                                mTvemail.setText(resultSet.getString("Email"));

                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}