package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private static final String TAG = "CattingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        initializeProperty();
        initializeView();
        initializeListener();
        setupChatRooms();

    }

    private void initializeProperty() {
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

                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 3);
                startActivity(intent);
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
        FirebaseDatabase.getInstance().getReference()
                .child("chatRooms").orderByChild("users/" + opponentUser.getUid()).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Log.d(TAG, "onDataChange: " + data.getValue(ChatRoom.class) + " 받은 chatRoom:" + chatRoom);
                            chatRoomKey = data.getKey();
                            setupRecycler();


                            break;
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });

        /* //이거 보류
        FirebaseDatabase.getInstance().getReference()
                .child("chatRooms").orderByChild("users/" + user.getUid()).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            ChatRoom chatRoom2=data.getValue(ChatRoom.class);
                            Log.d(TAG, "onDataChange: "+chatRoom2);
                            if(chatRoom2.getUsers().containsKey(opponentUser.getUid())){
                                Log.d(TAG, "onDataChange: chat if 문 안들어옴");
                                Log.d(TAG, "onDataChange: "+chatRoom2+" 받은 chatRoom:"+chatRoom);
                                chatRoomKey=data.getKey();
                                setupRecycler();
                                break;
                            }

                            break;
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });*/
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


}
