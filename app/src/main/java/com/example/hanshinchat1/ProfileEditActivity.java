package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileEditActivity extends MainActivity {

    private TextView editName, editGender, editInterest, editPersonality, editAge, editStudentId, editDepartment,
            editHeight, editReligion, editAddress, editSmoking, editDrinking, editForm, editGrade, editMbti;
    private ImageView editImage;

    private ArrayList<String> interestList;
    private ArrayList<String> personalityList;
    private Button editBtn, cancelBtn;
    private DatabaseReference databaseReference;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();

        editImage = (ImageView) findViewById(R.id.edit_image);
        editName = findViewById(R.id.edit_name);
        editGender = findViewById(R.id.edit_gender);
        editAge = findViewById(R.id.edit_age);
        editGrade = findViewById(R.id.edit_grade);
        editStudentId = findViewById(R.id.edit_student_id);
        editDepartment = findViewById(R.id.edit_department);
        editHeight = findViewById(R.id.edit_height);
        editForm = findViewById(R.id.edit_form);
        editAddress = findViewById(R.id.edit_address);
        editReligion = findViewById(R.id.edit_religion);
        editSmoking = findViewById(R.id.edit_smoking);
        editDrinking = findViewById(R.id.edit_drinking);
        editInterest = findViewById(R.id.interest);
        editPersonality = findViewById(R.id.personality);
        editMbti = findViewById(R.id.edit_mbti);

        editBtn = findViewById(R.id.edit_profile_edit);
        cancelBtn = findViewById(R.id.edit_profile_cancel);

        databaseReference = FirebaseDatabase.getInstance().getReference("userInfo");

        editName.setOnClickListener(v -> showEditTextDialog("이름을 입력해주세요", editName));
        editAge.setOnClickListener(v -> showEditTextDialog("나이를 입력해주세요", editAge));
        editGrade.setOnClickListener(v -> showEditTextDialog("학년을 입력해주세요", editGrade));
        editStudentId.setOnClickListener(v -> showEditTextDialog("학번을 입력해주세요", editStudentId));

        myRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userInfo = dataSnapshot.getValue(UserInfo.class);
                    if (userInfo != null) {
                        String imageUrl=userInfo.getPhotoUrl();
                        Uri imageUri=Uri.parse(imageUrl);
                        Glide.with(getApplicationContext())
                                .load(imageUri)
                                .into(editImage);

                        editName.setText(userInfo.getName());
                        editGender.setText(userInfo.getGender());
                        editAge.setText(String.valueOf(userInfo.getAge()));
                        editGrade.setText(String.valueOf(userInfo.getGrade()));
                        editStudentId.setText(String.valueOf(userInfo.getStudentId()));
                        editDepartment.setText(userInfo.getDepartment());
                        editHeight.setText(String.valueOf(userInfo.getHeight()));
                        editForm.setText(userInfo.getForm());
                        editAddress.setText(userInfo.getAddress());
                        editReligion.setText(userInfo.getReligion());
                        editSmoking.setText(userInfo.getSmoking());
                        editDrinking.setText(userInfo.getDrinking());
                        editMbti.setText(userInfo.getMbti());
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
                String newName = editName.getText().toString();
                String newGender = editGender.getText().toString();
                Integer newAge = Integer.valueOf(editAge.getText().toString());
                Integer newGrade = Integer.valueOf(editGrade.getText().toString());
                Integer newStudentId = Integer.valueOf(editStudentId.getText().toString());
                String newDepartment = editDepartment.getText().toString();
                Integer newHeight = Integer.valueOf(editHeight.getText().toString());
                String newForm = editForm.getText().toString();
                String newAddress = editAddress.getText().toString();
                String newReligion = editReligion.getText().toString();
                String newSmoking = editSmoking.getText().toString();
                String newDrinking = editDrinking.getText().toString();
                String newMbti = editMbti.getText().toString();

                UserInfo updatedUserInfo = new UserInfo(
                        userInfo.getUid(),
                        userInfo.getPhotoUrl(),
                        newName,
                        newGender,
                        newAge,
                        newGrade,
                        newStudentId,
                        newDepartment,
                        newHeight,
                        newForm,
                        newAddress,
                        newReligion,
                        newSmoking,
                        newDrinking,
                        userInfo.getInterest(),
                        userInfo.getPersonality(),
                        newMbti,
                        userInfo.getIdealTypeFirst(),
                        userInfo.getIdealTypeSecond()
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
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 이름 나이 학년 학과
    private void showEditTextDialog(String title, final TextView textView) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        View EditTextLayout = getLayoutInflater().inflate(R.layout.edit_profile_edit_text_dialog, null);

        Button btnInput = EditTextLayout.findViewById(R.id.input_btn);
        Button btnCancel = EditTextLayout.findViewById(R.id.cancel_btn);

        EditText editText = EditTextLayout.findViewById(R.id.edit_text);

        String currentValue = textView.getText().toString();
        editText.setText(currentValue);

        alertDialog.setView(EditTextLayout);
        alertDialog.setTitle(title);

        AlertDialog dialog = alertDialog.create();

        btnInput.setOnClickListener(view -> {
            String newValue = editText.getText().toString();
            textView.setText(newValue);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(view -> {
            dialog.cancel();
        });

        dialog.show();
    }

    private void showEditGenderDialog(String title, final TextView textView){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

    }

}