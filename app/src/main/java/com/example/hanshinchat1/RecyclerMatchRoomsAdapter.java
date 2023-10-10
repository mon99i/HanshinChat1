package com.example.hanshinchat1;

import android.content.Context;
import android.provider.ContactsContract;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerMatchRoomsAdapter extends RecyclerView.Adapter<RecyclerMatchRoomsAdapter.ViewHolder> {

    private Context context;
    private final static String TAG = "매칭요청실패";

    private ArrayList<String> alreadyMatchedUids;
    private List<MatchRoom> matchRoomsList;
    private ArrayList<String> matchKeyList;
    private FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

    public RecyclerMatchRoomsAdapter(Context context) {
        this.context = context;
        setupAllMatchRoomList();
        matchRoomsList = new ArrayList<>();
        matchKeyList = new ArrayList<>();
    }

    private void setupAllMatchRoomList() {
        FirebaseDatabase.getInstance().getReference().child("matchRooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchRoomsList.clear();                  //채팅룸 ArrayList 초기화
                matchKeyList.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (item != null) {
                        matchRoomsList.add(item.getValue(MatchRoom.class));
                        matchKeyList.add(item.getKey());
                    }

                }

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

        return new RecyclerMatchRoomsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerMatchRoomsAdapter.ViewHolder holder, int position) {

        final int currentPosition = holder.getAdapterPosition();
        MatchRoom matchRoom = matchRoomsList.get(currentPosition);
        holder.txt_roomTitle.setText(matchRoom.getRoomInfo().getTitle());
        holder.txt_roomCategory.setText(matchRoom.getRoomInfo().getCategory());
//        holder.txt_roomGender.setText(matchRoom.getRoomInfo().getGender());
//        holder.txt_roomMember.setText(matchRoom.getRoomInfo().getNum());
        holder.btn_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMatch(currentPosition);
            }
        });
    }

    private void requestMatch(int position) {
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
    }


    @Override
    public int getItemCount() {
        return matchRoomsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View background;
        Button btn_match;
        TextView txt_roomTitle;
        TextView txt_roomCategory;
//        TextView txt_roomGender;
//        TextView txt_roomMember;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.matchRoomBackground);
            btn_match = itemView.findViewById(R.id.btn_match);
            txt_roomTitle = itemView.findViewById(R.id.txt_roomTitle);
            txt_roomCategory = itemView.findViewById(R.id.txt_roomCategory);
//            txt_roomMember = itemView.findViewById(R.id.txt_roomMember);
//            txt_roomGender = itemView.findViewById(R.id.txt_roomGender);
        }
    }
}
