package com.example.hanshinchat1.simulation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.MainMenuActivity;
import com.example.hanshinchat1.R;

public class Simulation0 extends MainActivity {
    private Button nextBtn, guideBtn;
    private ImageButton backBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation0);
        nextBtn = (Button) findViewById(R.id.next_btn);
        guideBtn = (Button) findViewById(R.id.guide);
        backBtn = (ImageButton) findViewById(R.id.back_btn);
        homeBtn = (ImageButton) findViewById(R.id.home_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Simulation1.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimulationGuideViewerDialog imageViewerDialog = new SimulationGuideViewerDialog(Simulation0.this);
                imageViewerDialog.show();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 1);
                startActivity(intent);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 1);
                startActivity(intent);
            }
        });
    }
}
