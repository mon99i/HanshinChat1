package com.example.hanshinchat1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.hanshinchat1.MainMenu.MainHomeFragment;
import com.example.hanshinchat1.recycler.RecyclerGetRequestAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class GetRequestActivity extends MainActivity {

    ImageButton getRequestBackBtn;
    RecyclerView recycler_get_request;
    ArrayList<String> getRequestUids;
    ArrayList<String> getMatchKeys;
    ArrayList<Match> getMatches;
    HashMap<String, ArrayList<String>> getRoomRequestUids;
    LinearLayout noneRequestUserlayout;

    private static final String TAG="GetRequestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_request);

        initializeView();
        initializeListener();

    }

    private void initializeView() {
        noneRequestUserlayout=findViewById(R.id.noneRequestUserLayout);
        getRequestBackBtn=findViewById(R.id.getRequestBackBtn);
        recycler_get_request=findViewById(R.id.recycler_get_request);
        /*getRequestUids= (ArrayList<String>)getIntent().getSerializableExtra("getRequestUids");
        getMatchKeys=(ArrayList<String>)getIntent().getSerializableExtra("getMatchKeys");*/
        getMatches=(ArrayList<Match>)getIntent().getSerializableExtra("getMatches");

        //getRoomRequestUids= (HashMap<String, ArrayList<String>>) getIntent().getSerializableExtra("getRoomRequestUids");
        if(getMatches.isEmpty()){
            noneRequestUserlayout.setVisibility(View.VISIBLE);
        }

        recycler_get_request.setLayoutManager(new LinearLayoutManager(this));
        recycler_get_request.setAdapter(new RecyclerGetRequestAdapter(this,getMatches));

    }

    private void initializeListener() {
        getRequestBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 1);
                startActivity(intent);
            }
        });
    }
}
