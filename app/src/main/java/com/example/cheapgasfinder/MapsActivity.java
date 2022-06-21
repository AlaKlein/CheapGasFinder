package com.example.cheapgasfinder;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cheapgasfinder.components.PositionDialog;
import com.example.cheapgasfinder.db.Position;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.cheapgasfinder.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity {

    private ActivityMapsBinding binding;
    private FirebaseAuth mAuth;
    private FloatingActionButton addButton;
    private FloatingActionButton searchButton;
    private FloatingActionButton logoutButton;


    private DatabaseReference db;

    private Position p;

    private PositionDialog s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        s = new PositionDialog();

        mAuth = FirebaseAuth.getInstance();
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        addButton = findViewById( R.id.addButton );
        searchButton = findViewById( R.id.searchButton );
        logoutButton = findViewById( R.id.logoutButton );

        db = FirebaseDatabase.getInstance().getReference();

        db.child("positions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Object result = task.getResult().getValue();

                Map<String, List> map = (HashMap) result;

                for ( Map.Entry<String, List> e: map.entrySet()) {

                }
            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        p = new Position();

                        p.setLatitude( latLng.latitude );
                        p.setLongitude( latLng.longitude );

                        googleMap.clear();
                        googleMap.addMarker( new MarkerOptions().position( latLng ) );
                    }
                });
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( p != null ) {
                    s.setPosition(p);
                    s.show(getSupportFragmentManager(), "ActionBottomDialog");
                }else{
                    Toast.makeText(MapsActivity.this, "Select a place on the map first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}