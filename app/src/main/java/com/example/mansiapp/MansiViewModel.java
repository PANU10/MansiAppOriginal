package com.example.mansiapp;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mansiapp.db.MansiDao;
import com.example.mansiapp.db.MansiDataBase;
import com.example.mansiapp.model.Accessory;
import com.example.mansiapp.model.Offer;
import com.example.mansiapp.model.ProfileStatics;
import com.example.mansiapp.model.Shop;
import com.example.mansiapp.model.Smartphone;
import com.example.mansiapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class MansiViewModel extends AndroidViewModel {

  //  public List<Accessory> accesoriesList = new ArrayList<>();

    MansiDao mansiDao;


    public enum EstadoDeLaAutenticacion {
        NO_AUTENTICADO,
        AUTENTICADO,
        AUTENTICACION_INVALIDA
    }

    public enum EstadoDelRegistro {
        INICIO_DEL_REGISTRO,
        NOMBRE_NO_DISPONIBLE,
        REGISTRO_COMPLETADO
    }

    public User usuarioLogeado;

    public MutableLiveData<EstadoDeLaAutenticacion> estadoDeLaAutenticacion = new MutableLiveData<>(EstadoDeLaAutenticacion.NO_AUTENTICADO);
    public MutableLiveData<EstadoDelRegistro> estadoDelRegistro = new MutableLiveData<>(EstadoDelRegistro.INICIO_DEL_REGISTRO);

    public MansiViewModel(@NonNull Application application) {
        super(application);

        mansiDao = MansiDataBase.getInstance(application).mansiDao();
    }

    public void iniciarRegistro(){
        estadoDelRegistro.postValue(EstadoDelRegistro.INICIO_DEL_REGISTRO);
    }

    public void crearCuentaEIniciarSesion(final String nombre, final String email, final String contrasenya, final String biografia) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                User usuario = mansiDao.comprobarNombreDisponible(nombre);
                if(usuario == null){
                    mansiDao.insertarUsuario(new User(nombre,email, contrasenya, biografia));
                    estadoDelRegistro.postValue(EstadoDelRegistro.REGISTRO_COMPLETADO);
                    iniciarSesion(nombre, contrasenya);
                } else {
                    estadoDelRegistro.postValue(EstadoDelRegistro.NOMBRE_NO_DISPONIBLE);
                }
            }
        });
    }


    public void iniciarSesion(final String nombre, final String contrasenya) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                User usuario = mansiDao.autenticar(nombre, contrasenya);
                if(usuario != null){
                    usuarioLogeado = usuario;
                    estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.AUTENTICADO);
                } else {
                    estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.AUTENTICACION_INVALIDA);
                }
            }
        });
    }

    public void cerrarSesion() {
        usuarioLogeado = null;
        estadoDeLaAutenticacion.setValue(EstadoDeLaAutenticacion.NO_AUTENTICADO);
    }


    public LiveData<List<Smartphone>> cargarSmartphones() {
        return mansiDao.cargarSmartphones();
    }

    public LiveData<List<Accessory>> cargarAccessory() {
        return mansiDao.cargarAccessory();
    }

    public LiveData<List<Offer>> cargarOffers() {
        return mansiDao.cargarOffers();
    }

    public LiveData<List<Shop>> cargarShops() {
        return mansiDao.getShopList();
    }

    public LiveData<List<ProfileStatics>> cargarProfileStatics() {
        return mansiDao.getProfileStaticsList();
    }








    //Para ver los usuarios.

//    public void verBaseDeDatos(){
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                List<User> users = mansiDao.verUsuarios();
//                for(User user:users){
//                    Log.e("ABCD", "Usuario = " + user);
//                }
//
//
//
//                // List<Smartphone>
//                List<Smartphone> smartphones = mansiDao.verSmartphones();
//            }
//        });
//
//    }
}
