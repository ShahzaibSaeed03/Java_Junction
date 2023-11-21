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

public class Login_java extends AppCompatActivity {

    EditText  UserEmail, UserPassword;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    TextView SignUp;

    Button login_btn;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Login_java.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_java);


        mAuth = FirebaseAuth.getInstance();

        UserEmail = findViewById(R.id.LoginEmail);

        UserPassword = findViewById(R.id.LoginPassword);
        progressBar = findViewById(R.id.Progessbar);
        SignUp = findViewById(R.id.Sign_up_text);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_java.this, SignUp_page.class);
                startActivity(intent);
                finish();
            }
        });

        // Correct way to initialize the login_btn button
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    String Email,Password;
//
                    Email = String.valueOf(UserEmail.getText());
                    Password = String.valueOf(UserPassword.getText());
//

                    if (TextUtils.isEmpty(Email)){
                        Toast.makeText(Login_java.this,"Enter Email",Toast.LENGTH_SHORT);
                        return;
                    }

                    if (TextUtils.isEmpty(Password)){
                        Toast.makeText(Login_java.this,"Enter Password",Toast.LENGTH_SHORT);
                        return;
                    }
                    mAuth.signInWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {

                                        Intent intent = new Intent(Login_java.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Intent intent = new Intent(Login_java.this, SignUp_page.class);
                                        startActivity(intent);
                                        finish();

                                        Toast.makeText(Login_java.this, "Please Create A account.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
        });
        }

}
