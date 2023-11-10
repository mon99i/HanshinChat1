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
        boardDataList = new ArrayList<>();
        boardKeyList = new ArrayList<>();
        boardAdapter = new ListViewAdapter(boardDataList);
        boardDataList.clear();
        boardAdapter.notifyDataSetChanged();

        return view;
    }
}
