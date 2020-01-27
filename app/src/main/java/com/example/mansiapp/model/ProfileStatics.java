package com.example.mansiapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ProfileStatics {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String profileDarkText;
    private String profileLightText;

    public ProfileStatics (){

    }

    public ProfileStatics(String profileDarkText, String profileLightText) {
        this.profileDarkText = profileDarkText;
        this.profileLightText = profileLightText;
    }

    public String getProfileDarkText() {
        return profileDarkText;
    }

    public void setProfileDarkText(String profileDarkText) {
        this.profileDarkText = profileDarkText;
    }

    public String getProfileLightText() {
        return profileLightText;
    }

    public void setProfileLightText(String profileLightText) {
        this.profileLightText = profileLightText;
    }
}
