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

public class FilterDialog extends DialogFragment {

    private Button addButton;
    private Button cancelButton;

    private TextView nameField;
    private TextView priceGasField;
    private TextView priceEthanolField;
    private TextView priceDieselField;
    private TextView maxDateField;
    private TextView minDateField;

    private Callback<Object> callback;

    private void clearFields() {
        nameField.setText("");
        priceGasField.setText("");
        priceEthanolField.setText("");
        priceDieselField.setText("");
    }

    private DatabaseReference db;

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

        nameField = view.findViewById(R.id.nameField);
        priceGasField = view.findViewById(R.id.priceGasField);
        priceEthanolField = view.findViewById(R.id.priceAlcoolField);
        priceDieselField = view.findViewById(R.id.priceDieselField);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
