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

import com.example.hanshinchat1.recycler.RecyclerGetRequestAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class GetRequestActivity extends AppCompatActivity {

    ImageButton getRequestBackBtn;
    RecyclerView recycler_get_request;
    ArrayList<String> getUserRequestUids;
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
        getUserRequestUids= (ArrayList<String>)getIntent().getSerializableExtra("getUserRequestUids");
        getRoomRequestUids= (HashMap<String, ArrayList<String>>) getIntent().getSerializableExtra("getRoomRequestUids");
        if((getUserRequestUids==null&& getRoomRequestUids==null)||(getUserRequestUids.isEmpty()&& getRoomRequestUids.isEmpty())){
            noneRequestUserlayout.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "oGetRequestActivity: "+getUserRequestUids);
        Log.d(TAG, "oGetRequestActivity: "+ getRoomRequestUids);
        recycler_get_request.setLayoutManager(new LinearLayoutManager(this));
        recycler_get_request.setAdapter(new RecyclerGetRequestAdapter(this,getUserRequestUids,getRoomRequestUids));

    }

    private void initializeListener() {
        getRequestBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}