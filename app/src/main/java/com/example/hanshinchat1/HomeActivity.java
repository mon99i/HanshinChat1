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

public class HomeActivity extends AppCompatActivity {

    Button deleteBtn;
    Button logoutBtn2;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount gsa;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);


        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference();

        deleteBtn=(Button)findViewById(R.id.deleteBtn);
        logoutBtn2=(Button)findViewById(R.id.logoutBtn);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUserInfo();
                }
        });

        logoutBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();

                }
        });

        ImageButton homeBtn = findViewById(R.id.home);
        ImageButton roomBtn = findViewById(R.id.room);
        ImageButton chatBtn = findViewById(R.id.chat);
        ImageButton postBtn = findViewById(R.id.post);
        ImageButton infoBtn = findViewById(R.id.info);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(chatIntent);
                finish();
            }
        });
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(postIntent);
                finish();
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(infoIntent);
                finish();
            }
        });


    }
    private void signOut() {


        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    mAuth.signOut();
                    Toast.makeText(getApplicationContext(), R.string.success_logout, Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    // ...
                });
        gsa = null;



    }


    private void deleteUserInfo() {
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
    }



    private void revokeAccess() {  //g회원 사  ??
        mAuth.signOut();
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}