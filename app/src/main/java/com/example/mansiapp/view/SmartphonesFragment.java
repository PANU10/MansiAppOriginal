package com.example.mansiapp.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mansiapp.MansiViewModel;
import com.example.mansiapp.R;
import com.example.mansiapp.model.Smartphone;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;


public class SmartphonesFragment extends Fragment {

    MansiViewModel mansiViewModel;

    public SmartphonesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_samrtphones, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.smartphoneList);

        Query query = FirebaseFirestore.getInstance().collection("Smartphone").limit(50);

        FirestoreRecyclerOptions<Smartphone> options = new FirestoreRecyclerOptions.Builder<Smartphone>()
                .setQuery(query, Smartphone.class)
                .setLifecycleOwner(this)
                .build();

        recyclerView.setAdapter(new SmartphonesAdapter(options));


    }

    class SmartphonesAdapter extends FirestoreRecyclerAdapter<Smartphone, SmartphonesAdapter.SmartphoneViewHolder> {

        List<Smartphone> smartphoneList;

        /**
         * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
         * FirestoreRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public SmartphonesAdapter(@NonNull FirestoreRecyclerOptions<Smartphone> options) {
            super(options);
        }

        @NonNull
        @Override
        public SmartphonesAdapter.SmartphoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        protected void onBindViewHolder(@NonNull SmartphoneViewHolder holder, int position, @NonNull Smartphone model) {
            Smartphone smartphone = smartphoneList.get(position);
            holder.tipoTextView.setText(smartphone.getTipo());
            holder.nameTextView.setText(smartphone.getNombre());
            holder.precioTextView.setText(String.valueOf(smartphone.getPrecio()));
            Glide.with(requireActivity()).load(R.drawable.samsing_movile).into(holder.imageImageView);
        }

        public void establecerListaSmarphones(List<Smartphone> s){
            smartphoneList = s;
            notifyDataSetChanged();
        }

        class SmartphoneViewHolder extends RecyclerView.ViewHolder {
            ImageView imageImageView;
            TextView tipoTextView, nameTextView, precioTextView;

            SmartphoneViewHolder(@NonNull View itemView) {
                super(itemView);
                imageImageView = itemView.findViewById(R.id.item_imagen);
                nameTextView = itemView.findViewById(R.id.item_name_movile);
                tipoTextView = itemView.findViewById(R.id.item_tipo_movile);
                precioTextView = itemView.findViewById(R.id.item_price);
            }
        }
    }
}

