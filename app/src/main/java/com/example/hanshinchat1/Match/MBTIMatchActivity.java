package com.example.hanshinchat1.Match;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hanshinchat1.MainActivity;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;
import com.example.hanshinchat1.board.BoardWriteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MBTIMatchActivity extends MainActivity {


    private Button mbtiBtn;
    private TextView age;
    private TextView grade;
    private TextView department;
    private TextView mbti;
    private TextView mymbti;
    private String key;
    private String myMBTI;
    private String myGender;
    private String currentUid;
    private ViewPager2 viewPager;
    private ArrayList<mbtiModel> mbtiDataList;
    private int currentIndex = 0;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mbtimatch);
//        setContentView(R.layout.mbtimatch2);
        setContentView(R.layout.activity_main);
//        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

//        viewPager = findViewById(R.id.mbtiViewPager);   잠깐
        viewPager = findViewById(R.id.viewpager);
        mbtiDataList = new ArrayList<>();
//        adapter = new mbtiAdapter(mbtiDataList);
        mbtiDataList.add(new mbtiModel(25, "Computer Science", "INTJ"));
        mbtiDataList.add(new mbtiModel(22, "Electrical Engineering", "INFP"));
//        mbtiAdapter adapter = new mbtiAdapter(this, mbtiDataList);
//        viewPager.setAdapter(adapter);



//        mbtiBtn = findViewById(R.id.mbtiBtn);
//        mbtiBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        adapter = new mbtiAdapter(mbtiDataList);


    }


    private void MyUid() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                UserInfo currentUserInfo = datasnapshot.getValue(UserInfo.class);
                if (currentUserInfo != null) {
                    String myMBTI = currentUserInfo.getMbti();
                    String myGender = currentUserInfo.getGender();

                    // 현재 사용자의 MBTI와 성별을 기반으로 추천 받는 로직 수행
                    getRecommend(myMBTI, myGender);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MBTIMatchActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });


    }
    private void getMyGender(String key) {
        if (key == null) {
            Toast.makeText(MBTIMatchActivity.this, "설마 여기?", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");

//        final String[] myGender = new String[4];

        userRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {

                UserInfo dataModel = datasnapshot.getValue(UserInfo.class);
                if (dataModel != null) {
//                    String userGender = dataModel.getMbti();
//                    mymbti.setText(userGender != null ? userGender : "");
//                    myGender[0] = userGender;
                    myGender = dataModel.getGender();
                    getMyMbti(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MBTIMatchActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });
//        return myGender[0];
    }

    private void getMyMbti(String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");

//        final String[] myMbti = new String[4];

        userRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {

                UserInfo dataModel = datasnapshot.getValue(UserInfo.class);
                if (dataModel != null) {
//                    String userMbti = dataModel.getMbti();
//                    mymbti.setText(userMbti != null ? userMbti : "");
//                    myMbti[0] = userMbti;
                    myMBTI = dataModel.getMbti();
                    getRecommend(myMBTI, myGender);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MBTIMatchActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });
//        return myMbti[0];
    }

    private void getRecommend(String myMBTI, String myGender) {
        String my_mbti = myMBTI;
        String my_gender = myGender;

        String mbtiList[] = {"ENTJ", "ENTP", "INTJ", "INTP", "ESTJ", "ESFJ", "ISTJ", "ISFJ", "ENFJ", "ENFP", "INFJ", "INFP", "ESTP", "ESFP", "ISTP", "ISFP"};
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");

        List<UserInfo> matchingUsers = new ArrayList<>();


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
//                List<UserInfo> matchingUsers = new ArrayList<>();
                for (DataSnapshot userSnapshot : datasnapshot.getChildren()) {
//                    UserInfo userMBTI = userSnapshot.child("mbti").getValue(UserInfo.class);
//                    UserInfo userGender = userSnapshot.child("gender").getValue(UserInfo.class);
                    UserInfo userInfo = userSnapshot.getValue(UserInfo.class);
                    if (userInfo != null) {
                        String userMBTI = userInfo.getMbti();
                        String userGender = userInfo.getGender();

                        if (!my_gender.equals(userGender) && userMBTI != null) {
                            // ENTJ
                            if ((my_mbti.equals(mbtiList[0])) && (userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ESTP")
                                    || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ENTP
                            else if ((my_mbti.equals(mbtiList[1])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("ESTJ")
                                    || userMBTI.equalsIgnoreCase("ESFJ") || userMBTI.equalsIgnoreCase("ISTJ") || userMBTI.equalsIgnoreCase("ISFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // INTJ
                            else if ((my_mbti.equals(mbtiList[2])) && (userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ESTP")
                                    || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // INTP
                            else if ((my_mbti.equals(mbtiList[3])) && (userMBTI.equalsIgnoreCase("ESTJ") || userMBTI.equalsIgnoreCase("ESFJ")
                                    || userMBTI.equalsIgnoreCase("ISFJ") || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ESTJ
                            else if ((my_mbti.equals(mbtiList[4])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("INTP")
                                    || userMBTI.equalsIgnoreCase("ENFP") || userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ESFJ
                            else if ((my_mbti.equals(mbtiList[5])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("INTP")
                                    || userMBTI.equalsIgnoreCase("ENFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ISTJ
                            else if ((my_mbti.equals(mbtiList[6])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("ENFP")
                                    || userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ISFJ
                            else if ((my_mbti.equals(mbtiList[7])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("INTP")
                                    || userMBTI.equalsIgnoreCase("ENFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ENFJ
                            else if ((my_mbti.equals(mbtiList[8])) && (userMBTI.equalsIgnoreCase("INTP") || userMBTI.equalsIgnoreCase("ENFJ")
                                    || userMBTI.equalsIgnoreCase("ESTP") || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ENFP
                            else if ((my_mbti.equals(mbtiList[9])) && (userMBTI.equalsIgnoreCase("ESFJ") || userMBTI.equalsIgnoreCase("ISTJ")
                                    || userMBTI.equalsIgnoreCase("ISFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // INFJ
                            else if ((my_mbti.equals(mbtiList[10])) && (userMBTI.equalsIgnoreCase("INTP") || userMBTI.equalsIgnoreCase("ESTP")
                                    || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // INFP
                            else if ((my_mbti.equals(mbtiList[11])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ESTJ") || userMBTI.equalsIgnoreCase("ISTJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ESTP
                            else if ((my_mbti.equals(mbtiList[12])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ESFP
                            else if ((my_mbti.equals(mbtiList[13])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ISTP
                            else if ((my_mbti.equals(mbtiList[14])) && (userMBTI.equalsIgnoreCase("ESFJ") || userMBTI.equalsIgnoreCase("ISFJ")
                                    || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ISFP
                            else if ((my_mbti.equals(mbtiList[1])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ESTJ") || userMBTI.equalsIgnoreCase("ISTJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(MBTIMatchActivity.this, "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), MBTIMatchNone.class);
                                startActivity(intent);
                                Toast.makeText(MBTIMatchActivity.this, "회원님과 어울리는 MBTI를 가진 사람이 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }

                if (!matchingUsers.isEmpty()) {

                    for (UserInfo userInfo : matchingUsers) {
                        mbtiDataList.add(new mbtiModel(
                                userInfo.getAge(),
                                userInfo.getDepartment(),
                                userInfo.getMbti()
                        ));
                    }

//                    adapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림

                    // 뷰페이저를 순서대로 보여주도록 설정
                    if (currentIndex < mbtiDataList.size()) {
                        viewPager.setCurrentItem(currentIndex);
                    } else {
                        viewPager.setCurrentItem(0); // 리스트가 끝에 도달하면 첫 번째 아이템으로 돌아감
                    }

                    Toast.makeText(MBTIMatchActivity.this, "사용자 정보를 가져왔다", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getApplicationContext(), MBTIMatchNone.class);
                    startActivity(intent);
                    Toast.makeText(MBTIMatchActivity.this, "매칭된 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MBTIMatchActivity", "loadPost:onCancelled", databaseError.toException());
            }

        });

    }
}


