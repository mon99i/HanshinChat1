package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanshinchat1.recycler.VerticalDecoration;
import com.example.hanshinchat1.utils.Utils;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.TimeZone;

public class ChattingActivity extends MainActivity {

    private ImageButton chatting_btn_back;
    private Button btn_submit;
    private TextView chatting_txt_nickname;
    private EditText edt_message;
    private DatabaseReference firebaseDatabase;
    private RecyclerView recycler_chatting;
    private ChatRoom chatRoom;
    private UserInfo opponentUser;
    private String chatRoomKey;
    private String myUid;

    private static final String TAG="CattingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        initializeProperty();
        initializeView();
        initializeListener();
        setupChatRooms();
        showLetsChatDialog();

    }

    private void initializeProperty() {  //chatroomActivity에서 받아옴
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        chatRoom = (ChatRoom) getIntent().getSerializableExtra("ChatRoom");
        chatRoomKey = getIntent().getStringExtra("ChatRoomKey");
        opponentUser = (UserInfo) getIntent().getSerializableExtra("Opponent");
    }

    private void initializeView() {
        chatting_btn_back = findViewById(R.id.chatting_btn_back);
        recycler_chatting = findViewById(R.id.recycler_chatting);
        edt_message = findViewById(R.id.edt_message);
        btn_submit = findViewById(R.id.btn_submit);
        chatting_txt_nickname = findViewById(R.id.chatting_txt_nickname);
        chatting_txt_nickname.setText(opponentUser != null ? opponentUser.getName() : "");
    }

    private void initializeListener() {
        chatting_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChattingActivity.this, ChatRoomActivity.class));
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putMessage();
            }
        });
    }


    //받아온 chatRoomKey가 있으면 리사이클러뷰 설정하고, 없으면 만들기
    private void setupChatRooms() {
        if (chatRoomKey == null || chatRoomKey.isEmpty()) {
            setupChatRoomKey();
        } else {
            setupRecycler();

        }
    }

    private void setupChatRoomKey() {   //새롭게 방을 만들경우 받아오는 chatRoomKey가 없으므로, 이곳에서 생성

        //이거 보류
     /*   FirebaseDatabase.getInstance().getReference()
                .child("chatRooms").orderByChild("users/" + opponentUser.getUid()).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            chatRoomKey = data.getKey();
                            setupRecycler();


                            break;
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });*/


        FirebaseDatabase.getInstance().getReference().child("chatRooms")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot item:snapshot.getChildren()){
                            if(item.getValue(ChatRoom.class).equals(chatRoom)){
                                chatRoomKey=item.getKey();
                                setupRecycler();
                                showLetsChatDialog();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void putMessage() {          //데이터베이스에 메세지 넣고, 저장된 데이터를 다시 리사이클러뷰에 보이게하는거임
        try {
            Message message = new Message(myUid, getDateTimeString(), edt_message.getText().toString());
            FirebaseDatabase.getInstance().getReference().child("chatRooms")
                    .child(chatRoomKey).child("messages")
                    .push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            edt_message.getText().clear();
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDateTimeString() {
        try {
            LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            return localDateTime.format(dateTimeFormatter).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getTimeError");
        }

    }

    private void setupRecycler() {  //리사이클러뷰에 어댑터 설정
        recycler_chatting.setLayoutManager(new LinearLayoutManager(this));
        recycler_chatting.setAdapter(new RecyclerChattingAdapter(this, chatRoomKey, opponentUser.getUid()));

    }


    private void showLetsChatDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.lets_chat_dialog, null);

        // AlertDialog.Builder를 사용하여 커스텀 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        alertDialog.getWindow().setGravity(Gravity.TOP); //상단에 위치
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //밖에 배경 어둡지않게
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));  // 배경 투명하게
        //alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 다이얼로그 표시
        alertDialog.show();
        if (!isFinishing()) {

     /*       Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.dismiss(); // AlertDialog 닫기
                    }
                }
            }, 3000);*/

        }
    }
}
