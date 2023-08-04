package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddChatRoomActivity extends MainActivity {

    private ImageButton addChat_btn_exit;
    private EditText edt_opponent;
    private DatabaseReference firebaseDatabase;
    private RecyclerView recycler_addchat;

    private static final String TAG="AddChatRoomActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat_room);
       /* clickHome();
        clickBoard();
        clickProfile();
        clickRoom();*/
        initializeView();
        initializeListener();
        setupRecycler();
    }

    private void initializeView() {
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        addChat_btn_exit = findViewById(R.id.addChat_btn_exit);
        edt_opponent = findViewById(R.id.edt_opponent);
        recycler_addchat = findViewById(R.id.recycler_addchat);
    }

    private void initializeListener() {
        addChat_btn_exit.setOnClickListener(v -> {
            startActivity(new Intent(AddChatRoomActivity.this, ChatRoomActivity.class));
        });

        edt_opponent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                RecyclerUsersAdapter adapter = (RecyclerUsersAdapter) recycler_addchat.getAdapter();
                if (adapter != null) {
                    //adapter.setupAllUserList();
                    adapter.searchItem(s.toString());
                }
                else Log.d(TAG, "addChatRoomActivity에서 에러");
            }
        });
    }

    private void setupRecycler() {
        recycler_addchat.setLayoutManager(new LinearLayoutManager(this));
        recycler_addchat.setAdapter(new RecyclerUsersAdapter(this));
    }
}
