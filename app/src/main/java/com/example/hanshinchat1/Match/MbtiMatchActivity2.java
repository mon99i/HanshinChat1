package com.example.hanshinchat1.Match;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.HomeActivity;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.UserInfo;
import com.example.hanshinchat1.recycler.GridSpaceDecoration;
import com.example.hanshinchat1.recycler.RecyclerRecommendMatchAdapter;

import java.util.ArrayList;

public class MbtiMatchActivity2 extends AppCompatActivity {
    private final static String TAG = "recommendMatch";
    /*
        private ImageView recommendMatchImage1;
        private ImageView recommendMatchImage2;
        private ImageView recommendMatchImage3;
        private ImageView recommendMatchImage4;
        private TextView recommendMatchName1;
        private TextView recommendMatchName2;
        private TextView recommendMatchName3;
        private TextView recommendMatchName4;

         private Button chatRequestBtn;
    */
    private ImageButton recommendMatchBackBtn;
    private TextView noneRecommendTxt;
    private RecyclerView recyclerView;
    private ArrayList<UserInfo> matchingUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbti_match);

        initializeView();
        initializeListener();

        //setUpUsers();
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


        matchingUsers = (ArrayList<UserInfo>) getIntent().getSerializableExtra("matchingUsers");
        if(matchingUsers==null||matchingUsers.isEmpty()){
            noneRecommendTxt.setVisibility(View.VISIBLE);
        }else{
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new GridSpaceDecoration(2,100,20));
            recyclerView.setAdapter(new RecyclerRecommendMatchAdapter(this,matchingUsers));
        }

    }

    private void setUpUsers(){
      /*  if(recommendUsers!=null){
            Uri imageUri = Uri.parse( recommendUsers.get(0).getPhotoUrl());
            Glide.with(this).load(imageUri).into(recommendMatchImage1);
            recommendMatchImage1.
            recommendMatchName1.setText(recommendUsers.get(0).getName());
        }else noneRecommendTxt.setVisibility(View.VISIBLE);*/

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