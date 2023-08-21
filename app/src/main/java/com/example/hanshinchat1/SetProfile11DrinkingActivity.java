package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SetProfile11DrinkingActivity extends MainActivity {
    AppCompatRadioButton setProfileDrinkingY, setProfileDrinkingN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_11_drinking);

        Button nextBtn = findViewById(R.id.set_drinking_next);

        setProfileDrinkingY = findViewById(R.id.set_profile_drinking_y);
        setProfileDrinkingN = findViewById(R.id.set_profile_drinking_n);

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = myRef.child("users").child(user.getUid());
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if(setProfileDrinkingY.isChecked()){
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                String strDrinking = "네";
                                userInfo.setDrinking(strDrinking);
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile12InterestActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            } else if (setProfileDrinkingN.isChecked()) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                String strDrinking = "아니오";
                                userInfo.setDrinking(strDrinking);
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile12InterestActivity.class);
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
                });
            }
        });
    }

    public void onRadioButtonClicked(View view){
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.set_profile_gender_male:
                if(isSelected) {
                    // 기능 구현
                }
                break;
            case R.id.set_profile_gender_female:
                if(isSelected){
                    // 기능 구현
                }
                break;
        }
    }
}
