package com.example.hanshinchat1.board;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.MainMenuActivity;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.utils.FBAuth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class BoardWriteActivity1 extends MainActivity {

    public ImageView writeUploadBtn;
    private ImageView image;
    private ImageView imageBtn;
    private ImageView backBtn;

    private boolean isImageUpload = false;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_write);

        backBtn = findViewById(R.id.backBtn);
        writeUploadBtn = findViewById(R.id.writeUploadBtn);
        EditText writeTitle = findViewById(R.id.writeTitleArea);
        EditText writeContent = findViewById(R.id.writeContentArea);
        image = findViewById(R.id.imageArea);
        imageBtn = findViewById(R.id.imageBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 4);
                startActivity(intent);
            }
        });
        writeUploadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = writeTitle.getText().toString().trim();
                String content = writeContent.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "제목과 내용을 입력하세요!", Toast.LENGTH_SHORT).show();

                } else {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("board");

                    String key = myRef.push().getKey();
                    myRef.child(key).setValue(new ListViewItem(writeTitle.getText().toString(),
                            writeContent.getText().toString(), FBAuth.getTime(), FBAuth.getUid()));

                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.putExtra("show_fragment", 4);
                    startActivity(intent);

                    if (isImageUpload == true) {
                        imageUpload(key);
                    }

                    finish();
                }
            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 100);
                isImageUpload = true;

            }
        });

    }

    private void imageUpload(String key) {
        StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef = storageRef.child(key + ".png");


        ImageView imageView = image;
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            image.setImageURI(data.getData());

        }
    }
}
