package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class profile extends AppCompatActivity {

    Button profileEdit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profileEdit = findViewById(R.id.profileEdit);

        profileEdit.setOnClickListener(v -> {
            Intent intent = new Intent(profile.this, profile_edit.class);
            startActivity(intent);
        });
    }
}
