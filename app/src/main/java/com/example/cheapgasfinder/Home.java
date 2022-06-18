package com.example.cheapgasfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btRegisterGs = null;
    private Button btMap = null;
    private Button btLogout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        btRegisterGs = findViewById(R.id.btRegisterGasStation);
        btMap = findViewById(R.id.btMap);
        btLogout = findViewById(R.id.btLogout);

        btRegisterGs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, GasStationRegistration.class);
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                finish();
            }
        });

        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, MapsActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                finish();
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(Home.this, MainActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                finish();
            }
        });

    }
}