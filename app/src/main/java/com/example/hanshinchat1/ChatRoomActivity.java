package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatRoomActivity extends MainActivity {
    private ImageButton btn_addChat;
    private EditText editText_chat;
    private Button btn_send;
    private DatabaseReference chatRef;
    private RecyclerView recycler_chatrooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        clickHome();
        clickBoard();
        clickProfile();
        clickRoom();
        clickMenu();
        initializeView();
        initializeListener();
        setupRecycler();
    }

    private void initializeView() {
        try {
            chatRef= FirebaseDatabase.getInstance().getReference();
            btn_addChat=(ImageButton)findViewById(R.id.btn_addChat);
            recycler_chatrooms= findViewById(R.id.recycler_chatrooms);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "화면 초기화 중 오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
        }
    }

    private void initializeListener() {

        btn_addChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddChatRoomActivity.class));
                finish();
            }
        });
    }

    private void setupRecycler() {
        recycler_chatrooms.setLayoutManager(new LinearLayoutManager(this));   //아래향으로 리사이클러뷰나오게..?
        recycler_chatrooms.setAdapter(new RecyclerChatRoomsAdapter(this));    //리사이클러뷰에 만들어놓은 어댑터 설정
    }
}
