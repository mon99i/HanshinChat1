package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Context;
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
//import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hanshinchat1.viewpager.RecommendViewPagerAdapter;
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
    private Map<String,Room> roomMap;

    private ArrayList<MatchRoom> filteredList;
    private boolean isDepartmentFilterEnabled = false;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String selectedCategory;
    private AlertDialog dialog;

    private UserInfo userInfo;

    private  RecyclerView recyclerView;
    public RecyclerMatchRoomsAdapter(Context context) {
        this.context = context;
        roomList = new ArrayList<>();
        roomKeyList = new ArrayList();
        //filteredList = new ArrayList<>();
        recyclerView = ((RoomActivity) context).findViewById(R.id.recycler_matchRooms);
        setUpRooms(null,false);
        //loadAllMatchRooms();
    }
    public void setUpRooms(String category,boolean checkBoxChecked) {
        DatabaseReference matchRoomsRef = FirebaseDatabase.getInstance().getReference().child("rooms");
        matchRoomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomList.clear();
                roomKeyList.clear();
                if(category==null){
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Room room = item.getValue(Room.class);
                        roomList.add(room);
                        roomKeyList.add(item.getKey());


                    }
                }else {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Room room = item.getValue(Room.class);
                        if(room.getCategory().equals(category)){
                            roomList.add(room);
                            roomKeyList.add(item.getKey());
                        }

                    }

                }

                Collections.reverse(roomKeyList);
                Collections.reverse(roomList);


                if(checkBoxChecked){
                    excludeSameDepartment();
                }else{
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
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo userInfo=snapshot.getValue(UserInfo.class);
                        Map<String,Room> roomMap=new HashMap<>();
                        for(int i=0;i<roomList.size();i++){
                            roomMap.put(roomKeyList.get(i),roomList.get(i));
                        }
                        for(Map.Entry<String,Room> map:roomMap.entrySet()){
                            String department=map.getValue().getDepartment();
                            if(department!=null&&department.equals(userInfo.getDepartment())){
                                roomKeyList.remove(map.getKey());
                                roomList.remove(map.getValue());
                            }
                        }
                           /*  for (int i = roomList.size() - 1; i >= 0; i--) {
                            RoomInfo roomInfo = roomList.get(i).getRoomInfo();
                            Log.d(TAG, "onDataChange: " + roomInfo.getDepartment());
                            if (roomInfo.getDepartment() != null) {
                                Log.d(TAG, "onDataChange: " + roomInfo.getTitle() + " " + roomInfo.getDepartment());
                                roomKeyList.remove(i);
                                roomList.remove(i);
                            }
                        }*/

                        notifyDataSetChanged();
                        recyclerView.scrollToPosition(0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }



   /* public void loadAllMatchRooms() {
        DatabaseReference matchRoomsRef = FirebaseDatabase.getInstance().getReference().child("matchRooms");
        matchRoomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomList.clear();
                roomKeyList.clear();
                filteredList.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    MatchRoom matchRoom = item.getValue(MatchRoom.class);
                    if (matchRoom != null) {
                        matchRoomsList.add(matchRoom);
                        matchKeyList.add(item.getKey());
                    }
                }
                Collections.reverse(matchKeyList);
                Collections.reverse(matchRoomsList);


                // 카테고리 적용
                if (selectedCategory != null) {
                    filterMatchRoomsByCategory(selectedCategory);
                }

                // Department 필터 적용
                if (isDepartmentFilterEnabled!=false) {
                    compareDepartment(userInfo);
                } else {
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
*/

        /*//다시 만나지않기, 같은과 만나지않기, 등 필터링 구현예시
        FirebaseDatabase.getInstance().getReference().child("matchRooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               *//* for(DataSnapshot item:snapshot.getChildren()){
                    for(DataSnapshot subItem:item.child("matchInfo").getChildren()){
                        if(subItem.getValue(MatchInfo.class))
                    }
                }*//*

                matchRoomsList.clear();
                matchKeyList.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    Map<String,MatchInfo> matchUsers=item.getValue(MatchRoom.class).getMatchInfo();
                    String hostUid=item.getValue(MatchRoom.class).getRoomInfo().getHost();
                    if(matchUsers.containsKey(user.getUid())){
                       alreadyMatchedUids.add(hostUid);
                    }
                    else{
                        matchRoomsList.add(item.getValue(MatchRoom.class));
                        matchKeyList.add(item.getKey());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



    @NonNull
    @Override
    public RecyclerMatchRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_matchroom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerMatchRoomsAdapter.ViewHolder holder, int position) {
        String roomKey=roomKeyList.get(position);
        Room room = roomList.get(position);
        //final int currentPosition = holder.getAdapterPosition();
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
                showRecommendUserDialog(roomKey,room);
             /*   int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    MatchRoom currentMatchRoom = roomList.get(currentPosition);
                    showRecommendUserDialog(currentPosition);

//                showRecommendUserDialog(matchRoomsList.get(currentPosition));

                    Log.d("RoomActivity", matchRoom.getRoomInfo().getHost());

                }*/
            }
        });

    }

   /* public void setSelectedCategory(String category) {
        selectedCategory = category;

        // 방 목록 다시 로드
        loadAllMatchRooms();
    }

    public void filterMatchRoomsByCategory(String category) {
        if (category == null) {
            return;
        }

        filteredList = new ArrayList<>();

        for (MatchRoom matchRoom : matchRoomsList) {
            if (matchRoom.getRoomInfo().getCategory().equals(category)) {
                filteredList.add(matchRoom);
            }
        }

        matchRoomsList.clear();
        matchRoomsList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public void removeMatchRoomsByCategory(String category) {
        for (MatchRoom matchRoom : matchRoomsList) {
            if (matchRoom.getRoomInfo().getCategory().equals(category)) {
                filteredList.add(matchRoom);
            }
        }
        matchRoomsList.remove(filteredList);
        notifyDataSetChanged();
    }

    public void removeMatchRoomsByDepartment() {
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo userInfo=snapshot.getValue(UserInfo.class);
                        removeCompareDepartment(userInfo);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    private void removeCompareDepartment(UserInfo userInfo) {

        for (MatchRoom matchRoom : roomList) {
            String department = matchRoom.getRoomInfo().getDepartment();
            if (department == null && department.equals(userInfo.getDepartment())) {
                filteredList.add(matchRoom);
            }
        }
        roomList.removeAll(filteredList);
        notifyDataSetChanged();
    }
  *//*  public void setDepartmentFilterEnabled(boolean enabled) {
        isDepartmentFilterEnabled = enabled;

        if (enabled) {
            compareDepartment(userInfo);
        } else {
            filteredList.clear();
            filteredList.addAll(matchRoomsList);
            notifyDataSetChanged();
        }
    }
    *//*
    public void filterMatchRoomsByDepartment() {
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo userInfo=snapshot.getValue(UserInfo.class);
                        compareDepartment(userInfo);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }*/
/*    private void compareDepartment(UserInfo userInfo) {

        filteredList = new ArrayList<>();

        for (MatchRoom matchRoom : matchRoomsList) {
            String department = matchRoom.getRoomInfo().getDepartment();
            if (department != null && !department.equals(userInfo.getDepartment())) {
                filteredList.add(matchRoom);
            }
        }

        matchRoomsList.clear();
        matchRoomsList.addAll(filteredList);
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        return roomList.size();
    }


/*

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
*/


    private void showRecommendUserDialog(String roomKey,Room room){
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



        roomTitle.setText("[" + room.getCategory() + "] " + room.getTitle());

        FirebaseDatabase.getInstance().getReference().child("users").child(room.getHost())
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
        dialog = builder.create();
        //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();


        matchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int adapterPosition = getAdapterPosition(); // 현재 아이템의 위치를 가져옵니다.
//                if (adapterPosition != RecyclerView.NO_POSITION) {
//                    MatchRoom matchRoom = matchRoomsList.get(adapterPosition);
//                    requestMatch(matchRoom); // 선택한 MatchRoom 객체를 전달
//                }
//                requestMatch(recommendViewPager.getVerticalScrollbarPosition());
//                if (currentPosition != RecyclerView.NO_POSITION) {
                    requestMatch(roomKey,room);
//                }
            }
        });

    }

    private void requestMatch(String roomKey,Room room) {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String hostUid =room.getHost();

//        DatabaseReference matchInfoRef = FirebaseDatabase.getInstance().getReference().child("matchRooms").child(matchKey)
//                .child("matchInfo").child(currentUid);

//        String myUid = FBAuth.getUid();
//        String writerUid = dataModel.getUid();

//        if(myUid.equals(writerUid))
//        if (!currentUid.equals(hostUid) && matchRoom != null) { //방만든애가 자기가 만든 방에 요청 안되게
        if (!user.getUid().equals(hostUid)) {
            DatabaseReference myMatchRef = FirebaseDatabase.getInstance().getReference().child("matches")
                    .child("rooms").child(roomKey).child(user.getUid());
            myMatchRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(context, "이미 요청한 방입니다", Toast.LENGTH_SHORT).show();
                            }else {
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
                   /* .child("matchInfo").child(currentUid);
            matchInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {  //매칭정보가 없을때 매칭요청가능하게, 이미 요청내역이 있을 경우 요청불가.
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue(MatchInfo.class) == null) {
                        MatchInfo matchInfo = new MatchInfo(true, null, null);
                        matchInfoRef.setValue(matchInfo);
                        Toast.makeText(context, "매칭 요청 완료!!", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(context, "이미 요청 하였습니다!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/
        } else Toast.makeText(context, "내가 만든방입니다.", Toast.LENGTH_SHORT).show();
    }


/*    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout room_background;
        TextView txt_roomTitle;
        ImageView roomCategory;
        TextView txt_roomGender;
        TextView txt_roomMember;
        TextView txt_roomDepartment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_roomTitle = itemView.findViewById(R.id.txt_roomTitle);
            roomCategory = itemView.findViewById(R.id.roomCategory);
            txt_roomMember = itemView.findViewById(R.id.txt_roomMember);
            txt_roomGender = itemView.findViewById(R.id.txt_roomGender);
            txt_roomDepartment = itemView.findViewById(R.id.txt_roomDepartment);

            room_background = itemView.findViewById(R.id.room_background);
        }


       /* private void requestMatch(int position) {
            MatchRoom matchRoom = matchRoomsList.get(position);
            String matchKey = matchKeyList.get(position);

            String hostUid = matchRoom.getRoomInfo().getHost();
            String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DatabaseReference matchInfoRef = FirebaseDatabase.getInstance().getReference().child("matchRooms").child(matchKey)
                    .child("matchInfo").child(currentUid);

            if (!currentUid.equals(hostUid) && matchRoom != null) { //방만든애가 자기가 만든 방에 요청 안되게
                matchInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {  //매칭정보가 없을때 매칭요청가능하게, 이미 요청내역이 있을 경우 요청불가.
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue(MatchInfo.class) == null) {
                            MatchInfo matchInfo = new MatchInfo(true, null, null);
                            matchInfoRef.setValue(matchInfo);
                            Toast.makeText(context, "매칭 요청 완료!!", Toast.LENGTH_SHORT).show();
                        } else Toast.makeText(context, "이미 요청 하였습니다!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else Toast.makeText(context, "내가 만든방입니다.", Toast.LENGTH_SHORT).show();
        }*/
    }
}
