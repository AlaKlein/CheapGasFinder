package com.example.cheapgasfinder.components;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cheapgasfinder.R;
import com.example.cheapgasfinder.adapter.ImageAdapter;
import com.example.cheapgasfinder.db.Position;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PositionSheet extends BottomSheetDialogFragment {

    private Position position;

    private ListView imageList;
    private ImageAdapter imageAdapter;

    private Button imageButton;
    private Button addButton;
    private Button cancelButton;

    private TextView nameField;
    private TextView priceGasField;
    private TextView priceEthanolField;
    private TextView priceDieselField;

    private void clearFields() {
        nameField.setText("");
        priceGasField.setText("");
        priceEthanolField.setText("");
        priceDieselField.setText("");
    }

    private DatabaseReference db;

    public void setPosition(Position position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.possition_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference();

        imageList = view.findViewById(R.id.list);
        imageButton = view.findViewById(R.id.imageButton);
        addButton = view.findViewById(R.id.okButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        nameField = view.findViewById(R.id.nameField);
        priceGasField = view.findViewById(R.id.priceGasField);
        priceEthanolField = view.findViewById(R.id.priceAlcoolField);
        priceDieselField = view.findViewById(R.id.priceDieselField);

        imageAdapter = new ImageAdapter(getContext(), R.layout.image_item, new ArrayList<Bitmap>());

        imageList.setAdapter(imageAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Object result = task.getResult().getValue();
                        List<Position> items = new ArrayList<>();
                        if (TextUtils.isEmpty(nameField.getText().toString())) {
                            nameField.setError("Field can't be empty!");
                        } else if (TextUtils.isEmpty(priceGasField.getText().toString())) {
                            priceGasField.setError("Field can't be empty!");
                        } else if (TextUtils.isEmpty(priceEthanolField.getText().toString())) {
                            priceEthanolField.setError("Field can't be empty!");
                        } else if (TextUtils.isEmpty(priceDieselField.getText().toString())) {
                            priceDieselField.setError("Field can't be empty!");
                        } else {
                            if (result != null && result instanceof List) {
                                items = (List<Position>) result;
                            }

                            position.setName(nameField.getText().toString());

                            items.add(position);

                            db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(items);
                            clearFields();
                            dismiss();
                        }
                    }
                });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
                dismiss();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == -1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageAdapter.insert(imageBitmap, 0);
        }
    }
}
