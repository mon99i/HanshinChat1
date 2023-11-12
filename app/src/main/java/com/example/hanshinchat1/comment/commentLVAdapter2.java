package com.example.hanshinchat1.comment;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class commentLVAdapter2 extends BaseAdapter {

    private Context context;
    private TextView commentTitle;
    private TextView commentCreatedTime;
    private ImageView commentDelete;
    private ArrayList<commentModel> commentList;
//    private ArrayList<String> commentKeyList;

    public commentLVAdapter2(Context context, ArrayList<commentModel> commentList) {
        this.context = context;
        this.commentList = commentList != null ? commentList : new ArrayList<>();
//        this.commentKeyList = commentKeyList != null ? commentKeyList : new ArrayList<>();

    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_list_item, parent, false);
        }


        commentTitle = (TextView) convertView.findViewById(R.id.titleArea);
        commentCreatedTime = (TextView) convertView.findViewById(R.id.timeArea);


//        commentDelete = (ImageView) convertView.findViewById(R.id.commentDelete);
        //        String commentKey = commentKeyList.get(position);
        commentModel item = commentList.get(position);


        commentTitle.setText(item.getCommentTitle());
        commentCreatedTime.setText(item.getCommentCreatedtime());

//        if (item.getCommentUserUid().equals(FBAuth.getUid())) {
//            commentDelete.setVisibility(View.VISIBLE);
//            commentDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showCommentDialog(commentKey);
//                }
//            });
//        } else {
//            commentDelete.setVisibility(View.GONE);
//        }
        return convertView;
    }

    public void showCommentDialog(String commentKey) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference commentRef = database.getReference("comment");

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comment_delete_dialog, null);
        ImageView cancelBtn = view.findViewById(R.id.dialog_cancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view);
        AlertDialog alertDialog = builder.show();

        Button deleteRoomBtn = alertDialog.findViewById(R.id.deleteCommentBtn);
        deleteRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentRef.child(commentKey).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if (error == null) {
                            alertDialog.dismiss();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}
