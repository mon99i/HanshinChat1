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
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;
import com.example.hanshinchat1.fragment.ShowUserFragment1;
import com.example.hanshinchat1.fragment.ShowUserFragment2;
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

public class RecyclerGetRequestAdapter extends RecyclerView.Adapter<RecyclerGetRequestAdapter.ViewHolder> {
    private ArrayList<String> getRequestUids;
    private ArrayList<UserInfo> getRequestUsers;
    private Context context;
    private static final String TAG = "RecyclerGetRequestAdapter";

    public RecyclerGetRequestAdapter(Context context, ArrayList<String> getRequestUids) {
        super();
        this.context = context;
        this.getRequestUids = getRequestUids;
        getRequestUsers = new ArrayList<>();
        setUpAlUsers();
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
    }

    @NonNull
    @Override
    public RecyclerGetRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_get_request, parent, false);

        return new RecyclerGetRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerGetRequestAdapter.ViewHolder holder, int position) {


        UserInfo userInfo = getRequestUsers.get(position);
        Log.d(TAG, "onBindViewHolder: " + position + " " + getRequestUsers.size() + " " + userInfo);
        Uri imageUri = Uri.parse(userInfo.getPhotoUrl());
        Glide.with(context).load(imageUri).into(holder.getRequestProfile);

        holder.getRequestTxt.setText(userInfo.getName() + "님이 대화를 요청했어요.");
        //holder.getRequestDate.setText

        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserInfoDialog(context, userInfo);
            }
        });

    }

    private void showUserInfoDialog(Context context, UserInfo userInfo) {
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
        DatabaseReference matchRef = FirebaseDatabase.getInstance().getReference().child("matches").child(user.getUid());

        acceptUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void createChatRoom(UserInfo userInfo) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference().child("chatRooms");

        Map<String, Boolean> usersMap = new HashMap<>();
        usersMap.put(user.getUid(), true);
        usersMap.put(userInfo.getUid(), true);
        ChatRoom chatRoom = new ChatRoom(usersMap, null);

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
        });*/

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
                            chatRoomsRef.push().setValue(chatRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
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
