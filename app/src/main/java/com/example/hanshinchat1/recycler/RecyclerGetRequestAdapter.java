package com.example.hanshinchat1.recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.ChatRoom;
import com.example.hanshinchat1.ChattingActivity;
import com.example.hanshinchat1.Match;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.Room;
import com.example.hanshinchat1.UserInfo;
import com.example.hanshinchat1.viewpager.RecommendViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator3;

public class RecyclerGetRequestAdapter extends RecyclerView.Adapter<RecyclerGetRequestAdapter.ViewHolder> {
    private static final String TAG = "RecyclerGetRequestAdapter";

    private Context context;
    private Map<String, Room> myRoom;
    private ArrayList<UserInfo> getRequestUsers;

    private ArrayList<String>  getRequestUids;
    private ArrayList<String>  getMatchKeys;

    private ArrayList<Match> getMatches;


    private ArrayList<Map.Entry<String,ArrayList<Match>>> dateMatchList;
    private Map<String,ArrayList<Match>> dateMatchMap;

    public RecyclerGetRequestAdapter(Context context,ArrayList<Match> getMatches) {
        super();
        this.context = context;

        dateMatchMap=new LinkedHashMap<>();
        setUpAllMatch(getMatches);

    }

    private void setUpAllMatch(ArrayList<Match> getMatches){
      /*  ArrayList<Match> sortedMatches=new ArrayList<>();
        sortedMatches.addAll(getMatches);*/

        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


        ArrayList<LocalDateTime> dateTimes = new ArrayList<>();
        for (Match match : getMatches) {
            String dateString=match.getRequest_date();
            LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
            dateTimes.add(dateTime);
        }

        // 역순 정렬 (최신 날짜 및 시간부터)
        Collections.sort(dateTimes, Collections.reverseOrder());


        //최신순으로 정렬
        Collections.sort(getMatches, new Comparator<Match>() {
                    @Override
                    public int compare(Match o1, Match o2) {
                        String date1 = o1.getRequest_date();
                        String date2 = o2.getRequest_date();


                        LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
                        LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
                        return dateTime2.compareTo(dateTime1);
                    }
                });



        Map < String, ArrayList < Match >> dateMatchMap = new LinkedHashMap<>();
        ArrayList<Match> todayMatches=new ArrayList<>();
        ArrayList<Match> oneWeekAgoMatches=new ArrayList<>();
        ArrayList<Match> twoWeekAgoMatches=new ArrayList<>();

        for (Match match : getMatches) {
    /*        int index = getMatches.indexOf(match);    //기존의 getMatches의 match Index
            if (index >= 0) {
                sortedRequestUids.add(getRequestUids.get(index));
                sortedMatchKeys.add(getMatchKeys.get(index));
                senderMap.put(getRequestUids.get(index),match);
                allSortedMap.put(getMatchKeys.get(index),new HashMap<>(getRequestUids.get(index),match);



            }*/
            String timeString = match.getRequest_date(); // Match 객체에서 시간을 가져옵니다.
            LocalDateTime requestTime = LocalDateTime.parse(timeString, formatter);    // 문자열을 LocalDateTime으로 파싱

            if (requestTime.toLocalDate().isEqual(currentTime.toLocalDate())) {
                todayMatches.add(match);
                dateMatchMap.put("오늘",todayMatches);
            }else if(requestTime.isAfter(currentTime.minusDays(7)) && requestTime.isBefore(currentTime)){
                oneWeekAgoMatches.add(match);
                dateMatchMap.put("지난 1주일", oneWeekAgoMatches);
            }else if (requestTime.isAfter(currentTime.minusDays(14)) && requestTime.isBefore(currentTime.minusDays(7))) {
                twoWeekAgoMatches.add(match);
                dateMatchMap.put("지난 2주일", twoWeekAgoMatches);
            }

       /*     if(requestTime.isEqual(currentTime))
            for (LocalDateTime dateTime : dateTimes) {
                if (dateTime.isEqual(today)) {
                    todayData.add(dateTime);
                } else if (dateTime.isAfter(today.minusDays(7)) && dateTime.isBefore(today)) {
                    lastWeekData.add(dateTime);
                } else if (dateTime.isAfter(today.minusDays(14)) && dateTime.isBefore(today.minusDays(7))) {
                    lastTwoWeeksData.add(dateTime);
                }
            }*/

          /*  Duration duration = Duration.between(requestTime, currentTime);

            // 현재 시간과 요청 시간을 비교하여 오늘 날짜에 해당하는 데이터만 추가
            if (requestTime.toLocalDate().isEqual(currentTime.toLocalDate())) {
                todayMatches.add(match);
                dateMatchMap.put("오늘",todayMatches);
            }else if(duration.toDays() <= 7) {
                oneWeekAgoMatches.add(match);
                dateMatchMap.put("지난 1주일", oneWeekAgoMatches);
            }else if(duration.toDays() <= 14) {
                twoWeekAgoMatches.add(match);
                dateMatchMap.put("지난 2주일", twoWeekAgoMatches);
            }*/

        }

        dateMatchList=new ArrayList<>(dateMatchMap.entrySet());
        notifyDataSetChanged();




       /* Map<String,Map<String,Match>> matchMap=new LinkedHashMap<>();
        for(int i=0;i<sortedMatches.size();i++){
           String timeString = sortedMatches.get(i).getRequest_date(); // Match 객체에서 시간을 가져옵니다.
           // 문자열을 LocalDateTime으로 파싱
           LocalDateTime requestTime = LocalDateTime.parse(timeString, dateTimeFormatter);
           // 현재 시간과 요청 시간을 비교하여 오늘 날짜에 해당하는 데이터만 추가
           if (requestTime.toLocalDate().isEqual(currentTime.toLocalDate())) {
               sortedMatchMap.put("오늘",new LinkedHashMap<>(sortedMatchKeys.get(i),sortedRequestUids.ge))
           }
       }
*/


/*
        ArrayList<Map.Entry<String, Match>> entryList = new ArrayList<>(allMatchMap.entrySet());
        for(Map.Entry<String,Map<String,Match>> map :allMatchMap.entrySet()){
            for(Map.Entry<String,Match> subMap:map.getValue().entrySet()){
                String matchKey=subMap.getKey();
                Match match=subMap.getValue();


                Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                        return entry1.getValue().compareTo(entry2.getValue()); // 값으로 정렬
                    }
                });
            }
        }
*/




     /*   DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime threeWeeksAgo = currentTime.minusWeeks(3);
        String currentTimeString = currentTime.format(dateTimeFormatter);
        String threeWeeksAgoString = threeWeeksAgo.format(dateTimeFormatter);
        */

    }

/*    private void setUpAlUsers() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getRequestUsers.clear();
                for (String uid : getRequestUids) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        if (uid.equals(item.getKey())) {
                            getRequestUsers.add(item.getValue(UserInfo.class));
                        }
                    }
                }
                notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("rooms").orderByChild("host")
                .equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myRoom.clear();
                        for(DataSnapshot item:snapshot.getChildren()){
                            myRoom.put(item.getKey(),item.getValue(Room.class));
                        }
                        notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setUpAlUsers(ArrayList<String> getUserRequestUids, HashMap<String, ArrayList<String>> getRoomRequestUids) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getRequestUsers.clear();
                for (String uid : getUserRequestUids) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        if (uid.equals(item.getKey())) {
                            getRequestUsers.add(item.getValue(UserInfo.class));
                        }
                    }
                }
                notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/


    @NonNull
    @Override
    public RecyclerGetRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_get_request, parent, false);

        return new RecyclerGetRequestAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerGetRequestAdapter.ViewHolder holder, int position) {

        //ArrayList<Map.Entry<String, ArrayList<Match>>> dateMatchList=new ArrayList<>(dateMatchMap.entrySet());
        String date=dateMatchList.get(position).getKey();
        ArrayList<Match> matches=dateMatchList.get(position).getValue();

        holder.getRequestDate.setText(date);

        Log.d(TAG, "dateMatchList.size(): "+dateMatchList.size());
        Log.d(TAG, "matches : "+matches);
        holder.recyclerView.setAdapter(new RecyclerGetRequestAdapter2(context,matches));




/*
        UserInfo userInfo = getRequestUsers.get(position);
        String matchKey= getMatchKeys.get(position);
        Match match=getMatches.get(position);

        Log.d(TAG, "onBindViewHolder: "+matchKey+myRoom);
        if(myRoom.containsKey(matchKey)){
            holder.getRequestTxt.setText("["+myRoom.get(matchKey).getCategory()+"] 방에 새로운 매칭이 성사됬어요");
        }else{
            holder.getRequestTxt.setText(userInfo.getName() + "님이 대화를 요청했어요.");
        }

        holder.getRequestDate.setText(match.getRequest_date());


        Uri imageUri = Uri.parse(userInfo.getPhotoUrl());
        Glide.with(context).load(imageUri).into(holder.getRequestProfile);


        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserInfoDialog(context, userInfo,matchKey);
            }
        });*/

    }

    private void showUserInfoDialog(Context context, UserInfo userInfo,String matchKey) {
        if(myRoom.containsKey(matchKey)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.decision_match_dialog, null);

            ViewPager2 recommendViewPager=view.findViewById(R.id.decisionMatchViewPager);
            Button acceptBtn=view.findViewById(R.id.acceptMatchBtn);
            Button refuseBtn=view.findViewById(R.id.refuseMatchBtn);
            TextView matchTxt=view.findViewById(R.id.decisionMatchTxt);

            CircleIndicator3 indicator = view.findViewById(R.id.indicator);
            indicator.setViewPager(recommendViewPager);
            indicator.createIndicators(2, 0);
            recommendViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


            matchTxt.setText("[" + myRoom.get(matchKey).getCategory() + "] " + myRoom.get(matchKey).getTitle());

            FirebaseDatabase.getInstance().getReference().child("users").child(myRoom.get(matchKey).getHost())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                UserInfo hostUserInfo = snapshot.getValue(UserInfo.class);

                                Log.d("RoomActivity", hostUserInfo.toString());

                                recommendViewPager.setAdapter(new RecommendViewPagerAdapter((FragmentActivity) context, hostUserInfo, false));
                                recommendViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                    //                        @Override
//                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                            if (positionOffsetPixels == 0) {
//                                recommendViewPager.setCurrentItem(position);
//                            }
//                        }
//
                                    @Override
                                    public void onPageSelected(int position) {
                                        super.onPageSelected(position);
                                        indicator.animatePageSelected(position % 2);

                                    }
                                });

                            } else {
                                Log.d("RoomActivity", "아무것도 없나봐...");

                                // 해당 MatchRoom 데이터가 없을 경우 처리
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            builder.setView(view);
            AlertDialog dialog = builder.create();
            //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
            dialog.show();


        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.decision_match_dialog, null);

            Button acceptMatchBtn = view.findViewById(R.id.acceptMatchBtn);
            Button refuseMatchBtn= view.findViewById(R.id.refuseMatchBtn);
            TextView decisionUserName = view.findViewById(R.id.decisionMatchTxt);
            ViewPager2 decisionViewPager = view.findViewById(R.id.decisionMatchViewPager);


            decisionUserName.setText(userInfo.getName());
            RecommendViewPagerAdapter adapter = new RecommendViewPagerAdapter((FragmentActivity) context, userInfo, true);
            decisionViewPager.setAdapter(adapter);

            builder.setView(view);
            AlertDialog dialog = builder.create();
            //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
            dialog.show();


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference matchRef = FirebaseDatabase.getInstance().getReference().child("matches").child("users")
                    .child(user.getUid()).child(userInfo.getUid());

            acceptMatchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchRef.child("approved").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            createChatRoom(userInfo);
                            getRequestUsers.remove(userInfo);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
      matchRef.child(userInfo.getUid()).child("approved").setValue(true)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    createChatRoom(userInfo);
                                    getRequestUsers.remove(userInfo);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });



                }
            });

            refuseMatchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchRef.child(userInfo.getUid()).child("approved").setValue(false)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    getRequestUsers.remove(userInfo);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                }
            });
        }

    }

    private void createChatRoom(UserInfo userInfo) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("chatRooms");

        Map<String, Boolean> usersMap = new HashMap<>();
        usersMap.put(user.getUid(), true);
        usersMap.put(userInfo.getUid(), true);
        ChatRoom chatRoom = new ChatRoom(usersMap, null);

    /*   chatRoomsRef.push().setValue(chatRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(context, ChattingActivity.class);
                                    intent.putExtra("ChatRoom", chatRoom); // 채팅방 정보
                                    intent.putExtra("Opponent", userInfo); // 상대방 정보
                                    intent.putExtra("ChatRoomKey", ""); // 채팅방 키
                                    context.startActivity(intent);
                                    ((AppCompatActivity) context).finish();
            }
        });
*/

        //굳이 필요한지 나중에 확인필요
        chatRoomsRef.orderByChild("users/" + user.getUid()).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean chatRoomExists = false;
                        //해당 유저와의 채팅방이 이미 있는지 확인
                        for (DataSnapshot item : snapshot.getChildren()) {
                            Map<String, Boolean> chatUsers = item.getValue(ChatRoom.class).getUsers();
                            if (chatUsers.containsKey(userInfo.getUid())) {   //이미 상대와의 채팅방이 있는경우
                                chatRoomExists = true;
                            }
                        }
                        //해당 유저와의 uid와의 채팅방 없는경우 새로 생성
                        if (chatRoomExists == false) {
                            DatabaseReference newChatRoomRef=chatRoomsRef.push();
                            String chatRoomKey= newChatRoomRef.getKey();
                            newChatRoomRef.setValue(chatRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(context, ChattingActivity.class);
                                    intent.putExtra("ChatRoom", chatRoom); // 채팅방 정보
                                    intent.putExtra("Opponent", userInfo); // 상대방 정보
                                    intent.putExtra("ChatRoomKey", chatRoomKey); // 채팅방 키
                                    context.startActivity(intent);
                                    ((AppCompatActivity) context).finish();
                                }
                            });
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    @Override
    public int getItemCount() {
        return dateMatchList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView getRequestDate;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.recycler_get_request2);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            getRequestDate = itemView.findViewById(R.id.getRequestDate);


        }
    }
}





/*
package com.example.hanshinchat1.recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.ChatRoom;
import com.example.hanshinchat1.ChattingActivity;
import com.example.hanshinchat1.Match;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.Room;
import com.example.hanshinchat1.UserInfo;
import com.example.hanshinchat1.viewpager.RecommendViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator3;

public class RecyclerGetRequestAdapter extends RecyclerView.Adapter<RecyclerGetRequestAdapter.ViewHolder> {


    private Map<String, Room> myRoom;
    private ArrayList<UserInfo> getRequestUsers;

    private ArrayList<String>  getRequestUids;
    private ArrayList<String>  getMatchKeys;
    private ArrayList<Match> getMatches;


    private Context context;
    private static final String TAG = "RecyclerGetRequestAdapter";

*/
/*    public RecyclerGetRequestAdapter(Context context, ArrayList<String> getRequestUids) {
        super();
        this.context = context;
        this.getUserRequestUids = getUserRequestUids;

        setUpAlUsers();
    }*//*

    public RecyclerGetRequestAdapter(Context context, ArrayList<String> getRequestUids,ArrayList<String> getMatchKeys,ArrayList<Match> getMatches) {
        super();
        this.context = context;

        myRoom=new HashMap<>();
        getRequestUsers=new ArrayList<>();


        this.getRequestUids=getRequestUids;
        this.getMatchKeys=getMatchKeys;
        this.getMatches=getMatches;

        setUpAlUsers();
       */
/* ArrayList<Map.Entry<String, Match>> entryList = new ArrayList<>(map.entrySet());
        if(entryList.get(position).getKey()==myRoomKey)
            holder
        for (Map.Entry<String, Match> entry : entryList) {
            String key = entry.getKey();
            Match value = entry.getValue();
            // key와 value를 처리합니다.
        }*//*

    }

    private void setUpAlUsers() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getRequestUsers.clear();
                for (String uid : getRequestUids) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        if (uid.equals(item.getKey())) {
                            getRequestUsers.add(item.getValue(UserInfo.class));
                        }
                    }
                }
                notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("rooms").orderByChild("host")
                .equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRoom.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    myRoom.put(item.getKey(),item.getValue(Room.class));
                }
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUpAlUsers(ArrayList<String> getUserRequestUids, HashMap<String, ArrayList<String>> getRoomRequestUids) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getRequestUsers.clear();
                for (String uid : getUserRequestUids) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        if (uid.equals(item.getKey())) {
                            getRequestUsers.add(item.getValue(UserInfo.class));
                        }
                    }
                }
                notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

*/
/*    private void setUpAlUsers() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getRequestUsers.clear();
                for (String uid : getRequestUids) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        if (uid.equals(item.getKey())) {
                            getRequestUsers.add(item.getValue(UserInfo.class));
                        }
                    }
                }
                notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*//*


    @NonNull
    @Override
    public RecyclerGetRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_get_request, parent, false);

        return new RecyclerGetRequestAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerGetRequestAdapter.ViewHolder holder, int position) {
        UserInfo userInfo = getRequestUsers.get(position);
        String matchKey= getMatchKeys.get(position);
        Match match=getMatches.get(position);

        Log.d(TAG, "onBindViewHolder: "+matchKey+myRoom);
        if(myRoom.containsKey(matchKey)){
            holder.getRequestTxt.setText("["+myRoom.get(matchKey).getCategory()+"] 방에 새로운 매칭이 성사됬어요");
        }else{
            holder.getRequestTxt.setText(userInfo.getName() + "님이 대화를 요청했어요.");
        }

        holder.getRequestDate.setText(match.getRequest_date());


        Uri imageUri = Uri.parse(userInfo.getPhotoUrl());
        Glide.with(context).load(imageUri).into(holder.getRequestProfile);


        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserInfoDialog(context, userInfo,matchKey);
            }
        });

    }

    private void showUserInfoDialog(Context context, UserInfo userInfo,String matchKey) {
        if(myRoom.containsKey(matchKey)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recommend_room_user_dialog, null);

            ViewPager2 recommendViewPager=view.findViewById(R.id.decisionViewPager);
            Button matchBtn=view.findViewById(R.id.matchBtn);
            TextView roomTitle=view.findViewById(R.id.roomTitle);

            CircleIndicator3 indicator = view.findViewById(R.id.indicator);
            indicator.setViewPager(recommendViewPager);
            indicator.createIndicators(2, 0);
            recommendViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


            roomTitle.setText("[" + myRoom.get(matchKey).getCategory() + "] " + myRoom.get(matchKey).getTitle());

            FirebaseDatabase.getInstance().getReference().child("users").child(myRoom.get(matchKey).getHost())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                UserInfo hostUserInfo = snapshot.getValue(UserInfo.class);

                                Log.d("RoomActivity", hostUserInfo.toString());

                                recommendViewPager.setAdapter(new RecommendViewPagerAdapter((FragmentActivity) context, hostUserInfo, false));
                                recommendViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                    //                        @Override
//                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                            if (positionOffsetPixels == 0) {
//                                recommendViewPager.setCurrentItem(position);
//                            }
//                        }
//
                                    @Override
                                    public void onPageSelected(int position) {
                                        super.onPageSelected(position);
                                        indicator.animatePageSelected(position % 2);

                                    }
                                });

                            } else {
                                Log.d("RoomActivity", "아무것도 없나봐...");

                                // 해당 MatchRoom 데이터가 없을 경우 처리
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            builder.setView(view);
            AlertDialog dialog = builder.create();
            //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
            dialog.show();


        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.decision_user_dialog, null);

            Button acceptUserBtn = view.findViewById(R.id.acceptUserBtn);
            Button refuseUserBtn = view.findViewById(R.id.refuseUserBtn);
            TextView decisionUserName = view.findViewById(R.id.decisionUserName);
            ViewPager2 decisionViewPager = view.findViewById(R.id.decisionViewPager);


            decisionUserName.setText(userInfo.getName());
            RecommendViewPagerAdapter adapter = new RecommendViewPagerAdapter((FragmentActivity) context, userInfo, true);
            decisionViewPager.setAdapter(adapter);

            builder.setView(view);
            AlertDialog dialog = builder.create();
            //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
            dialog.show();


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference matchRef = FirebaseDatabase.getInstance().getReference().child("matches").child("users")
                            .child(user.getUid()).child(userInfo.getUid());

            acceptUserBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchRef.child("approved").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            createChatRoom(userInfo);
                            getRequestUsers.remove(userInfo);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
              */
/*      matchRef.child(userInfo.getUid()).child("approved").setValue(true)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    createChatRoom(userInfo);
                                    getRequestUsers.remove(userInfo);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });*//*



                }
            });

            refuseUserBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchRef.child(userInfo.getUid()).child("approved").setValue(false)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    getRequestUsers.remove(userInfo);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                }
            });
        }

    }

    private void createChatRoom(UserInfo userInfo) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("chatRooms");

        Map<String, Boolean> usersMap = new HashMap<>();
        usersMap.put(user.getUid(), true);
        usersMap.put(userInfo.getUid(), true);
        ChatRoom chatRoom = new ChatRoom(usersMap, null);

 */
/*       chatRoomsRef.push().setValue(chatRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(context, ChattingActivity.class);
                                    intent.putExtra("ChatRoom", chatRoom); // 채팅방 정보
                                    intent.putExtra("Opponent", opponent); // 상대방 정보
                                    intent.putExtra("ChatRoomKey", ""); // 채팅방 키
                                    context.startActivity(intent);
                                    ((AppCompatActivity) context).finish();
            }
        });*//*


        //굳이 필요한지 나중에 확인필요
        chatRoomsRef.orderByChild("users/" + user.getUid()).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean chatRoomExists = false;
                        //해당 유저와의 채팅방이 이미 있는지 확인
                        for (DataSnapshot item : snapshot.getChildren()) {
                            Map<String, Boolean> chatUsers = item.getValue(ChatRoom.class).getUsers();
                            if (chatUsers.containsKey(userInfo.getUid())) {   //이미 상대와의 채팅방이 있는경우
                                chatRoomExists = true;
                            }
                        }
                        //해당 유저와의 uid와의 채팅방 없는경우 새로 생성
                        if (chatRoomExists == false) {
                            DatabaseReference newChatRoomRef=chatRoomsRef.push();
                            String chatRoomKey= newChatRoomRef.getKey();
                            newChatRoomRef.setValue(chatRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(context, ChattingActivity.class);
                                    intent.putExtra("ChatRoom", chatRoom); // 채팅방 정보
                                    intent.putExtra("Opponent", userInfo); // 상대방 정보
                                    intent.putExtra("ChatRoomKey", chatRoomKey); // 채팅방 키
                                    context.startActivity(intent);
                                    ((AppCompatActivity) context).finish();
                                }
                            });
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    @Override
    public int getItemCount() {
        return getRequestUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private View background;
        private ImageView getRequestProfile;
        private TextView getRequestTxt;
        private TextView getRequestDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.getRequestBackground);
            getRequestProfile = itemView.findViewById(R.id.getRequestProfile);
            getRequestTxt = itemView.findViewById(R.id.getRequestTxt);
            getRequestDate = itemView.findViewById(R.id.getRequestDate);


        }
    }
}
*/
