package com.example.handsonfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    TextView phoneNo, name, email, password;

    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = findViewById(R.id.email_text_view);
        password = findViewById(R.id.password_text_view);
        name = findViewById(R.id.username_text_view);
        phoneNo = findViewById(R.id.phoneNo_text_view);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loginhelperclass currentUser = snapshot.getValue(loginhelperclass.class);
                settingTextView(currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void settingTextView(loginhelperclass currentUser){
        name.setText(currentUser.getUserName());
        email.setText(currentUser.getUserEmail());
        password.setText(currentUser.getPassword());
        phoneNo.setText(currentUser.getPhoneNo());
    }
}