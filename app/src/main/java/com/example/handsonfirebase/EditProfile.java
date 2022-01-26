package com.example.handsonfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    private final int PICK_IMAGE = 25;

    String imageDownloadPath;

    Bitmap bitmap;

    Button selectImage, uploadImage;
    Uri imageUri;
    ImageView profilePhoto;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        selectImage = findViewById(R.id.selectImage);
        uploadImage = findViewById(R.id.uploadImage);
        profilePhoto = findViewById(R.id.profilePhoto);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference();

        selectImage.setOnClickListener(this);
        uploadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectImage:
                selectImage();
            case R.id.uploadImage:
                uploadImage();
        }
    }

    public void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                profilePhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(){
        if (imageUri != null){
            StorageReference ref = storageReference.child("images/" + mAuth.getUid());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = ref.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(EditProfile.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageDownloadPath = uri.toString();
                        }
                    });
                }
            });

            Intent intent = getIntent();
            loginhelperclass userDetails = new loginhelperclass(intent.getStringExtra("userPhoneNo"),
                    intent.getStringExtra("userName"), intent.getStringExtra("userEmail"),
                    imageDownloadPath);

            databaseReference.child(mAuth.getUid()).setValue(userDetails);
        }
    }
}