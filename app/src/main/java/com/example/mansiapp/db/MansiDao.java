package com.example.mansiapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mansiapp.model.Accessory;
import com.example.mansiapp.model.Offer;
import com.example.mansiapp.model.ProfileStatics;
import com.example.mansiapp.model.Shop;
import com.example.mansiapp.model.Smartphone;
import com.example.mansiapp.model.User;
import com.example.mansiapp.view.OferrsFragment;

import java.util.List;

@Dao
public abstract class MansiDao {
    @Insert
    public abstract void insertarUsuario(User user);

    @Insert
    public  abstract void insertShop(Shop shop);

    @Insert
    public  abstract void insertStatics(ProfileStatics profileStatics);

    @Insert
    public  abstract void insertarProductOffers(Offer offer);

    @Insert
    public abstract void insertMobile(Smartphone smartphone);

    @Insert
    public abstract void insertAccessory(Accessory accessory);

    @Query("SELECT * FROM User WHERE email = :nombre AND password = :contrasenya")
    public abstract User autenticar(String nombre, String contrasenya);

    @Query("SELECT * FROM User WHERE email = :nombre")
    public abstract User comprobarNombreDisponible(String nombre);

    @Query("SELECT * FROM Shop")
    public abstract LiveData<List<Shop>> getShopList();

    @Query("SELECT * FROM ProfileStatics")
    public abstract LiveData<List<ProfileStatics>> getProfileStaticsList();

    @Query("SELECT * FROM Shop")
    public abstract List<Shop> getShop();

    //Comprobar la lista
    @Query("SELECT * FROM User")
    public abstract List<User> verUsuarios();


    @Query("SELECT * FROM Smartphone")
    public abstract LiveData<List<Smartphone>> cargarSmartphones();

    @Query("SELECT * FROM Offer")
    public abstract LiveData<List<Offer>> cargarOffers();


    @Query("SELECT * FROM Smartphone")
    public abstract List<Smartphone> verSmartphones();

    @Query("SELECT * FROM Accessory")
    public abstract LiveData<List<Accessory>> cargarAccessory();

    @Query("SELECT * FROM Accessory")
    public abstract List<Accessory> verAccessories();

}
