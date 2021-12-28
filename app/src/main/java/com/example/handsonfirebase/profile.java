package com.example.handsonfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    TextView phoneNo, name, email;

    FirebaseAuth mAuth;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = findViewById(R.id.email_text_view);
        name = findViewById(R.id.username_text_view);
        phoneNo = findViewById(R.id.phoneNo_text_view);

        logout = findViewById(R.id.logout_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile.this, MainActivity.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
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
        phoneNo.setText(currentUser.getPhoneNo());
    }
}