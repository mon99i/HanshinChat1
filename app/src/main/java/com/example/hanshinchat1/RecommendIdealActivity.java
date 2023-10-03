package com.example.hanshinchat1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hanshinchat1.recycler.GridSpaceDecoration;
import com.example.hanshinchat1.recycler.RecyclerRecommendIdealAdapter;
import com.example.hanshinchat1.recycler.RecyclerRecommendMatchAdapter;

import java.util.ArrayList;

public class RecommendIdealActivity extends AppCompatActivity {


    private static final String TAG = "recommendIdealActivity";

    private ImageButton recommendMatchBackBtn;
    private TextView noneRecommendTxt;
    private RecyclerView recyclerView;
    private ArrayList<UserInfo> firstIdealUsers;
    private ArrayList<UserInfo> secondIdealUsers;
    private ArrayList<UserInfo> thirdIdealUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_ideal);


        initializeView();
        initializeListener();
    }

    private void initializeView(){
   /*     recommendMatchImage1=findViewById(R.id.recommendMatchImage1);
        recommendMatchImage2=findViewById(R.id.recommendMatchImage2);
        recommendMatchImage3=findViewById(R.id.recommendMatchImage3);
        recommendMatchImage4=findViewById(R.id.recommendMatchImage3);
        recommendMatchName1=findViewById(R.id.recommendMatchName1);
        recommendMatchName2=findViewById(R.id.recommendMatchName2);
        recommendMatchName3=findViewById(R.id.recommendMatchName3);
        recommendMatchName4=findViewById(R.id.recommendMatchName4);

        chatRequestBtn=findViewById(R.id.chatRequestBtn);*/

        noneRecommendTxt=findViewById(R.id.noneRecommendTxt);
        recommendMatchBackBtn=findViewById(R.id.recommendMatchBackBtn);
        recyclerView = findViewById(R.id.recycler_recommend_ideal);


        firstIdealUsers = (ArrayList<UserInfo>) getIntent().getSerializableExtra("firstIdealUsers");
        secondIdealUsers = (ArrayList<UserInfo>) getIntent().getSerializableExtra("secondIdealUsers");
        thirdIdealUsers = (ArrayList<UserInfo>) getIntent().getSerializableExtra("thirdIdealUsers");
        if(firstIdealUsers.isEmpty()&&secondIdealUsers.isEmpty()&&thirdIdealUsers.isEmpty()){
            noneRecommendTxt.setVisibility(View.VISIBLE);
        } else{
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new GridSpaceDecoration(2,100,20));
            recyclerView.setAdapter(new RecyclerRecommendIdealAdapter(this,firstIdealUsers,secondIdealUsers,thirdIdealUsers));
        }
        Log.d(TAG, "initializeView: "+firstIdealUsers.size()+secondIdealUsers.size()+thirdIdealUsers.size());

    }

    private void initializeListener(){
        recommendMatchBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}