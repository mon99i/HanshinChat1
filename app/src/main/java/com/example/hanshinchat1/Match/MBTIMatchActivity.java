package com.example.hanshinchat1.Match;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

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

//    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Button recommendBtn;
    private TextView age;
    private TextView grade;
    private TextView department;
    private TextView mbti;
    private TextView mymbti;
    private String key;
    private String myMBTI;
    private String myGender;
    private String currentUid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mbtimatch);

        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        age = (TextView) findViewById(R.id.ageArea);
        department = findViewById(R.id.departmentArea);
        mbti = findViewById(R.id.mbtiArea);

        key = getIntent().getStringExtra("key");

//        myMBTI = getMyMbti(key);     기둘
//        myGender = getMyGender(key);  기둘
//        getMyGender(key);   사실 얘도 필요없었던거임...
//        getMyMbti(key);   얘를 쓰나?
//        getRecommend(myMBTI, myGender);   얘도?

        MyUid();

        recommendBtn = findViewById(R.id.recommendBtn);
        recommendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
            Random random = new Random();
            int randomIndex = random.nextInt(matchingUsers.size());
            UserInfo randomUser = matchingUsers.get(randomIndex);

                    UserInfo dataModel = datasnapshot.child(randomUser.getUid()).getValue(UserInfo.class);

                    age.setText(dataModel != null ? String.valueOf(dataModel.getAge()) : "");
                    department.setText(dataModel != null ? dataModel.getDepartment() : "");
                    mbti.setText(dataModel != null ? dataModel.getMbti() : "");

                    Toast.makeText(MBTIMatchActivity.this, "사용자 정보를 가져왔다", Toast.LENGTH_SHORT).show();

                } else {
            Toast.makeText(MBTIMatchActivity.this, "매칭된 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
        }


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MBTIMatchActivity", "loadPost:onCancelled", databaseError.toException());
            }

        });

//        if (!matchingUsers.isEmpty()) {
//            Random random = new Random();
//            int randomIndex = random.nextInt(matchingUsers.size());
//            UserInfo randomUser = matchingUsers.get(randomIndex);
//
////            DatabaseReference randomUserRef = userRef.orderByChild("mbti").equalTo(randomUser).getRef();
//
//            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                    UserInfo dataModel = datasnapshot.child(randomUser.getUid()).getValue(UserInfo.class);
//
//                    age.setText(dataModel != null ? String.valueOf(dataModel.getAge()) : "");
//                    department.setText(dataModel != null ? dataModel.getDepartment() : "");
//                    mbti.setText(dataModel != null ? dataModel.getMbti() : "");
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Log.w("MBTIMatchActivity", "loadPost:onCancelled", databaseError.toException());
//                }
//            });
//        } else {
//            Toast.makeText(MBTIMatchActivity.this, "매칭된 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
//        }

//                여기에 쓰기 matchingUsers


    }
}


//                for (DataSnapshot userSnapshot : datasnapshot.getChildren()) {
//                    String mbti = userSnapshot.child("mbti").getValue(String.class);
//                    if (mbti != null && mbti.equalsIgnoreCase("intj")) {
//                        age.setText(dataModel != null ? String.valueOf(age) : "");
//                        department.setText(dataModel != null ? dataModel.getDepartment() : "");
//                        mbti_1.setText(dataModel != null ? dataModel.getMbti() : "");
//                    }
//                }
//                if (dataModel != null) {
//                    age.setText(dataModel != null ? dataModel.getDepartment() : "");
//                    department.setText(dataModel != null ? dataModel.getDepartment() : "");
//                    mbti.setText(dataModel != null ? dataModel.getMbti() : "");
//
//                }

//            }

//        });
//
//    }

//}
