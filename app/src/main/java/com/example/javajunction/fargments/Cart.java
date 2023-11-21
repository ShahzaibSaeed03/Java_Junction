package com.example.javajunction.fargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javajunction.AdopterClass.Add_to_cart_adopter;
import com.example.javajunction.MainActivity;
import com.example.javajunction.ModelClass.Add_To_cart_model;
import com.example.javajunction.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cart extends Fragment {
    RecyclerView recyclerView;

    ImageView delete_item;
    Add_to_cart_adopter addToCartAdopter;
//    List<Add_To_cart_model> list;

    public Cart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.Addcart_Layout);
//        MainActivity.list = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        addToCartAdopter = new Add_to_cart_adopter(MainActivity.list);
        recyclerView.setAdapter(addToCartAdopter);

        // Fetch data from Firebase and update the list
        fetchDataFromFirebase();

        return view;
    }

    // Method to fetch data from Firebase
    private void fetchDataFromFirebase() {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cart");

        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if MainActivity.list is null and initialize it
                if (MainActivity.list == null) {
                    MainActivity.list = new ArrayList<>();
                } else {
                    // Clear the existing list
                    MainActivity.list.clear();
                }

                // Iterate through the dataSnapshot to get the data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Add_To_cart_model cartItem = snapshot.getValue(Add_To_cart_model.class);
                    MainActivity.list.add(cartItem);
                }

                // Notify the adapter about the data change
                addToCartAdopter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }


    // Method to update data in the Cart fragment
    public void updateCartData(Add_To_cart_model cartItem) {
        // Add the item to the list and notify the adapter
        MainActivity.list.add(cartItem);
        addToCartAdopter.notifyItemInserted(MainActivity.list.size() - 1);
    }
}
