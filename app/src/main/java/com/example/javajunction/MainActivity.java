package com.example.javajunction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javajunction.fargments.Cappuccuino_Btn;
import com.example.javajunction.fargments.Cart;
import com.example.javajunction.fargments.Favtrioute;
import com.example.javajunction.fargments.Notifvcation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    Cappuccuino_Btn firstFragment;
    Button btn1;
    Favtrioute favtrioute;
    Cart cart;
    Notifvcation notifvcation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Initialize your fragments
        firstFragment = new Cappuccuino_Btn();
        favtrioute = new Favtrioute();
        cart = new Cart();
        notifvcation = new Notifvcation();

        // Load the default fragment when the activity is created
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, firstFragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.homess) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, firstFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.Favt) {// Replace the current fragment with the Favtrioute fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, favtrioute)
                    .commit();
            return true;
        } else if (itemId == R.id.Cart) {// Replace the current fragment with the Cart fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, cart)
                    .commit();
            return true;
        } else if (itemId == R.id.Noti) {// Replace the current fragment with the Notifvcation fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, notifvcation)
                    .commit();
            return true;
        }
        return false;
    }
}
