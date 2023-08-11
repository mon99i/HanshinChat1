package com.example.hanshinchat1;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;

public class RecyclerChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String chatRoomKey;
    private String opponentUid;
    private ArrayList<Message> messages = new ArrayList<>();     //배열에 메시지 저장
    private ArrayList<String> messageKeys = new ArrayList<>();
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private RecyclerView recyclerView;

    private static final String TAG="RecyclerChattingAdapter";
    public RecyclerChattingAdapter(Context context, String chatRoomKey, String opponentUid) {
        this.context = context;
        this.chatRoomKey = chatRoomKey;
        this.opponentUid = opponentUid;
        recyclerView = ((ChattingActivity) context).findViewById(R.id.recycler_chatting);
        setupMessages();
    }

    private void setupMessages() {
        getMessages();
    }

    private void getMessages() {   //데이터베이스에 추가된 메세지 가져오기
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("chatRooms").child(chatRoomKey).child("messages");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                messages.clear();
                messageKeys.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Message message = data.getValue(Message.class);
                    if (message != null) {
                        messages.add(message);
                        messageKeys.add(data.getKey());
                        Log.d(TAG, "getmessage댐");
                    }
                }
                notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size() - 1);
                Log.d(TAG, "getmessage댐");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d(TAG, "getmessage안댐");
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getSenderUid().equals(myUid) ? 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        if (viewType == 1) {
            view = inflater.inflate(R.layout.row_mychat, parent, false);
            return new MyMessageViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.row_opponentchat, parent, false);
            return new OtherMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (messages.get(position).getSenderUid().equals(myUid)) {
            ((MyMessageViewHolder) holder).bind(position);
        } else {
            ((OtherMessageViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private class OtherMessageViewHolder extends RecyclerView.ViewHolder {
        private View background;
        private TextView txt_Message;
        private TextView txt_date;

        private ImageView profile;
        private TextView txtIsShown;
        private TextView txt_nickname;

        OtherMessageViewHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.opponentChatBackground);
            profile=itemView.findViewById(R.id.opponent_profile);
            txt_Message = itemView.findViewById(R.id.opponent_txt_message);
            txt_date = itemView.findViewById(R.id.opponent_txt_date);
            txtIsShown = itemView.findViewById(R.id.opponent_txt_isShown);
            txt_nickname = itemView.findViewById(R.id.row_opponent_nickname);

        }

        void bind(int position) {
            Message message = messages.get(position);
            String sendDate = message.getSended_date();
            txt_Message.setText(message.getContent());
            txt_date.setText(getKoreanDateText(sendDate));

            if (message.isConfirmed()) {
                txtIsShown.setVisibility(View.GONE);
            } else {
                txtIsShown.setVisibility(View.VISIBLE);
            }

            opponentUid=message.getSenderUid();
           FirebaseDatabase.getInstance().getReference().child("users").child(opponentUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    txt_nickname.setText(snapshot.getValue(UserInfo.class).getName());
                    String imageUrl=snapshot.getValue(UserInfo.class).getPhotoUrl();
                    Uri imageUri=Uri.parse(imageUrl);
                    Glide.with(context).load(imageUri).into(profile);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            setShown(position);
        }



        String getKoreanDateText(String sendDate){                   //현재 앱을 실행시킨 시간을 기준으로 하고있음 수정필요.
            LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

            // 시간 포맷 지정
            String amPmText = currentTime.getHour() < 12 ?
                    "오전" : "오후";
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendLiteral(amPmText)  // 오전/오후 텍스트 삽입
                    .appendPattern(" hh:mm")
                    .toFormatter();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a hh:mm");
            String koreanDateText= currentTime.format(formatter);

            return koreanDateText;
        }


        void setShown(int position) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                    .child("chatRooms").child(chatRoomKey).child("messages")
                    .child(messageKeys.get(position)).child("confirmed");

            reference.setValue(true)
                    .addOnSuccessListener(aVoid -> Log.i("checkShown", "성공"));
        }
    }

    private class MyMessageViewHolder extends RecyclerView.ViewHolder {
        private View background;
        private TextView txt_message;
        private TextView txt_date;
        private TextView txt_isShown;

        MyMessageViewHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.myChatBackground);
            txt_message = itemView.findViewById(R.id.my_txt_message);
            txt_date = itemView.findViewById(R.id.my_txt_date);
            txt_isShown = itemView.findViewById(R.id.opponent_txt_isShown);
        }

        void bind(int position) {
            Message message = messages.get(position);
            String sendDate = message.getSended_date();

            txt_message.setText(message.getContent());
            txt_date.setText(getKoreanDateText(sendDate));

            if (message.isConfirmed()) {
                txt_isShown.setVisibility(View.GONE);
            } else {
                txt_isShown.setVisibility(View.VISIBLE);
            }
        }

        String getKoreanDateText(String sendDate){                               //현재 앱을 실행시킨 시간을 기준으로 하고있음 수정필요.
            LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

            // 시간 포맷 지정
            String amPmText = currentTime.getHour() < 12 ?
                    "오전" : "오후";
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendLiteral(amPmText)  // 오전/오후 텍스트 삽입
                    .appendPattern(" hh:mm")
                    .toFormatter();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a hh:mm");
            String koreanDateText= currentTime.format(formatter);

            return koreanDateText;
        }

       /* String getDateText(String sendDate) {
            String dateText = "";
            String timeString = "";

            if (sendDate != null && !sendDate.isEmpty()) {
                timeString = sendDate.substring(8, 12);
                String hour = timeString.substring(0, 2);
                String minute = timeString.substring(2, 4);

                String timeFormat = "%02d:%02d";

                if (Integer.parseInt(hour) > 11) {
                    dateText += "오후 ";
                    dateText += String.format(timeFormat, Integer.parseInt(hour) - 12, Integer.parseInt(minute));
                } else {
                    dateText += "오전 ";
                    dateText += String.format(timeFormat, Integer.parseInt(hour), Integer.parseInt(minute));
                }
            }
            return dateText;
        }*/



    }
}
