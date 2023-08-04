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
import androidx.appcompat.app.AppCompatActivity;
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
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class ChattingActivity extends MainActivity {

    private ImageButton chatting_btn_exit;
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
    }

    private void initializeProperty() {
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        chatRoom = (ChatRoom) getIntent().getSerializableExtra("ChatRoom");
        chatRoomKey = getIntent().getStringExtra("ChatRoomKey");
        opponentUser = (UserInfo) getIntent().getSerializableExtra("Opponent");
    }

    private void initializeView() {
        chatting_btn_exit = findViewById(R.id.chatting_btn_exit);
        recycler_chatting = findViewById(R.id.recycler_chatting);
        edt_message = findViewById(R.id.edt_message);
        btn_submit = findViewById(R.id.btn_submit);
        chatting_txt_nickname = findViewById(R.id.chatting_txt_nickname);
        chatting_txt_nickname.setText(opponentUser != null ? opponentUser.getNickName() : "");
    }

    private void initializeListener() {
        chatting_btn_exit.setOnClickListener(new View.OnClickListener() {
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

    private void setupChatRooms() {
        if (chatRoomKey == null || chatRoomKey.isEmpty()) {
            setupChatRoomKey();
        } else {
            setupRecycler();

        }
    }

    private void setupChatRoomKey() {
        FirebaseDatabase.getInstance().getReference()
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
                });
    }

    private void putMessage() {
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
            LocalDateTime localDateTime = LocalDateTime.now();
            localDateTime.atZone(TimeZone.getDefault().toZoneId());
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            return localDateTime.format(dateTimeFormatter).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getTimeError");
        }
    }

    private void setupRecycler() {
        recycler_chatting.setLayoutManager(new LinearLayoutManager(this));
        recycler_chatting.setAdapter(new RecyclerChattingAdapter(this, chatRoomKey, opponentUser.getUid()));

    }
}
