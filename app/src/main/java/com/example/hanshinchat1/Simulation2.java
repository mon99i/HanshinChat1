package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Simulation2 extends MainActivity {
    public int questionScore = 0;
    private Button nextBtn;
    private ImageButton backBtn, homeBtn;
    private RadioGroup questionRadioGroup;
    private RadioButton answer1, answer2, answer3, answer4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation02);

        questionRadioGroup = (RadioGroup) findViewById(R.id.question_radio_group);
        answer1 = (RadioButton) findViewById(R.id.answer1_btn);
        answer2 = (RadioButton) findViewById(R.id.answer2_btn);
        answer3 = (RadioButton) findViewById(R.id.answer3_btn);
        answer4 = (RadioButton) findViewById(R.id.answer4_btn);
        nextBtn = (Button) findViewById(R.id.next_btn);
        nextBtn.setEnabled(false);
        backBtn = (ImageButton) findViewById(R.id.back_btn);
        homeBtn = (ImageButton) findViewById(R.id.home_btn);
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionScore = 7;
                SimulationScoreManager.setQuestionScore(Simulation2.class, questionScore);
            }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionScore = 4;
                SimulationScoreManager.setQuestionScore(Simulation2.class, questionScore);
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionScore = 2;
                SimulationScoreManager.setQuestionScore(Simulation2.class, questionScore);
            }
        });
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionScore = 10;
                SimulationScoreManager.setQuestionScore(Simulation2.class, questionScore);
            }
        });
        questionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                nextBtn.setEnabled(checkedId != -1);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.addToScore(questionScore);

                Intent intent = new Intent(getApplicationContext(), Simulation3.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.resetScore();

                Intent intent = new Intent(getApplicationContext(), Simulation1.class);
                startActivity(intent);
                finish();
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.resetScore();

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
