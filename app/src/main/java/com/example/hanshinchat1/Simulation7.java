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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation07);
        RadioButton question6_Answer1 = findViewById(R.id.question6_answer1);
        RadioButton question6_Answer2 = findViewById(R.id.question6_answer2);
        RadioButton question6_Answer3 = findViewById(R.id.question6_answer3);
        RadioButton question6_Answer4 = findViewById(R.id.question6_answer4);
        Button next7 = (Button) findViewById(R.id.next7);

        question6_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question6Score = 4;
            }
        });
        question6_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question6Score = 2;
            }
        });
        question6_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question6Score = 7;
            }
        });
        question6_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question6Score = 10;
            }
        });

        next7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.addToScore(question6Score);

                Intent intent = new Intent(getApplicationContext(), Simulation8.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
