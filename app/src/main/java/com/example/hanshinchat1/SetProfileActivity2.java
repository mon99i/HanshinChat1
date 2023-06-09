package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetProfileActivity2 extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText nickname;
    EditText hobby;
    EditText mbti;

    EditText major;
    Button saveBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile2);

        database = FirebaseDatabase.getInstance();
        myRef=  database.getReference();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        nickname=(EditText) findViewById(R.id.nickname);
        hobby=(EditText) findViewById(R.id.hobby);
        mbti=(EditText) findViewById(R.id.mbti);
        major=(EditText)findViewById(R.id.major);
        saveBtn=(Button)findViewById(R.id.saveBtn);


        if(user != null){

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveUserInfo();
            }
        });
        }
        else
            Toast.makeText(getApplicationContext(), "로그인 한 사람이 없다!!", Toast.LENGTH_SHORT).show();


    }
    public void  saveUserInfo() {
        DatabaseReference usersRef = myRef.child("users").child(user.getUid());
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);

                    String strNickName=nickname.getText().toString();
                    String strMajor=major.getText().toString();
                    String strHobby=hobby.getText().toString();
                    String strMbti=mbti.getText().toString();
                    // UserInfo 객체 업데이트
                    userInfo.setNickName(strNickName);
                    userInfo.setMajor(strMajor);
                    userInfo.setHobby(strHobby);
                    userInfo.setMbti(strMbti);

                    // 업데이트된 UserInfo 객체를 실시간 데이터베이스에 저장
                    usersRef.setValue(userInfo);

                    Toast.makeText(getApplicationContext(), "프로필 저장 성공!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "UserInfo가 없다!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "프로필 저장 실패!!", Toast.LENGTH_SHORT).show();
            }

            //String idToken=user.getUid();

            //UserInfo userInfo=new UserInfo();

            //myRef.setValue("hello world");

            // saveUserInfo(userInfo);
        });
      /*  String userId =user.getUid();
        // [START rtdb_write_new_user_task]
        myRef.child("users").child(userId).setValue(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        Toast.makeText(getApplicationContext(), "프로필 저장 성공!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "프로필 저장 실패!!", Toast.LENGTH_SHORT).show();
                        // Intent intent=new Intent(SetProfileActivity.this,MainActivity.class);
                        //  startActivity(intent);

                        // Write failed
                        // ...
                    }
                });*/
        // [END rtdb_write_new_user_task]
    }

}