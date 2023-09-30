package com.example.hanshinchat1.Match;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fragment_2 extends Fragment {
    private TextView age;
    private TextView department;
    private TextView mbti;
    private String currentUid;

    private ArrayList<mbtiModel> mbtiDataList;
    private MyAdapter adapter;
    FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frame_2, container, false);


        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        frameLayout = rootView.findViewById(R.id.mbtiuser);
        age = rootView.findViewById(R.id.ageArea);
        department = rootView.findViewById(R.id.departmentArea);
        mbti = rootView.findViewById(R.id.mbtiArea);
        frameLayout.setAdapter(adapter);
        
        MyUid();

        return rootView;
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
                Log.w("Fragment_2", "loadPost:onCancelled", databaseError.toException());
            }
        });


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
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ENTP
                            else if ((my_mbti.equals(mbtiList[1])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("ESTJ")
                                    || userMBTI.equalsIgnoreCase("ESFJ") || userMBTI.equalsIgnoreCase("ISTJ") || userMBTI.equalsIgnoreCase("ISFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // INTJ
                            else if ((my_mbti.equals(mbtiList[2])) && (userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ESTP")
                                    || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // INTP
                            else if ((my_mbti.equals(mbtiList[3])) && (userMBTI.equalsIgnoreCase("ESTJ") || userMBTI.equalsIgnoreCase("ESFJ")
                                    || userMBTI.equalsIgnoreCase("ISFJ") || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ESTJ
                            else if ((my_mbti.equals(mbtiList[4])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("INTP")
                                    || userMBTI.equalsIgnoreCase("ENFP") || userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ESFJ
                            else if ((my_mbti.equals(mbtiList[5])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("INTP")
                                    || userMBTI.equalsIgnoreCase("ENFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ISTJ
                            else if ((my_mbti.equals(mbtiList[6])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("ENFP")
                                    || userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ISFJ
                            else if ((my_mbti.equals(mbtiList[7])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("INTP")
                                    || userMBTI.equalsIgnoreCase("ENFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ENFJ
                            else if ((my_mbti.equals(mbtiList[8])) && (userMBTI.equalsIgnoreCase("INTP") || userMBTI.equalsIgnoreCase("ENFJ")
                                    || userMBTI.equalsIgnoreCase("ESTP") || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ENFP
                            else if ((my_mbti.equals(mbtiList[9])) && (userMBTI.equalsIgnoreCase("ESFJ") || userMBTI.equalsIgnoreCase("ISTJ")
                                    || userMBTI.equalsIgnoreCase("ISFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // INFJ
                            else if ((my_mbti.equals(mbtiList[10])) && (userMBTI.equalsIgnoreCase("INTP") || userMBTI.equalsIgnoreCase("ESTP")
                                    || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // INFP
                            else if ((my_mbti.equals(mbtiList[11])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ESTJ") || userMBTI.equalsIgnoreCase("ISTJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ESTP
                            else if ((my_mbti.equals(mbtiList[12])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                            }
                            // ESFP
                            else if ((my_mbti.equals(mbtiList[13])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ISTP
                            else if ((my_mbti.equals(mbtiList[14])) && (userMBTI.equalsIgnoreCase("ESFJ") || userMBTI.equalsIgnoreCase("ISFJ")
                                    || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // ISFP
                            else if ((my_mbti.equals(mbtiList[15])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ESTJ") || userMBTI.equalsIgnoreCase("ISTJ"))) {
                                matchingUsers.add(userInfo);
                                Toast.makeText(getContext(), "매칭된 사람이 있기는 함", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Intent intent = new Intent(getContext(), MBTIMatchNone.class);
                                startActivity(intent);
                                Toast.makeText(getContext(), "회원님과 어울리는 MBTI를 가진 사람이 없습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                    }
                }

                if (!matchingUsers.isEmpty()) {

                    frameLayout.removeAllViews();
                    for (UserInfo userInfo : matchingUsers) {
                        View userView = createUserView(userInfo);
                        frameLayout.addView(userView);
                    }
//                    Random random = new Random();
//                    int randomIndex = random.nextInt(matchingUsers.size());
//                    UserInfo randomUser = matchingUsers.get(randomIndex);
//
//                    UserInfo dataModel = datasnapshot.child(randomUser.getUid()).getValue(UserInfo.class);
//
//                    age.setText(dataModel != null ? String.valueOf(dataModel.getAge()) : "");
//                    department.setText(dataModel != null ? dataModel.getDepartment() : "");
//                    mbti.setText(dataModel != null ? dataModel.getMbti() : "");  여기까지

//                    for (UserInfo userInfo : matchingUsers) {
//                        mbtiDataList.add(new mbtiModel(
//                                userInfo.getAge(),
//                                userInfo.getDepartment(),
//                                userInfo.getMbti()
//                        ));
//                    }   잠깐2

//                    adapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림

//                    // 뷰페이저를 순서대로 보여주도록 설정
//                    if (currentIndex < mbtiDataList.size()) {
//                        viewPager.setCurrentItem(currentIndex);
//                    } else {
//                        viewPager.setCurrentItem(0); // 리스트가 끝에 도달하면 첫 번째 아이템으로 돌아감
//                    }

                    Toast.makeText(getContext(), "사용자 정보를 가져왔다", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getContext(), MBTIMatchNone.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "매칭된 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Fragment_2", "loadPost:onCancelled", databaseError.toException());
            }

        });

    }

    private View createUserView(UserInfo userInfo) {
        // 개별 사용자에 대한 정보를 보여주는 View를 생성하고 반환
        View userView = LayoutInflater.from(getContext()).inflate(R.layout.frame_2, null);

        TextView ageTextView = userView.findViewById(R.id.ageArea);
        TextView departmentTextView = userView.findViewById(R.id.departmentArea);
        TextView mbtiTextView = userView.findViewById(R.id.mbtiArea);

        // userInfo에서 필요한 정보를 가져와서 TextView에 설정
        ageTextView.setText(String.valueOf(userInfo.getAge()));
        departmentTextView.setText(userInfo.getDepartment());
        mbtiTextView.setText(userInfo.getMbti());

        return userView;
    }
}
