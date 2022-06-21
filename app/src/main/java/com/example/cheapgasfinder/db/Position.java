package com.example.cheapgasfinder.db;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

public class Position {
    private double latitude;
    private double longitude;
    private long timestamp;
    private String name;
    private double priceGas;
    private double priceAlcool;
    private double priceDiesel;

    private List<Bitmap> images;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceGas() {
        return priceGas;
    }

    public void setPriceGas(double priceGas) {
        this.priceGas = priceGas;
    }

    public double getPriceAlcool() {
        return priceAlcool;
    }

    public void setPriceAlcool(double priceAlcool) {
        this.priceAlcool = priceAlcool;
    }

    public double getPriceDiesel() {
        return priceDiesel;
    }

    public void setPriceDiesel(double priceDiesel) {
        this.priceDiesel = priceDiesel;
    }

    public List<Bitmap> getImages() {
        return images;
    }

    public void setImages(List<Bitmap> images) {
        this.images = images;
    }
}
