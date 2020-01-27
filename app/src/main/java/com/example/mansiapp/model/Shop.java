package com.example.mansiapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Shop {
    private String location;
    private String city;
    private String addresse;

    @PrimaryKey(autoGenerate = true)
    public int id;

    public Shop(){
    }

    public Shop(String location, String city, String addresse) {
        this.location = location;
        this.city = city;
        this.addresse = addresse;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }


}
