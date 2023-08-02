package com.example.hanshinchat1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends MainActivity{

    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        clickMenu();
        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();

        ImageView profile = (ImageView) findViewById(R.id.profileImage);

        Button profileEditBtn = (Button) findViewById(R.id.profile_edit);
        
        myRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserInfo userInfo=snapshot.getValue(UserInfo.class);
                    String imageUrl=userInfo.getPhotoUrl();
                    Uri imageUri=Uri.parse(imageUrl);
                    Glide.with(getApplicationContext())
                            .load(imageUri)
                            .into(profile);
                 
                }
                else Log.d(TAG, "onDataChange: 데이터없음");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileEditActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

// 프로필 이미지 삽입 다른 코드
/*
        StorageReference profileRef=storageRef.child("profile.jpg/"+user.getUid());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(profile);
                //profile.setImageURI(uri);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "프로필 안뜸!", Toast.LENGTH_SHORT).show();
            }
        }); */
