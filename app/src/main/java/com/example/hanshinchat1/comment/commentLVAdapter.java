package com.example.hanshinchat1.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hanshinchat1.R;

import java.util.ArrayList;

public class commentLVAdapter extends BaseAdapter {

    private TextView commentTitle;
    private TextView commentCreatedTime;
    private ArrayList<commentModel> commentList;

    public commentLVAdapter(ArrayList<commentModel> commentList) {
        this.commentList = commentList != null ? commentList : new ArrayList<>();
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

        commentModel item = commentList.get(position);

        commentTitle.setText(item.getCommentTitle());
        commentCreatedTime.setText(item.getCommentCreatedtime());

        return convertView;
    }
}
