package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator3;

public class RecyclerMatchRoomsAdapter extends RecyclerView.Adapter<RecyclerMatchRoomsAdapter.ViewHolder> {

    private Context context;
    private final static String TAG = "매칭요청실패";
    private ArrayList<String> roomKeyList;
    private ArrayList<Room> roomList;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private AlertDialog dialog;


    private RecyclerView recyclerView;

    public RecyclerMatchRoomsAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        roomList = new ArrayList<>();
        roomKeyList = new ArrayList();

        setUpRooms(null, false);
        this.recyclerView = recyclerView;
    }

    public void setUpRooms(String category, boolean checkBoxChecked) {
        DatabaseReference matchRoomsRef = FirebaseDatabase.getInstance().getReference().child("rooms");
        matchRoomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomList.clear();
                roomKeyList.clear();
                if (category == null) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Room room = item.getValue(Room.class);
                        roomList.add(room);
                        roomKeyList.add(item.getKey());


                    }
                } else {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Room room = item.getValue(Room.class);
                        if (room.getCategory().equals(category)) {
                            roomList.add(room);
                            roomKeyList.add(item.getKey());
                        }

                    }

                }

                Collections.reverse(roomKeyList);
                Collections.reverse(roomList);


                if (checkBoxChecked) {
                    excludeSameDepartment();
                } else {
                    notifyDataSetChanged();
                    recyclerView.scrollToPosition(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void excludeSameDepartment() {
        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo myInfo = snapshot.child(user.getUid()).getValue(UserInfo.class);
                        String myDepartment = myInfo.getDepartment();

                        Map<String, Room> roomMap = new HashMap<>();
                        for (int i = 0; i < roomList.size(); i++) {
                            roomMap.put(roomKeyList.get(i), roomList.get(i));
                        }
                        for (Map.Entry<String, Room> map : roomMap.entrySet()) {
                            String hostUid = map.getValue().getHost();
                            String hostDepartment = snapshot.child(hostUid).getValue(UserInfo.class).getDepartment();
                            if (myDepartment.equals(hostDepartment)) {
                                roomKeyList.remove(map.getKey());
                                roomList.remove(map.getValue());
                            }
                        }


                        notifyDataSetChanged();
                        recyclerView.scrollToPosition(0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


    @NonNull
    @Override
    public RecyclerMatchRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_matchroom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerMatchRoomsAdapter.ViewHolder holder, int position) {
        String roomKey = roomKeyList.get(position);
        Room room = roomList.get(position);

        holder.txt_roomTitle.setText(room.getTitle());

        String categoryTxt = room.getCategory();
        int imageResourceId;

        if ("미팅".equals(categoryTxt)) {
            imageResourceId = R.drawable.icon1;
        } else if ("과팅".equals(categoryTxt)) {
            imageResourceId = R.drawable.icon2;
        } else if ("밥팅".equals(categoryTxt)) {
            imageResourceId = R.drawable.icon3;
        } else {
            imageResourceId = R.drawable.icon4;
        }
        holder.roomCategory.setImageResource(imageResourceId);
        holder.txt_roomGender.setText(room.getGender());
        holder.txt_roomDepartment.setText(room.getDepartment());
        holder.txt_roomMember.setText(room.getNum());

        holder.room_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserDialog(roomKey, room);

            }
        });

        if (user != null && room.getHost().equals(user.getUid())) {
            holder.roomSetting.setVisibility(View.VISIBLE);
            holder.roomSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSettingDialog(roomKey, room);
                }
            });
        } else {
            holder.roomSetting.setVisibility(View.GONE);
        }

        holder.roomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingDialog(roomKey, room);
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }


    private void showSettingDialog(String roomkey, Room room) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomRef = database.getReference("rooms");

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.room_edit_dialog, null);
        ImageView cancelBtn = view.findViewById(R.id.make_room_cancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view);
        AlertDialog alertDialog = builder.show();

        Button deleteRoomBtn = alertDialog.findViewById(R.id.deleteRoomBtn);
        deleteRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomRef.child(roomkey).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if (error == null) {
                            alertDialog.dismiss();
                            notifyDataSetChanged();
                            Intent intent = new Intent(context, MainMenuActivity.class);
                            intent.putExtra("show_fragment", 2);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


    private void showUserDialog(String roomKey, Room room) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_match_dialog, null);

        ViewPager2 showMatchViewPager = view.findViewById(R.id.showMatchViewPager);
        ImageView cancelBtn = view.findViewById(R.id.cancel_image_view);
        Button matchBtn = view.findViewById(R.id.requestMatchBtn);
        matchBtn.setText("매칭신청");
        TextView roomTitle = view.findViewById(R.id.showMatchTxt);

        CircleIndicator3 indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(showMatchViewPager);
        indicator.createIndicators(2, 0);
        showMatchViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


        roomTitle.setText("[" + room.getCategory() + "] " + room.getTitle());

        FirebaseDatabase.getInstance().getReference().child("users").child(room.getHost())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo hostUserInfo = snapshot.getValue(UserInfo.class);

                            Log.d("RoomActivity", hostUserInfo.toString());

                            showMatchViewPager.setAdapter(new RecommendViewPagerAdapter((FragmentActivity) context, hostUserInfo, true));
                            showMatchViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();


        matchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestMatch(roomKey, room);

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



    }

    private void requestMatch(String roomKey, Room room) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String hostUid = room.getHost();


//        if (!currentUid.equals(hostUid) && matchRoom != null) { //방만든애가 자기가 만든 방에 요청 안되게

        if (!user.getUid().equals(hostUid)) {
            DatabaseReference myMatchRef = FirebaseDatabase.getInstance().getReference().child("matches")
                    .child("rooms").child(roomKey).child(user.getUid());
            myMatchRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(context, "이미 요청한 방입니다", Toast.LENGTH_SHORT).show();
                    } else {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
                        String currentTimeString = currentTime.format(dateTimeFormatter);
                        Match match = new Match(true, currentTimeString, null);
                        myMatchRef.setValue(match);
                        Toast.makeText(context, "요청 완료", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else Toast.makeText(context, "내가 만든방입니다.", Toast.LENGTH_SHORT).show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout room_background;
        TextView txt_roomTitle;
        ImageView roomCategory;
        TextView txt_roomGender;
        TextView txt_roomMember;
        TextView txt_roomDepartment;
        ImageView roomSetting;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_roomTitle = itemView.findViewById(R.id.txt_roomTitle);
            roomCategory = itemView.findViewById(R.id.roomCategory);
            txt_roomMember = itemView.findViewById(R.id.txt_roomMember);
            txt_roomGender = itemView.findViewById(R.id.txt_roomGender);
            txt_roomDepartment = itemView.findViewById(R.id.txt_roomDepartment);

            roomSetting = itemView.findViewById(R.id.roomSetting);
            room_background = itemView.findViewById(R.id.room_background);
        }

    }
}
