package com.example.cheapgasfinder.db;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

public class Position {
    private int i;
    private String user;
    private double latitude;
    private double longitude;
    private long timestamp;
    private String name;
    private double priceGas;
    private double priceAlcool;
    private double priceDiesel;

    private int images;

    private boolean editable;

    public Position() {
    }

    public Position(Map<String, Object> map) {
        this.name = String.valueOf( map.get( "name" ) );
        this.longitude = Double.parseDouble( map.get("longitude").toString() );
        this.latitude = Double.parseDouble( map.get("latitude").toString() );
        this.timestamp = Long.parseLong( map.get("timestamp").toString() );
        this.priceGas = Double.parseDouble( map.get("priceGas").toString() );
        this.priceAlcool = Double.parseDouble( map.get("priceAlcool").toString() );
        this.priceDiesel = Double.parseDouble( map.get( "priceDiesel" ).toString() );
        this.images = Integer.parseInt( map.get( "images" ).toString() );
    }

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

    public int getImages() {
        return images;
    }

    public boolean isEditable() {
        return editable;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }
}
