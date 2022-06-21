package com.example.cheapgasfinder.components;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.cheapgasfinder.Callback;
import com.example.cheapgasfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FilterDialog extends DialogFragment {

    private Button addButton;
    private Button cancelButton;

    private TextView nameField;
    private TextView priceGasField;
    private TextView priceEthanolField;
    private TextView priceDieselField;
//    private TextView maxDateField;
//    private TextView minDateField;

    private Callback<Map<String, String>> callback;

    private void clearFields() {
        nameField.setText("");
        priceGasField.setText("");
        priceEthanolField.setText("");
        priceDieselField.setText("");
//        maxDateField.setText("");
//        minDateField.setText("");
    }

    private DatabaseReference db;

    public void setCallback(Callback<Map<String, String>> callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference();

        addButton = view.findViewById(R.id.okButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        nameField = view.findViewById(R.id.nameFilterField);
        priceGasField = view.findViewById(R.id.maxPriceGasField);
        priceEthanolField = view.findViewById(R.id.maxPriceAlcoolField);
        priceDieselField = view.findViewById(R.id.maxPriceDieselField);
//        maxDateField = view.findViewById(R.id.maxDateField);
//        minDateField = view.findViewById(R.id.minDateField);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> vals = new HashMap<>();

                vals.put("name", nameField.getText().toString());
                vals.put("gas", priceGasField.getText().toString());
                vals.put("alcool", priceEthanolField.getText().toString());
                vals.put("diesel", priceDieselField.getText().toString());
//                vals.put("maxDate", maxDateField.getText().toString());
//                vals.put("minDate", minDateField.getText().toString());
                callback.doAccept(vals);

                clearFields();
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
                dismiss();
            }
        });
    }
}
