package com.example.hanshinchat1.board;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.databinding.BoardBinding;
import com.example.hanshinchat1.utils.FBAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BoardEditActivity extends MainActivity {

    private ImageView image;
    private ImageView editBtn;
    private EditText titleArea;
    private EditText contentArea;
    private String writerUid;
    private String key;
    private DatabaseReference myRef;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.board_edit);

        titleArea = findViewById(R.id.writeTitleArea);
        contentArea = findViewById(R.id.writeContentArea);
        image = findViewById(R.id.imageArea);
        editBtn = findViewById(R.id.editBtn);
        key = getIntent().getStringExtra("key");

        getBoardData(key);
        getImageData(key);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBoardData(key);
//                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void editBoardData(String key) {

        myRef.child(key)
                .setValue(
                        new ListViewItem(titleArea.getText().toString(),
                                contentArea.getText().toString(),
                                FBAuth.getTime(),
                                writerUid));
        finish();

    }
    private void getImageData(String key) {

        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(key + ".png");

        // ImageView in your Activity
//        ImageView imageViewFB = binding.imageArea;
//        if(binding.imageArea != null) {
        if(image != null) {
            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Glide.with(BoardEditActivity.this)
                                .load(task.getResult())
                                .into(image);
                    } else {

                    }
                }
            });
        }
    }

    private void getBoardData(String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("board");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ListViewItem dataModel = dataSnapshot.getValue(ListViewItem.class);

                if(dataModel != null) {
//                    EditText titleArea = findViewById(R.id.writeTitleArea);
//                    EditText contentArea = findViewById(R.id.writeContentArea);
//                    binding.timeArea.setText(dataModel != null ? dataModel.getTime() : "");

                    titleArea.setText(dataModel.getTitle());
                    contentArea.setText(dataModel.getContent());
                    writerUid = dataModel.getUid();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("BoardEditActivity", "loadPost:onCancelled", databaseError.toException());
            }

        };
        myRef.child(key).addValueEventListener(postListener);
    }
}
