package com.example.mansiapp.view;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mansiapp.MansiViewModel;
import com.example.mansiapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.mansiapp.MansiViewModel.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    NavController navController;
    private FirebaseAuth mAuth;
    MansiViewModel mansiViewModel;

    private EditText nombreEditText, emailEditText, passwordEditText, biografiaEditText;
    private Button registrarButton;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();

        //mansiViewModel = ViewModelProviders.of(requireActivity()).get(MansiViewModel.class);

        nombreEditText = view.findViewById(R.id.edittext_nombre_registrar);
        emailEditText = view.findViewById(R.id.edittext_email_registrar);
        passwordEditText = view.findViewById(R.id.edittext_password_registrar);
        biografiaEditText = view.findViewById(R.id.edittext_bio_registrar);
        registrarButton = view.findViewById(R.id.button_registrar);

        //TODO Firebase

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ABCD", "CLICKED BUTTON");
                crearCuenta();
                Navigation.findNavController(view).navigate(R.id.homeFragment);
            }
        });
    }
    private void crearCuenta(){
        if (!validarFormulario()) {
            Log.e("ABCD", "Formulario balidado");
            return;
        }

        registrarButton.setEnabled(false);

        Log.e("ABCD", "creando cuenta....");
        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("ABCD", "Completado registrado");
                        if (task.isSuccessful()) {
                            Log.e("ABCD", "Registro succesfull");
                            actualizarUI(mAuth.getCurrentUser());
                        } else {
                            Log.e("ABCD", "Registro fallo " + task.getException());
                            Snackbar.make(requireView(), "Error: " + task.getException(), Snackbar.LENGTH_LONG).show();

                        }
                        registrarButton.setEnabled(true);
                    }
                });
    }


    private void actualizarUI(FirebaseUser currentUser) {
        if(currentUser != null){
            navController.navigate(R.id.homeFragment);
        }
    }

    private boolean validarFormulario() {
        boolean valid = true;

        if (TextUtils.isEmpty(emailEditText.getText().toString())) {
            emailEditText.setError("Required.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordEditText.setError("Required.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }

}

