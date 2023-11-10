package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class RecyclerChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String chatRoomKey;
    private String opponentUid;
    private ArrayList<Message> messages = new ArrayList<>();     //배열에 메시지 저장
    private ArrayList<String> messageKeys = new ArrayList<>();
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private RecyclerView recyclerView;

    private static final String TAG = "RecyclerChattingAdapter";

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
                if (!snapshot.exists()) {
                    showLetsChatDialog();
                }
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

    private void showLetsChatDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lets_chat_dialog, null);

        // AlertDialog.Builder를 사용하여 커스텀 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        alertDialog.getWindow().setGravity(Gravity.TOP); //상단에 위치
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //밖에 배경 어둡지않게
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));  // 배경 투명하게

        // 다이얼로그 표시
        alertDialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss(); // AlertDialog 닫기
                }
            }
        }, 2000);

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

        Log.d(TAG, "onBindViewHolder: " + position);
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
        private ImageView profile;
        private TextView txt_Message;
        private TextView txt_date;
        private TextView txt_isShown;
        private TextView txt_nickname;
        private TextView opponnet_changed_date;

        OtherMessageViewHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.opponentChatBackground);
            profile = itemView.findViewById(R.id.opponent_profile);
            txt_Message = itemView.findViewById(R.id.opponent_txt_message);
            txt_date = itemView.findViewById(R.id.opponent_txt_date);
            txt_isShown = itemView.findViewById(R.id.txt_isShown);
            txt_nickname = itemView.findViewById(R.id.row_opponent_nickname);
            opponnet_changed_date = itemView.findViewById(R.id.opponent_changed_date);
        }

        void bind(int position) {
            Message message = messages.get(position);

            // profile
            opponentUid = message.getSenderUid();
            FirebaseDatabase.getInstance().getReference().child("users").child(opponentUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    txt_nickname.setText(snapshot.getValue(UserInfo.class).getName());
                    String imageUrl = snapshot.getValue(UserInfo.class).getPhotoUrl();
                    Uri imageUri = Uri.parse(imageUrl);
                    Glide.with(context).load(imageUri).into(profile);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //보낸 메세지 내용
            txt_Message.setText(message.getContent());

            //보낸 날짜
            String sendDate = message.getSended_date();
            txt_date.setText(getKoreanDateText(sendDate));

            //confirm 적용
            if (message.isConfirmed()) {
                txt_isShown.setVisibility(View.GONE);
            } else {
                txt_isShown.setVisibility(View.VISIBLE);
            }

            //날짜 바뀔시 적용
            opponnet_changed_date.setVisibility(View.GONE);     //기본적으로 안보임
            setChangedDate(position);

            setShown(position);
        }


        void setChangedDate(int position) {
            if (position > 0) {
                String previousSendedDate = messages.get(position - 1).sended_date;
                String currentSendedDate = messages.get(position).sended_date;

                //디비에서 시간 String 가져와서 localDateTime형식으로 변경
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime previousDateTime = LocalDateTime.parse(previousSendedDate, dateTimeFormatter);
                LocalDateTime currentDateTime = LocalDateTime.parse(currentSendedDate, dateTimeFormatter);

                if (currentDateTime.getDayOfWeek() != previousDateTime.getDayOfWeek() ||
                        currentDateTime.getDayOfMonth() != previousDateTime.getDayOfMonth() ||
                        currentDateTime.getDayOfYear() != previousDateTime.getDayOfYear()) {
                    //다시 원하는 포맷으로 변경
                    DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
                    String changedDate = currentDateTime.format(newFormatter);
                    opponnet_changed_date.setVisibility(View.VISIBLE);
                    opponnet_changed_date.setText(changedDate);

                }
            } else {
                String currentSendedDate = messages.get(position).sended_date;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime currentDateTime = LocalDateTime.parse(currentSendedDate, dateTimeFormatter);

                DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
                String changedDate = currentDateTime.format(newFormatter);
                opponnet_changed_date.setVisibility(View.VISIBLE);
                opponnet_changed_date.setText(changedDate);
            }

        }


        String getKoreanDateText(String sendDate) {                   //현재 앱을 실행시킨 시간을 기준으로 하고있음 수정필요.
            try {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime localDateTime = LocalDateTime.parse(sendDate, dateTimeFormatter);

                // 시간 포맷 지정
                String amPmText = localDateTime.getHour() < 12 ? "오전" : "오후";
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendLiteral(amPmText)  // 오전/오후 텍스트 삽입
                        .appendPattern(" hh:mm")
                        .toFormatter();

                String koreanDateText = localDateTime.format(formatter);
                return koreanDateText;
                // koreanDateText를 사용하면 지정된 형식의 시간을 얻을 수 있습니다.
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                throw new RuntimeException("시간 파싱 오류");
            }

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
        private TextView my_changed_date;

        MyMessageViewHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.myChatBackground);
            txt_message = itemView.findViewById(R.id.my_txt_message);
            txt_date = itemView.findViewById(R.id.my_txt_date);
            txt_isShown = itemView.findViewById(R.id.txt_isShown);
            my_changed_date = itemView.findViewById(R.id.my_changed_date);
        }

        void bind(int position) {
            Message message = messages.get(position);

            //보낸 메세지 내용
            txt_message.setText(message.getContent());

            //보낸 날짜
            String sendDate = message.getSended_date();
            txt_date.setText(getKoreanDateText(sendDate));

            if (message.isConfirmed()) {
                txt_isShown.setVisibility(View.GONE);
            } else {
                txt_isShown.setVisibility(View.VISIBLE);
            }

            //날짜 바뀔시 적용
            my_changed_date.setVisibility(View.GONE);  //기본적으로 안보임
            setChangedDate(position);
        }

        void setChangedDate(int position) {
            if (position > 0) {
                String previousSendedDate = messages.get(position - 1).sended_date;
                String currentSendedDate = messages.get(position).sended_date;

                //디비에서 시간 String 가져와서 localDateTime형식으로 변경
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime previousDateTime = LocalDateTime.parse(previousSendedDate, dateTimeFormatter);
                LocalDateTime currentDateTime = LocalDateTime.parse(currentSendedDate, dateTimeFormatter);

                if (currentDateTime.getDayOfWeek() != previousDateTime.getDayOfWeek() ||
                        currentDateTime.getDayOfMonth() != previousDateTime.getDayOfMonth() ||
                        currentDateTime.getDayOfYear() != previousDateTime.getDayOfYear()) {
                    //다시 원하는 포맷으로 변경
                    DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
                    String changedDate = currentDateTime.format(newFormatter);
                    my_changed_date.setVisibility(View.VISIBLE);
                    my_changed_date.setText(changedDate);

                }
            } else {
                String currentSendedDate = messages.get(position).sended_date;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime currentDateTime = LocalDateTime.parse(currentSendedDate, dateTimeFormatter);

                DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
                String changedDate = currentDateTime.format(newFormatter);
                my_changed_date.setVisibility(View.VISIBLE);
                my_changed_date.setText(changedDate);

            }

        }

        String getKoreanDateText(String sendDate) {                            //현재 앱을 실행시킨 시간을 기준으로 하고있음 수정필요.

            try {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime localDateTime = LocalDateTime.parse(sendDate, dateTimeFormatter);

                // 시간 포맷 지정
                String amPmText = localDateTime.getHour() < 12 ? "오전" : "오후";
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendLiteral(amPmText)  // 오전/오후 텍스트 삽입
                        .appendPattern(" hh:mm")
                        .toFormatter();

                String koreanDateText = localDateTime.format(formatter);
                return koreanDateText;
                // koreanDateText를 사용하면 지정된 형식의 시간을 얻을 수 있습니다.
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                throw new RuntimeException("시간 파싱 오류");
            }

        }


    }

}
