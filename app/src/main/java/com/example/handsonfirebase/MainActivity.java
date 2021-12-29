package com.example.handsonfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    private EditText userName , userPassword, userEmail, userPhoneNo;

    String email, password, name, phoneNo;
    Button upload, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        userName = findViewById(R.id.username_edit_text);
        userPassword = findViewById(R.id.password_edittext);
        userEmail = findViewById(R.id.email_edit_text);
        userPhoneNo = findViewById(R.id.phoneNo_edit_text);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.loggedIn);
        upload = findViewById(R.id.upload_button);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettingValuesFromEditText();
                validateUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signin.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }
    }

    public void gettingValuesFromEditText(){
        phoneNo = userPhoneNo.getText().toString().trim();
        email = userEmail.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        name = userName.getText().toString().trim();
    }

    public void registerUser(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            loginhelperclass userData = new loginhelperclass(phoneNo, name, email);

                            mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(MainActivity.this, "Succefully signed in", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
    }

    public void validateUser(){
        if (name.isEmpty()){
            userName.setError("Full name is required");
            userName.requestFocus();
            return;
        }
        if (name.length() < 5){
            userName.setError("Name is too short");
            userName.requestFocus();
            return;
        }
        if (name.length() > 20){
            userName.setError("Name is too large");
            userName.requestFocus();
            return;
        }
        if (password.isEmpty()){
            userPassword.setError("Password cannot be empty");
            userPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            userPassword.setError("Password should be atleast 6 characters");
            userPassword.requestFocus();
            return;
        }
        if (email.isEmpty()){
            userEmail.setError("User email required");
            userEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Provide valid email");
            userEmail.requestFocus();
            return;
        }
        if (phoneNo.isEmpty()){
            userPhoneNo.setError("Phone number cannot be empty");
            userPhoneNo.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        registerUser();
    }

}