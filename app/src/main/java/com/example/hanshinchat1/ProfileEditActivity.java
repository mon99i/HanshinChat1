package com.example.hanshinchat1;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.MainMenu.MainProfileFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileAddressFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileAgeFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileDepartmentFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileDrinkingFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileFashionFemaleFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileFashionMaleFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileFormFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileGenderFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileGradeFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileHeightFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileIdealTypeFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileInterestFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileMbtiFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileNameFragment;
import com.example.hanshinchat1.ProfileFragment.ProfilePersonalityFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileReligionFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileSmokingFragment;
import com.example.hanshinchat1.ProfileFragment.ProfileStudentIdFragment;
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
import java.util.Arrays;

public class ProfileEditActivity extends MainActivity {
    private static final String TAG = "ProfileEditActivity";
    private static final int REQUEST_PERMISSION = 50;
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private static final int REQUEST_CODE_EDIT_NAME = 1;
    private static final int REQUEST_CODE_EDIT_Gender = 2;
    private static final int REQUEST_CODE_EDIT_INTEREST = 3;
    private static final int REQUEST_CODE_EDIT_PERSONALITY = 4;
    private static final int REQUEST_CODE_EDIT_AGE = 5;
    private static final int REQUEST_CODE_EDIT_STUDENTID = 6;
    private static final int REQUEST_CODE_EDIT_DEPARTMENT = 7;
    private static final int REQUEST_CODE_EDIT_HEIGHT = 8;
    private static final int REQUEST_CODE_EDIT_RELIGION = 9;
    private static final int REQUEST_CODE_EDIT_ADDRESS = 10;
    private static final int REQUEST_CODE_EDIT_SMOKING = 11;
    private static final int REQUEST_CODE_EDIT_DRINKING = 12;
    private static final int REQUEST_CODE_EDIT_FORM = 13;
    private static final int REQUEST_CODE_EDIT_GRADE = 14;
    private static final int REQUEST_CODE_EDIT_MBTI = 15;
    private static final int REQUEST_CODE_EDIT_FASHION_MALE = 16;
    private static final int REQUEST_CODE_EDIT_FASHION_FEMALE = 17;

    private Bitmap imageBitmap;
    private TextView editName, editGender, editInterest, editPersonality, editAge, editStudentId, editDepartment,
            editHeight, editReligion, editAddress, editSmoking, editDrinking, editForm, editGrade, editMbti, editFashion;
    private ImageView editImage;
    private ImageButton backBtn;

    private ArrayList<String> interestList;
    private ArrayList<String> newInterestList;
    private ArrayList<String> personalityList;
    private ArrayList<String> newPersonalityList;
    private Button editCompleteBtn, cancelCompleteBtn;

    private StorageReference profileRef;
    private DatabaseReference userRef;
    private UserInfo userInfo;
    private Button profileEditBtn, profileCancelBtn;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ProfileNameFragment nameFragment;
    private ProfileGenderFragment genderFragment;
    private ProfileAgeFragment ageFragment;
    private ProfileGradeFragment gradeFragment;
    private ProfileStudentIdFragment studentIdFragment;
    private ProfileDepartmentFragment departmentFragment;
    private ProfileHeightFragment heightFragment;
    private ProfileFormFragment formFragment;
    private ProfileAddressFragment addressFragment;
    private ProfileReligionFragment religionFragment;
    private ProfileSmokingFragment smokingFragment;
    private ProfileDrinkingFragment drinkingFragment;
    private ProfileInterestFragment interestFragment;
    private ProfilePersonalityFragment personalityFragment;
    private ProfileMbtiFragment mbtiFragment;
    private ProfileIdealTypeFragment idealTypeFragment;
    private ProfileFashionMaleFragment fashionMaleFragment;
    private ProfileFashionFemaleFragment fashionFemaleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        initializeView();

        profileEditBtn = findViewById(R.id.edit_profile_edit);
        profileCancelBtn = findViewById(R.id.edit_profile_cancel);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child(user.getUid());

        if (checkPermission() == false) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.CAMERA}, REQUEST_PERMISSION);
        }

        editImage = findViewById(R.id.edit_image);
        editImage.setOnClickListener(v -> showEditImageDialog());
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_NAME);
            }
        });
        editGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_Gender);
            }
        });
        editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_AGE);
            }
        });
        editInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_INTEREST);
            }
        });
        editPersonality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_PERSONALITY);
            }
        });
        editStudentId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_STUDENTID);
            }
        });
        editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_AGE);
            }
        });
        editDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_DEPARTMENT);
            }
        });
        editHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_HEIGHT);
            }
        });
        editReligion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_RELIGION);
            }
        });
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_ADDRESS);
            }
        });
        editSmoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_SMOKING);
            }
        });
        editDrinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_DRINKING);
            }
        });
        editForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_FORM);
            }
        });
        editGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_GRADE);
            }
        });
        editMbti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditFragment(REQUEST_CODE_EDIT_MBTI);
            }
        });
        editFashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = userInfo.getGender();
                if (gender.equals("남자")) {
                    showEditFragment(REQUEST_CODE_EDIT_FASHION_MALE);
                } else if (gender.equals("여자")) {
                    showEditFragment(REQUEST_CODE_EDIT_FASHION_FEMALE);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 5);
                startActivity(intent);
            }
        });


        myRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userInfo = dataSnapshot.getValue(UserInfo.class);
                    if (userInfo != null) {
                        String imageUrl = userInfo.getPhotoUrl();
                        Uri imageUri = Uri.parse(imageUrl);
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
                        personalityList = userInfo.getPersonality();
                        String personalityString = TextUtils.join(", ", personalityList);
                        editPersonality.setText(personalityString);
                        interestList = userInfo.getInterest();
                        String interestString = TextUtils.join(", ", interestList);
                        editInterest.setText(interestString);
                        editFashion.setText(userInfo.getFashion());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                String newInterest = editInterest.getText().toString();
                ArrayList<String> newInterestList = new ArrayList<>(Arrays.asList(newInterest.split(", ")));
                String newPersonality = editPersonality.getText().toString();
                ArrayList<String> newPersonalityList = new ArrayList<>(Arrays.asList(newPersonality.split(", ")));
                String newFashion = editFashion.getText().toString();

                UserInfo updatedUserInfo = new UserInfo(
                        userInfo.getLike(),
                        userInfo.getUid(),
                        newName,
                        newGender,
                        newAge,
                        newStudentId,
                        newDepartment,
                        newHeight,
                        newReligion,
                        newAddress,
                        newSmoking,
                        newDrinking,
                        newInterestList,
                        newPersonalityList,
                        newForm,
                        newGrade,
                        newMbti,
                        userInfo.getIdealTypeFirst(),
                        userInfo.getIdealTypeSecond(),
                        userInfo.getPhotoUrl(),
                        userInfo.getCreationTime(),
                        userInfo.getLastSignInTime(),
                        newFashion
                );

                userRef.setValue(updatedUserInfo);

                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 5);
                startActivity(intent);
            }
        });

        cancelCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 5);
                startActivity(intent);
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

            case REQUEST_CODE_EDIT_NAME:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedName = data.getStringExtra("editedName");
                        if (editedName != null) {
                            editName.setText(editedName);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_Gender:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedGender = data.getStringExtra("editedGender");
                        if (editedGender != null) {
                            editGender.setText(editedGender);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_INTEREST:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedInterest = data.getStringExtra("editedInterest");
                        if (editedInterest != null) {
                            editInterest.setText(editedInterest);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_PERSONALITY:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedPersonality = data.getStringExtra("editedPersonality");
                        if (editedPersonality != null) {
                            editPersonality.setText(editedPersonality);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_AGE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedAge = data.getStringExtra("editedAge");
                        if (editedAge != null) {
                            editAge.setText(editedAge);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_STUDENTID:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedStudentId = data.getStringExtra("editedStudentId");
                        if (editedStudentId != null) {
                            editStudentId.setText(editedStudentId);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_DEPARTMENT:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedDepartment = data.getStringExtra("editedDepartment");
                        if (editedDepartment != null) {
                            editDepartment.setText(editedDepartment);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_HEIGHT:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedHeight = data.getStringExtra("editedHeight");
                        if (editedHeight != null) {
                            editHeight.setText(editedHeight);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_RELIGION:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedReligion = data.getStringExtra("editedReligion");
                        if (editedReligion != null) {
                            editReligion.setText(editedReligion);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_ADDRESS:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedAddress = data.getStringExtra("editedAddress");
                        if (editedAddress != null) {
                            editAddress.setText(editedAddress);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_SMOKING:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedSmoking = data.getStringExtra("editedSmoking");
                        if (editedSmoking != null) {
                            editSmoking.setText(editedSmoking);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_DRINKING:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedDrinking = data.getStringExtra("editedDrinking");
                        if (editedDrinking != null) {
                            editDrinking.setText(editedDrinking);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_FORM:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedForm = data.getStringExtra("editedForm");
                        if (editedForm != null) {
                            editForm.setText(editedForm);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_GRADE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedGrade = data.getStringExtra("editedGrade");
                        if (editedGrade != null) {
                            editGrade.setText(editedGrade);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_MBTI:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedMbti = data.getStringExtra("editedMbti");
                        if (editedMbti != null) {
                            editMbti.setText(editedMbti);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_FASHION_MALE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedFashion = data.getStringExtra("editedFashion");
                        if (editedFashion != null) {
                            editFashion.setText(editedFashion);
                        }
                    }
                }
            case REQUEST_CODE_EDIT_FASHION_FEMALE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String editedFashion = data.getStringExtra("editedFashion");
                        if (editedFashion != null) {
                            editFashion.setText(editedFashion);
                        }
                    }
                }
        }
    }

    private void showEditFragment(int requestCode) {
        Intent intent = new Intent(this, ProfileEditActivity2.class);
        intent.putExtra("requestCode", requestCode);
        startActivityForResult(intent, requestCode);
    }
    private void showEditImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.add_profile_dialog, null);
        ConstraintLayout cameraLayout = view.findViewById(R.id.cameraLayout);
        ConstraintLayout galleryLayout = view.findViewById(R.id.galleryLayout);

        builder.setView(view);
        AlertDialog dialog = builder.create();
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

    private void uploadFileToStorageAndDatabase() {
        StorageReference profileRef = storageRef.child("profile.jpg/" + user.getUid());
        DatabaseReference usersRef = myRef.child("users").child(user.getUid());

        editImage.setDrawingCacheEnabled(true);
        editImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) editImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileRef.putBytes(data);
        //스토리지에 사진 업로드
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    UserInfo userInfo = snapshot.getValue(UserInfo.class);
                                    userInfo.setPhotoUrl(imageUrl);
                                    usersRef.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // 에러 처리
                                Log.e(TAG, "onDataChange: Error: " + error.getMessage());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "스토리지에서 이미지 다운로드 실패");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "스토리지에 이미지 업로드 실패");
            }
        });
    }


    public boolean checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
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
                // 권한이 취소되면 result 배열은 비어있다.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "권한 확인", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }
    private void initializeView() {
        editImage = (ImageView) findViewById(R.id.edit_image);
        editName = (TextView) findViewById(R.id.edit_name);
        editGender = (TextView) findViewById(R.id.edit_gender);
        editAge = (TextView)findViewById(R.id.edit_age);
        editGrade = (TextView)findViewById(R.id.edit_grade);
        editStudentId = (TextView)findViewById(R.id.edit_student_id);
        editDepartment = (TextView)findViewById(R.id.edit_department);
        editHeight = (TextView)findViewById(R.id.edit_height);
        editForm = (TextView)findViewById(R.id.edit_form);
        editAddress = (TextView)findViewById(R.id.edit_address);
        editReligion = (TextView)findViewById(R.id.edit_religion);
        editSmoking = (TextView)findViewById(R.id.edit_smoking);
        editDrinking = (TextView)findViewById(R.id.edit_drinking);
        editInterest = (TextView)findViewById(R.id.interest);
        editPersonality = (TextView)findViewById(R.id.personality);
        editMbti = (TextView)findViewById(R.id.edit_mbti);
        editFashion = (TextView)findViewById(R.id.edit_fashion);

        editCompleteBtn = findViewById(R.id.edit_profile_edit);
        cancelCompleteBtn = findViewById(R.id.edit_profile_cancel);
        backBtn = findViewById(R.id.edit_profile_back_btn);

        nameFragment = new ProfileNameFragment();
        genderFragment = new ProfileGenderFragment();
        ageFragment = new ProfileAgeFragment();
        gradeFragment = new ProfileGradeFragment();
        studentIdFragment = new ProfileStudentIdFragment();
        departmentFragment = new ProfileDepartmentFragment();
        heightFragment = new ProfileHeightFragment();
        formFragment = new ProfileFormFragment();
        addressFragment = new ProfileAddressFragment();
        religionFragment = new ProfileReligionFragment();
        smokingFragment = new ProfileSmokingFragment();
        drinkingFragment = new ProfileDrinkingFragment();
        interestFragment = new ProfileInterestFragment();
        personalityFragment = new ProfilePersonalityFragment();
        mbtiFragment = new ProfileMbtiFragment();
        idealTypeFragment = new ProfileIdealTypeFragment();
        fashionMaleFragment = new ProfileFashionMaleFragment();
        fashionFemaleFragment = new ProfileFashionFemaleFragment();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

    }
}
