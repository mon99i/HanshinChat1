package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SetProfile6StudentIdActivity extends MainActivity {

    private EditText studentId;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_profile_6_studentid);

        nextBtn = findViewById(R.id.set_studentid_next);
        studentId = (EditText) findViewById(R.id.student_id);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = myRef.child("users").child(user.getUid());
                String strStudentId = studentId.getText().toString();
                if (!strStudentId.isEmpty()) {
                    if (strStudentId.length() == 9) {
                        try {
                            Integer intStudentId = Integer.valueOf(strStudentId);
                            userRef.child("studentId").setValue(intStudentId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(getApplicationContext(), SetProfile7DepartmentActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                }
                            });
                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "올바른 학번을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "학번은 9자리여야 합니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "학번을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

               /* usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);

                            String strStudentId = studentId.getText().toString();
                            if (!strStudentId.isEmpty()) {
                                if (strStudentId.length() == 9) {
                                    try {
                                        Integer intStudentId = Integer.valueOf(strStudentId);
                                        userInfo.setStudentId(intStudentId);
                                        usersRef.setValue(userInfo);
                                        Intent intent = new Intent(getApplicationContext(), SetProfile7DepartmentActivity.class);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                    } catch (NumberFormatException e) {
                                        Toast.makeText(getApplicationContext(), "올바른 학번을 입력해주세요", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "학번은 9자리여야 합니다", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "학번을 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
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
            }
        });


    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile5GradeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}