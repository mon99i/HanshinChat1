package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SetProfile9FormActivity extends MainActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_9_form);

        radioGroup = findViewById(R.id.form_radio_group);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);

        Button nextBtn = findViewById(R.id.set_form_next);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_checked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });

        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton2.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton3.setBackgroundResource(R.drawable.radio_button_unchecked);
                radioButton4.setBackgroundResource(R.drawable.radio_button_checked);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                if (selectedRadioButtonId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String selectedForm = selectedRadioButton.getText().toString();

                    DatabaseReference usersRef = myRef.child("users").child(user.getUid());
                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);

                                userInfo.setForm(selectedForm);
                                userInfo.setUid(user.getUid());
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile10addressActivity.class);
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
                    });
                }
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile8HeightActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
