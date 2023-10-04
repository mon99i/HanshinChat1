package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Simulation9 extends AppCompatActivity {
    public int question8Score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation09);
        RadioButton question8_Answer1 = findViewById(R.id.question8_answer1);
        RadioButton question8_Answer2 = findViewById(R.id.question8_answer2);
        RadioButton question8_Answer3 = findViewById(R.id.question8_answer3);
        RadioButton question8_Answer4 = findViewById(R.id.question8_answer4);
        Button next9 = (Button) findViewById(R.id.next9);

        question8_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question8Score = 4;
            }
        });
        question8_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question8Score = 7;
            }
        });
        question8_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question8Score = 10;
            }
        });
        question8_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question8Score = 2;
            }
        });

        next9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationScoreManager.addToScore(question8Score);

                Intent intent = new Intent(getApplicationContext(), Simulation10.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
