package com.example.hanshinchat1;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        checkCurrentUser();


        clickBoard();
        clickChat();
        clickRoom();
        clickHome();

        profile=(ImageView)findViewById(R.id.profile);

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
        });*/
        
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



        // 하단바 Fragment 상속해서 사용할때 쓸 코드

//        //기본화면 설정, MainActivity xml 만든 후 넣기
//         //getSupportFragmentManager().beginTransaction().replace(R.id.top_view, mainActivity).commit;
//
//        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
//        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
////                    case R.id.home:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,mainActivity).commit();
////                    case R.id.room:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,mainActivity).commit();
//                    case R.id.post:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,listActivity).commit();
//                    case R.id.chat:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,chatActivity).commit();
////                    case R.id.info:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,mainActivity).commit();
//                        return true;
//                }
//                return false;
//            }
//        });

    }
}
