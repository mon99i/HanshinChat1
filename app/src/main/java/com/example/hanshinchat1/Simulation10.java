package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Simulation10 extends AppCompatActivity {
    public int question9Score = 0;
    public int totalScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation10);
        RadioButton question9_Answer1 = findViewById(R.id.question9_answer1);
        RadioButton question9_Answer2 = findViewById(R.id.question9_answer2);
        RadioButton question9_Answer3 = findViewById(R.id.question9_answer3);
        RadioButton question9_Answer4 = findViewById(R.id.question9_answer4);
        Button next10 = (Button) findViewById(R.id.next10);
        Intent intent = getIntent();
        totalScore = intent.getIntExtra("totalScore", totalScore);
        question9_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question9Score = 7;
                totalScore += question9Score;
            }
        });
        question9_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question9Score = 10;
                totalScore += question9Score;
            }
        });
        question9_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question9Score = 2;
                totalScore += question9Score;
            }
        });
        question9_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question9Score = 4;
                totalScore += question9Score;
            }
        });

        next10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Simulation11.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }

        });
    }
}
