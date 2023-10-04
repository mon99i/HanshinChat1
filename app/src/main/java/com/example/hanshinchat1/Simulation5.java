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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation05);
        RadioButton question4_Answer1 = findViewById(R.id.question4_answer1);
        RadioButton question4_Answer2 = findViewById(R.id.question4_answer2);
        RadioButton question4_Answer3 = findViewById(R.id.question4_answer3);
        RadioButton question4_Answer4 = findViewById(R.id.question4_answer4);
        Button next5 = (Button) findViewById(R.id.next5);

        question4_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question4Score = 7;
            }
        });
        question4_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question4Score = 2;
            }
        });
        question4_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question4Score = 4;
            }
        });
        question4_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question4Score = 10;
            }
        });

        next5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.addToScore(question4Score);

                Intent intent = new Intent(getApplicationContext(), Simulation6.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
