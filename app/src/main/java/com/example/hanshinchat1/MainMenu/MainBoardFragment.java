package com.example.hanshinchat1.MainMenu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.hanshinchat1.R;
import com.example.hanshinchat1.board.ListViewAdapter;
import com.example.hanshinchat1.board.ListViewItem;
import com.example.hanshinchat1.board.MyPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainBoardFragment extends Fragment {

    private ArrayList<ListViewItem> boardDataList;
    private ArrayList<String> boardKeyList;
    private ListView listView;
    private ListViewAdapter boardAdapter;
    private ImageView writePageBtn;
    ViewPager viewPager;
    TabLayout boardtab;
    MyPageAdapter myPageAdapter;
    int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.boardfragment, container, false);


        boardtab = view.findViewById(R.id.board_tab);
        viewPager = view.findViewById(R.id.boardViewPager);

        boardtab.addTab(boardtab.newTab().setText("자유 게시판"));
        boardtab.addTab(boardtab.newTab().setText("연애상담 게시판"));
        boardtab.setTabGravity(boardtab.GRAVITY_FILL);

        myPageAdapter = new MyPageAdapter(getChildFragmentManager(), 2);
        viewPager.setAdapter(myPageAdapter);

        boardtab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        boardtab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(boardtab));
//        boardDataList.clear();
//        boardAdapter.notifyDataSetChanged();
//
//        FBData();

        boardDataList = new ArrayList<>();
        boardKeyList = new ArrayList<>();
        boardAdapter = new ListViewAdapter(boardDataList);
//        listView = findViewById(R.id.boardListView);     ㅁㅁ
//        listView.setAdapter(boardAdapter);   ㅁㅁ
//        messageboard = findViewById(R.id.messageboard);
//        dating_advice = findViewById(R.id.dating_advice);
//
//        messageboard.setPaintFlags(messageboard.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        dating_advice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), List2Activity.class);
//                startActivity(intent);
//            }
//        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
//                intent.putExtra("key", boardKeyList.get(position));
//                startActivity(intent);
//
//            }
//        });  ㅁㅁ

//        writePageBtn = (ImageView) findViewById(R.id.writePageBtn);
//        writePageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), BoardWriteActivity.class);
//                startActivity(intent);
////                finish();
//            }
//        });


        boardDataList.clear();
        boardAdapter.notifyDataSetChanged();

//        FBData();

        return view;

    }

    private void FBData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("board");

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
    };
}
