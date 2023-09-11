package com.example.hanshinchat1.board;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class List2Activity extends MainActivity {

    private ArrayList<ListViewItem> boardDataList;
    private ArrayList<String> boardKeyList;
    private ListView listView;
    private ListViewAdapter boardAdapter;
    private ImageView writePageBtn;
    private TextView messageboard, dating_advice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_list);

//        boardDataList.clear();
//        boardAdapter.notifyDataSetChanged();
//
//        FBData();

        boardDataList = new ArrayList<>();
        boardKeyList = new ArrayList<>();
        boardAdapter = new ListViewAdapter(boardDataList);
        listView = findViewById(R.id.boardListView);
        listView.setAdapter(boardAdapter);
        messageboard = findViewById(R.id.messageboard);
        dating_advice = findViewById(R.id.dating_advice);

        dating_advice.setPaintFlags(dating_advice.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        messageboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                intent.putExtra("key", boardKeyList.get(position));
                startActivity(intent);

            }
        });

        writePageBtn = (ImageView) findViewById(R.id.writePageBtn);
        writePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardWriteActivity.class);
                startActivity(intent);
//                finish();
            }
        });


        boardDataList.clear();
        boardAdapter.notifyDataSetChanged();

        FBData();


        clickMenu();
        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();
    }

    private void FBData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("연애상담");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boardDataList.clear();

                for (DataSnapshot dataModel : dataSnapshot.getChildren()) {
                    Log.d("List2Activity", dataModel.toString());

                    ListViewItem item = dataModel.getValue(ListViewItem.class);
                    boardDataList.add(item);
                    boardKeyList.add(dataModel.getKey());
                }
                Collections.reverse(boardKeyList);
                Collections.reverse(boardDataList);
                boardAdapter.notifyDataSetChanged();
                Log.w("List2Activity", boardDataList.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("List2Activity", "loadPost:onCancelled", databaseError.toException());
            }

        };
        myRef.addValueEventListener(postListener);
    };


}
