package com.example.hanshinchat1.simulation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.MainMenuActivity;
import com.example.hanshinchat1.R;

public class Simulation1 extends MainActivity {
    private Button nextBtn;
    private ImageButton backBtn, homeBtn;
    private RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation01);

        nextBtn = (Button) findViewById(R.id.next_btn);
        nextBtn.setEnabled(false);
        backBtn = (ImageButton) findViewById(R.id.back_btn);
        homeBtn = (ImageButton) findViewById(R.id.home_btn);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender);
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                nextBtn.setEnabled(checkedId != -1);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Simulation2.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Simulation0.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
