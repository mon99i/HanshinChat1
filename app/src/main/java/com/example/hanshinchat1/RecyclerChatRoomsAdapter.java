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
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

public class RecyclerChatRoomsAdapter extends RecyclerView.Adapter<RecyclerChatRoomsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ChatRoom> chatRooms;
    private ArrayList<String> chatRoomKeys;
    private String myUid;

    private static final String TAG="RecyclerChatRoomsAdapter";

    public RecyclerChatRoomsAdapter(Context context) {    //어댑터 생성자
        this.context = context;
        chatRooms = new ArrayList<>();
        chatRoomKeys = new ArrayList<>();
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setupAllUserList();
    }

    private void setupAllUserList() {       //현재 user가 채팅하는 목록나열?
       FirebaseDatabase.getInstance().getReference().child("chatRooms")
                .orderByChild("users/" + myUid).equalTo(true)     //현재 유저가 이용중인 채팅룸, 즉 myUid 가 true인 채팅룸 나열
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatRooms.clear();                  //채팅룸 ArrayList 초기화
                        chatRoomKeys.clear();               //채팅룸 키 ArrayList 초기화
                        for (DataSnapshot data : snapshot.getChildren()) {
                            chatRooms.add(data.getValue(ChatRoom.class));    //my uid가 true인 방 객체를, 채팅룸 ArrayList에 추가
                            chatRoomKeys.add(data.getKey());                 //my uid가 true인 방 uid를, 채팅룸 키 ArrayList에 추가
                        }
                        notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    @NonNull
    @Override      //각각의 리사이클러뷰(row_chatroom.xml)에대한 뷰홀더 생성
    public RecyclerChatRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chatroom, parent, false);
        return new RecyclerChatRoomsAdapter.ViewHolder(view);
    }


    @Override   //뷰홀더에 새로운 데이터 매칭
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> userIdList = new ArrayList<>(chatRooms.get(position).getUsers().keySet());
        //리스트에 현재 user가 속한 방안의 모든 유저 키(uid) 저장

        final int currentPosition = holder.getAdapterPosition();
        String opponent = "";
        for (String userId : userIdList) {    // oppnentId 구하기
            if (!userId.equals(myUid)) {
                opponent = userId;
                break;
            }
        }

        // users데이터베이스 아래에 uid가 opponenId와 같은것을 조회하는 쿼리
        FirebaseDatabase.getInstance().getReference().child("users")
                .orderByChild("uid").equalTo(opponent)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {

                            holder.chatRoomKey = data.getKey();

                            holder.opponentUser = data.getValue(UserInfo.class);
                            holder.txt_nickname.setText(data.getValue(UserInfo.class).getName());
                            String imageUrl=data.getValue(UserInfo.class).getPhotoUrl();
                            Uri imageUri=Uri.parse(imageUrl);
                            Glide.with(context).load(imageUri).into(holder.profile);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });





        holder.background.setOnClickListener(new View.OnClickListener() {  //홀더누르면 각각의 채팅방으로 이동
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new Intent(context, ChattingActivity.class);
                    intent.putExtra("ChatRoom", chatRooms.get(currentPosition));
                    intent.putExtra("Opponent", holder.opponentUser);
                    intent.putExtra("ChatRoomKey", chatRoomKeys.get(currentPosition));   //채팅방 키 넘기기
                    context.startActivity(intent);
                    ((AppCompatActivity) context).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "채팅방 이동 중 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (chatRooms.get(position).getMessages().size() > 0) {  //메세지 기록있으면 채팅방list에서 볼수있게끔
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

    private String getLastMessageTimeString(String lastTimeString) {    //현재 앱을 실행시킨 시간을 기준으로 하고있음 수정필요.
        try {


            LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            //LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());

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
