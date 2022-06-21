package com.example.cheapgasfinder.components;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import androidx.fragment.app.DialogFragment;

import com.example.cheapgasfinder.Callback;
import com.example.cheapgasfinder.R;
import com.example.cheapgasfinder.adapter.ImageAdapter;
import com.example.cheapgasfinder.db.Position;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PositionDialog extends DialogFragment {

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

    private int mode = 0;

    private DatabaseReference db;
    private StorageReference storage;

    private Callback<Object> callback;

    private void clearFields() {
        nameField.setText("");
        priceGasField.setText("");
        priceEthanolField.setText("");
        priceDieselField.setText("");
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setCallback(Callback<Object> callback) {
        this.callback = callback;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.possition_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();

        imageList = view.findViewById(R.id.list);
        imageButton = view.findViewById(R.id.imageButton);
        addButton = view.findViewById(R.id.okButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        nameField = view.findViewById(R.id.nameField);
        priceGasField = view.findViewById(R.id.priceGasField);
        priceEthanolField = view.findViewById(R.id.priceAlcoolField);
        priceDieselField = view.findViewById(R.id.priceDieselField);

        imageAdapter = new ImageAdapter(getContext(), R.layout.image_item, new ArrayList<Bitmap>());

        imageAdapter.setMode( mode );

        imageList.setAdapter(imageAdapter);

        if( mode > 0 )
        {
            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

            nameField.setText(position.getName() );
            priceGasField.setText( String.valueOf( position.getPriceGas() ));
            priceEthanolField.setText( String.valueOf( position.getPriceAlcool() ) );
            priceDieselField.setText( String.valueOf( position.getPriceDiesel() ) );

            for( int i = 0; i < position.getImages(); i++ ) {
                final int c = i;
                StorageReference ref = storage.child(user + "/" + position.getI()+"/" + i + ".jpg");

                ref.getBytes( 1024 * 1024 ).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                    @Override
                    public void onComplete(@NonNull Task<byte[]> task) {
                        byte[] b = task.getResult();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                        imageAdapter.insert( bitmap, 0 );
                    }
                });
            }

            imageButton.setEnabled( false );
        }

        if( mode > 1 )
        {
            nameField.setEnabled( false );
            priceGasField.setEnabled( false );
            priceEthanolField.setEnabled( false );
            priceDieselField.setEnabled( false );
            addButton.setEnabled( false );
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                db.child(user).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                            position.setPriceGas( Double.parseDouble( priceGasField.getText().toString() ) );
                            position.setPriceAlcool( Double.parseDouble( priceEthanolField.getText().toString() ) );
                            position.setPriceDiesel( Double.parseDouble( priceDieselField.getText().toString() ) );
                            position.setTimestamp( System.currentTimeMillis() );
                            position.setImages( imageAdapter.getCount() );

                            items.add(position);

                            db.child(user).setValue(items);

                            for( int i = 0; i < imageAdapter.getCount(); i++ ) {
                                StorageReference ref = storage.child(user + "/" + items.indexOf(position) + "/" + i + ".jpg");

                                Bitmap bitmap = imageAdapter.getItem( i );
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();

                                ref.putBytes( data );
                            }

                            callback.doAccept( "lmao" );

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
