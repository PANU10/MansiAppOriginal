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

        mansiViewModel = ViewModelProviders.of(requireActivity()).get(MansiViewModel.class);;

        RecyclerView recyclerView = view.findViewById(R.id.smartphoneList);

        final SmartphonesAdapter smartphonesAdapter = new SmartphonesAdapter();
        recyclerView.setAdapter(smartphonesAdapter);

        mansiViewModel.cargarSmartphones().observe(getViewLifecycleOwner(), new Observer<List<Smartphone>>() {
            @Override
            public void onChanged(List<Smartphone> smartphones) {
                smartphonesAdapter.establecerListaSmarphones(smartphones);
            }
        });
    }


    class SmartphonesAdapter extends RecyclerView.Adapter<SmartphonesAdapter.SmartphoneViewHolder>{

        List<Smartphone> smartphoneList;

        @NonNull
        @Override
        public SmartphoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SmartphoneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_smartphone, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SmartphoneViewHolder holder, int position) {

            Smartphone smartphone = smartphoneList.get(position);
            holder.tipoTextView.setText(smartphone.getTipo());
            holder.nameTextView.setText(smartphone.getNombre());
            holder.precioTextView.setText(String.valueOf(smartphone.getPrecio()));
            Glide.with(requireActivity()).load(R.drawable.samsing_movile).into(holder.imageImageView);
        }

        @Override
        public int getItemCount() {
            return smartphoneList == null ? 0 : smartphoneList.size();
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

