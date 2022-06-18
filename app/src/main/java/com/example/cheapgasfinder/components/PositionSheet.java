package com.example.cheapgasfinder.components;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cheapgasfinder.R;
import com.example.cheapgasfinder.adapter.ImageAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PositionSheet extends BottomSheetDialogFragment {

    private ListView imageList;
    private ImageAdapter imageAdapter;

    private Button imageButton;

    private List<Bitmap> images = new ArrayList<Bitmap>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.possition_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageList = view.findViewById( R.id.list);
        imageButton = view.findViewById( R.id.imageButton);

        imageAdapter = new ImageAdapter( getContext(), R.layout.image_item, images );

        imageList.setAdapter( imageAdapter );

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

            imageAdapter.insert( imageBitmap, 0 );
        }
    }
}
