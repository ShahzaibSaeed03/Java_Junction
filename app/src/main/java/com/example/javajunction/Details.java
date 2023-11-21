package com.example.javajunction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javajunction.ModelClass.Add_To_cart_model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    ImageView imageView, LikeBtn;
    TextView Title, desc, Prices;
    Button buyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String Name = intent.getStringExtra("itemName");
        String Desc = intent.getStringExtra("itemDesc");
        String Price = intent.getStringExtra("itemPrice");
        int imageItem = intent.getIntExtra("imageItem", 0);

        imageView = findViewById(R.id.DeitlsImage);
        Title = findViewById(R.id.DetilsTile);
        desc = findViewById(R.id.descDetils);
        Prices = findViewById(R.id.item_price);

        Prices.setText(Price);
        Title.setText(Name);
        desc.setText(Desc);
        imageView.setImageResource(imageItem);

        buyBtn = findViewById(R.id.Butto_Add_Cart);
        LikeBtn = findViewById(R.id.Like);

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Details.this, "Add to cart", Toast.LENGTH_SHORT).show();

                // Get the data of the selected item
                String itemName = Title.getText().toString();
                String itemDesc = desc.getText().toString();
                int imageItem = getIntent().getIntExtra("imageItem", 0);
                String itemPrice = Prices.getText().toString();

                // Create a new Add_To_cart_model object
                Add_To_cart_model cartItem = new Add_To_cart_model(imageItem, itemName, itemDesc, itemPrice);

                uploadToFirebase(cartItem);

                // Add the item to the list
                if (MainActivity.list == null) {
                    MainActivity.list = new ArrayList<>();
                }
                MainActivity.list.add(cartItem);
            }
        });
        saveCartToSharedPreferences();

    }

    private void saveCartToSharedPreferences() {

        if (MainActivity.list != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String cartItemsJson = gson.toJson(MainActivity.list);
            editor.putString("cartItems", cartItemsJson);
            editor.apply();
        }
    }

    private void uploadToFirebase(Add_To_cart_model cartItem) {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cart");
        String key = cartRef.push().getKey();
        cartRef.child(key).setValue(cartItem);
    }
}
