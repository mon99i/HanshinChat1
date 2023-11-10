package com.example.hanshinchat1.report;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.SettingActivity;
import com.example.hanshinchat1.utils.FBAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportActivity extends MainActivity {
    private static final String TAG="ReportActivity";
    private EditText nameArea;
    private EditText usernameArea;
    private RadioGroup rg_report;
    private RadioButton rb_1, rb_2, rb_3;
    private EditText writeReportArea;
    private Button writeBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        nameArea = findViewById(R.id.name);
        usernameArea = findViewById(R.id.username);
        rg_report = findViewById(R.id.rg_report);
        rb_1 = findViewById(R.id.rb_1);
        rb_2 = findViewById(R.id.rb_2);
        rb_3 = findViewById(R.id.rb_3);
        writeReportArea = findViewById(R.id.writeReport);
        writeBtn = findViewById(R.id.writeBtn);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameArea.getText().toString().trim();
                String username = usernameArea.getText().toString().trim();
                String writeReport = writeReportArea.getText().toString().trim();

                if (name.isEmpty() || username.isEmpty() || writeReport.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "모든 항목을 입력하세요!", Toast.LENGTH_SHORT).show();

                } else {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("report");

                    String key = myRef.push().getKey();
                    myRef.child(key).setValue(new reportInfo(nameArea.getText().toString(), usernameArea.getText().toString(),
                            writeReportArea.getText().toString(), FBAuth.getTime(), FBAuth.getUid()));

                    findWarningUser(key, username);

                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        });

    }

    private void findWarningUser(String key, String usernameArea) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

            // Firebase 데이터베이스에서 사용자를 찾음
            usersRef.orderByChild("name").equalTo(usernameArea).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // 사용자를 찾았을 때
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String userId = userSnapshot.getKey();
                            giveWarning(userId); // 경고를 부여
                            break; // 한 번 경고를 부여하면 종료
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }

    private void giveWarning(String userUid) {
        Log.d(TAG, "giveWarning: ");
        DatabaseReference userWarningsRef = FirebaseDatabase.getInstance().getReference().child("warnings").child(userUid);
        userWarningsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    warning userWarning = snapshot.getValue(warning.class);
                    int currentWarnings = userWarning.getWarningCount();
                    userWarning.setWarningCount(++currentWarnings);
                    userWarningsRef.setValue(userWarning);


                } else {
                    warning newWarning = new warning(userUid, 1);
                    userWarningsRef.setValue(newWarning);

                }
                Toast.makeText(getApplicationContext(), "신고 성공", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // 에러 처리
            }
        });
    }
}
