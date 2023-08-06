package com.example.hanshinchat1;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecyclerChatRoomsAdapter extends RecyclerView.Adapter<RecyclerChatRoomsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ChatRoom> chatRooms;
    private ArrayList<String> chatRoomKeys;
    private String myUid;
    //private RecyclerView recyclerView;

/*
    RecyclerView recyclerView = findViewById(R.id.recycler_view); // 리사이클러뷰를 찾아옵니다.
    RecyclerView.Adapter adapter = recyclerView.getAdapter(); // 현재 어댑터를 가져옵니다.

if (adapter != null) {
        // 어댑터가 설정되어 있다면 실행할 코드
        // 예를 들어 어댑터의 데이터를 갱신하거나 다른 조작을 수행할 수 있습니다.
    } else {
        // 어댑터가 설정되어 있지 않을 때 실행할 코드
        // 예를 들어 어댑터를 설정하는 작업을 수행할 수 있습니다.
    }*/

    private static final String TAG="RecyclerChatRoomsAdapter";

    public RecyclerChatRoomsAdapter(Context context) {
        this.context = context;
        chatRooms = new ArrayList<>();
        chatRoomKeys = new ArrayList<>();
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //recyclerView = ((ChatRoomActivity) context).findViewById(R.id.recycler_chatrooms);
        setupAllUserList();
    }

    private void setupAllUserList() {
       FirebaseDatabase.getInstance().getReference().child("chatRooms")
                .orderByChild("users/" + myUid).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatRooms.clear();
                        chatRoomKeys.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            chatRooms.add(data.getValue(ChatRoom.class));
                            chatRoomKeys.add(data.getKey());
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
    public RecyclerChatRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chatroom, parent, false);
        return new RecyclerChatRoomsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {   //holder은 리사이클뷰의 하나하나인듯?
        List<String> userIdList = new ArrayList<>(chatRooms.get(position).getUsers().keySet());
        final int currentPosition = holder.getAdapterPosition();
        String opponent = "";
        for (String userId : userIdList) {
            if (!userId.equals(myUid)) {
                opponent = userId;
                break;
            }
        }


        FirebaseDatabase.getInstance().getReference().child("users")
                .orderByChild("uid").equalTo(opponent)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            holder.chatRoomKey = data.getKey();
                            holder.opponentUser = data.getValue(UserInfo.class);
                            holder.txt_nickname.setText(data.getValue(UserInfo.class).getNickName());
                            String imageUrl=data.getValue(UserInfo.class).getPhotoUrl();
                            Uri imageUri=Uri.parse(imageUrl);
                            Glide.with(context).load(imageUri).into(holder.profile);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });





        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new Intent(context, ChattingActivity.class);
                    intent.putExtra("ChatRoom", chatRooms.get(currentPosition));
                    intent.putExtra("Opponent", holder.opponentUser);
                    intent.putExtra("ChatRoomKey", chatRoomKeys.get(currentPosition));
                    context.startActivity(intent);
                    ((AppCompatActivity) context).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "채팅방 이동 중 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (chatRooms.get(position).getMessages().size() > 0) {
            setupLastMessageAndDate(holder, position);
            setupMessageCount(holder, position);
        }
    }

    private void setupLastMessageAndDate(ViewHolder holder, int position) {
        try {
            List<Message> messageList = new ArrayList<>(chatRooms.get(position).getMessages().values());
            messageList.sort((m1, m2) -> m1.getSended_date().compareTo(m2.getSended_date()));
            Message lastMessage = messageList.get(messageList.size() - 1);
            holder.txt_message.setText(lastMessage.getContent());
            holder.txt_date.setText(getLastMessageTimeString(lastMessage.getSended_date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupMessageCount(ViewHolder holder, int position) {
        try {
            List<Message> messageList = new ArrayList<>(chatRooms.get(position).getMessages().values());
            long unconfirmedCount = messageList.stream()
                    .filter(message -> !message.isConfirmed() && !message.getSenderUid().equals(myUid))
                    .count();
            if (unconfirmedCount > 0) {
                holder.txt_chatCount.setVisibility(View.VISIBLE);
                holder.txt_chatCount.setText(String.valueOf(unconfirmedCount));
            } else {
                holder.txt_chatCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.txt_chatCount.setVisibility(View.GONE);
        }
    }

    private String getLastMessageTimeString(String lastTimeString) {
        try {
            LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            int messageMonth = Integer.parseInt(lastTimeString.substring(4, 6));
            int messageDate = Integer.parseInt(lastTimeString.substring(6, 8));
            int messageHour = Integer.parseInt(lastTimeString.substring(8, 10));
            int messageMinute = Integer.parseInt(lastTimeString.substring(10, 12));

            String formattedCurrentTimeString = currentTime.format(dateTimeFormatter);
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
        return chatRooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {   //각 홀더가 무슨 양식인지?
        private UserInfo opponentUser;
        private String chatRoomKey;
        private View background;
        private TextView txt_nickname;
        private TextView txt_message;
        private TextView txt_date;
        private TextView txt_chatCount;
        private ImageView profile;

        public ViewHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.chatRoomBackground);
            txt_nickname = itemView.findViewById(R.id.room_txt_nickname);
            txt_message = itemView.findViewById(R.id.room_txt_message);
            txt_date = itemView.findViewById(R.id.room_txt_date);
            txt_chatCount = itemView.findViewById(R.id.room_txt_chatCount);
            profile=itemView.findViewById(R.id.room_profile);
        }
    }
}
