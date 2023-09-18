package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Simulation5 extends AppCompatActivity {
    public int question4Score = 0;
    public int totalScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation05);
        RadioButton question4_Answer1 = findViewById(R.id.question4_answer1);
        RadioButton question4_Answer2 = findViewById(R.id.question4_answer2);
        RadioButton question4_Answer3 = findViewById(R.id.question4_answer3);
        RadioButton question4_Answer4 = findViewById(R.id.question4_answer4);
        Button next5 = (Button) findViewById(R.id.next5);
        Intent intent = getIntent();
        totalScore = intent.getIntExtra("totalScore", totalScore);
        question4_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question4Score = 7;
                totalScore += question4Score;
            }
        });
        question4_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question4Score = 2;
                totalScore += question4Score;
            }
        });
        question4_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question4Score = 4;
                totalScore += question4Score;
            }
        });
        question4_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question4Score = 10;
                totalScore += question4Score;
            }
        });

        next5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Simulation5.this, "중간 Total Score: " + totalScore, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Simulation5.this, Simulation6.class);
                intent.putExtra("totalScore", totalScore);
                startActivity(intent);
            }

        });
    }
}
