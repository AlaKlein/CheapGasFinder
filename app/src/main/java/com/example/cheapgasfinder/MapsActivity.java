package com.example.cheapgasfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.cheapgasfinder.components.FilterDialog;
import com.example.cheapgasfinder.components.PositionDialog;
import com.example.cheapgasfinder.databinding.ActivityMapsBinding;
import com.example.cheapgasfinder.db.Position;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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

    private GoogleMap googleMap;

    private ActivityMapsBinding binding;
    private FirebaseAuth mAuth;
    private FloatingActionButton addButton;
    private FloatingActionButton searchButton;
    private FloatingActionButton logoutButton;

    private DatabaseReference db;

    private Position p;

    private PositionDialog s;

    private FilterDialog f;

    private Map<String, String> filter;

    private Map<Marker,Position> markers;

    private Marker temp;

    public void refreshContent() {
        db.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                googleMap.clear();

                Object result = task.getResult().getValue();

                if( result != null ) {
                    Map<String, List> map = (HashMap) result;

                    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    for (Map.Entry<String, List> e : map.entrySet()) {
                        boolean editable = e.getKey().equals(user);

                        for (Object o : e.getValue()) {
                            Map<String, Object> m = (HashMap) o;

                            Position p = new Position(m);

                            p.setEditable( editable );

                            if (filter.containsKey("name") && !filter.get("name").isEmpty() && !p.getName().contains(filter.get("name"))) {
                                continue;
                            }

                            if( filter.containsKey("gas") && !filter.get( "gas" ).isEmpty() )
                            {
                                double max = Double.parseDouble( filter.get("gas") );

                                if( p.getPriceGas() > max )
                                {
                                    continue;
                                }
                            }

                            if( filter.containsKey("alcool") && !filter.get( "alcool" ).isEmpty() )
                            {
                                double max = Double.parseDouble( filter.get("alcool") );

                                if( p.getPriceAlcool() > max )
                                {
                                    continue;
                                }
                            }

                            if( filter.containsKey("diesel") && !filter.get( "diesel" ).isEmpty() )
                            {
                                double max = Double.parseDouble( filter.get("diesel") );

                                if( p.getPriceDiesel() > max )
                                {
                                    continue;
                                }
                            }

                            Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(p.getLatitude(), p.getLongitude()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(editable ? BitmapDescriptorFactory.HUE_BLUE : BitmapDescriptorFactory.HUE_RED)));

                            markers.put( marker, p );
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        s = new PositionDialog();
        f = new FilterDialog();

        mAuth = FirebaseAuth.getInstance();
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        addButton = findViewById(R.id.addButton);
        searchButton = findViewById(R.id.searchButton);
        logoutButton = findViewById(R.id.logoutButton);

        db = FirebaseDatabase.getInstance().getReference();

        filter = new HashMap<>();
        markers = new HashMap<>();

        f.setCallback(new Callback<Map<String, String>>() {
            @Override
            public void doAccept(Map<String, String> o) {
                filter = o;

                refreshContent();
            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                MapsActivity.this.googleMap = googleMap;

                refreshContent();

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        p = new Position();

                        p.setLatitude(latLng.latitude);
                        p.setLongitude(latLng.longitude);

                        if( temp != null ) {
                            temp.remove();
                        }

                        temp = googleMap.addMarker(new MarkerOptions().position(latLng));
                    }
                });

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Position position = markers.get( marker );

                        s.setPosition(position);
                        s.setMode( position.isEditable() ? 1 : 2 );
                        s.show(getSupportFragmentManager(), "PossitonDialog");

                        return false;
                    }
                });
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p != null) {
                    s.setPosition(p);
                    s.show(getSupportFragmentManager(), "PossitonDialog");
                } else {
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
                f.show(getSupportFragmentManager(), "FilterDialog");
            }
        });
    }
}