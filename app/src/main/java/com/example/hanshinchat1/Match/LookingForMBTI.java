package com.example.hanshinchat1.Match;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.R;

public class LookingForMBTI extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookingfor_mbti);

        Intent intent = new Intent(getApplicationContext(), MBTIMatchActivity.class);
        startActivity(intent);
    }
}
