package com.example.javajunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javajunction.ModelClass.Add_To_cart_model;
import com.example.javajunction.fargments.Cart;

public class Details extends AppCompatActivity {

    ImageView imageView;
    TextView Title, desc;
    Button buyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String Name = intent.getStringExtra("itemName");
        String Desc = intent.getStringExtra("itemDesc");
        int imageItem = intent.getIntExtra("imageItem", 0);

        imageView = findViewById(R.id.DeitlsImage);
        Title = findViewById(R.id.DetilsTile);
        desc = findViewById(R.id.descDetils);

        Title.setText(Name);
        desc.setText(Desc);
        imageView.setImageResource(imageItem);
        buyBtn = findViewById(R.id.Butto_Add_Cart);

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Details.this, "Add to cart", Toast.LENGTH_SHORT).show();

                // Get the data of the selected item
                String itemName = Title.getText().toString();
                String itemDesc = desc.getText().toString();
                int imageItem = getIntent().getIntExtra("imageItem", 0);

                // Create a bundle to pass the data to the fragment
                Bundle bundle = new Bundle();
                bundle.putString("itemName", itemName);
                bundle.putString("itemDesc", itemDesc);
                bundle.putInt("imageItem", imageItem);

                // Get the Cart fragment instance
                Cart cartFragment = (Cart) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                if (cartFragment != null) {
                    // Update the data in the Cart fragment through the adapter
                    cartFragment.updateCartData(new Add_To_cart_model(imageItem, itemName, itemDesc, "$15"));
                }
            }
        });




    }
}
