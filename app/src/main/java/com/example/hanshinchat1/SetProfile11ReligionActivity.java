package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SetProfile11ReligionActivity extends MainActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;

    private RadioButton selectedRadioButton;
    private Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_11_religion);

        radioGroup = findViewById(R.id.religion_radio_group);
        radioButton1 = findViewById(R.id.religion_radio_btn_1);
        radioButton2 = findViewById(R.id.religion_radio_btn_2);
        radioButton3 = findViewById(R.id.religion_radio_btn_3);
        radioButton4 = findViewById(R.id.religion_radio_btn_4);
        radioButton5 = findViewById(R.id.religion_radio_btn_5);

        nextBtn = findViewById(R.id.set_religion_next);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = radioButton1;
                radioButton1.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton5.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = radioButton2;
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton5.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = radioButton3;
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton5.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });

        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = radioButton4;
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton5.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });

        radioButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = radioButton5;
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton5.setBackgroundResource(R.drawable.radio_button_checked);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                selectedRadioButton = findViewById(selectedRadioButtonId);

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRadioButton != null) {
                    DatabaseReference userRef = myRef.child("users").child(user.getUid());

                    String selectedReligion = selectedRadioButton.getText().toString();
                    userRef.child("religion").setValue(selectedReligion).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getApplicationContext(), SetProfile12SmokingActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                    });
                    /*usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);

                                String selectedReligion = selectedRadioButton.getText().toString();
                                userInfo.setReligion(selectedReligion);
                                userInfo.setUid(user.getUid());
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile12SmokingActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
                } else {
                    Toast.makeText(getApplicationContext(), "종교를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile10AddressActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
