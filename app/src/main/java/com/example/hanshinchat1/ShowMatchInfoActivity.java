package com.example.hanshinchat1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private ArrayList<UserInfo> requestedUserInfoList;

    private ArrayList<String> approvedUidList;
    private ArrayList<UserInfo> approvedUserInfoList;
    private ArrayList<String> matchRoomKeyList;
    private ArrayList<MatchRoom> matchRoomList;

/*
    ArrayList<String> matchInfoList;
    ArrayList<UserInfo> userInfoList;
*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_match_info);
        initializeView();
        initializeListener();


    }


    private void initializeView() {
        requestedUidList = (ArrayList<String>) getIntent().getSerializableExtra("requestedUidList");
        requestedUserInfoList = (ArrayList<UserInfo>) getIntent().getSerializableExtra("requestedUserInfoList");
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


        matchPagerAdapter = new MatchPagerAdapter(getSupportFragmentManager(), matchRoomKeyList, requestedUserInfoList);
        matchPager = findViewById(R.id.matchPager);
        matchPager.setAdapter(matchPagerAdapter);

    }

    private void initializeListener() {
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDB(true);


            }

        });
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDB(false);


            }
        });
    }

    private void updateDB(boolean b) {
        String matchRoomKey = matchRoomKeyList.get(matchPager.getCurrentItem());
        UserInfo userInfo = requestedUserInfoList.get(matchPager.getCurrentItem());
        String requestedUid = userInfo.getUid();

        DatabaseReference matchInfoRef = FirebaseDatabase.getInstance().getReference().child("matchRooms")
                .child(matchRoomKey).child("matchInfo").child(requestedUid);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        matchInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MatchInfo matchInfo = snapshot.getValue(MatchInfo.class);
                if (b == true) {     //승인시
                    matchInfo.setApproved(true);
                    matchInfo.setConfirmed(true);
                    matchInfoRef.setValue(matchInfo);


                    createChatRoom(requestedUid);
                    //approved가 true인 uid로 채팅방생성

                    //approvedUidList.add(requestedUid);

                } else {//거절시
                    matchInfo.setApproved(false);
                    matchInfo.setConfirmed(true);
                    matchInfoRef.setValue(matchInfo);
                }

                //마지막 페이지일경우 넘어감
                int currentItem = matchPager.getCurrentItem();
                if (currentItem < requestedUserInfoList.size() - 1) {
                    matchPager.setCurrentItem(currentItem + 1);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void createChatRoom(String approvedUid) {  //디비에 chatRoom 생성

        //승인된uid를 통해 채팅방 데이터베이스 생성
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("chatRooms");
        Map<String, Boolean> usersMap = new HashMap<>();
        usersMap.put(user.getUid(), true);  //currentUser
        usersMap.put(approvedUid, true);
        ChatRoom chatRoom = new ChatRoom(usersMap, null);


        //현재 내 uid가 true인 채팅방 나열
        chatRoomsRef.orderByChild("users/" + user.getUid()).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean chatExists =false;
                        for (DataSnapshot item : snapshot.getChildren()) {
                            Map<String, Boolean> chatUsers = item.getValue(ChatRoom.class).getUsers();
                            if (chatUsers.containsKey(approvedUid)) {   //이미 채팅방 있는경우
                                chatExists = true;
                                Log.d(TAG, "onDataChange: 이미 채팅정보 있음!!");

                            }
                        }
                        if (chatExists == false) {  //이후 수락한 uid와의 채팅방 없는경우
                            chatRoomsRef.push().setValue(chatRoom).addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "onDataChange: 채팅방생성!!");
                            });
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

/*

        Query query = chatRoomsRef.orderByChild("users/" + approvedUid).equalTo(true); // 상대방 Uid가 포함된 채팅방이 있는 지 확인
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) { // 채팅방이 없는 경우
                    chatRoomsRef.push().setValue(chatRoom).addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "onDataChange: 채팅방생성!!");

                    });
                } else {
                    Log.d(TAG, "onDataChange: 이미있는상대!!");
                    //context.startActivity(new Intent(context, ChatRoomActivity.class));  //일단홈으로
                    //goToChatRoom(chatRoom, opponent); // 해당 채팅방으로 이동
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
*/


    }

}