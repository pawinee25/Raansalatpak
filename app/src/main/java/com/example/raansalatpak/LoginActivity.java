package com.example.raansalatpak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    private Button mBtnregister;
    private Button mBtnlogin;
    private EditText mEdtusername;
    private EditText mEdtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnregister = (Button) findViewById(R.id.btn_register);
        mBtnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });

        mEdtusername = (EditText) findViewById(R.id.edt_username);
        mEdtpassword = (EditText) findViewById(R.id.edt_password);

        mBtnlogin = (Button) findViewById(R.id.btn_login);
        mBtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEdtusername.getText().toString().trim();
                String password = mEdtpassword.getText().toString().trim();
                login(username, password);
            }
        });
    }

    private void login(String username, String password) {
        String sql = "SELECT Customer_ID FROM healthy.customer where UserName = '" + username + "' and Password = '" + password + "'";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            if (resultSet.next()) {
                                getSharedPreferences("file",MODE_PRIVATE).edit()
                                        .putString("customerId",resultSet.getString("Customer_ID"))
                                        .apply();
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getBaseContext(), "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
    }
}