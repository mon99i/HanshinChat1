package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetProfile3GenderActivity extends MainActivity {
    private AppCompatRadioButton setProfileMale, setProfileFemale;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_3_gender);

        nextBtn = findViewById(R.id.set_gender_next);

        setProfileMale = findViewById(R.id.set_profile_gender_male);
        setProfileFemale = findViewById(R.id.set_profile_gender_female);

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseReference userRef = myRef.child("users").child(user.getUid());
                if (setProfileMale.isChecked()) {
                    String strGender = "남자";
                    userRef.child("gender").setValue(strGender).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(getApplicationContext(), SetProfile4AgeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                }
                            });

                } else if (setProfileFemale.isChecked()) {
                    String strGender = "여자";
                    userRef.child("gender").setValue(strGender).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getApplicationContext(), SetProfile4AgeActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                    });
                }



                /*usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if(setProfileMale.isChecked()){
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                String strGender = "남자";
                                userInfo.setGender(strGender);
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile4AgeActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            } else if (setProfileFemale.isChecked()) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                String strGender = "여자";
                                userInfo.setGender(strGender);
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile4AgeActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            }
                        }
                        else {
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

    public void onRadioButtonClicked(View view) {
        boolean isSelected = ((AppCompatRadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.set_profile_gender_male:
                if (isSelected) {
                    // 기능 구현
                }
                break;
            case R.id.set_profile_gender_female:
                if (isSelected) {
                    // 기능 구현
                }
                break;
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile2NameActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
