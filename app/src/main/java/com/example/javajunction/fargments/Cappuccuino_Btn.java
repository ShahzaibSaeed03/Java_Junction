package com.example.javajunction.fargments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javajunction.AdopterClass.CoffeAdapter;
import com.example.javajunction.ModelClass.CoffeeModel;
import com.example.javajunction.Details;
import com.example.javajunction.R;

import java.util.ArrayList;
import java.util.List;

public class Cappuccuino_Btn extends Fragment {
    RecyclerView recyclerView;
    Button filterButton;
    CoffeAdapter adapter;

    List<CoffeeModel>list;
    List<CoffeeModel> filterlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cappuccuino__btn, container, false);

        filterButton = view.findViewById(R.id.Cappuccino);
        recyclerView = view.findViewById(R.id.fargments);

        list = new ArrayList<>();


        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false);



        list.add(new CoffeeModel(R.drawable.coffe1, "Cappuccino", "With Chocolate", "$15"));
        list.add(new CoffeeModel(R.drawable.capp_2, "Cappuccino", "With Vanilla", "$16"));
        list.add(new CoffeeModel(R.drawable.capp_3, "Cappuccino", "With Caramel", "$14"));
        list.add(new CoffeeModel(R.drawable.capp_8, "Cappuccino", "With Hazelnut", "$17"));
        list.add(new CoffeeModel(R.drawable.capp_5, "Cappuccino", "With Almond", "$15"));
        list.add(new CoffeeModel(R.drawable.capp_6, "Cappuccino", "With Coconut", "$18"));

        list.add(new CoffeeModel(R.drawable.c1, "Macchiato", "With Caramel", "$15" ));
        list.add(new CoffeeModel(R.drawable.c2, "Macchiato", "With Vanilla", "$10" ));
        list.add(new CoffeeModel(R.drawable.c3, "Macchiato", "With Hazelnut", "$18" ));
        list.add(new CoffeeModel(R.drawable.c4, "Macchiato", "With Mocha", "$17" ));
        list.add(new CoffeeModel(R.drawable.c5, "Macchiato", "With Coconut", "$15" ));
        list.add(new CoffeeModel(R.drawable.c6, "Macchiato", "With Irish Cream", "$13" ));

        list.add(new CoffeeModel(R.drawable.c7, "Latti", "With Caramel", "$20" ));
        list.add(new CoffeeModel(R.drawable.c8, "Latti", "With Vanilla", "$24" ));
        list.add(new CoffeeModel(R.drawable.c9, "Latti", "With Hazelnut", "$28" ));
        list.add(new CoffeeModel(R.drawable.capp_2, "Latti", "With Mocha", "$27" ));
        list.add(new CoffeeModel(R.drawable.capp_4, "Latti", "With Coconut ", "$35" ));
        list.add(new CoffeeModel(R.drawable.capp_6, "Latti", "With Irish Cream", "$23" ));

        recyclerView.setLayoutManager(layoutManager);
        adapter = new CoffeAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoffeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CoffeeModel selectedItem = list.get(position);
                String itemName = selectedItem.getTitle();
                String itemDesc = selectedItem.getDescription();
                String itemPrice = selectedItem.getPrice();
                int imageItem = selectedItem.getImage();
                Intent intent = new Intent(requireContext()  , Details.class);
                intent.putExtra("itemName",itemName);
                intent.putExtra("itemDesc",itemDesc);
                intent.putExtra("itemPrice",itemPrice);
                intent.putExtra("imageItem",imageItem);


                startActivity(intent);
            }
        });

        filterButton.setText("Cappuccino"); // Set the button text to "Cappuccino"

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterlist=new ArrayList<>();
                // Get the text from the button
                String buttonText = filterButton.getText().toString();
                filterlist.clear();

                for (CoffeeModel item : list) {
                    if (item.getTitle().contains(buttonText)) {
                        filterlist.add(item);
                    }
                }

                // Call the filter method
                adapter.filterByTitle(filterlist);
                if (filterlist.isEmpty()){
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });





        return view;
    }
}
