package com.example.handsonfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText userName , userPassword, userEmail, userPhoneNo;

    String email, password, name, phoneNo;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        userName = findViewById(R.id.username_edit_text);
        userPassword = findViewById(R.id.password_edittext);
        userEmail = findViewById(R.id.email_edit_text);
        userPhoneNo = findViewById(R.id.phoneNo_edit_text);

        upload = findViewById(R.id.upload_button);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettingValuesFromEditText();
                writeUserDatainToDatabase();

                Intent intent = new Intent(MainActivity.this, profile.class);
                startActivity(intent);
            }
        });
    }

    public void gettingValuesFromEditText(){
        phoneNo = userPhoneNo.getText().toString();
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();
        name = userName.getText().toString();
    }

    public void writeUserDatainToDatabase(){

        loginhelperclass userData = new loginhelperclass(phoneNo, name, password, email);

        mDatabase.child("user").setValue(userData);

    }

}