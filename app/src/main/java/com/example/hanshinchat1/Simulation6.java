package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Simulation6 extends AppCompatActivity {
    public int question5Score = 0;
    public int totalScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation06);
        RadioButton question5_Answer1 = findViewById(R.id.question5_answer1);
        RadioButton question5_Answer2 = findViewById(R.id.question5_answer2);
        RadioButton question5_Answer3 = findViewById(R.id.question5_answer3);
        RadioButton question5_Answer4 = findViewById(R.id.question5_answer4);
        Button next6 = (Button) findViewById(R.id.next6);
        Intent intent = getIntent();
        totalScore = intent.getIntExtra("totalScore", totalScore);
        question5_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question5Score = 4;
                totalScore += question5Score;
            }
        });
        question5_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question5Score = 2;
                totalScore += question5Score;
            }
        });
        question5_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question5Score = 10;
                totalScore += question5Score;
            }
        });
        question5_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question5Score = 7;
                totalScore += question5Score;
            }
        });

        next6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Simulation6.this, "중간 Total Score: " + totalScore, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Simulation6.this, Simulation7.class);
                intent.putExtra("totalScore", totalScore);
                startActivity(intent);
            }

        });
    }
}
