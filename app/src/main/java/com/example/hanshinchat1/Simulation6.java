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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation06);
        RadioButton question5_Answer1 = findViewById(R.id.question5_answer1);
        RadioButton question5_Answer2 = findViewById(R.id.question5_answer2);
        RadioButton question5_Answer3 = findViewById(R.id.question5_answer3);
        RadioButton question5_Answer4 = findViewById(R.id.question5_answer4);
        Button next6 = (Button) findViewById(R.id.next6);

        question5_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question5Score = 4;
            }
        });
        question5_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question5Score = 2;
            }
        });
        question5_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question5Score = 10;
            }
        });
        question5_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question5Score = 7;
            }
        });

        next6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.addToScore(question5Score);

                Intent intent = new Intent(getApplicationContext(), Simulation7.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
