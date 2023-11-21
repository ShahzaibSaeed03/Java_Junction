package com.example.javajunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Contact_US extends AppCompatActivity {

    ImageView Back_pressed;
    LinearLayout MapClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Back_pressed = findViewById(R.id.backpressed);
        Back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        MapClick = findViewById(R.id.linearLayout14);
        MapClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contact_US.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}