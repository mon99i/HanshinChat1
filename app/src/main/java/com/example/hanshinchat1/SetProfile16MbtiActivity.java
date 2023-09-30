package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SetProfile16MbtiActivity extends MainActivity {

    private String selectedFirst = "", selectedSecond = "", selectedThird = "", selectedForth = "";

    private RadioButton btnE, btnI, btnN, btnS, btnF, btnT, btnP, btnJ;

    private Button nextBtn;
    private LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_16_mbti);


        nextBtn = findViewById(R.id.set_mbti_next);

        btnE = findViewById(R.id.mbti_e);
        btnI = findViewById(R.id.mbti_i);
        btnN = findViewById(R.id.mbti_n);
        btnS = findViewById(R.id.mbti_s);
        btnF = findViewById(R.id.mbti_f);
        btnT = findViewById(R.id.mbti_t);
        btnP = findViewById(R.id.mbti_p);
        btnJ = findViewById(R.id.mbti_j);

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFirst = "E";
                btnE.setBackgroundResource(R.drawable.radio_button_checked);
                btnI.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });

        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFirst = "I";
                btnE.setBackgroundResource(R.drawable.radio_button_unchecked);
                btnI.setBackgroundResource(R.drawable.radio_button_checked);
            }
        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSecond = "N";
                btnN.setBackgroundResource(R.drawable.radio_button_checked);
                btnS.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSecond = "S";
                btnS.setBackgroundResource(R.drawable.radio_button_checked);
                btnN.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedThird = "F";
                btnF.setBackgroundResource(R.drawable.radio_button_checked);
                btnT.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedThird = "T";
                btnT.setBackgroundResource(R.drawable.radio_button_checked);
                btnF.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedForth = "P";
                btnP.setBackgroundResource(R.drawable.radio_button_checked);
                btnJ.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });
        btnJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedForth = "J";
                btnJ.setBackgroundResource(R.drawable.radio_button_checked);
                btnP.setBackgroundResource(R.drawable.radio_button_unchecked);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedFirst.isEmpty() && !selectedSecond.isEmpty() && !selectedThird.isEmpty() && !selectedForth.isEmpty()) {
                    String mbtiValue = selectedFirst + selectedSecond + selectedThird + selectedForth;
                    String currentTime=localDateTime.format(dateTimeFormatter);
                    Map<String, Object> childUpdates= new HashMap<>();
                    childUpdates.put("/mbti/",mbtiValue);
                    childUpdates.put("/creationTime/",currentTime);      //계정생성 시간
                    childUpdates.put("/lastSignInTime/",currentTime);    //마지막 로그인시간

                    DatabaseReference userRef = myRef.child("users").child(user.getUid());
                    userRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getApplicationContext(), SetProfile17IdealTypeActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                    });
                   /* usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                UserInfo userInfo = snapshot.getValue(UserInfo.class);

                                String mbtiValue = selectedFirst + selectedSecond + selectedThird + selectedForth;

                                userInfo.setMbti(mbtiValue);
                                usersRef.setValue(userInfo);

                                Intent intent = new Intent(getApplicationContext(), SetProfile17IdealTypeActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            } else {
                                Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "프로필 저장 실패", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                } else {
                    Toast.makeText(getApplicationContext(), "MBTI를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile15PersonalityActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
