package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Simulation8 extends AppCompatActivity {
    public int question7Score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation08);
        RadioButton question7_Answer1 = findViewById(R.id.question7_answer1);
        RadioButton question7_Answer2 = findViewById(R.id.question7_answer2);
        RadioButton question7_Answer3 = findViewById(R.id.question7_answer3);
        RadioButton question7_Answer4 = findViewById(R.id.question7_answer4);
        Button next8 = (Button) findViewById(R.id.next8);

        question7_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question7Score = 4;
            }
        });
        question7_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question7Score = 10;
            }
        });
        question7_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question7Score = 2;
            }
        });
        question7_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question7Score = 7;
            }
        });

        next8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.addToScore(question7Score);

                Intent intent = new Intent(getApplicationContext(), Simulation9.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
