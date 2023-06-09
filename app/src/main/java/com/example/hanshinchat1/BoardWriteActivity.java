package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BoardWriteActivity extends AppCompatActivity {

    public Button writeUploadBtn;
    public EditText writeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_write);

        writeUploadBtn = (Button) findViewById(R.id.writeUploadBtn);
        writeUploadBtn.setOnClickListener(view -> {

            writeText = (EditText) findViewById(R.id.writeTextArea);
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("board");

            myRef.push().setValue(new ListViewItem(writeText.getText().toString()));
            startActivity(new Intent(view.getContext(), ListActivity.class));

        });

    }
}
