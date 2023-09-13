package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEditActivity extends MainActivity {


    private EditText editAge, editStudentId, editDepartment, editHeight, editReligion,
            editAddress, editSmoking, editDrinking, editForm, editGrade, editMbti;
    private TextView name, gender, interest, personality;

    private Button editBtn, cancelBtn;
    private DatabaseReference databaseReference;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        clickMenu();
        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();
        checkMatchRequest();
        checkProfileExist();

        editAge = findViewById(R.id.edit_age);
        editStudentId = findViewById(R.id.edit_student_id);
        editDepartment = findViewById(R.id.edit_department);
        editHeight = findViewById(R.id.edit_height);
        editReligion = findViewById(R.id.edit_religion);
        editAddress = findViewById(R.id.edit_address);
        editSmoking = findViewById(R.id.edit_smoking);
        editDrinking = findViewById(R.id.edit_drinking);
        editForm = findViewById(R.id.edit_form);
        editGrade = findViewById(R.id.edit_grade);
        editMbti = findViewById(R.id.edit_mbti);

        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        interest = findViewById(R.id.interest);
        personality = findViewById(R.id.personality);

        editBtn = findViewById(R.id.edit_profile_edit);
        cancelBtn = findViewById(R.id.edit_profile_cancel);

        databaseReference = FirebaseDatabase.getInstance().getReference("userInfo");

        myRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userInfo = dataSnapshot.getValue(UserInfo.class);
                    if (userInfo != null) {
                        editAge.setText(String.valueOf(userInfo.getAge()));
                        editStudentId.setText(String.valueOf(userInfo.getStudentId()));
                        editDepartment.setText(userInfo.getDepartment());
                        editHeight.setText(String.valueOf(userInfo.getHeight()));
                        editReligion.setText(userInfo.getReligion());
                        editAddress.setText(userInfo.getAddress());
                        editSmoking.setText(userInfo.getSmoking());
                        editDrinking.setText(userInfo.getDrinking());
                        editForm.setText(userInfo.getForm());
                        editGrade.setText(String.valueOf(userInfo.getGrade()));
                        editMbti.setText(userInfo.getMbti());

                        name.setText(userInfo.getName());
                        gender.setText(userInfo.getGender());
                        interest.setText(userInfo.getInterest());
                        personality.setText(userInfo.getPersonality());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer newAge = Integer.valueOf(editAge.getText().toString());
                Integer newStudentId = Integer.valueOf(editStudentId.getText().toString());
                String newDepartment = editDepartment.getText().toString();
                Integer newHeight = Integer.valueOf(editHeight.getText().toString());
                String newReligion = editReligion.getText().toString();
                String newAddress = editAddress.getText().toString();
                String newSmoking = editSmoking.getText().toString();
                String newDrinking = editDrinking.getText().toString();
                String newForm = editForm.getText().toString();
                Integer newGrade = Integer.valueOf(editGrade.getText().toString());
                String newMbti = editMbti.getText().toString();

                UserInfo updatedUserInfo = new UserInfo(
                        userInfo.getName(),
                        userInfo.getGender(),
                        newAge,
                        newStudentId,
                        newDepartment,
                        newHeight,
                        newReligion,
                        newAddress,
                        newSmoking,
                        newDrinking,
                        userInfo.getInterest(),
                        userInfo.getPersonality(),
                        newForm,
                        newGrade,
                        newMbti,
                        userInfo.getIdealTypeFirst(),
                        userInfo.getIdealTypeSecond(),
                        userInfo.getPhotoUrl()
                );

                databaseReference.setValue(updatedUserInfo);

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo != null) {
                    editAge.setText(String.valueOf(userInfo.getAge()));
                    editStudentId.setText(String.valueOf(userInfo.getStudentId()));
                    editDepartment.setText(userInfo.getDepartment());
                    editHeight.setText(String.valueOf(userInfo.getHeight()));
                    editReligion.setText(userInfo.getReligion());
                    editAddress.setText(userInfo.getAddress());
                    editSmoking.setText(userInfo.getSmoking());
                    editDrinking.setText(userInfo.getDrinking());
                    editForm.setText(userInfo.getForm());
                    editGrade.setText(String.valueOf(userInfo.getGrade()));
                    editMbti.setText(userInfo.getMbti());
                }
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
