package com.example.handsonfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class signin extends AppCompatActivity {

    String email, password;

    TextView reset;

    EditText userEmail_SignIn, passwordSignIn;
    Button signIN;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userEmail_SignIn = findViewById(R.id.email_signin_edit_text);
        passwordSignIn = findViewById(R.id.password_signin_edit_text);
        signIN = findViewById(R.id.signin);
        reset = findViewById(R.id.resetTextView);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signin.this, ForgotPassword.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();

        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmailandPassword();
                signInUser(email, password);
            }
        });


    }

    public void getEmailandPassword(){
        email = userEmail_SignIn.getText().toString().trim();
        password = passwordSignIn.getText().toString().trim();
    }

    public void signInUser(String email, String password){
        validateEmailandPassword();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()){
                                startActivity(new Intent(signin.this, profile.class));
                            }
                            else{
                                user.sendEmailVerification();
                                Toast.makeText(signin.this, "Check your email for verification", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(signin.this, "Failed to login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void validateEmailandPassword(){
        if (password.isEmpty()){
            passwordSignIn.setError("Password cannot be empty");
            passwordSignIn.requestFocus();
            return;
        }
        if (password.length() < 6){
            passwordSignIn.setError("Password should be atleast 6 characters");
            passwordSignIn.requestFocus();
        }
        if (email.isEmpty()){
            userEmail_SignIn.setError("User email required");
            userEmail_SignIn.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail_SignIn.setError("Provide valid email");
            userEmail_SignIn.requestFocus();
        }
    }
}