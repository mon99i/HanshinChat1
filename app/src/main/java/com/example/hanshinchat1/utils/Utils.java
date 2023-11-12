package com.example.hanshinchat1.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanshinchat1.GetRequestActivity;
import com.example.hanshinchat1.Ideal;

import com.example.hanshinchat1.Match;
import com.example.hanshinchat1.RecommendMatchActivity;
import com.example.hanshinchat1.SetIdealActivity;

import com.example.hanshinchat1.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Utils {
    private static final String TAG = "Utils";

    public static void goToGetRequestActivity(Context context) {
        ArrayList<Match> getMatches = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query=FirebaseDatabase.getInstance().getReference().child("rooms").orderByChild("host").equalTo(user.getUid());
        DatabaseReference matchRef = FirebaseDatabase.getInstance().getReference().child("matches");
        matchRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        getMatches.clear();
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot roomSnapshot) {
                                if (roomSnapshot.exists()) {        //내가만든방이있다면 해당 방 매칭기록 조회
                                    for (DataSnapshot item : roomSnapshot.getChildren()) {
                                        String myRoomKey = item.getKey();
                                        for (DataSnapshot subSnapshot : snapshot.child("rooms").child(myRoomKey).getChildren()) {
                                            Match match = subSnapshot.getValue(Match.class);
                                            if (match.getApproved() ==null) {
                                                match.setMatch_key(myRoomKey);
                                                match.setSender_uid(subSnapshot.getKey());
                                                getMatches.add(match);

                                            }
                                            matchRef.child("rooms").child(myRoomKey).child(subSnapshot.getKey()).child("request").setValue(false);
                                        }

                                    }

                                }
                                for (DataSnapshot subSnapshot : snapshot.child("users").child(user.getUid()).getChildren()) {
                                    Match match = subSnapshot.getValue(Match.class);
                                    if (match.getApproved() == null) {
                                        match.setMatch_key(user.getUid());
                                        match.setSender_uid(subSnapshot.getKey());
                                        getMatches.add(match);

                                    }
                                    matchRef.child("users").child(user.getUid()).child(subSnapshot.getKey()).child("request").setValue(false);
                                }

                                Log.d(TAG, "onDataChange: "+getMatches.size());

                                Intent intent = new Intent(context, GetRequestActivity.class);
                                intent.putExtra("getMatches", getMatches);
                                context.startActivity(intent);
                                ((AppCompatActivity) context).finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }



    public static void mostLikedMatching(Context context) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime threeWeeksAgo = currentTime.minusWeeks(3);
        String currentTimeString = currentTime.format(dateTimeFormatter);
        String threeWeeksAgoString = threeWeeksAgo.format(dateTimeFormatter);


        ArrayList<UserInfo> allUsers = new ArrayList<>();
        ArrayList<UserInfo> mostLikedUsers = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        usersRef.orderByChild("lastSignInTime").startAt(threeWeeksAgoString)
                .endAt(currentTimeString).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        allUsers.clear();
                        mostLikedUsers.clear();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            if (!item.getKey().equals(user.getUid())) {
                                UserInfo userInfo = item.getValue(UserInfo.class);
                                if (userInfo.getLike() != null) {
                                    allUsers.add(userInfo);
                                }
                            }
                        }

                        Collections.sort(allUsers, new Comparator<UserInfo>() {
                            @Override
                            public int compare(UserInfo object1, UserInfo object2) {
                                return object2.getLike() - object1.getLike();

                            }
                        });

                        if (allUsers.size() > 5) {
                            for (int i = 0; i < 5; i++) {
                                mostLikedUsers.add(allUsers.get(i));
                                Log.d(TAG, "most like user>5 " + mostLikedUsers.get(i).getName() + " 좋아요: " + mostLikedUsers.get(i).getLike());
                            }
                        } else {
                            for (int i = 0; i < allUsers.size(); i++) {
                                mostLikedUsers.add(allUsers.get(i));
                                Log.d(TAG, "most like user<=5 " + mostLikedUsers.get(i).getName() + " 좋아요: " + mostLikedUsers.get(i).getLike());
                            }
                        }

                        Log.d(TAG, "onDataChange: most like user size " + mostLikedUsers.size());

                        Intent intent = new Intent(context, RecommendMatchActivity.class);
                        intent.putExtra("recommendUsers", mostLikedUsers);
                        intent.putExtra("recommendType", "좋아요를 많이 받은 친구");
                        context.startActivity(intent);
                        ((AppCompatActivity) context).finish();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void recentConnectMatching(Context context) {    //하루전부터 지금까지 접속한 유저 조회
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime oneDayAgo = currentTime.minusDays(1);
        String currentTimeString = currentTime.format(dateTimeFormatter);
        String oneDayAgoString = oneDayAgo.format(dateTimeFormatter);

        Log.d(TAG, "recentConnectMatching: 예시");
        ArrayList<UserInfo> recentConnectUsers = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users")
                .orderByChild("lastSignInTime").startAt(oneDayAgoString)
                .endAt(currentTimeString).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        recentConnectUsers.clear();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            if (!item.getKey().equals(user.getUid())) {
                                UserInfo userInfo = item.getValue(UserInfo.class);
                                recentConnectUsers.add(userInfo);
                                Log.d(TAG, "onDataChange: " + userInfo.getName() + ": " + userInfo.getLastSignInTime());
                            }

                        }
                        Log.d(TAG, "onDataChange: " + recentConnectUsers.size());

                        Intent intent = new Intent(context, RecommendMatchActivity.class);
                        intent.putExtra("recommendUsers", recentConnectUsers);
                        intent.putExtra("recommendType", "오늘 접속한 친구");
                        context.startActivity(intent);
                        ((AppCompatActivity) context).finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public static void recentRegisterMatching(Context context) {    //일주일전부터 지금까지 새로 생성한 유저
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime oneWeekAgo = currentTime.minusWeeks(1);
        String currentTimeString = currentTime.format(dateTimeFormatter);
        String oneWeekAgoString = oneWeekAgo.format(dateTimeFormatter);

        ArrayList<UserInfo> recentRegisterUsers = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users")
                .orderByChild("creationTime").startAt(oneWeekAgoString)
                .endAt(currentTimeString).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        recentRegisterUsers.clear();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            if (!item.getKey().equals(user.getUid())) {
                                UserInfo userInfo = item.getValue(UserInfo.class);
                                recentRegisterUsers.add(userInfo);
                                Log.d(TAG, "onDataChange: " + userInfo.getName() + ": " + userInfo.getCreationTime());
                            }

                        }
                        Log.d(TAG, "onDataChange: " + recentRegisterUsers.size());

                        Intent intent = new Intent(context, RecommendMatchActivity.class);
                        intent.putExtra("recommendUsers", recentRegisterUsers);
                        intent.putExtra("recommendType", "새로 가입한 친구");
                        context.startActivity(intent);
                        ((AppCompatActivity) context).finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    public static void arroundMatching(Context context) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime threeWeeksAgo = currentTime.minusWeeks(3);
        String currentTimeString = currentTime.format(dateTimeFormatter);
        String threeWeeksAgoString = threeWeeksAgo.format(dateTimeFormatter);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<UserInfo> arroundUsers = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("users")
                .orderByChild("lastSignInTime").startAt(threeWeeksAgoString).endAt(currentTimeString)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo currentUser = snapshot.child(user.getUid()).getValue(UserInfo.class);
                        arroundUsers.clear();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            if (!item.getKey().equals(user.getUid())) {
                                UserInfo opponentUser = item.getValue(UserInfo.class);
                                if (currentUser.getAddress().equals(opponentUser.getAddress())) {
                                    Log.d(TAG, "onDataChange: " + opponentUser.getName() + ": " + opponentUser.getAddress());
                                    arroundUsers.add(opponentUser);
                                }
                            }

                        }
                        Intent intent = new Intent(context, RecommendMatchActivity.class);
                        intent.putExtra("recommendUsers", arroundUsers);
                        intent.putExtra("recommendType", "내 주변 친구");
                        context.startActivity(intent);
                        ((AppCompatActivity) context).finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    public static void idealMatching(Context context, Ideal ideal) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime threeWeeksAgo = currentTime.minusWeeks(3);
        String currentTimeString = currentTime.format(dateTimeFormatter);
        String threeWeeksAgoString = threeWeeksAgo.format(dateTimeFormatter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        HashSet<UserInfo> firstsSet = new HashSet<>();
        HashSet<UserInfo> secondsSet = new HashSet<>();
        HashSet<UserInfo> thirdsSet = new HashSet<>();

        FirebaseDatabase.getInstance().getReference().child("users")
                .orderByChild("lastSignInTime").startAt(threeWeeksAgoString).endAt(currentTimeString)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        firstsSet.clear();
                        secondsSet.clear();
                        thirdsSet.clear();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            String uid = item.getKey();
                            if (!uid.equals(user.getUid())) {
                                UserInfo userInfo = item.getValue(UserInfo.class);
                                for (DataSnapshot subItem : item.getChildren()) {
                                    Object opponentInfoValue = subItem.getValue();                // 값들이 하나로 저장되어 있는경우. 지역값, 종교값
                                    ArrayList<Object> opponentInfoValues;                       // 값들이 리스트로 저장되어 있는경우. 성격 값들, 흥미 값들

                                    //1순위 조건
                                    Object myFirstIdealValue = ideal.getPriority1().get(subItem.getKey());
                                    ArrayList<Object> myFirstIdealValues;
                                    if (myFirstIdealValue != null) {
                                        if (myFirstIdealValue instanceof ArrayList && opponentInfoValue instanceof ArrayList) {
                                            myFirstIdealValues = (ArrayList<Object>) myFirstIdealValue;
                                            opponentInfoValues = (ArrayList<Object>) opponentInfoValue;

                                            for (Object value : myFirstIdealValues) {                     //거꾸로도 가능
                                                if (opponentInfoValues.contains(value)) {
                                                    Log.d(TAG, "onDataChange: " + myFirstIdealValues);
                                                    firstsSet.add(userInfo);
                                                    Log.d(TAG, "둘다 리스트 1순위 이상형있음 " + uid + " " + value);
                                                }
                                            }
                                        } else if (myFirstIdealValue instanceof ArrayList) {
                                            myFirstIdealValues = (ArrayList<Object>) myFirstIdealValue;
                                            if (myFirstIdealValues.contains(opponentInfoValue)) {
                                                firstsSet.add(userInfo);
                                                Log.d(TAG, "object object1순위 이상형있음 " + uid + " " + opponentInfoValue);
                                            }
                                        } else if (opponentInfoValue instanceof ArrayList) {
                                            opponentInfoValues = (ArrayList<Object>) opponentInfoValue;
                                            if (opponentInfoValues.contains(myFirstIdealValue)) {
                                                firstsSet.add(userInfo);
                                                Log.d(TAG, "object object1순위 이상형있음 " + uid + " " + myFirstIdealValue);
                                            }
                                        } else if (myFirstIdealValue.equals(opponentInfoValue)) {
                                            firstsSet.add(userInfo);
                                            Log.d(TAG, "object object1순위 이상형있음 " + uid + " " + opponentInfoValue);
                                        }
                                    }


                                    //2순위 조건
                                    Object mySecondIdealValue = ideal.getPriority2().get(subItem.getKey());
                                    ArrayList<Object> mySecondIdealValues;
                                    if (mySecondIdealValue != null) {
                                        if (mySecondIdealValue instanceof ArrayList && opponentInfoValue instanceof ArrayList) {
                                            mySecondIdealValues = (ArrayList<Object>) mySecondIdealValue;
                                            opponentInfoValues = (ArrayList<Object>) opponentInfoValue;

                                            for (Object value : mySecondIdealValues) {                     //거꾸로도 가능
                                                if (opponentInfoValues.contains(value)) {
                                                    Log.d(TAG, "onDataChange: " + mySecondIdealValues);
                                                    secondsSet.add(userInfo);
                                                    Log.d(TAG, "둘다 리스트 2순위 이상형있음 " + uid + " " + value);
                                                }
                                            }
                                        } else if (mySecondIdealValue instanceof ArrayList) {
                                            mySecondIdealValues = (ArrayList<Object>) mySecondIdealValue;
                                            if (mySecondIdealValues.contains(opponentInfoValue)) {
                                                secondsSet.add(userInfo);
                                                Log.d(TAG, "object object2순위 이상형있음 " + uid + " " + opponentInfoValue);
                                            }
                                        } else if (opponentInfoValue instanceof ArrayList) {
                                            opponentInfoValues = (ArrayList<Object>) opponentInfoValue;
                                            if (opponentInfoValues.contains(mySecondIdealValue)) {
                                                secondsSet.add(userInfo);
                                                Log.d(TAG, "object object2순위 이상형있음 " + uid + " " + mySecondIdealValue);
                                            }
                                        } else if (mySecondIdealValue.equals(opponentInfoValue)) {
                                            secondsSet.add(userInfo);
                                            Log.d(TAG, "object object2순위 이상형있음 " + uid + " " + opponentInfoValue);
                                        }
                                    }


                                    //3순위 조건
                                    Object myThirdIdealValue = ideal.getPriority3().get(subItem.getKey());
                                    ArrayList<Object> myThirdIdealValues;
                                    if (myThirdIdealValue != null) {
                                        if (myThirdIdealValue instanceof ArrayList && opponentInfoValue instanceof ArrayList) {
                                            myThirdIdealValues = (ArrayList<Object>) myThirdIdealValue;
                                            opponentInfoValues = (ArrayList<Object>) opponentInfoValue;

                                            for (Object value : myThirdIdealValues) {                     //거꾸로도 가능
                                                if (opponentInfoValues.contains(value)) {
                                                    Log.d(TAG, "onDataChange: " + myThirdIdealValues);
                                                    thirdsSet.add(userInfo);
                                                    Log.d(TAG, "둘다 리스트 3순위 이상형있음 " + uid + " " + value);
                                                }
                                            }
                                        } else if (myThirdIdealValue instanceof ArrayList) {
                                            myThirdIdealValues = (ArrayList<Object>) myThirdIdealValue;
                                            if (myThirdIdealValues.contains(opponentInfoValue)) {
                                                thirdsSet.add(userInfo);
                                                Log.d(TAG, "object object3순위 이상형있음 " + uid + " " + opponentInfoValue);
                                            }
                                        } else if (opponentInfoValue instanceof ArrayList) {
                                            opponentInfoValues = (ArrayList<Object>) opponentInfoValue;
                                            if (opponentInfoValues.contains(myThirdIdealValue)) {
                                                thirdsSet.add(userInfo);
                                                Log.d(TAG, "object object3순위 이상형있음 " + uid + " " + myThirdIdealValue);
                                            }
                                        } else if (myThirdIdealValue.equals(opponentInfoValue)) {
                                            thirdsSet.add(userInfo);
                                            Log.d(TAG, "object object3순위 이상형있음 " + uid + " " + opponentInfoValue);
                                        }
                                    }


                                }

                            }
                        }


                        checkIdealPriority(context, firstsSet, secondsSet, thirdsSet);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private static void checkIdealPriority(Context context, HashSet<UserInfo> firstsSet, HashSet<UserInfo> secondsSet, HashSet<UserInfo> thirdsSet) {

        ArrayList<UserInfo> idealUsers = new ArrayList<>();


        ArrayList<UserInfo> firstIdealUsers = new ArrayList<>();
        ArrayList<UserInfo> secondIdealUsers = new ArrayList<>();
        ArrayList<UserInfo> thirdIdealUsers = new ArrayList<>();

        //2,3순위에서 1순위 중복 제거
        for (UserInfo userInfo : firstsSet) {
            if (secondsSet.contains(userInfo) && thirdsSet.contains(userInfo)) {
                firstIdealUsers.add(userInfo);
                //123순위 모두 충족하는 userInfo인경우
            } else {
                secondIdealUsers.add(userInfo);
                //1순위만 충족해도 seconds에 포함
            }
            secondsSet.remove(userInfo);
            thirdsSet.remove(userInfo);
        }
        Log.d(TAG, "checkIdealPriority: " + firstIdealUsers);

        //3순위에서 2순위 중복 제거
        for (UserInfo userInfo : secondsSet) {
            thirdsSet.remove(userInfo);
        }
        thirdIdealUsers.addAll(secondsSet);
        thirdIdealUsers.addAll(thirdsSet);


        Intent intent = new Intent(context, RecommendMatchActivity.class);
        intent.putExtra("recommendType", "이상형 추천");
        intent.putExtra("firstIdealUsers", firstIdealUsers);
        intent.putExtra("secondIdealUsers", secondIdealUsers);
        intent.putExtra("thirdIdealUsers", thirdIdealUsers);
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();



    }


    public static void checkIdealExists(Context context) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("ideals").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            Intent intent = new Intent(context, SetIdealActivity.class);
                            context.startActivity(intent);
                            ((AppCompatActivity) context).finish();
                        } else {
                            idealMatching(context, snapshot.getValue(Ideal.class));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public static void MyUid(Context context) {

        String currentUid;

        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                UserInfo currentUserInfo = datasnapshot.getValue(UserInfo.class);
                if (currentUserInfo != null) {
                    String myMBTI = currentUserInfo.getMbti();
                    String myGender = currentUserInfo.getGender();

                    // 현재 사용자의 MBTI와 성별을 기반으로 추천 받는 로직 수행
                    getRecommend(context, myMBTI, myGender);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Fragment_1", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }

    public static void getRecommend(Context context, String myMBTI, String myGender) {
        String my_mbti = myMBTI;
        String my_gender = myGender;

        String mbtiList[] = {"ENTJ", "ENTP", "INTJ", "INTP", "ESTJ", "ESFJ", "ISTJ", "ISFJ", "ENFJ", "ENFP", "INFJ", "INFP", "ESTP", "ESFP", "ISTP", "ISFP"};
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");

        ArrayList<UserInfo> matchingUsers = new ArrayList<>();

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
                            }
                            // ENTP
                            else if ((my_mbti.equals(mbtiList[1])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("ESTJ")
                                    || userMBTI.equalsIgnoreCase("ESFJ") || userMBTI.equalsIgnoreCase("ISTJ") || userMBTI.equalsIgnoreCase("ISFJ"))) {
                                matchingUsers.add(userInfo);
                            }
                            // INTJ
                            else if ((my_mbti.equals(mbtiList[2])) && (userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ESTP")
                                    || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                            }
                            // INTP
                            else if ((my_mbti.equals(mbtiList[3])) && (userMBTI.equalsIgnoreCase("ESTJ") || userMBTI.equalsIgnoreCase("ESFJ")
                                    || userMBTI.equalsIgnoreCase("ISFJ") || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ESTJ
                            else if ((my_mbti.equals(mbtiList[4])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("INTP")
                                    || userMBTI.equalsIgnoreCase("ENFP") || userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ESFJ
                            else if ((my_mbti.equals(mbtiList[5])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("INTP")
                                    || userMBTI.equalsIgnoreCase("ENFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ISTJ
                            else if ((my_mbti.equals(mbtiList[6])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("ENFP")
                                    || userMBTI.equalsIgnoreCase("INFP") || userMBTI.equalsIgnoreCase("ISFP"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ISFJ
                            else if ((my_mbti.equals(mbtiList[7])) && (userMBTI.equalsIgnoreCase("ENTP") || userMBTI.equalsIgnoreCase("INTP")
                                    || userMBTI.equalsIgnoreCase("ENFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ENFJ
                            else if ((my_mbti.equals(mbtiList[8])) && (userMBTI.equalsIgnoreCase("INTP") || userMBTI.equalsIgnoreCase("ENFJ")
                                    || userMBTI.equalsIgnoreCase("ESTP") || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ENFP
                            else if ((my_mbti.equals(mbtiList[9])) && (userMBTI.equalsIgnoreCase("ESFJ") || userMBTI.equalsIgnoreCase("ISTJ")
                                    || userMBTI.equalsIgnoreCase("ISFJ"))) {
                                matchingUsers.add(userInfo);
                            }
                            // INFJ
                            else if ((my_mbti.equals(mbtiList[10])) && (userMBTI.equalsIgnoreCase("INTP") || userMBTI.equalsIgnoreCase("ESTP")
                                    || userMBTI.equalsIgnoreCase("ESFP") || userMBTI.equalsIgnoreCase("ISTP"))) {
                                matchingUsers.add(userInfo);
                            }
                            // INFP
                            else if ((my_mbti.equals(mbtiList[11])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ESTJ") || userMBTI.equalsIgnoreCase("ISTJ"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ESTP
                            else if ((my_mbti.equals(mbtiList[12])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ESFP
                            else if ((my_mbti.equals(mbtiList[13])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ISTP
                            else if ((my_mbti.equals(mbtiList[14])) && (userMBTI.equalsIgnoreCase("ESFJ") || userMBTI.equalsIgnoreCase("ISFJ")
                                    || userMBTI.equalsIgnoreCase("ENFJ") || userMBTI.equalsIgnoreCase("INFJ"))) {
                                matchingUsers.add(userInfo);
                            }
                            // ISFP
                            else if ((my_mbti.equals(mbtiList[15])) && (userMBTI.equalsIgnoreCase("ENTJ") || userMBTI.equalsIgnoreCase("INTJ")
                                    || userMBTI.equalsIgnoreCase("ESTJ") || userMBTI.equalsIgnoreCase("ISTJ"))) {
                                matchingUsers.add(userInfo);
                            } else {
//                                Intent intent = new Intent(getContext(), MBTIMatchNone.class);
//                                startActivity(intent);
                            }
                        }

                    }
                }

                Intent intent = new Intent(context, RecommendMatchActivity.class);
                intent.putExtra("recommendType", "MBTI 추천");
                intent.putExtra("recommendUsers", matchingUsers);
                context.startActivity(intent);
                ((AppCompatActivity) context).finish();


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Fragment_1", "loadPost:onCancelled", databaseError.toException());
            }

        });
    }


}
