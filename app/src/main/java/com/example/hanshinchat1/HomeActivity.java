package com.example.hanshinchat1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends MainActivity {

    Button deleteBtn;
    Button logoutBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        checkCurrentUser();
       checkProfileExist();

        clickBoard();
        clickChat();
        clickProfile();
        clickRoom();


        deleteBtn=(Button)findViewById(R.id.deleteBtn);
        logoutBtn2=(Button)findViewById(R.id.logoutBtn);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUser();

                }
        });

        logoutBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();

                }
        });


    }
    /*protected void signOut() {


        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    mAuth.signOut();
                    Toast.makeText(getApplicationContext(), R.string.success_logout, Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class    );
                    startActivity(intent);
                    // ...
                });
        gsa = null;



    }*/


   /* private void deleteUerInfo() {
        // [START delete_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete();
        myRef.child("users").child(user.getUid()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        Toast.makeText(getApplicationContext(), "유저정보 삭제 성공!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "유저정보 삭제 실패!!", Toast.LENGTH_SHORT).show();
                        // Intent intent=new Intent(SetProfileActivity.this,MainActivity.class);
                        //  startActivity(intent);

                        // Write failed
                        // ...
                    }
                });
    }*/




}
