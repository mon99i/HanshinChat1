package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SetProfile8HeightActivity extends MainActivity {

    private String selectedHeight;
    private String[] height;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_profile_8_height);

        Button nextBtn = findViewById(R.id.set_height_next);

        numberPicker = findViewById(R.id.height_number_picker);

        height = getResources().getStringArray(R.array.키);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(height.length-1);
        numberPicker.setValue(20);

        numberPicker.setDisplayedValues(height);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                selectedHeight = height[newVal];
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = myRef.child("users").child(user.getUid());
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);
                            if (!selectedHeight.isEmpty()) {
                                try {
                                    int heightValue = Integer.parseInt(selectedHeight);
                                    userInfo.setHeight(heightValue);
                                    userInfo.setUid(user.getUid());
                                    usersRef.setValue(userInfo);

                                    Intent intent = new Intent(getApplicationContext(), SetProfile9FormActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "올바른 키를 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "키를 선택해주세요", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile7DepartmentActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
