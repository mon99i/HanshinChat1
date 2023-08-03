package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatRadioButton;

public class SetProfile3GenderActivity extends MainActivity {
    AppCompatRadioButton setProfileMale, setProfileFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_3_gender);

        Button nextBtn = findViewById(R.id.set_gender_next);

        setProfileMale = findViewById(R.id.set_profile_gender_male);
        setProfileFemale = findViewById(R.id.set_profile_gender_female);

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetProfile4AgeActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }

    public void onRadioButtonClicked(View view){
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.set_profile_gender_male:
                if(isSelected){
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
