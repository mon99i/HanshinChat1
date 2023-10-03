package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Simulation11 extends AppCompatActivity {
    public int question10Score = 0;
    public int totalScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation11);

        RadioButton question10_Answer1 = findViewById(R.id.question10_answer1);
        RadioButton question10_Answer2 = findViewById(R.id.question10_answer2);
        RadioButton question10_Answer3 = findViewById(R.id.question10_answer3);
        RadioButton question10_Answer4 = findViewById(R.id.question10_answer4);
        Button next11 = (Button) findViewById(R.id.next11);
        Intent intent = getIntent();
        totalScore = intent.getIntExtra("totalScore", totalScore);
        question10_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question10Score = 4;
                totalScore += question10Score;
            }
        });
        question10_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question10Score = 7;
                totalScore += question10Score;
            }
        });
        question10_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question10Score = 2;
                totalScore += question10Score;
            }
        });
        question10_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question10Score = 10;
                totalScore += question10Score;
            }
        });

        next11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SimulationResult.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }

        });
    }
}
