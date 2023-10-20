package com.example.hanshinchat1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class RecyclerMatchRoomsAdapter extends RecyclerView.Adapter<RecyclerMatchRoomsAdapter.ViewHolder> {

    private Context context;
    private final static String TAG = "매칭요청실패";

    private ArrayList<MatchRoom> matchRoomsList;
    private ArrayList<String> matchKeyList;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String selectedCategory;

    public RecyclerMatchRoomsAdapter(Context context) {
        this.context = context;
        matchRoomsList = new ArrayList<>();
        matchKeyList = new ArrayList();

        loadAllMatchRooms(); // 모든 방 목록
    }

    private void loadAllMatchRooms() {
        DatabaseReference matchRoomsRef = FirebaseDatabase.getInstance().getReference().child("matchRooms");
        matchRoomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchRoomsList.clear();
                matchKeyList.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    MatchRoom matchRoom = item.getValue(MatchRoom.class);
                    if (matchRoom != null) {
                        matchRoomsList.add(matchRoom);
                        matchKeyList.add(item.getKey());
                    }
                }
                // 카테고리 적용
                filterMatchRoomsByCategory();
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
    }

    @NonNull
    @Override
    public RecyclerMatchRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_matchroom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerMatchRoomsAdapter.ViewHolder holder, int position) {
        MatchRoom matchRoom = matchRoomsList.get(position);
        holder.txt_roomTitle.setText(matchRoom.getRoomInfo().getTitle());

        String categoryTxt = matchRoom.getRoomInfo().getCategory();
        int imageResourceId;

        if ("미팅".equals(categoryTxt)) {
            imageResourceId = R.drawable.icon1;
        } else if ("과팅".equals(categoryTxt)) {
            imageResourceId = R.drawable.icon2;
        } else if ("밥팅".equals(categoryTxt)) {
            imageResourceId = R.drawable.icon3;
        } else {
            imageResourceId = R.drawable.icon1;
        }
        holder.roomCategory.setImageResource(imageResourceId);
        holder.txt_roomGender.setText(matchRoom.getRoomInfo().getGender());
        holder.txt_roomDepartment.setText(matchRoom.getRoomInfo().getDepartment());
        holder.txt_roomMember.setText(matchRoom.getRoomInfo().getNum());

        //        holder.btn_match.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestMatch(currentPosition);
//            }
//        });
    }

    public void setSelectedCategory(String category) {
        selectedCategory = category;

        loadAllMatchRooms();
    }

    private void filterMatchRoomsByCategory() {
        if (selectedCategory == null) {

            return;
        }

        ArrayList<MatchRoom> filteredList = new ArrayList<>();

        for (MatchRoom matchRoom : matchRoomsList) {
            if (matchRoom.getRoomInfo().getCategory().equals(selectedCategory)) {
                filteredList.add(matchRoom);
            }
        }

        matchRoomsList.clear();
        matchRoomsList.addAll(filteredList);
    }

    @Override
    public int getItemCount() {
        return matchRoomsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
//    private void requestMatch(int position) {
//        MatchRoom matchRoom = matchRoomsList.get(position);
//        String matchKey = matchKeyList.get(position);
//
//        String hostUid = matchRoom.getRoomInfo().getHost();
//        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        DatabaseReference matchInfoRef = FirebaseDatabase.getInstance().getReference().child("matchRooms").child(matchKey)
//                .child("matchInfo").child(currentUid);
//
//        if (!currentUid.equals(hostUid) && matchRoom != null) { //방만든애가 자기가 만든 방에 요청 안되게
//            matchInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {  //매칭정보가 없을때 매칭요청가능하게, 이미 요청내역이 있을 경우 요청불가.
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.getValue(MatchInfo.class) == null) {
//                        MatchInfo matchInfo = new MatchInfo(true, null, null);
//                        matchInfoRef.setValue(matchInfo);
//                        Toast.makeText(context, "매칭 요청 완료!!", Toast.LENGTH_SHORT).show();
//                    } else Toast.makeText(context, "이미 요청 하였습니다!", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        } else Toast.makeText(context, "내가 만든방입니다.", Toast.LENGTH_SHORT).show();
//    }
}
