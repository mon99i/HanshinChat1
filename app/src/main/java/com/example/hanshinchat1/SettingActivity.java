package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hanshinchat1.board.ListActivity;
import com.example.hanshinchat1.report.ReportActivity;

public class SettingActivity extends MainActivity {

    ImageView backBtn;
    Button reportBtn;
    Button deleteBtn;
    Button logoutBtn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);


        backBtn = findViewById(R.id.edit_profile_back_btn);
        reportBtn = findViewById(R.id.reportBtn);
        deleteBtn=(Button)findViewById(R.id.deleteBtn);
        logoutBtn2=(Button)findViewById(R.id.logoutBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 5);
                startActivity(intent);
            }
        });
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();

            }
        });

        logoutBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });

    }
}
