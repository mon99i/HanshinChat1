package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetProfile2NameActivity extends MainActivity {

    private Button nextBtn;
    private EditText name;
    //chatRoom = (ChatRoom) getIntent().getSerializableExtra("ChatRoom");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_profile_2_name);
        UserInfo userInfo=(UserInfo) getIntent().getSerializableExtra("UserInfo");

        nextBtn = findViewById(R.id.set_name_next);
        name = (EditText) findViewById(R.id.name);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String strName = name.getText().toString();

                if (!strName.isEmpty() && strName.length() >= 2) {
                    try {
                        userInfo.setName(strName);
                        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid())
                                .setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(getApplicationContext(), SetProfile3GenderActivity.class);
                                        intent.putExtra("UserInfo",userInfo);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "올바른 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "이름은 2자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                }






                //DatabaseReference usersRef = myRef.child("users").child(user.getUid());

               /* usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);

                            String strName = name.getText().toString();
                            if (!strName.isEmpty() && strName.length() >= 2) {
                                try {
                                    userInfo.setName(strName);
                                    usersRef.setValue(userInfo);

                                    Intent intent = new Intent(getApplicationContext(), SetProfile3GenderActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "올바른 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "이름은 2자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "프로필 저장 실패", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile1PhotoActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
