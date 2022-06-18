package com.example.cheapgasfinder.components;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.example.cheapgasfinder.R;

public class ImageHolder {
    private ImageView imageView;

    public ImageHolder(View v ) {
        imageView = v.findViewById(R.id.imageView );
    }

    public void setImage( Bitmap i )
    {
        imageView.setImageBitmap( i );
    }
}
