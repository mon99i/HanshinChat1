package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetProfile6DepartmentActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_profile_6_department);

        Button nextBtn = findViewById(R.id.set_department_next);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetProfile7HeightActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}
