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
import android.widget.Toast;

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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator3;

public class RecyclerGetRequestAdapter2 extends RecyclerView.Adapter<RecyclerGetRequestAdapter2.ViewHolder> {
    private static final String TAG = "RecyclerGetRequestAdapter2";

    private Context context;
    private ArrayList<Match> matches;
    private Map<String, Room> myRooms;

    public RecyclerGetRequestAdapter2(Context context, ArrayList<Match> matches) {
        this.context = context;
        this.matches = matches;

        myRooms = new HashMap<>();
        getMyRooms();
    }

    private void getMyRooms() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("rooms").orderByChild("host")
                .equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            myRooms.clear();
                            for (DataSnapshot item : snapshot.getChildren()) {
                                myRooms.put(item.getKey(), item.getValue(Room.class));
                            }
                            Log.d(TAG, "myRoom1111 " + myRooms.size());
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    @NonNull
    @Override
    public RecyclerGetRequestAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_get_request2, parent, false);

        return new RecyclerGetRequestAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Match match = matches.get(position);
        String matchKey = match.getMatch_key();
        String uid = match.getSender_uid();


        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.userInfo = snapshot.getValue(UserInfo.class);

                        Uri imageUri = Uri.parse(holder.userInfo.getPhotoUrl());
                        Glide.with(context).load(imageUri).into(holder.getRequestProfile);
                        if (myRooms.containsKey(matchKey)) {
                            holder.getRequestTxt.setText("[" + myRooms.get(matchKey).getCategory() + "] 방에 새로운 요청이 들어왔어요.");
                        } else {
                            holder.getRequestTxt.setText(holder.userInfo.getName() + "님이 대화를 요청했어요.");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        Log.d(TAG, "onBindViewHolder: " + match);

        holder.getRequestDate2.setText(getRequestTimeString(match.getRequest_date()));


        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserInfoDialog(context, holder.userInfo, match);
                Log.d(TAG, "userInfo" + holder.userInfo);
            }
        });


    }


    private void showUserInfoDialog(Context context, UserInfo userInfo,Match match) {
        String matchKey=match.getMatch_key();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.decision_match_dialog, null);

        ImageView cancelBtn = view.findViewById(R.id.cancel_image_view);
        Button acceptMatchBtn = view.findViewById(R.id.acceptMatchBtn);
        Button refuseMatchBtn= view.findViewById(R.id.refuseMatchBtn);
        TextView decisionMatchTxt = view.findViewById(R.id.decisionMatchTxt);
        ViewPager2 decisionViewPager = view.findViewById(R.id.decisionMatchViewPager);
        decisionViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        CircleIndicator3 indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(decisionViewPager);
        indicator.createIndicators(2, 0);

        RecommendViewPagerAdapter decisionViewPagerAdapter;
        if(myRooms.containsKey(matchKey)) {
            decisionViewPagerAdapter=new RecommendViewPagerAdapter((FragmentActivity) context, userInfo, true);
            decisionMatchTxt.setText("[" + myRooms.get(matchKey).getCategory() + "] " + myRooms.get(matchKey).getTitle());
            decisionViewPager.setAdapter(decisionViewPagerAdapter);
        }else {
            decisionViewPagerAdapter=new RecommendViewPagerAdapter((FragmentActivity) context, userInfo, false);
            decisionMatchTxt.setText(userInfo.getName());
            decisionViewPager.setAdapter(decisionViewPagerAdapter);
        }
        decisionViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator.animatePageSelected(position % 2);

            }
        });


        builder.setView(view);
        AlertDialog dialog = builder.create();
        //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();


        DatabaseReference matchRef = FirebaseDatabase.getInstance().getReference().child("matches");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        acceptMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myRooms.containsKey(matchKey)) {
                    matchRef.child("rooms").child(matchKey).child(userInfo.getUid()).child("approved")
                            .setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    createChatRoom(userInfo);
                                    matches.remove(match);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                }else{
                    matchRef.child("users").child(matchKey).child(userInfo.getUid()).child("approved")
                            .setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    createChatRoom(userInfo);
                                    matches.remove(match);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                }

            }
        });

        refuseMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myRooms.containsKey(matchKey)) {
                    matchRef.child("rooms").child(matchKey).child(userInfo.getUid()).child("approved")
                            .setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    matches.remove(match);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                }else{
                    matchRef.child("users").child(matchKey).child(userInfo.getUid()).child("approved")
                            .setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    matches.remove(match);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                }

            }
        });
    }



    private void createChatRoom(UserInfo userInfo) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("chatRooms");

        Map<String, Boolean> usersMap = new HashMap<>();
        usersMap.put(user.getUid(), true);
        usersMap.put(userInfo.getUid(), true);
        ChatRoom chatRoom = new ChatRoom(usersMap, null);

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
                        }else Toast.makeText(context, "이미 해당 유저와 채팅 방이 있어요.", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }




    private String getRequestTimeString(String lastTimeString) {
        try {
            LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            int messageMonth = Integer.parseInt(lastTimeString.substring(4, 6));
            int messageDate = Integer.parseInt(lastTimeString.substring(6, 8));
            int messageHour = Integer.parseInt(lastTimeString.substring(8, 10));
            int messageMinute = Integer.parseInt(lastTimeString.substring(10, 12));

            String formattedCurrentTimeString = currentTime.format(formatter);
            int currentMonth = Integer.parseInt(formattedCurrentTimeString.substring(4, 6));
            int currentDate = Integer.parseInt(formattedCurrentTimeString.substring(6, 8));
            int currentHour = Integer.parseInt(formattedCurrentTimeString.substring(8, 10));
            int currentMinute = Integer.parseInt(formattedCurrentTimeString.substring(10, 12));

            int monthAgo = currentMonth - messageMonth;
            int dayAgo = currentDate - messageDate;
            int hourAgo = currentHour - messageHour;
            int minuteAgo = currentMinute - messageMinute;

            if (monthAgo > 0)
                return monthAgo + "개월 전";
            else {
                if (dayAgo > 0) {
                    if (dayAgo == 1)
                        return "어제";
                    else
                        return dayAgo + "일 전";
                } else {
                    if (hourAgo > 0)
                        return hourAgo + "시간 전";
                    else {
                        if (minuteAgo > 0)
                            return minuteAgo + "분 전";
                        else
                            return "방금";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }




    @Override
    public int getItemCount() {
        return matches.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private View background;
        private ImageView getRequestProfile;
        private TextView getRequestTxt;
        private TextView getRequestDate2;
        private UserInfo userInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.getRequestBackground);
            getRequestProfile = itemView.findViewById(R.id.getRequestProfile);
            getRequestTxt = itemView.findViewById(R.id.getRequestTxt);
            getRequestDate2 = itemView.findViewById(R.id.getRequestDate2);


        }
    }

}
