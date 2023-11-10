package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanshinchat1.recycler.GridSpaceDecoration;
import com.example.hanshinchat1.recycler.RecyclerRecommendMatchAdapter;

import java.util.ArrayList;

public class RecommendMatchActivity extends AppCompatActivity {
    private final static String TAG = "recommendMatch";

    private ImageButton recommendMatchBackBtn;
    private TextView noneRecommendTxt;
    private RecyclerView recyclerView;
    private TextView recommenTypeTxt;
    private String recommendType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_match);

        initializeView();
        setUpRecycler();
        initializeListener();
    }

    private void initializeView() {
        noneRecommendTxt = findViewById(R.id.noneRecommendTxt);
        recommendMatchBackBtn = findViewById(R.id.recommendMatchBackBtn);
        recyclerView = findViewById(R.id.recycler_recommend_match);

        recommenTypeTxt = findViewById(R.id.recommendTypeTxt);
        recommendType = getIntent().getStringExtra("recommendType");
        recommenTypeTxt.setText(recommendType);
    }

    private void setUpRecycler() {
        ArrayList<UserInfo> recommendUsers = (ArrayList<UserInfo>) getIntent().getSerializableExtra("recommendUsers");

        ArrayList<UserInfo> firstIdealUsers = (ArrayList<UserInfo>) getIntent().getSerializableExtra("firstIdealUsers");
        ArrayList<UserInfo> secondIdealUsers = (ArrayList<UserInfo>) getIntent().getSerializableExtra("secondIdealUsers");
        ArrayList<UserInfo> thirdIdealUsers = (ArrayList<UserInfo>) getIntent().getSerializableExtra("thirdIdealUsers");

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpaceDecoration(2, 100, 60));
        if (recommendType.equals("이상형 추천")) {
            if (firstIdealUsers.isEmpty() && secondIdealUsers.isEmpty() && thirdIdealUsers.isEmpty()) {
                noneRecommendTxt.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setAdapter(new RecyclerRecommendMatchAdapter(this, recommendType, firstIdealUsers, secondIdealUsers, thirdIdealUsers));
            }
        } else if (recommendType.equals("MBTI 추천")) {
            if (recommendUsers.isEmpty()) {
                noneRecommendTxt.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setAdapter(new RecyclerRecommendMatchAdapter(this, recommendUsers, recommendType));
            }
        } else {
            if (recommendUsers.isEmpty()) {
                noneRecommendTxt.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setAdapter(new RecyclerRecommendMatchAdapter(this, recommendUsers, recommendType));
            }
        }
    }

    private void initializeListener() {
        recommendMatchBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 1);
                startActivity(intent);
            }
        });
    }
}