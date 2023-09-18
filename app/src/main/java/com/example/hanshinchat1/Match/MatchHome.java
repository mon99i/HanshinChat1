package com.example.hanshinchat1.Match;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.R;

public class MatchHome extends MainActivity {

    private Button mbtiRecommendBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match);

        mbtiRecommendBtn = (Button) findViewById(R.id.btn);
        mbtiRecommendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LookingForMBTI.class);
                startActivity(intent);
            }
        });

    }
}
