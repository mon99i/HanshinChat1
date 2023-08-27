package com.example.hanshinchat1.board;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.comment.commentLVAdapter;
import com.example.hanshinchat1.comment.commentModel;
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

import java.util.ArrayList;
import java.util.Collections;

public class BoardActivity extends MainActivity {


    private ArrayList<commentModel> commentDataList;
    private commentLVAdapter commentAdapter;
    private BoardBinding binding;
    private ListView commentLv;
    private String key;
    private DatabaseReference myRef;
    private DatabaseReference commentRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board);

        binding.boardSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
        key = getIntent().getStringExtra("key");
        getBoardData(key);
        getImageData(key);

        binding.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = binding.commentArea.getText().toString().trim();

                if (comment.isEmpty()) {
                } else {
                    insertComment(key);
                }
            }

        });

        commentDataList = new ArrayList<>();
        commentAdapter = new commentLVAdapter(commentDataList);
        commentLv = findViewById(R.id.commentLV);
        commentLv.setAdapter(commentAdapter);

        getCommentData(key);

//        clickMenu();
//        clickHome();
//        clickRoom();
//        clickChat();
//        clickBoard();
//        clickProfile();

    }

    private void getCommentData(String key){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        commentRef = database.getReference("comment");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                commentDataList.clear();
                for (DataSnapshot dataModel : dataSnapshot.getChildren()) {
                    commentModel item = dataModel.getValue(commentModel.class);
                    commentDataList.add(item);

                }
                commentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ListActivity", "loadPost:onCancelled", databaseError.toException());
            }

        };
        commentRef.child(key).addValueEventListener(postListener);
    }

    private void insertComment(String key) {
        commentRef.child(key).push().setValue(
                new commentModel(binding.commentArea.getText().toString(), FBAuth.getTime()));
        binding.commentArea.setText("");
    }

    private void showDialog() {
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.board_dialog, null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("게시글 수정/삭제");

        AlertDialog alertDialog = mBuilder.show();
        alertDialog.findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardEditActivity.class);
                intent.putExtra("key", key);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(key).removeValue();
                finish();

            }
        });
    }
    private void getImageData(String key) {

        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(key + ".png");

        // ImageView in your Activity
        ImageView imageViewFB = binding.imageArea;

        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Glide.with(BoardActivity.this)
                            .load(task.getResult())
                            .into(imageViewFB);
                } else {
//                    binding.imageArea.setVisibility(View.INVISIBLE);
                    imageViewFB.setVisibility(View.GONE);
                }
            }
        });
    }
    private void getBoardData(String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("board");

        ImageView boardSetting = binding.boardSetting;
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ListViewItem dataModel = dataSnapshot.getValue(ListViewItem.class);

                if(dataModel != null) {
                    binding.titleArea.setText(dataModel != null ? dataModel.getTitle() : "");
                    binding.contentArea.setText(dataModel != null ? dataModel.getContent() : "");
                    binding.timeArea.setText(dataModel != null ? dataModel.getTime() : "");

                    String myUid = FBAuth.getUid();
                    String writerUid = dataModel.getUid();

                    if(myUid.equals(writerUid)) {
                        binding.boardSetting.setVisibility(View.VISIBLE);
                    } else {

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ListActivity", "loadPost:onCancelled", databaseError.toException());
            }

        };
        myRef.child(key).addValueEventListener(postListener);
    }
}