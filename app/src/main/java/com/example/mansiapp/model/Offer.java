package com.example.mansiapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Offer {


        @PrimaryKey(autoGenerate = true)
        public int id;

        private String tipo;
        private String nombre;
        private float precio;


        public Offer(){

        }

        public Offer(String tipo, String nombre, float precio) {
            this.tipo = tipo;
            this.nombre = nombre;
            this.precio = precio;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public float getPrecio() {
            return precio;
        }

        public void setPrecio(float precio) {
            this.precio = precio;
        }

}
