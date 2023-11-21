package com.example.javajunction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.javajunction.ModelClass.Add_To_cart_model;
import com.example.javajunction.fargments.Cappuccuino_Btn;
import com.example.javajunction.fargments.Cart;
import com.example.javajunction.fargments.Favtrioute;
import com.example.javajunction.fargments.My_Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    Cappuccuino_Btn firstFragment;
    ImageView menu;
    FirebaseAuth mAuth;
    LinearLayout Home, Contact_us , About_Us , Privecy , LogOut;
    Button btn1;
    Favtrioute favtrioute;
    Cart cart;
    My_Profile my_profile;

    ImageView profile_edit ;

    public static List<Add_To_cart_model> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.mainfargmentss);
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Contact_us = findViewById(R.id.Contect_us);
        Contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Contact_US.class);
                startActivity(intent);
            }
        });


        About_Us = findViewById(R.id.About_us);
        About_Us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, About_Us.class);
                startActivity(intent);
            }
        });

        Privecy = findViewById(R.id.Privecy_Policy);
        Privecy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Privecy_Policy.class);
                startActivity(intent);
            }
        });

        LogOut = findViewById(R.id.logout);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login_java.class);
                startActivity(intent);
                finish();

            }
        });




        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Initialize your fragments
        firstFragment = new Cappuccuino_Btn();
        favtrioute = new Favtrioute();
        cart = new Cart();
        my_profile = new My_Profile();

        // Load the default fragment when the activity is created
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, firstFragment)
                .commit();

        // Profile pic edit code

//        profile_edit = findViewById(R.id.Profile_image);
//        profile_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showImageChooserDialog();
//            }
//
//            private void showImageChooserDialog() {
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Choose Image Source");
//                builder.setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        int requestCode = (which == 0) ? 124 : 123; // 124 for camera, 123 for gallery
//                        imageChooser(requestCode);
//                    }
//                });
//                builder.show();
//            }
//
//            private void imageChooser(int requestCode) {
//                Intent i;
//                if (requestCode == 124) {
//                    // Capture image from camera
//                    i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    if (i.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(i, requestCode);
//                    }
//                } else {
//                    // Choose image from gallery
//                    i = new Intent();
//                    i.setType("image/*");
//                    i.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(i, "Select Picture"), requestCode);
//                }
//            }
//        });

        // Assuming you have a DatabaseReference pointing to your user's node in the database
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User Name");

// Replace "uid" with the UID of the current user
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

// Assuming you have a DatabaseReference pointing to the profile images node under the user's UID
        DatabaseReference profileImageRef = userRef.child(uid).child("profile_images");

        profileImageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assuming you have a field "imageUrl" under the profile_images node
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                    // Load the image into ImageView using a library like Picasso or Glide
                    ImageView imageView = findViewById(R.id.Profile_images);

                    Picasso.get().load(imageUrl).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            profile_edit.setImageURI(uri);
        } else if (requestCode == 124 && resultCode == RESULT_OK && data != null) {
            // Image captured from camera
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Do something with the bitmap, for example, set it to an ImageView
            profile_edit.setImageBitmap(imageBitmap);
        }
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
        } else if (itemId == R.id.Profile) {// Replace the current fragment with the Notifvcation fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, my_profile)
                    .commit();
            return true;
        }
        return false;
    }
}
