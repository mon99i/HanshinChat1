package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BoardWriteActivity extends MainActivity{

    public Button writeUploadBtn;
    public EditText writeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_write);

        clickMenu();
        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();

    }
}
