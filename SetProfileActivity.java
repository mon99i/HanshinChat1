package com.example.chattingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class SetProfileActivity extends AppCompatActivity {

    //private static final String TAG = "ProfileActivity";
    private static final int CAMERA= 100;
    private static final int GALLERY = 101;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount gsa;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageRef;
    Button getPhotoBtn;
    Button captureBtn;

    Button saveBtn;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        // Create a storage reference from our app
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference();
        storage=FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        getPhotoBtn=(Button)findViewById(R.id.getPhotoBtn);
        captureBtn=(Button)findViewById(R.id.captureBtn);
        saveBtn=(Button)findViewById(R.id.saveBtn);
        image=(ImageView)findViewById(R.id.image);

        getPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uploadPhoto();
                openCamera();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef=myRef.child("users").child(user.getUid());
                UserInfo userInfo=new UserInfo();

                storageRef.child("profile.jpg/"+user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        String Url=uri.toString();
                        userInfo.setPhotoUrl(Url);


                        // [START rtdb_write_new_user_task]
                        usersRef.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getApplicationContext(), SetProfileActivity2.class);
                                startActivity(intent);
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Toast.makeText(getApplicationContext(), "사진등록하세요!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, GALLERY);

    }

    private void openCamera(){

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if(requestCode==CAMERA &&requestCode==RESULT_OK){

                // Bundle로 데이터를 입력
                Bundle extras = data.getExtras();

                // Bitmap으로 컨버전
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // 이미지뷰에 Bitmap으로 이미지를 입력
                image.setImageBitmap(imageBitmap);

                // Google Sign In failed, update UI appropriatel

        }*/
        switch(requestCode){
            case CAMERA:
                // Bundle로 데이터를 입력
                Bundle extras = data.getExtras();

                // Bitmap으로 컨버전
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // 이미지뷰에 Bitmap으로 이미지를 입력
                image.setImageBitmap(imageBitmap);
                uploadCammeraFile();

                break;

                // Google Sign In failed, update UI appropriatel
            case GALLERY:
                Uri imageUri = data.getData();

                // 이미지 뷰에 사진 설정하기
                image.setImageURI(imageUri);

                uploadGalleryFile(imageUri);
                // Firebase Storage에 사진 업로드하기
              //  uploadImageToFirebase(imageUri);


                break;

        }

    }

    private void uploadGalleryFile(Uri imageUri){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        StorageReference profileRef=storageRef.child("profile.jpg/"+user.getUid());


        UploadTask uploadTask = profileRef.putFile(imageUri);
        // 업로드 상태 모니터링 및 완료 리스너 추가
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // 업로드 성공 시, 이미지의 다운로드 URL 가져오기
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // 파이어베이스 스토리지에 업로드된 이미지의 다운로드 URL 사용하기
                        String imageUrl = downloadUri.toString();
                        Toast.makeText(getApplicationContext(), "사진 업로드 성공!", Toast.LENGTH_SHORT).show();
                        // 다운로드 URL을 사용하여 추가 작업 수행
                        // 예를 들면, 데이터베이스에 이미지 URL 저장하기 등
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // 업로드 실패 시, 실패 처리 코드 작성
                Toast.makeText(getApplicationContext(), "사진 업로드 실패!", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void uploadCammeraFile(){



        StorageReference profileRef=storageRef.child("profile.jpg/"+user.getUid());



        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(), "사진 업로드 실패!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "사진 업로드 성공!", Toast.LENGTH_SHORT).show();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }




}

