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

public class GetRequestActivity extends AppCompatActivity {

    ImageButton getRequestBackBtn;
    RecyclerView recycler_get_request;
    ArrayList<String> getRequestUids;
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
        getRequestUids= (ArrayList<String>)getIntent().getSerializableExtra("getRequestUids");

        if(getRequestUids==null||getRequestUids.isEmpty()){
            noneRequestUserlayout.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "oGetRequestActivity: "+getRequestUids.size());
        recycler_get_request.setLayoutManager(new LinearLayoutManager(this));
        recycler_get_request.setAdapter(new RecyclerGetRequestAdapter(this,getRequestUids));

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