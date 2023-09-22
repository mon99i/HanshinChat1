package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SetProfile7DepartmentActivity extends MainActivity {

    private String selectedDepartment;
    private Button nextBtn;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_profile_7_department);
        UserInfo userInfo=(UserInfo) getIntent().getSerializableExtra("UserInfo");
        nextBtn = findViewById(R.id.set_department_next);
        spinner = findViewById(R.id.department_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.학과, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDepartment = parentView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때 처리
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = myRef.child("users").child(user.getUid());
                selectedDepartment = spinner.getSelectedItem().toString();
                if (!selectedDepartment.isEmpty()) {
                    try {
                        userInfo.setDepartment(selectedDepartment);
                        userInfo.setUid(user.getUid());
                        usersRef.setValue(userInfo);

                        Intent intent = new Intent(getApplicationContext(), SetProfile8HeightActivity.class);
                        intent.putExtra("UserInfo",userInfo);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "올바른 학과를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "학과를 선택해주세요", Toast.LENGTH_SHORT).show();
                }


                /*usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);
                            selectedDepartment = spinner.getSelectedItem().toString();
                            if (!selectedDepartment.isEmpty()) {
                                try {
                                    userInfo.setDepartment(selectedDepartment);
                                    userInfo.setUid(user.getUid());
                                    usersRef.setValue(userInfo);

                                    Intent intent = new Intent(getApplicationContext(), SetProfile8HeightActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "올바른 학과를 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "학과를 선택해주세요", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(getApplicationContext(), SetProfile6StudentIdActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
