package com.example.hanshinchat1.utils;

import androidx.annotation.NonNull;

import com.example.hanshinchat1.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Utils {

    public static FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    public static DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();





    public static Boolean checkProfileOpen(){

        myRef.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo=snapshot.getValue(UserInfo.class);
               
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return true;
    }
}
