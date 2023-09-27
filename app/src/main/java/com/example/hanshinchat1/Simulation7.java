package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Simulation7 extends AppCompatActivity {
    public int question6Score = 0;
    public int totalScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation07);
        RadioButton question6_Answer1 = findViewById(R.id.question6_answer1);
        RadioButton question6_Answer2 = findViewById(R.id.question6_answer2);
        RadioButton question6_Answer3 = findViewById(R.id.question6_answer3);
        RadioButton question6_Answer4 = findViewById(R.id.question6_answer4);
        Button next7 = (Button) findViewById(R.id.next7);
        Intent intent = getIntent();
        totalScore = intent.getIntExtra("totalScore", totalScore);
        question6_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question6Score = 4;
                totalScore += question6Score;
            }
        });
        question6_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question6Score = 2;
                totalScore += question6Score;
            }
        });
        question6_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question6Score = 7;
                totalScore += question6Score;
            }
        });
        question6_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question6Score = 10;
                totalScore += question6Score;
            }
        });

        next7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Simulation7.this, Simulation8.class);
                intent.putExtra("totalScore", totalScore);
                startActivity(intent);
            }

        });
    }
}
