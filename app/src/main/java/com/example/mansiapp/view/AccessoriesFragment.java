package com.example.mansiapp.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mansiapp.MansiViewModel;
import com.example.mansiapp.R;
import com.example.mansiapp.model.Accessory;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccessoriesFragment extends Fragment {

    MansiViewModel mansiViewModel;

    public AccessoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accessories, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mansiViewModel = ViewModelProviders.of(requireActivity()).get(MansiViewModel.class);;


        RecyclerView recyclerView = view.findViewById(R.id.accesorieList);

        final AccessoriesFragment.AccessoriesAdapter accessoriesAdapter = new AccessoriesAdapter();
        recyclerView.setAdapter(accessoriesAdapter);


        mansiViewModel.cargarAccessory().observe(getViewLifecycleOwner(), new Observer<List<Accessory>>() {
            @Override
            public void onChanged(List<Accessory> smartphones) {
                accessoriesAdapter.establecerListaAccessories(smartphones);
            }
        });
    }



    class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.AccessoryViewHolder>{

        List<Accessory> accessoryList;

        @NonNull
        @Override
        public AccessoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AccessoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_accessorie, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AccessoryViewHolder holder, int position) {


            Accessory accessory = accessoryList.get(position);
            holder.tipoTextView.setText(accessory.getTipo());
            holder.nameTextView.setText(accessory.getNombre());
            holder.precioTextView.setText(String.valueOf(accessory.getPrecio()));
            Glide.with(requireActivity()).load(R.drawable.iphone).into(holder.imageImageView);
        }

        @Override
        public int getItemCount() {
            return accessoryList == null ? 0 : accessoryList.size();
        }

        public void establecerListaAccessories(List<Accessory> a){
            accessoryList = a;
            notifyDataSetChanged();
        }

        class AccessoryViewHolder extends RecyclerView.ViewHolder {
            ImageView imageImageView;
            TextView tipoTextView, nameTextView, precioTextView;

            AccessoryViewHolder(@NonNull View itemView) {
                super(itemView);
                imageImageView = itemView.findViewById(R.id.acceImagen);
                nameTextView = itemView.findViewById(R.id.acceNameMovile);
                tipoTextView = itemView.findViewById(R.id.acceTipoMovile);
                precioTextView = itemView.findViewById(R.id.accePrice);
            }
        }
    }
}
