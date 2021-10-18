package com.example.raansalatpak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteUpdate;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEdtname;
    private EditText mEdtsurname;
    private EditText mEdtusername;
    private EditText mEdtpassword;
    private EditText mEdtrepassword;
    private EditText mEdtdateofbirth;
    private EditText mEdtgender;
    private EditText mEdttel;
    private EditText mEdtemail;
    private Button mBtconfirm;
    private Button mBtcancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("กรอกรายละเอียดเพื่อสมัครสมาชิก");
        setSupportActionBar(toolbar);

        mEdtname = (EditText) findViewById(R.id.edt_name);
        mEdtsurname = (EditText) findViewById(R.id.edt_surname);
        mEdtusername = (EditText) findViewById(R.id.edt_username);
        mEdtpassword = (EditText) findViewById(R.id.edt_password);
        mEdtrepassword = (EditText) findViewById(R.id.edt_repassword);
        mEdtdateofbirth = (EditText) findViewById(R.id.edt_dateofbirth);
        mEdtgender = (EditText) findViewById(R.id.edt_gender);
        mEdttel = (EditText) findViewById(R.id.edt_tel);
        mEdtemail = (EditText) findViewById(R.id.edt_email);
        mBtconfirm = (Button) findViewById(R.id.bt_confirm);
        mBtcancel = (Button) findViewById(R.id.bt_cancel);



        mBtconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();

            }
        });
    }

    private void register() {
        String username = mEdtusername.getText().toString().trim();
        String name = mEdtname.getText().toString().trim();
        String surname = mEdtsurname.getText().toString().trim();
        String dateofbirth = mEdtdateofbirth.getText().toString().trim();
        String gender = mEdtgender.getText().toString().trim();
        String tel = mEdttel.getText().toString().trim();
        String email = mEdtemail.getText().toString().trim();
        String password = mEdtpassword.getText().toString().trim();

        String sql = "INSERT INTO `customer`(`UserName`, `Name`, `Surname`, `DateOfBirth`, `Gender`, `Tel`, `Email`, `Password`) " +
                "VALUES ('" + username + "','" + name + "','" + surname + "','" + dateofbirth + "','" + gender + "','" + tel + "','" + email + "','" + password + "')";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteUpdate() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(),LoginActivity.class));
                        finish();
                    }
                });

        mBtcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
            }
        });

    }
}

