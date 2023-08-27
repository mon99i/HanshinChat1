package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SetProfile13PersonalityActivity extends MainActivity {

    private List<String> selectedPersonality = new ArrayList<>();
    private static final int MAX_PERSONALITY = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] personalityArray = getResources().getStringArray(R.array.성격);

        setContentView(R.layout.set_profile_13_personality);

        Button nextBtn = findViewById(R.id.set_personality_next);
        LinearLayout personalityCheckBoxLayout = findViewById(R.id.personalityCheckBoxLayout);

        LinearLayout currentLinearLayout = null;
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        checkBoxParams.weight = 1;

        for (int i = 0; i < personalityArray.length; i++) {
            final String currentInterest = personalityArray[i];

            if (i % 4 == 0) {
                currentLinearLayout = new LinearLayout(this);
                currentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                personalityCheckBoxLayout.addView(currentLinearLayout);
            }

            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(currentInterest);
            checkBox.setLayoutParams(checkBoxParams);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (selectedPersonality.size() < MAX_PERSONALITY) {
                            selectedPersonality.add(currentInterest);
                        } else {
                            checkBox.setChecked(false);
                            Toast.makeText(getApplicationContext(), "최대 " + MAX_PERSONALITY + "개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        selectedPersonality.remove(currentInterest);
                    }
                }
            });
            currentLinearLayout.addView(checkBox);
        }


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = myRef.child("users").child(user.getUid());
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);
                            if (!selectedPersonality.isEmpty()) {
                                userInfo.setPersonality(String.valueOf(selectedPersonality));
                                userInfo.setUid(user.getUid());
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile14IdealTypeActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            } else {
                                Toast.makeText(getApplicationContext(), "관심사를 선택해주세요", Toast.LENGTH_SHORT).show();
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
                });
            }
        });
    }
}
