package com.example.cheapgasfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cheapgasfinder.model.GasStationModel;
import com.google.firebase.auth.FirebaseAuth;

public class GasStationRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edName = null;
    private EditText edAddress = null;
    private EditText edPhone = null;
    private CheckBox cbGas = null;
    private CheckBox cbEthanol = null;
    private CheckBox cbDiesel = null;
    private Button btSubmitGasStation = null;
    private Button btCancelGasStation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_station_registration);

        edName = findViewById(R.id.edName);
        edAddress = findViewById(R.id.edAddress);
        edPhone = findViewById(R.id.edPhone);
        cbGas = findViewById(R.id.cbGas);
        cbEthanol = findViewById(R.id.cbEthanol);
        cbDiesel = findViewById(R.id.cbDiesel);
        btSubmitGasStation = findViewById(R.id.btSubmitGasStation);
        btCancelGasStation = findViewById(R.id.btCancelGasStation);

        btSubmitGasStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GasStationModel gs = new GasStationModel();
                gs.setName(edName.getText().toString());
                gs.setAddress(edAddress.getText().toString());
                gs.setTelephone(edPhone.getText().toString());
                if (cbGas.isChecked()) {
                   gs.setGas(1);
                }else{
                    gs.setGas(0);
                }
                if (cbEthanol.isChecked()) {
                    gs.setEthanol(1);
                }else{
                    gs.setEthanol(0);
                }
                if (cbDiesel.isChecked()) {
                    gs.setDiesel(1);
                }else{
                    gs.setDiesel(0);
                }

                //if(!TextUtils.isEmpty("edName") || !TextUtils.isEmpty("edAddress") || !TextUtils.isEmpty("edPhone")) {
                //gs.setId(mAuth.getUid());
                gs.save();
                //}
            }
        });

        btCancelGasStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}