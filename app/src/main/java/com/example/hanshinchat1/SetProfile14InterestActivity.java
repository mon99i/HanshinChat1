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

public class SetProfile14InterestActivity extends MainActivity {

    private List<String> selectedInterests = new ArrayList<>();
    private static final int MAX_INTERESTS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] interestArray = getResources().getStringArray(R.array.관심사);

        setContentView(R.layout.set_profile_14_interest);

        Button nextBtn = findViewById(R.id.set_interest_next);
        LinearLayout checkBoxLayout = findViewById(R.id.checkBoxLayout);

        // 수정된 부분
        LinearLayout currentLinearLayout = null;
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        checkBoxParams.weight = 1;

        for (int i = 0; i < interestArray.length; i++) {
            final String currentInterest = interestArray[i]; // final 변수로 선언

            if (i % 4 == 0) {
                currentLinearLayout = new LinearLayout(this);
                currentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                checkBoxLayout.addView(currentLinearLayout);
            }

            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(currentInterest);
            checkBox.setLayoutParams(checkBoxParams);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (selectedInterests.size() < MAX_INTERESTS) {
                            selectedInterests.add(currentInterest); // final 변수 사용
                        } else {
                            checkBox.setChecked(false);
                            Toast.makeText(getApplicationContext(), "최대 " + MAX_INTERESTS + "개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        selectedInterests.remove(currentInterest); // final 변수 사용
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
                            if (!selectedInterests.isEmpty()) {
                                userInfo.setInterest(String.valueOf(selectedInterests));
                                userInfo.setUid(user.getUid());
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile15PersonalityActivity.class);
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
