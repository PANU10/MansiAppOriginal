package com.example.mansiapp.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mansiapp.MansiViewModel;
import com.example.mansiapp.R;
import com.example.mansiapp.model.Shop;
import com.example.mansiapp.model.Smartphone;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    MansiViewModel mansiViewModel;
    SupportMapFragment mapFragment;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View map = inflater.inflate(R.layout.fragment_shop, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fr = getFragmentManager();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return map;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mansiViewModel = ViewModelProviders.of(requireActivity()).get(MansiViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.mapAdrressesList);

        final TiendasAdapter tiendasAdapter = new TiendasAdapter();
        recyclerView.setAdapter(tiendasAdapter);


        mansiViewModel.cargarShops().observe(getViewLifecycleOwner(), new Observer<List<Shop>>() {
            @Override
            public void onChanged(List<Shop> shops) {
                tiendasAdapter.establecerListaTiendas(shops);
            }
        });
    }


    class TiendasAdapter extends RecyclerView.Adapter<TiendasAdapter.TiendaViewHolder> {

        List<Shop> shopAddressesList;  // Lista de tiendas

        @NonNull
        @Override
        public TiendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TiendaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_shop, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TiendaViewHolder holder, int position) {
            Shop shop = shopAddressesList.get(position);

            holder.locationTextView.setText(shop.getLocation());
            holder.cityTextView.setText(shop.getCity());
            holder.addresseTextView.setText(shop.getAddresse());
        }

        @Override
        public int getItemCount() {
            return shopAddressesList == null ? 0 : shopAddressesList.size();
        }

        public void establecerListaTiendas(List<Shop> shopList) {
            shopAddressesList = shopList;
            notifyDataSetChanged();
        }


        class TiendaViewHolder extends RecyclerView.ViewHolder {
            TextView locationTextView, cityTextView, addresseTextView;

            TiendaViewHolder(@NonNull View itemView) {
                super(itemView);
                locationTextView = itemView.findViewById(R.id.item_center_map);
                cityTextView = itemView.findViewById(R.id.item_city_map);
                addresseTextView = itemView.findViewById(R.id.item_direction_map);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(41.3789631, 2.169138117);
        googleMap.addMarker(new MarkerOptions()
        .position(latLng)
        .title("Mobile Shop"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

}
