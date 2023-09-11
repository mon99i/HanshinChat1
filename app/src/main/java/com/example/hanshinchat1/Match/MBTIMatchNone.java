package com.example.hanshinchat1.Match;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.RoomActivity;

public class MBTIMatchNone extends MainActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mbtimatchnone);

        TextView roomBtn = findViewById(R.id.gotoRoomBtn);

        roomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                startActivity(intent);
            }
        });
    }
}
