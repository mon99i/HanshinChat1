package com.example.hanshinchat1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends MainActivity {

    Button deleteBtn;
    Button logoutBtn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();

        deleteBtn=(Button)findViewById(R.id.deleteBtn);
        logoutBtn2=(Button)findViewById(R.id.logoutBtn);

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
