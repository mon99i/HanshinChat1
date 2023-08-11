package com.example.hanshinchat1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
/*
import com.miso.chatapplication.databinding.ListPersonItemBinding;
import com.miso.chatapplication.model.User;
*/




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerUsersAdapter extends RecyclerView.Adapter<RecyclerUsersAdapter.ViewHolder>{
    private Context context;
    private ArrayList<UserInfo> users = new ArrayList<>();  // 검색어로 일치한 사용자 목록
    private final ArrayList<UserInfo> allUsers = new ArrayList<>();  // 전체 사용자 목록
    private UserInfo currentUser;

    private static final String TAG="리사이클유저에러";
    public RecyclerUsersAdapter(Context context) {
        this.context = context;
        setupAllUserList();
    }

    public void setupAllUserList() {
        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 사용자 아이디
        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
               /* for (DataSnapshot data : snapshot.getChildren()) {
                    UserInfo item = data.getValue(UserInfo.class);
                    if (item != null && item.getUid().equals(myUid)) {
                        currentUser = item; // 전체 사용자 목록에서 현재 사용자는 제외

                    } else if (item != null) {
                        allUsers.add(item); // 전체 사용자 목록에 추가
                    }
                }
                users = (ArrayList<UserInfo>) allUsers.clone();*/

                for(DataSnapshot data : snapshot.getChildren()){
                    UserInfo item=data.getValue(UserInfo.class);
                    if(item!=null&&data.getKey().equals(myUid)){
                        currentUser=item;
                    }
                    else if(item!=null) {
                        allUsers.add(item);
                    }
                }
                users= (ArrayList<UserInfo>) allUsers.clone();


                notifyDataSetChanged(); // 화면 업데이트
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void searchItem(String target) { // 검색                     //주의
        if (target.equals("")) { // 검색어 없는 경우 전체 목록 표시
            users = (ArrayList<UserInfo>) allUsers.clone();
        } else {
            ArrayList<UserInfo> matchedList = new ArrayList<>();
            for (UserInfo user : allUsers) {
                if (user.getName() != null && user.getName().contains(target)) {
                    matchedList.add(user); // 검색어 포함된 항목 불러오기
                }
            }
            users.clear();
            users.addAll(matchedList);
        }
        notifyDataSetChanged(); // 화면 업데이트
    }

    @NonNull
    @Override
    public RecyclerUsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_addchat, parent, false);

        return new RecyclerUsersAdapter.ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInfo user = users.get(holder.getAdapterPosition());
        holder.txt_nickname.setText(user.getName());
        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChatRoom(holder.getAdapterPosition()); // 해당 사용자 선택 시
            }
        });
    }



    private void addChatRoom(int position) { // 채팅방 추가
        UserInfo opponent = users.get(position); // 채팅할 상대방 정보
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("chatRooms"); // 넣을 database reference 세팅

        Map<String, Boolean> usersMap = new HashMap<>();
        usersMap.put(currentUser.getUid(), true);
        usersMap.put(opponent.getUid(), true);
        ChatRoom chatRoom = new ChatRoom(usersMap, null);

        //ChatRoom chatRoom = new ChatRoom(new HashMap<>(Map.of(currentUser.getUid(), true, opponent.getUid(), true)), null); // 추가할 채팅방 정보 세팅
        String myUid = FirebaseAuth.getInstance().getUid(); // 내 Uid
        Query query = database.orderByChild("users/" + opponent.getUid()).equalTo(true); // 상대방 Uid가 포함된 채팅방이 있는 지 확인
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) { // 채팅방이 없는 경우
                    database.push().setValue(chatRoom).addOnSuccessListener(aVoid -> {
                        goToChatRoom(chatRoom, opponent);
                    });
                } else {
                    context.startActivity(new Intent(context, ChatRoomActivity.class));  //일단홈으로
                    goToChatRoom(chatRoom, opponent); // 해당 채팅방으로 이동
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void goToChatRoom(ChatRoom chatRoom, UserInfo opponent) { // 채팅방으로 이동
        Intent intent = new Intent(context, ChattingActivity.class);
        intent.putExtra("ChatRoom", chatRoom); // 채팅방 정보
        intent.putExtra("Opponent", opponent); // 상대방 정보
        intent.putExtra("ChatRoomKey", ""); // 채팅방 키
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();
    }
    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View background;
        TextView txt_nickname;


        public ViewHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.addChatBackground);
            txt_nickname = itemView.findViewById(R.id.add_txt_nickname);
            /*txt_message = itemView.findViewById(R.id.txt_message);
            txt_date = itemView.findViewById(R.id.txtMessageDate);
            txt_chatCount = itemView.findViewById(R.id.txtChatCount);*/

        }


    }
}
