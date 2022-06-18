package com.example.cheapgasfinder.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cheapgasfinder.R;
import com.example.cheapgasfinder.components.ImageHolder;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<Bitmap> {

    private List<Bitmap> images = new ArrayList<Bitmap>();

    public ImageAdapter(@NonNull Context context, int resource, @NonNull List<Bitmap> objects) {
        super(context, resource, objects);

        images = objects;
    }

    public void setImages(List<Bitmap> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageHolder holder = null;

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.image_item, parent, false);

            holder = new ImageHolder(convertView);

            convertView.setTag(holder);
        }
        else{
            holder = (ImageHolder) convertView.getTag();
        }

        holder.setImage( images.get( position ) );

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }
}