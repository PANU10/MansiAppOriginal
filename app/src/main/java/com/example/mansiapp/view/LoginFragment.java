package com.example.mansiapp.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mansiapp.MansiViewModel;
import com.example.mansiapp.MansiViewModel.EstadoDeLaAutenticacion;
import com.example.mansiapp.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private NavController navController = null;
    private GoogleSignInClient googleSignInClient;
    private int RC_SIGN_IN_WITH_GOOGLE = 1111;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    private MansiViewModel mansiViewModel;
    private EditText usuarioEditText, contrasenyaEditText;
    private Button iniciarSesionButton;
    private TextView irAlRegistroTextView;

    ImageView googleImageView;
    ImageView facebookImageView;

    public LoginFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();

        //Google SignInCliente
        googleImageView = view.findViewById(R.id.imageView_google);
        facebookImageView = view.findViewById(R.id.imageView_facebook);

        mAuth = FirebaseAuth.getInstance();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build());

        firebaseAuthWithGoogle(GoogleSignIn.getLastSignedInAccount(requireContext()));

        view.findViewById(R.id.imageView_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        usuarioEditText = view.findViewById(R.id.editText_name_Login);
        contrasenyaEditText = view.findViewById(R.id.ediText_password_Login);
        iniciarSesionButton = view.findViewById(R.id.button_Acceder_Login);
        irAlRegistroTextView = view.findViewById(R.id.texView_irAlRegistro_Login);
        progressBar = view.findViewById(R.id.progress_circular);


        irAlRegistroTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.registerFragment2);
            }
        });

        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                accederConEmail();
                Navigation.findNavController(view).navigate(R.id.homeFragment);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Navigation.findNavController(view).popBackStack(R.id.homeFragment, false);
                    }
                });
    }

    public void accederConEmail() {

//        usuarioEditText.setVisibility(View.GONE);
//        contrasenyaEditText.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(usuarioEditText.getText().toString(), contrasenyaEditText.getText().toString())
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            actualizarUI(mAuth.getCurrentUser());
                        } else {
                            Snackbar.make(requireView(), "Error: " + task.getException(), Snackbar.LENGTH_LONG).show();
                        }
//                        usuarioEditText.setVisibility(View.VISIBLE);
//                        contrasenyaEditText.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void actualizarUI(FirebaseUser currentUser) {
        if(currentUser != null){
            navController.navigate(R.id.homeFragment);
        }
    }

    private void updateUI(FirebaseUser firebaseUser) {
        if(firebaseUser != null){
            navController.navigate(R.id.homeFragment);
        }
    }

    private void signIn() {
        startActivityForResult(googleSignInClient.getSignInIntent(), RC_SIGN_IN_WITH_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN_WITH_GOOGLE) {
            try {
                firebaseAuthWithGoogle(GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class));
            } catch (ApiException e) {
                Log.e("ABCD", "signInResult:failed code=" + e.getStatusCode());
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

            if(acct == null) return;

            mAuth.signInWithCredential(GoogleAuthProvider.getCredential(acct.getIdToken(), null))
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.e("ABCD", "signInWithCredential:success");
                                updateUI(mAuth.getCurrentUser());
                            } else {
                                Log.e("ABCD", "signInWithCredential:failure", task.getException());
                                googleImageView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }


}

