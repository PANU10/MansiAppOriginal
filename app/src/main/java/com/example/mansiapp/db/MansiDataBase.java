package com.example.mansiapp.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mansiapp.model.Accessory;
import com.example.mansiapp.model.Offer;
import com.example.mansiapp.model.ProfileStatics;
import com.example.mansiapp.model.Shop;
import com.example.mansiapp.model.Smartphone;
import com.example.mansiapp.model.User;

@Database(entities = {User.class, Smartphone.class, Shop.class, Accessory.class, ProfileStatics.class, Offer.class}, version = 12)
public abstract class MansiDataBase extends RoomDatabase {

    public abstract MansiDao mansiDao();

    static MansiDataBase mansiDataBase;

    public static MansiDataBase getInstance(Context context){
        if (mansiDataBase == null){
            mansiDataBase = Room
                    .databaseBuilder(context, MansiDataBase.class, "mansi.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            anyadirusuario();
                            addMobile();
                            addShop();
                            addAccessory();
                            addProfileStatics();
                            addOffersProducts();
                        }
                    })
                    .build();
        }
        return mansiDataBase;
    }

    private static void anyadirusuario() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mansiDataBase.mansiDao().insertarUsuario(new User("admin", "pratik@example.com", "admin","bhbghbg"));
            }
        });
    }

    private static void addMobile(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Samsung", 999.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Sony", 499.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Samsung", 999.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Oppo", 399.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Oppo", 399.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Samsung", 999.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Sony", 499.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Nokie", 199.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Nokie", 199.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Oppo", 399.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Samsung", 999.99f));
                mansiDataBase.mansiDao().insertMobile(new Smartphone("Smartphone", "Oppo", 399.99f));
            }
        });
    }


    private static void addShop(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mansiDataBase.mansiDao().insertShop(new Shop("Barcelona-C.C. La Maquinista", "Barcelona", "Carrer de Potosí, 2 , Bacrcelona, 08030"));
                mansiDataBase.mansiDao().insertShop(new Shop("Badalona Centro", "Barcelona", "Carrer de Potosí, 2 , Bacrcelona, 08030"));
                mansiDataBase.mansiDao().insertShop(new Shop("Santa Coloma de Gramenet", "Barcelona", "Carrer de Potosí, 2 , Bacrcelona, 08030"));
                mansiDataBase.mansiDao().insertShop(new Shop("Santa Rosa", "Barcelona", "Carrer de Potosí, 2 , Bacrcelona, 08030"));
                mansiDataBase.mansiDao().insertShop(new Shop("Fondo", "Barcelona", "Carrer de Potosí, 2 , Bacrcelona, 08030"));
            }
        });
    }


    private static void addAccessory(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
                mansiDataBase.mansiDao().insertAccessory(new Accessory("Accessory", "Samsung S10", 99.99f));
            }
        });
    }

    private static void addProfileStatics() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mansiDataBase.mansiDao().insertStatics(new ProfileStatics("My orders", "Already have 12 orders"));
                mansiDataBase.mansiDao().insertStatics(new ProfileStatics("shipping_address", "3 address"));
                mansiDataBase.mansiDao().insertStatics(new ProfileStatics("payment methode", "Vida **34"));
                mansiDataBase.mansiDao().insertStatics(new ProfileStatics("Settings", "Notification, password"));
            }
        });
    }


    private static void addOffersProducts(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
                mansiDataBase.mansiDao().insertarProductOffers(new Offer("Smartphone", "Iphone", 999.99f));
            }
        });
    }
}
