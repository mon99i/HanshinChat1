package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetProfile15CompleteActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_profile_15_complete);

        Button nextBtn = findViewById(R.id.set_complete_next);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
