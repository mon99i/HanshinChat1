package com.example.hanshinchat1.MainMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.RecyclerChatRoomsAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainChatFragment extends Fragment {
    private DatabaseReference chatRef;
    private RecyclerView recycler_chatrooms;
    private RequestManager glideRequestManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatfragment, container, false);


        initializeView(view);
        setupRecycler();

        return view;
    }

    private void initializeView(View view) {
        try {
            glideRequestManager = Glide.with(this);
            chatRef = FirebaseDatabase.getInstance().getReference();
            recycler_chatrooms = view.findViewById(R.id.recycler_chatrooms);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "화면 초기화 중 오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
        }
    }

    private void setupRecycler() {
        glideRequestManager = Glide.with(this);
        recycler_chatrooms.setLayoutManager(new LinearLayoutManager(getContext()));   //아래향으로 리사이클러뷰나오게
        recycler_chatrooms.setAdapter(new RecyclerChatRoomsAdapter(getContext(),glideRequestManager));    //리사이클러뷰에 만들어놓은 어댑터 설정
    }
}
