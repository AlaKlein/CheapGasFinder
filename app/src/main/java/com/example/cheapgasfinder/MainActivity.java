package com.example.cheapgasfinder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
    private CheckBox cbPassword = null;
    private ProgressBar signInProgressBar = null;
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        edUsername = (EditText) findViewById(R.id.etEmail);
        edPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btSignIn);
        btRegister = (Button) findViewById(R.id.btSignUp);
        cbPassword = (CheckBox) findViewById(R.id.cbPassword2);
        signInProgressBar = (ProgressBar) findViewById(R.id.signUnProgressBar);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edUsername.getText().toString())) {
                    edUsername.setError("Field can't be empty!");
                } else if (TextUtils.isEmpty(edPassword.getText().toString())) {
                    edPassword.setError("Field can't be empty!");
                } else {
                    signInProgressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(edUsername.getText().toString(), getMd5Hash(edPassword.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                intent.setAction(Intent.ACTION_VIEW);
                                startActivity(intent);
                                signInProgressBar.setVisibility(View.INVISIBLE);
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                signInProgressBar.setVisibility(View.INVISIBLE);
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

        cbPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbPassword.isChecked()) {
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentuser == null) {
            //Intent intent = new Intent(MainActivity.this, MainActivity.class);
            //intent.setAction(Intent.ACTION_VIEW);
            //startActivity(intent);
        }
    }
}