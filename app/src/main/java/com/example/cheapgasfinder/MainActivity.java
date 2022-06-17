package com.example.cheapgasfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private EditText edUsername = null;
    private EditText edPassword = null;
    private Button btLogin = null;
    private Button btRegister = null;
    private TextView tvError = null;
    //String login = "ala.klein@teste.com";
    //String password = "25d55ad283aa400af464c76d713c07ad";

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

       mAuth = FirebaseAuth.getInstance();
       edUsername = (EditText) findViewById( R.id.et_login);
       edPassword = (EditText) findViewById( R.id.et_password);
       btLogin = (Button) findViewById ( R.id.bt_login);
       btRegister = (Button) findViewById( R.id.bt_register);
       tvError = (TextView) findViewById( R.id.tv_error);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (edUsername.getText().toString().equals(login) && (getMd5Hash(edPassword.getText().toString()).equals(password))) {
                String email = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    mAuth.signInWithEmailAndPassword(email,getMd5Hash(password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                intent.setAction(Intent.ACTION_VIEW);
                                startActivity(intent);
                            }else {
                                tvError.setText("Incorrect user or password!");
                            }
                        }
                    });
                }
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUsername.setText("");
                edPassword.setText("");
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentuser == null) {

        }
    }
}