package com.example.hanshinchat1.board;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hanshinchat1.R;
import com.example.hanshinchat1.R.layout;

import java.util.ArrayList;

public final class ListViewAdapter extends BaseAdapter {

    private TextView title;
    private TextView content;
    private TextView time;
    private ArrayList<ListViewItem> boardList;

    public ListViewAdapter(ArrayList<ListViewItem> boardList) {
        this.boardList = boardList != null ? boardList : new ArrayList<>();
    }

    @Override
    public int getCount() {
        return boardList.size();
    }

    @Override
    public Object getItem(int position) {
        return boardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // 버그 생기면 지우면 됨
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout.board_list_cardview, parent, false);
        }


        title = (TextView) convertView.findViewById(R.id.titleArea);
        content = (TextView) convertView.findViewById(R.id.contentArea);
        time = (TextView) convertView.findViewById(R.id.timeArea);

        ListViewItem item = boardList.get(position);

        title.setText(item.getTitle());
        content.setText(item.getContent());
        time.setText(item.getTime());
        Log.d("ListViewAdapter", "onBindViewHolder: position " + position);

        return convertView;
    }
}