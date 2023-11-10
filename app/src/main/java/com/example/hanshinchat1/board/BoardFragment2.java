package com.example.hanshinchat1.board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BoardFragment2 extends Fragment {

    private ArrayList<ListViewItem> boardDataList;
    private ArrayList<String> boardKeyList;
    private ListView listView;
    private ListViewAdapter boardAdapter;
    private ImageView writePageBtn;

    public BoardFragment2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag2View = inflater.inflate(R.layout.boardfragment_2, container, false);

        boardDataList = new ArrayList<>();
        boardKeyList = new ArrayList<>();
        boardAdapter = new ListViewAdapter(boardDataList);
        listView = frag2View.findViewById(R.id.boardListView);
        listView.setAdapter(boardAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BoardActivity2.class);
                intent.putExtra("key", boardKeyList.get(position));
                startActivity(intent);

            }
        });

        writePageBtn = (ImageView) frag2View.findViewById(R.id.writePageBtn);
        writePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BoardWriteActivity2.class);
                startActivity(intent);
            }
        });

        boardDataList.clear();
        boardAdapter.notifyDataSetChanged();

        FBData();

        return frag2View;
    }

    private void FBData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("board2");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boardDataList.clear();

                for (DataSnapshot dataModel : dataSnapshot.getChildren()) {
                    Log.d("ListActivity", dataModel.toString());

                    ListViewItem item = dataModel.getValue(ListViewItem.class);
                    boardDataList.add(item);
                    boardKeyList.add(dataModel.getKey());
                }
                Collections.reverse(boardKeyList);
                Collections.reverse(boardDataList);
                boardAdapter.notifyDataSetChanged();
                Log.w("ListActivity", boardDataList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ListActivity", "loadPost:onCancelled", databaseError.toException());
            }
        };
        myRef.addValueEventListener(postListener);
    }
}
