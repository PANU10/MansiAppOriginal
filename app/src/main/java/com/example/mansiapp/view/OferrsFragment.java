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
import com.example.mansiapp.model.Offer;
import com.example.mansiapp.model.Smartphone;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OferrsFragment extends Fragment {

    MansiViewModel mansiViewModel;

    public OferrsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_oferrs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mansiViewModel = ViewModelProviders.of(requireActivity()).get(MansiViewModel.class);;

        RecyclerView recyclerView = view.findViewById(R.id.offerList);

        final OferrsFragment.OfferAdapter offerAdapter = new OfferAdapter();
        recyclerView.setAdapter(offerAdapter);

        mansiViewModel.cargarOffers().observe(getViewLifecycleOwner(), new Observer<List<Offer>>() {
            @Override
            public void onChanged(List<Offer> o) {
                offerAdapter.establecerListaOffers(o);
            }
        });
    }


    class OfferAdapter extends RecyclerView.Adapter<OferrsFragment.OfferAdapter.OfferViewHolder>{

        List<Offer> offerList;

        @NonNull
        @Override
        public OferrsFragment.OfferAdapter.OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OfferViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_offers, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull OferrsFragment.OfferAdapter.OfferViewHolder holder, int position) {

            Offer offer = offerList.get(position);
            holder.tipoTextView.setText(offer.getTipo());
            holder.nameTextView.setText(offer.getNombre());
            holder.precioTextView.setText(String.valueOf(offer.getPrecio()));
            Glide.with(requireActivity()).load(R.drawable.iphone_offers).into(holder.imageImageView);
        }

        @Override
        public int getItemCount() {
            return offerList == null ? 0 : offerList.size();
        }

        public void establecerListaOffers(List<Offer> f){
            offerList = f;
            notifyDataSetChanged();
        }

        class OfferViewHolder extends RecyclerView.ViewHolder {
            ImageView imageImageView;
            TextView tipoTextView, nameTextView, precioTextView;

            OfferViewHolder(@NonNull View itemView) {
                super(itemView);
                imageImageView = itemView.findViewById(R.id.offerImagen);
                nameTextView = itemView.findViewById(R.id.offerNameMovile);
                tipoTextView = itemView.findViewById(R.id.offerTipoMovile);
                precioTextView = itemView.findViewById(R.id.offerPrice);
            }
        }
    }

}
