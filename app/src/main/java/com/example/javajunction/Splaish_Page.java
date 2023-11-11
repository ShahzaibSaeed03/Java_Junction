package com.example.javajunction;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Splaish_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splaish_page);
        Thread thread = new Thread() {
            public void run () {

                try {
                    sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(Splaish_Page.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };thread.start();
    }
}