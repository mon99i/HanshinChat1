package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetProfile11DrinkingActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_profile_11_drinking);

        Button nextBtn = findViewById(R.id.set_drinking_next);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetProfile12InterestActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
