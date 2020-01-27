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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mansiapp.MansiViewModel;
import com.example.mansiapp.R;

import static com.example.mansiapp.MansiViewModel.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    NavController navController;
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
        mansiViewModel = ViewModelProviders.of(requireActivity()).get(MansiViewModel.class);

        nombreEditText = view.findViewById(R.id.edittext_nombre_registrar);
        emailEditText = view.findViewById(R.id.edittext_email_registrar);
        passwordEditText = view.findViewById(R.id.edittext_password_registrar);
        biografiaEditText = view.findViewById(R.id.edittext_bio_registrar);
        registrarButton = view.findViewById(R.id.button_registrar);

        mansiViewModel.iniciarRegistro();

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mansiViewModel.crearCuentaEIniciarSesion(nombreEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString(), biografiaEditText.getText().toString());

                mansiViewModel.estadoDelRegistro.observe(getViewLifecycleOwner(), new Observer<EstadoDelRegistro>() {
                    @Override
                    public void onChanged(EstadoDelRegistro estadoDelRegistro) {
                        switch (estadoDelRegistro){
                            case NOMBRE_NO_DISPONIBLE:
                                Toast.makeText(getContext(), "NOMBRE DE USUARIO NO DISPONIBLE", Toast.LENGTH_SHORT).show();
                                break;

                            case REGISTRO_COMPLETADO:
                                Navigation.findNavController(view).navigate(R.id.homeFragment);
                                break;
                        }
                    }
                });

            }
        });


    }
}
