package com.example.cheapgasfinder.db;

import java.util.Date;

public class Position {
    private double latitude;
    private double longitude;
    private Date date;
    private String name;
    private double price;

    public Position() {
    }

    public Position(long latitude, long longitude, Date date, String name, double price) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
