package com.example.hanshinchat1.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanshinchat1.Ideal;
import com.example.hanshinchat1.Match.MbtiMatchActivity2;

import com.example.hanshinchat1.RecommendMatchActivity;
import com.example.hanshinchat1.SetIdealActivity;
import com.example.hanshinchat1.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {
    private static final String TAG = "Utils";

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
                .endAt(currentTimeString).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                        intent.putExtra("recommendType","오늘 접속한 친구");
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
                .endAt(currentTimeString).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

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
                        intent.putExtra("recommendType","새로 가입한 친구");
                        context.startActivity(intent);
                        ((AppCompatActivity) context).finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    public static void arroundMatching(Context context) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<UserInfo> arroundUsers = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo currentUser = snapshot.child(user.getUid()).getValue(UserInfo.class);

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
                        intent.putExtra("recommendType","내 주변 친구");
                        context.startActivity(intent);
                        ((AppCompatActivity) context).finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    public static void idealMatching(Context context, Ideal ideal) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<UserInfo> firsts = new ArrayList<>();
        ArrayList<UserInfo> seconds = new ArrayList<>();
        ArrayList<UserInfo> thirds = new ArrayList<>();

        //ArrayList<UserInfo> idealUsers=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                            if(myFirstIdealValue!=null){
                                if (myFirstIdealValue instanceof ArrayList && opponentInfoValue instanceof ArrayList) {
                                    myFirstIdealValues = (ArrayList<Object>) myFirstIdealValue;
                                    opponentInfoValues = (ArrayList<Object>) opponentInfoValue;

                                    for (Object value : myFirstIdealValues) {                     //거꾸로도 가능
                                        if (opponentInfoValues.contains(value)) {
                                            Log.d(TAG, "onDataChange: "+myFirstIdealValues);
                                            firsts.add(userInfo);
                                            Log.d(TAG, "둘다 리스트 1순위 이상형있음 " + uid + " " + value);
                                        }
                                    }
                                }else if(myFirstIdealValue instanceof ArrayList){
                                    myFirstIdealValues = (ArrayList<Object>) myFirstIdealValue;
                                    if(myFirstIdealValues.contains(opponentInfoValue)){
                                        firsts.add(userInfo);
                                        Log.d(TAG, "object object1순위 이상형있음 " + uid + " " + opponentInfoValue);
                                    }
                                }else if(opponentInfoValue instanceof ArrayList){
                                    opponentInfoValues=(ArrayList<Object>) opponentInfoValue;
                                    if (opponentInfoValues.contains(myFirstIdealValue)){
                                        firsts.add(userInfo);
                                        Log.d(TAG, "object object1순위 이상형있음 " + uid + " " + myFirstIdealValue);
                                    }
                                } else if (myFirstIdealValue.equals(opponentInfoValue)) {
                                    firsts.add(userInfo);
                                    Log.d(TAG, "object object1순위 이상형있음 " + uid + " " + opponentInfoValue);
                                }
                            }


                            //2순위 조건
                            Object mySecondIdealValue = ideal.getPriority2().get(subItem.getKey());
                            ArrayList<Object> mySecondIdealValues;
                            if(mySecondIdealValue!=null){
                                if (mySecondIdealValue instanceof ArrayList && opponentInfoValue instanceof ArrayList) {
                                    mySecondIdealValues = (ArrayList<Object>) mySecondIdealValue;
                                    opponentInfoValues = (ArrayList<Object>) opponentInfoValue;

                                    for (Object value : mySecondIdealValues) {                     //거꾸로도 가능
                                        if (opponentInfoValues.contains(value)) {
                                            Log.d(TAG, "onDataChange: "+mySecondIdealValues);
                                            seconds.add(userInfo);
                                            Log.d(TAG, "둘다 리스트 2순위 이상형있음 " + uid + " " + value);
                                        }
                                    }
                                }else if(mySecondIdealValue instanceof ArrayList){
                                    mySecondIdealValues = (ArrayList<Object>) mySecondIdealValue;
                                    if(mySecondIdealValues.contains(opponentInfoValue)){
                                        seconds.add(userInfo);
                                        Log.d(TAG, "object object2순위 이상형있음 " + uid + " " + opponentInfoValue);
                                    }
                                }else if(opponentInfoValue instanceof ArrayList){
                                    opponentInfoValues=(ArrayList<Object>) opponentInfoValue;
                                    if (opponentInfoValues.contains(mySecondIdealValue)){
                                        seconds.add(userInfo);
                                        Log.d(TAG, "object object2순위 이상형있음 " + uid + " " + mySecondIdealValue);
                                    }
                                } else if (mySecondIdealValue.equals(opponentInfoValue)) {
                                    seconds.add(userInfo);
                                    Log.d(TAG, "object object2순위 이상형있음 " + uid + " " + opponentInfoValue);
                                }
                            }


                            //3순위 조건
                            Object myThirdIdealValue = ideal.getPriority3().get(subItem.getKey());
                            ArrayList<Object> myThirdIdealValues;
                            if(myThirdIdealValue!=null){
                                if (myThirdIdealValue instanceof ArrayList && opponentInfoValue instanceof ArrayList) {
                                    myThirdIdealValues = (ArrayList<Object>) myThirdIdealValue;
                                    opponentInfoValues = (ArrayList<Object>) opponentInfoValue;

                                    for (Object value : myThirdIdealValues) {                     //거꾸로도 가능
                                        if (opponentInfoValues.contains(value)) {
                                            Log.d(TAG, "onDataChange: "+myThirdIdealValues);
                                            thirds.add(userInfo);
                                            Log.d(TAG, "둘다 리스트 3순위 이상형있음 " + uid + " " + value);
                                        }
                                    }
                                }else if(myThirdIdealValue instanceof ArrayList){
                                    myThirdIdealValues = (ArrayList<Object>) myThirdIdealValue;
                                    if(myThirdIdealValues.contains(opponentInfoValue)){
                                        thirds.add(userInfo);
                                        Log.d(TAG, "object object3순위 이상형있음 " + uid + " " + opponentInfoValue);
                                    }
                                }else if(opponentInfoValue instanceof ArrayList){
                                    opponentInfoValues=(ArrayList<Object>) opponentInfoValue;
                                    if (opponentInfoValues.contains(myThirdIdealValue)){
                                        thirds.add(userInfo);
                                        Log.d(TAG, "object object3순위 이상형있음 " + uid + " " + myThirdIdealValue);
                                    }
                                } else if (myThirdIdealValue.equals(opponentInfoValue)) {
                                    thirds.add(userInfo);
                                    Log.d(TAG, "object object3순위 이상형있음 " + uid + " " + opponentInfoValue);
                                }
                            }
                            //priority1
                            /*for (Map.Entry<String, Object> priority1 : ideal.getPriority1().entrySet()) {
                                if (subItem.getKey().equals(priority1.getKey())) {
                                    Object userValue = subItem.getValue();
                                    Object priorityValue = priority1.getValue();
                                    ArrayList<Object> userValues;
                                    ArrayList<Object> priorityValues;
                                    if (userValue instanceof ArrayList && priorityValue instanceof ArrayList) {
                                        userValues = (ArrayList<Object>) userValue;
                                        priorityValues = (ArrayList<Object>) priorityValue;

                                        for (Object value : priorityValues) {
                                            if (userValues.contains(value)) {
                                                firstIdealUsers.add(userInfo);
                                                Log.d(TAG, "둘다 리스트 1순위 이상형있음 " + uid + " " + value);
                                            }
                                        }
                                    } else if (userValue instanceof ArrayList) {
                                        userValues = (ArrayList<Object>) userValue;
                                        if (userValues.contains(priorityValue)) {
                                            firstIdealUsers.add(userInfo);
                                            Log.d(TAG, "리스트 object 1순위 이상형있음 " + uid + " " + priorityValue);
                                        }
                                    } else if (priorityValue instanceof ArrayList) {
                                        priorityValues = (ArrayList<Object>) priorityValue;
                                        if (priorityValues.contains(userValue)) {
                                            firstIdealUsers.add(userInfo);
                                            Log.d(TAG, "object list 1순위 이상형있음 " + uid + " " + userValue);
                                        }
                                    } else if (userValue.equals(priorityValue)) {
                                        firstIdealUsers.add(userInfo);
                                        Log.d(TAG, "object object1순위 이상형있음 " + uid + " " + priorityValue);
                                    }

                                }

                            }

                            if (!firstIdealUsers.contains(userInfo)) {

                                //priority2
                                for (Map.Entry<String, Object> priority2 : ideal.getPriority2().entrySet()) {
                                    if (subItem.getKey().equals(priority2.getKey())) {
                                        Object userValue = subItem.getValue();
                                        Object priorityValue = priority2.getValue();
                                        ArrayList<Object> userValues;
                                        ArrayList<Object> priorityValues;
                                        if (userValue instanceof ArrayList && priorityValue instanceof ArrayList) {
                                            userValues = (ArrayList<Object>) userValue;
                                            priorityValues = (ArrayList<Object>) priorityValue;

                                            for (Object value : priorityValues) {
                                                if (userValues.contains(value)) {
                                                    secondIdealUsers.add(userInfo);
                                                    Log.d(TAG, "둘다 리스트 2순위 이상형있음 " + uid + " " + value);
                                                }
                                            }
                                        } else if (userValue instanceof ArrayList) {
                                            userValues = (ArrayList<Object>) userValue;
                                            if (userValues.contains(priorityValue)) {
                                                secondIdealUsers.add(userInfo);
                                                Log.d(TAG, "리스트 object 2순위 이상형있음 " + uid + " " + priorityValue);
                                            }
                                        } else if (priorityValue instanceof ArrayList) {
                                            priorityValues = (ArrayList<Object>) priorityValue;
                                            if (priorityValues.contains(userValue)) {
                                                secondIdealUsers.add(userInfo);
                                                Log.d(TAG, "object list 2순위 이상형있음 " + uid + " " + userValue);
                                            }
                                        } else if (userValue.equals(priorityValue)) {
                                            secondIdealUsers.add(userInfo);
                                            Log.d(TAG, "object object2순위 이상형있음 " + uid + " " + priorityValue);
                                        }

                                    }

                                }


                            }


                            if (!firstIdealUsers.contains(userInfo) && !secondIdealUsers.contains(userInfo)) {

                                //priority3
                                for (Map.Entry<String, Object> priority3 : ideal.getPriority3().entrySet()) {
                                    if (subItem.getKey().equals(priority3.getKey())) {
                                        Object userValue = subItem.getValue();
                                        Object priorityValue = priority3.getValue();
                                        ArrayList<Object> userValues;
                                        ArrayList<Object> priorityValues;
                                        if (userValue instanceof ArrayList && priorityValue instanceof ArrayList) {
                                            userValues = (ArrayList<Object>) userValue;
                                            priorityValues = (ArrayList<Object>) priorityValue;

                                            for (Object value : priorityValues) {
                                                if (userValues.contains(value)) {
                                                    thirdIdealUsers.add(userInfo);
                                                    Log.d(TAG, "둘다 리스트 3순위 이상형있음 " + uid + " " + value);
                                                }
                                            }
                                        } else if (userValue instanceof ArrayList) {
                                            userValues = (ArrayList<Object>) userValue;
                                            if (userValues.contains(priorityValue)) {
                                                thirdIdealUsers.add(userInfo);
                                                Log.d(TAG, "리스트 object 3순위 이상형있음 " + uid + " " + priorityValue);
                                            }
                                        } else if (priorityValue instanceof ArrayList) {
                                            priorityValues = (ArrayList<Object>) priorityValue;
                                            if (priorityValues.contains(userValue)) {
                                                thirdIdealUsers.add(userInfo);
                                                Log.d(TAG, "object list 3순위 이상형있음 " + uid + " " + userValue);
                                            }
                                        } else if (userValue.equals(priorityValue)) {
                                            thirdIdealUsers.add(userInfo);
                                            Log.d(TAG, "object object3순위 이상형있음 " + uid + " " + priorityValue);
                                        }

                                    }


                                }

                            }
*/

                        }

                    }
                }


         /*       for(UserInfo userInfo :firstIdealUsers){
                    if(secondIdealUsers.contains(userInfo)){
                        secondIdealUsers.remove(userInfo);
                    }else if(thirdIdealUsers.contains(userInfo)){
                        thirdIdealUsers.remove(userInfo);
                        thirdIdealUsers.co
                    }
                }
                for(UserInfo userInfo : secondIdealUsers){
                    if(thirdIdealUsers.contains(userInfo)){
                        thirdIdealUsers.remove(userInfo);
                    }
                }*/
                checkIdealPriority(context,firsts,seconds,thirds);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private static void checkIdealPriority(Context context,ArrayList<UserInfo> firsts, ArrayList<UserInfo> seconds, ArrayList<UserInfo> thirds) {

        ArrayList<UserInfo> idealUsers=new ArrayList<>();


        ArrayList<UserInfo > firstIdealUsers=new ArrayList<>();
        ArrayList<UserInfo> secondIdealUsers=new ArrayList<>();
        ArrayList<UserInfo> thirdIdealUsers=new ArrayList<>();

        //2,3순위에서 1순위 중복 제거
        for(UserInfo userInfo:firsts){
            if(seconds.contains(userInfo)&&thirds.contains(userInfo)){
                firstIdealUsers.add(userInfo);
                //123순위 모두 충족하는 userInfo인경우
            }else{
                secondIdealUsers.add(userInfo);
                //1순위만 충족해도 seconds에 포함
            }
            seconds.remove(userInfo);
            thirds.remove(userInfo);
        }
        Log.d(TAG, "checkIdealPriority: "+firstIdealUsers);

        //3순위에서 2순위 중복 제거
        for(UserInfo userInfo:seconds){
            thirds.remove(userInfo);
        }
        thirdIdealUsers.addAll(seconds);
        thirdIdealUsers.addAll(thirds);


        Intent intent = new Intent(context, RecommendMatchActivity.class);
        intent.putExtra("recommendType","이상형 추천");
        intent.putExtra("firstIdealUsers", firstIdealUsers);
        intent.putExtra("secondIdealUsers", secondIdealUsers);
        intent.putExtra("thirdIdealUsers", thirdIdealUsers);
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();

/*

        for(UserInfo userInfo:firstIdealUsers){
            idealUsers=new ArrayList<>();
            if(secondIdealUsers.contains(userInfo)&&thirdIdealUsers.contains(userInfo)){
               idealUsers.
                idealConditionMap.put(1,idealUsers);
                type="first";
                secondIdealUsers.remove(userInfo);
                thirdIdealUsers.remove(userInfo);
            }else if(secondIdealUsers.contains(userInfo)){
                type="second";
                secondIdealUsers.remove(userInfo);
            }else if(thirdIdealUsers.contains(userInfo)){
                type="third";
                thirdIdealUsers.remove(userInfo);
            }else{
                ty

            }
        }

        for(UserInfo userInfo:secondIdealUsers){
            if(thirdIdealUsers.contains(userInfo)){
                type="fourth";
                thirdIdealUsers.remove(userInfo);
            }else{
                type="fifth";
            }
        }
*/

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

/*    public static ArrayList<UserInfo> getAllUserInfo() {   //나를 제외한 모든 유저
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<UserInfo> allUserInfoList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {
                            if (!item.getKey().equals(user.getUid())) {
                                allUserInfoList.add(item.getValue(UserInfo.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return allUserInfoList;
    }*/

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

                Intent intent=new Intent(context, RecommendMatchActivity.class );
                intent.putExtra("recommendType", "MBTI 추천");
                intent.putExtra("recommendUsers",matchingUsers);
                context.startActivity(intent);
                ((AppCompatActivity) context).finish();



            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Fragment_1", "loadPost:onCancelled", databaseError.toException());
            }

        });
    }

    public static Boolean checkProfileOpen() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo = snapshot.getValue(UserInfo.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return true;
    }
}
