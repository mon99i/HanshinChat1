package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProfileEditActivity extends MainActivity {

    private static final String TAG = "SetProfileActivity";
    private static final int REQUEST_PERMISSION = 50;
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private Bitmap imageBitmap;
    private TextView editName, editGender, editInterest, editPersonality, editAge, editStudentId, editDepartment,
            editHeight, editReligion, editAddress, editSmoking, editDrinking, editForm, editGrade, editMbti;
    private ImageView editImage;

    private ArrayList<String> interestList;
    private ArrayList<String> personalityList;
    private Button editBtn, cancelBtn;

    private StorageReference profileRef;
    private DatabaseReference userRef;
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

        profileRef = storageRef.child("profile.jpg/" + user.getUid());
        userRef = myRef.child("users").child(user.getUid());

        if (checkPermission() == false) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.CAMERA}, REQUEST_PERMISSION);
        }

        editImage.setOnClickListener(v -> showEditImageDialog());
     /*   editName.setOnClickListener(v -> showEditTextDialog("이름을 입력해주세요", editName));
        editAge.setOnClickListener(v -> showEditTextDialog("나이를 입력해주세요", editAge));
        editGender.setOnClickListener(v -> showEditGenderDialog("성별을 선택해주세요"));
        editGrade.setOnClickListener(v -> showEditTextDialog("학년을 입력해주세요", editGrade));
        editStudentId.setOnClickListener(v -> showEditTextDialog("학번을 입력해주세요", editStudentId));*/

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
                Drawable drawable = editImage.getDrawable();
                uploadFileToStorageAndDatabase();

                String newName = editName.getText().toString();
                String newGender = editGender.getText().toString();
                String strAge = editAge.getText().toString();
                Integer newAge = Integer.valueOf(strAge);
                String strGrade = editGrade.getText().toString();
                Integer newGrade = Integer.valueOf(strGrade);
                String strStudentId = editStudentId.getText().toString();
                Integer newStudentId = Integer.valueOf(strStudentId);
                String newDepartment = editDepartment.getText().toString();
                String strHeight = editHeight.getText().toString();
                Integer newHeight = Integer.valueOf(strHeight);
                String newForm = editForm.getText().toString();
                String newAddress = editAddress.getText().toString();
                String newReligion = editReligion.getText().toString();
                String newSmoking = editSmoking.getText().toString();
                String newDrinking = editDrinking.getText().toString();
                String newMbti = editMbti.getText().toString();

               /* UserInfo updatedUserInfo = new UserInfo(
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

                userRef.setValue(updatedUserInfo);
*/
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

// 성별
  /*  private void showEditGenderDialog(String title) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View EditLayout = getLayoutInflater().inflate(R.layout.edit_profile_gender, null);

        Button btnMale = eLayout.findViewById(R.id.male);
        Button btnFemale = EditLayout.findViewById(R.id.female);

        alertDialog.setView(EditLayout);
        alertDialog.setTitle(title);
        AlertDialog dialog = alertDialog.create();

        btnMale.setOnClickListener(view -> {
            editGender.setText("남자");
            dialog.dismiss();
        });
        btnFemale.setOnClickListener(view -> {
            editGender.setText("여자");
            dialog.dismiss();
        });
        dialog.show();
    }*/

    // 프로필 이미지
    private void showEditImageDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.add_profile_dialog, null);
        ConstraintLayout cameraLayout = view.findViewById(R.id.cameraLayout);
        ConstraintLayout galleryLayout = view.findViewById(R.id.galleryLayout);

        builder.setView(view);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();

        cameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission() == true) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "카메라 촬영을 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        galleryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_GALLERY);
                dialog.dismiss();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");

                    editImage.setImageBitmap(imageBitmap);
                }
                break;

            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();

                    InputStream inputStream = null;
                    try {
                        inputStream = getContentResolver().openInputStream(imageUri);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    imageBitmap = BitmapFactory.decodeStream(inputStream);
                    editImage.setImageBitmap(imageBitmap);
                }
                break;
        }
    }

    private void uploadFileToStorageAndDatabase() {

        editImage.setDrawingCacheEnabled(true);
        editImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) editImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = profileRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        userInfo.setPhotoUrl(imageUrl);
                        userInfo.setUid(user.getUid());
                        userRef.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "스토리지에서 사진 다운로드 실패");
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "스토리지에 사진 업로드 실패");
            }
        });
    }


    public boolean checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionCamera != PackageManager.PERMISSION_GRANTED
            ) {
                return false;
            } else return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 확인", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }


// 이름 나이 학년 학과
   /* private void showEditTextDialog(String title, final TextView textView) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        View EditTextLayout = getLayoutInflater().inflate(R.layout.edit_profile_edit_text, null);

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
    }*/
}