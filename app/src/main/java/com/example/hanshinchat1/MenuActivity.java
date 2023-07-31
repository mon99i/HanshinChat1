package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends MainActivity {

    Button deleteBtn;
    Button logoutBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        checkCurrentUser();
        checkProfileExist();

        clickBoard();
        clickChat();
        clickProfile();
        clickRoom();


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

        ImageButton homeBtn = findViewById(R.id.home);
        ImageButton roomBtn = findViewById(R.id.room);
        ImageButton chatBtn = findViewById(R.id.chat);
        ImageButton postBtn = findViewById(R.id.post);
        ImageButton infoBtn = findViewById(R.id.info);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(chatIntent);
                finish();
            }
        });
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(postIntent);
                finish();
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(infoIntent);
                finish();
            }
        });


    }
}
