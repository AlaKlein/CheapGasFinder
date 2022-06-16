package com.example.cheapgasfinder;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cheapgasfinder.components.PositionSheet;
import com.example.cheapgasfinder.db.Position;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.cheapgasfinder.databinding.ActivityMapsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity {

    private ActivityMapsBinding binding;

    private FloatingActionButton addButton;
    private FloatingActionButton searchButton;

    private DatabaseReference db;

    private Position p;

    private PositionSheet s = new PositionSheet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        addButton = findViewById( R.id.addButton );
        searchButton = findViewById( R.id.searchButton );

        db = FirebaseDatabase.getInstance().getReference();

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
                s.show( getSupportFragmentManager(), "ActionBottomDialog" );
            }
        });
    }
}