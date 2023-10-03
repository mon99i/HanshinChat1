package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

    public class Simulation2 extends AppCompatActivity {
        public int question1Score = 0;
        public int totalScore;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.simulation02);

            RadioButton question1_Answer1 = findViewById(R.id.question1_answer1);
            RadioButton question1_Answer2 = findViewById(R.id.question1_answer2);
            RadioButton question1_Answer3 = findViewById(R.id.question1_answer3);
            RadioButton question1_Answer4 = findViewById(R.id.question1_answer4);

            Button next2 = findViewById(R.id.next2);

            question1_Answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question1Score = 7;
                    totalScore += question1Score;
                }
            });

            question1_Answer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question1Score = 4;
                    totalScore += question1Score;
                }
            });

            question1_Answer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question1Score = 2;
                    totalScore += question1Score;
                }
            });

            question1_Answer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question1Score = 10;
                    totalScore += question1Score;
                }
            });

            next2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Simulation3.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            });
        }
    }
