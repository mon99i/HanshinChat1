package com.example.hanshinchat1;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShowMatchInfoActivity extends MainActivity {           //슬라이드 뷰로 넘김, 요청확인한경우는 안뜨게끔해야함

    private static final String TAG = "SHowMatchInfoActivity";
    private int position = 0;
    Button acceptButton;
    Button declineButton;
    TextView gender;
    TextView department;
    TextView age;
    TextView hobby;
    TextView name;
    ImageView image;

    private ViewPager matchPager;
    private MatchPagerAdapter matchPagerAdapter;
    private ArrayList<String> requestedUidList;
    private ArrayList<UserInfo> userInfoList;
    private ArrayList<String> matchRoomKeyList;
    private ArrayList<MatchRoom> matchRoomList;

/*
    ArrayList<String> matchInfoList;
    ArrayList<UserInfo> userInfoList;
*/

    //ArrayList<UserInfo> receivedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_match_info);
        initializeView();
        initializeListener();
        showReceivedUserInfo(position);

    }


    private void initializeView() {
        requestedUidList=(ArrayList<String>) getIntent().getSerializableExtra("requestedUidList");
        userInfoList = (ArrayList<UserInfo>) getIntent().getSerializableExtra("userInfoList");
        matchRoomKeyList = (ArrayList<String>) getIntent().getSerializableExtra("matchRoomKeyList");
        matchRoomList = (ArrayList<MatchRoom>) getIntent().getSerializableExtra("matchRoomList");


        acceptButton = findViewById(R.id.acceptButton);
        declineButton = findViewById(R.id.declineButton);
        gender = findViewById(R.id.showMatch_txt_gender);
        department = findViewById(R.id.showMatch_txt_department);
        age = findViewById(R.id.showMatch_txt_age);
        hobby = findViewById(R.id.showMatch_txt_hobby);
        name = findViewById(R.id.showMatch_txt_name);
        image = findViewById(R.id.showMatch_image);


        matchPagerAdapter = new MatchPagerAdapter(getSupportFragmentManager(), matchRoomKeyList,userInfoList);
        matchPager = findViewById(R.id.matchPager);
        matchPager.setAdapter(matchPagerAdapter);

    }

    private void initializeListener() {
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDB(true);
                int currentItem = matchPager.getCurrentItem();
                if (currentItem < userInfoList.size() - 1) {            //  0123  34
                    matchPager.setCurrentItem(currentItem + 1);
                }else{
                    Intent intent=new Intent(getApplicationContext(),ChatRoomActivity.class);
                    startActivity(intent);
                    finish();
                }
                   /*  position++;
                if (position == receivedList.size()) {
                    Log.d(TAG, position + "사용자 초과");
                    position--;
                }
                showReceivedUserInfo(position);

*/
            }

        });
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDB(false);
                int currentItem = matchPager.getCurrentItem();
                if (currentItem < userInfoList.size() - 1) {
                    matchPager.setCurrentItem(currentItem + 1);
                }else{
                    Intent intent=new Intent(getApplicationContext(),ChatRoomActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void updateDB(boolean b) {
        String matchRoomKey=matchRoomKeyList.get(matchPager.getCurrentItem());
        UserInfo userInfo = userInfoList.get(matchPager.getCurrentItem());
        String requestedUid = userInfo.getUid();

        DatabaseReference matchInfoRef=FirebaseDatabase.getInstance().getReference().child("matchRooms")
                .child(matchRoomKey).child("matchInfo").child(requestedUid);
        matchInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MatchInfo matchInfo=snapshot.getValue(MatchInfo.class);
                if(b==true){
                    matchInfo.setApproved(true);  //승인시
                    matchInfo.setConfirmed(true);
                    matchInfoRef.setValue(matchInfo);
                }

                else{
                    matchInfo.setApproved(false);  //거절시
                    matchInfo.setConfirmed(true);
                    matchInfoRef.setValue(matchInfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    private void showReceivedUserInfo(int position) {
        /*    Log.d(TAG, receivedList.size() + "a123");
         *//* for(UserInfo a : receivedList){
          Uri imageUri=Uri.parse(a.getPhotoUrl());
          Glide.with(getApplicationContext()).load(imageUri).into(image);
          gender.setText(a.getGender());
          department.setText(a.getDepartment());
          age.setText(a.getAge().toString());
          hobby.setText(a.getHobby());
      }*//*

        if (position == receivedList.size()) {
            Log.d(TAG, position + "사용자 초과");
            position--;
        } else if (position < 0) {
            Log.d(TAG, position + "사용자 미만/없음");
            position++;
        } else {
            UserInfo a = receivedList.get(position);
            Uri imageUri = Uri.parse(a.getPhotoUrl());
            Glide.with(getApplicationContext()).load(imageUri).into(image);

            name.setText(a.getName());
            gender.setText(a.getGender());
            department.setText(a.getDepartment());
            age.setText(a.getAge().toString());
            hobby.setText(a.getHobby());
        }*/
    }
}