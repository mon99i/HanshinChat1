package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.text.TextUtils; // 추가된 import 문

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SetProfile15PersonalityActivity extends MainActivity {

    private ArrayList<String> selectedPersonality = new ArrayList<>();

    private static final int MAX_PERSONALITY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_15_personality);

        String[] personalityArray = getResources().getStringArray(R.array.성격);


        Button nextBtn = findViewById(R.id.set_personality_next);
        LinearLayout checkBoxLayout = findViewById(R.id.personality_checkbox_layout);

        // 수정된 부분
        LinearLayout currentLinearLayout = null;
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        checkBoxParams.weight = 1;
        checkBoxParams.setMargins(2, 2, 2, 2);

        for (int i = 0; i < personalityArray.length; i++) {
            final String currentPersonality = personalityArray[i];

            if (i % 4 == 0) {
                currentLinearLayout = new LinearLayout(this);
                currentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                checkBoxLayout.addView(currentLinearLayout);
            }

            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(currentPersonality);
            checkBox.setLayoutParams(checkBoxParams);
            checkBox.setBackgroundResource(R.drawable.button_border);
            checkBox.setButtonDrawable(null);
            checkBox.setGravity(Gravity.CENTER);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (selectedPersonality.size() < MAX_PERSONALITY) {
                            selectedPersonality.add(currentPersonality);
                        } else {
                            checkBox.setChecked(false);
                            Toast.makeText(getApplicationContext(), "최대 " + MAX_PERSONALITY + "개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        selectedPersonality.remove(currentPersonality);
                    }
                }
            });
            currentLinearLayout.addView(checkBox);
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = myRef.child("users").child(user.getUid());

                if (!selectedPersonality.isEmpty()) {
                    userRef.child("personality").setValue(selectedPersonality).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getApplicationContext(), SetProfile16MbtiActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "성격 타입을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
               /* usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);
                            if (!selectedPersonality.isEmpty()) {

                                String personalityAsString = TextUtils.join(", ", selectedPersonality);

                                usersRef.child("personality").setValue(personalityAsString);

                                userInfo.setPersonality(personalityAsString);
                                userInfo.setUid(user.getUid());
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile16MbtiActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            } else {
                                Toast.makeText(getApplicationContext(), "성격 타입을 선택해주세요", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(getApplicationContext(), SetProfile14InterestActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
