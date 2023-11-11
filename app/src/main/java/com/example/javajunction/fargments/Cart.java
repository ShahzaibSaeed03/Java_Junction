package com.example.javajunction.fargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javajunction.AdopterClass.Add_to_cart_adopter;
import com.example.javajunction.ModelClass.Add_To_cart_model;
import com.example.javajunction.R;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment {
    RecyclerView recyclerView;
    Add_to_cart_adopter addToCartAdopter;
    List<Add_To_cart_model> list;

    public Cart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.Addcart_Layout);
        list = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), RecyclerView.VERTICAL);


        recyclerView.setLayoutManager(layoutManager);
        addToCartAdopter = new Add_to_cart_adopter(list);
        recyclerView.setAdapter(addToCartAdopter);

        return view;
    }

    // Method to update data in the Cart fragment
    public void updateCartData(Add_To_cart_model cartItem) {
        // Add the item to the list and notify the adapter
        list.add(cartItem);
        addToCartAdopter.notifyItemInserted(list.size() - 1);
    }
}
