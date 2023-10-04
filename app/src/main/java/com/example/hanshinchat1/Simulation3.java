package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Simulation3 extends AppCompatActivity {
    public int question2Score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation03);

        RadioButton question2_Answer1 = findViewById(R.id.question2_answer1);
        RadioButton question2_Answer2 = findViewById(R.id.question2_answer2);
        RadioButton question2_Answer3 = findViewById(R.id.question2_answer3);
        RadioButton question2_Answer4 = findViewById(R.id.question2_answer4);

        Button next3 = findViewById(R.id.next3);

        question2_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question2Score = 4;
            }
        });

        question2_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question2Score = 10;
            }
        });

        question2_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question2Score = 7;
            }
        });

        question2_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question2Score = 2;
            }
        });

        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.addToScore(question2Score);

                Intent intent = new Intent(getApplicationContext(), Simulation4.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
