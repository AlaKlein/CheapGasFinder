package com.example.cheapgasfinder.db;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

public class Position {
    private double latitude;
    private double longitude;
    private Date date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriceGas(double priceGas) {
        this.priceGas = priceGas;
    }

    public void setPriceAlcool(double priceAlcool) {
        this.priceAlcool = priceAlcool;
    }

    public void setPriceDiesel(double priceDiesel) {
        this.priceDiesel = priceDiesel;
    }

    public void setImages(List<Bitmap> images) {
        this.images = images;
    }
}
