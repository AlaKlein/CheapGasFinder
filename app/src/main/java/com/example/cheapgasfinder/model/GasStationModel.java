package com.example.cheapgasfinder.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GasStationModel {
    private String id;
    private String name;
    private String address;
    private String telephone;
    private int gas;
    private int ethanol;
    private int diesel;


    public GasStationModel() {
    }

    public GasStationModel(String id, String name, String address, String telephone, int gas, int ethanol, int diesel) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.gas = gas;
        this.ethanol = ethanol;
        this.diesel = diesel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public int getEthanol() {
        return ethanol;
    }

    public void setEthanol(int ethanol) {
        this.ethanol = ethanol;
    }

    public int getDiesel() {
        return diesel;
    }

    public void setDiesel(int diesel) {
        this.diesel = diesel;
    }

    public void save() {
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //reference.child("gasstation").child(getId()).setValue(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GasStation");
        myRef.setValue(this);

    }
}
