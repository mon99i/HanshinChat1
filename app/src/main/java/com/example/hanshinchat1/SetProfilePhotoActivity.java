package com.example.hanshinchat1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SetProfilePhotoActivity extends MainActivity {
    private static final String TAG = "SetProfileActivity";
    private static final int REQUEST_PERMISSION = 50;
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    Button saveBtn;
    ImageView image;
    private Bitmap imageBitmap;
    private ImageButton addProfileBtn;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_photo);
        if (checkPermission() == false) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.CAMERA}, REQUEST_PERMISSION);
        }
        addProfileBtn = findViewById(R.id.addProfileBtn);
        saveBtn = findViewById(R.id.set_photo_next);
        image = findViewById(R.id.image);

        addProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProfileDialog();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = image.getDrawable();
                if (drawable instanceof VectorDrawable) {   //기본 이미지일때
                    Toast.makeText(getApplicationContext(), "사진을 등록하세요", Toast.LENGTH_SHORT).show();
                } else uploadFileToStorageAndDatabase();
            }
        });
    }

    private void showAddProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.add_profile_dialog, null);
        ConstraintLayout camerLayout = view.findViewById(R.id.cameraLayout);
        ConstraintLayout galleryLayout = view.findViewById(R.id.galleryLayout);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();

        camerLayout.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    image.setImageBitmap(imageBitmap);
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
                    image.setImageBitmap(imageBitmap);
                }
                break;
        }
    }

    private void uploadFileToStorageAndDatabase() {

        UserInfo userInfo = new UserInfo();
        StorageReference profileRef = storageRef.child("profile.jpg/" + user.getUid());
        DatabaseReference usersRef = myRef.child("users").child(user.getUid());

        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
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
                        usersRef.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "사진 등록 성공!", Toast.LENGTH_SHORT).show();
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
            if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else
                return true;
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

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
