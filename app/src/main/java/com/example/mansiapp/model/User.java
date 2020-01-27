package com.example.mansiapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre, email, password, biografia;

    public User(String nombre, String email, String password, String biografia) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.biografia = biografia;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", biografia='" + biografia + '\'' +
                '}';
    }
}
