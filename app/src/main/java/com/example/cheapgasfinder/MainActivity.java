package com.example.cheapgasfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    private EditText ed_username = null;
    private EditText ed_password = null;
    private Button bt_login = null;
    private Button bt_register = null;
    private TextView tv_error = null;
    String login = "ala.klein";
    String password = "25d55ad283aa400af464c76d713c07ad";

    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       ed_username = (EditText) findViewById( R.id.et_login);
       ed_password = (EditText) findViewById( R.id.et_password);
       bt_login = (Button) findViewById ( R.id.bt_login);
       bt_register = (Button) findViewById( R.id.bt_register);
       tv_error = (TextView) findViewById( R.id.tv_error);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_username.getText().toString().equals(login) && (getMd5Hash(ed_password.getText().toString()).equals(password))) {
                    Intent intent = new Intent(MainActivity.this, com.example.cheapgasfinder.home.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                }else{
                  tv_error.setText("Incorrect user or password!");
                }
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, com.example.cheapgasfinder.sign_up.class);
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });
    }
}