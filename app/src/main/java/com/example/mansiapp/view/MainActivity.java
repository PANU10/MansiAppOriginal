package com.example.mansiapp.view;

import android.os.Bundle;
import android.view.View;


import com.example.mansiapp.R;
import com.example.mansiapp.model.Accessory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.cartFragment, R.id.mapFragment, R.id.favFragment, R.id.profileFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // Navbar Desactivado
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        FirebaseFirestore.getInstance().setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build());

   //     FirebaseFirestore.getInstance().collection("accessory").add(new Accessory("Accessory", "Samsung S10", 99.99f));


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()){
                    case R.id.homeFragment:
                    case R.id.cartFragment:
                    case R.id.mapFragment:
                    case R.id.favFragment:
                    case R.id.profileFragment:
                        navView.setVisibility(View.VISIBLE);
                        getSupportActionBar().show();
                        break;
                    default:
                        navView.setVisibility(View.GONE);
                        getSupportActionBar().hide();
                        break;
                }
            }
        });


    }

}
