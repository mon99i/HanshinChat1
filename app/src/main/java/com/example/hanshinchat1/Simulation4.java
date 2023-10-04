package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Simulation4 extends AppCompatActivity {
    public int question3Score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation04);
        RadioButton question3_Answer1 = findViewById(R.id.question3_answer1);
        RadioButton question3_Answer2 = findViewById(R.id.question3_answer2);
        RadioButton question3_Answer3 = findViewById(R.id.question3_answer3);
        RadioButton question3_Answer4 = findViewById(R.id.question3_answer4);
        Button next4 = (Button) findViewById(R.id.next4);

        question3_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question3Score = 10;
            }
        });
        question3_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question3Score = 4;
            }
        });
        question3_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question3Score = 2;
            }
        });
        question3_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question3Score = 7;
            }
        });

        next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.addToScore(question3Score);

                Intent intent = new Intent(getApplicationContext(), Simulation5.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
