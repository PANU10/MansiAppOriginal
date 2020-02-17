package com.example.mansiapp.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mansiapp.MansiViewModel;
import com.example.mansiapp.R;
import com.example.mansiapp.model.ProfileStatics;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    MansiViewModel mansiViewModel;
    Button buttonLogut;
    TextView userProfile,emailProfile;


    NavController navController;


    public ProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = null;

        //TODO firebase



        mansiViewModel = ViewModelProviders.of(requireActivity()).get(MansiViewModel.class);
        buttonLogut = view.findViewById(R.id.button_Logout);

        RecyclerView recyclerView = view.findViewById(R.id.profileStaticsList);

        final ProfileFragment.ProfileStaticsAdapter profileStaticsAdapter = new ProfileStaticsAdapter();
        recyclerView.setAdapter(profileStaticsAdapter);

        mansiViewModel.cargarProfileStatics().observe(getViewLifecycleOwner(), new Observer<List<ProfileStatics>>() {
            @Override
            public void onChanged(List<ProfileStatics> profileStatics) {
                profileStaticsAdapter.establecerListaProfileStatics(profileStatics);
            }
        });

        buttonLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mansiViewModel.cerrarSesion();


                GoogleSignIn.getClient(requireActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .build()).signOut();

                FirebaseAuth.getInstance().signOut();

                navController.navigate(R.id.loginFragment2);
            }
        });

        }


    class ProfileStaticsAdapter extends RecyclerView.Adapter<ProfileFragment.ProfileStaticsAdapter.ProfileViewHolder> {

        List<ProfileStatics> profileStaticsList;

        @NonNull
        @Override
        public ProfileStaticsAdapter.ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProfileStaticsAdapter.ProfileViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_profile_statistics, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileStaticsAdapter.ProfileViewHolder holder, int position) {
            ProfileStatics profileStatics = profileStaticsList.get(position);

            holder.textviewDark.setText(profileStatics.getProfileDarkText());
            holder.textViewLight.setText(profileStatics.getProfileLightText());

        }

        @Override
        public int getItemCount() {
            return profileStaticsList == null ? 0 : profileStaticsList.size();
        }

        public void establecerListaProfileStatics(List<ProfileStatics> profileStatics) {
            profileStaticsList = profileStatics;
            notifyDataSetChanged();
        }


        class ProfileViewHolder extends RecyclerView.ViewHolder {
            TextView textviewDark, textViewLight;

            ProfileViewHolder(@NonNull View itemView) {
                super(itemView);
                textviewDark = itemView.findViewById(R.id.textview_profile_darktext);
                textViewLight = itemView.findViewById(R.id.textview_profile_lighttext);
            }
        }
    }
}
