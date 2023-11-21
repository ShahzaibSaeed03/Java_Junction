package com.example.javajunction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SignUp_page extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("User Name");

    EditText Username , UserEmail,UserAddress,UserMobileNo, UserPassword;
    Button SiginUp;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView SignIn;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(SignUp_page.this, Login_java.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        mAuth = FirebaseAuth.getInstance();
       Username = findViewById(R.id.UserNames);
       UserEmail = findViewById(R.id.editTextHomeEmail);
       UserAddress = findViewById(R.id.editTextAddress);
       UserMobileNo = findViewById(R.id.editTextMobileNo);
       UserPassword = findViewById(R.id.editTextPassword);
       progressBar = findViewById(R.id.Progessbar);
       SignIn = findViewById(R.id.Signup_To_SignIn);
       SignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(SignUp_page.this, Login_java.class);
               startActivity(intent);
               finish();
           }
       });

       SiginUp = findViewById(R.id.signup_btn);
        // ...

        SiginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String Email, Password, Name, Address;
                String Mobile_No;

                Email = String.valueOf(UserEmail.getText());
                Password = String.valueOf(UserPassword.getText());
                Address = String.valueOf(UserAddress.getText());
                Name = String.valueOf(Username.getText());
                Mobile_No = String.valueOf(UserMobileNo.getText());

                // Create a HashMap to store user information
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("Name", Name);
                userMap.put("Address", Address);
                userMap.put("Number", Mobile_No);

                root.setValue(userMap);

                // Convert mobile number to a long
                long mobileNo = 0;
                try {
                    mobileNo = Long.parseLong(Mobile_No);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUp_page.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (TextUtils.isEmpty(Email)) {
                    // ... rest of your code ...
                }

                long finalMobileNo = mobileNo;
                mAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Get the user ID of the current user
                                    String userId = mAuth.getCurrentUser().getUid();

                                    // Create a reference to the user node in the database
                                    DatabaseReference userRef = root.child(userId);

                                    // Save user information under the user node
                                    userRef.child("Name").setValue(Name);
                                    userRef.child("Address").setValue(Address);
                                    userRef.child("Number").setValue(finalMobileNo);

                                    progressBar.setVisibility(View.GONE);
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(SignUp_page.this, Login_java.class);
                                    startActivity(intent);
                                    Toast.makeText(SignUp_page.this, "Authentication Successful.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    // ... rest of your code ...
                                }
                            }
                        });
            }
        });

// ...




    }
}