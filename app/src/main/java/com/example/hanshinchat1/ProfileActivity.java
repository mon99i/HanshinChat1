package com.example.hanshinchat1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends MainActivity{

    private static final String TAG = "ProfileActivity";

    private TextView name, gender, age;
    private DatabaseReference databaseReference;
    private UserInfo userInfo;

    Button ideal_edit_btn, settingBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();

        ImageView profile = (ImageView) findViewById(R.id.profileImage);

        Button profileEditBtn = (Button) findViewById(R.id.profile_edit);

        settingBtn = (Button) findViewById(R.id.setting);

        ideal_edit_btn=findViewById(R.id.ideal_edit_btn);

        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);

        databaseReference = FirebaseDatabase.getInstance().getReference("userInfo");


        myRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userInfo = snapshot.getValue(UserInfo.class);
                    name.setText(userInfo.getName());
                    gender.setText(userInfo.getGender());
                    age.setText(userInfo.getAge().toString());

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

        ideal_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetIdealActivity.class);
                startActivity(intent);
                finish();
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
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
