package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SetProfileCompleteActivity extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseUser user;
    private LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private Button now, later;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_complete);

        now = findViewById(R.id.set_now);
        later = findViewById(R.id.set_later);
        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = myRef.child("users").child(user.getUid());
                String currentTime = localDateTime.format(dateTimeFormatter);
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/creationTime/", currentTime);      //계정생성 시간
                childUpdates.put("/lastSignInTime/", currentTime);    //마지막 로그인시간
                userRef.updateChildren(childUpdates);

                Intent intent = new Intent(getApplicationContext(), SetIdealActivity.class);
                startActivity(intent);
                finish();
            }
        });

        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = myRef.child("users").child(user.getUid());
                String currentTime = localDateTime.format(dateTimeFormatter);
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/creationTime/", currentTime);      //계정생성 시간
                childUpdates.put("/lastSignInTime/", currentTime);    //마지막 로그인시간
                userRef.updateChildren(childUpdates);

                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
