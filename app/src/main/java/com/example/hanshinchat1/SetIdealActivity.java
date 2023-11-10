package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedHashMap;
import java.util.Map;

public class SetIdealActivity extends MainActivity {

    TextView firstPriorityTxt;
    TextView firstChoiceTxt;
    TextView secondPriorityTxt;
    TextView secondChoiceTxt;
    TextView thirdPriorityTxt;
    TextView thirdChoiceTxt;
    ImageButton idealCancelBtn1;
    ImageButton idealCancelBtn2;
    ImageButton idealCancelBtn3;

    ImageButton backBtn;


    private Map<String, String> idealMap = new LinkedHashMap<String, String>() {{
        put("age", "나이");
        put("address", "거주지");
        put("department", "학과");
        put("form", "체형");
        put("drinking", "음주");
        put("height", "키");
        put("interest", "관심사");
        put("personality", "성격");
        put("religion", "종교");
        put("smoking", "흡연");

    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_ideal);

        clickBackBtn();

        initializeView();
        setupPriority();
        initializeListener();
    }

    private void clickBackBtn() {
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("show_fragment", 5);
                startActivity(intent);
            }
        });
    }


    private void initializeView() {

        firstPriorityTxt = findViewById(R.id.firstPriorityTxt);
        firstChoiceTxt = findViewById(R.id.firstChoiceTxt);
        secondPriorityTxt = findViewById(R.id.secondPriorityTxt);
        secondChoiceTxt = findViewById(R.id.secondChoiceTxt);
        thirdPriorityTxt = findViewById(R.id.thirdPrioirtyTxt);
        thirdChoiceTxt = findViewById(R.id.thirdChoiceTxt);

        idealCancelBtn1=findViewById(R.id.idealCancelBtn1);
        idealCancelBtn2=findViewById(R.id.idealCancelBtn2);
        idealCancelBtn3=findViewById(R.id.idealCancelBtn3);

        //글자아래에 밑줄
        firstChoiceTxt.setPaintFlags(firstChoiceTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        secondChoiceTxt.setPaintFlags(secondChoiceTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        thirdChoiceTxt.setPaintFlags(thirdChoiceTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void setupPriority(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference priorityRef= FirebaseDatabase.getInstance().getReference().child("ideals")
                .child(user.getUid());
        priorityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    Ideal ideal=snapshot.getValue(Ideal.class);
                    for(String priority1:ideal.getPriority1().keySet()) {
                        firstPriorityTxt.setText(idealMap.get(priority1));           //선택한 ideal이 순위에 뜨게
                        firstChoiceTxt.setVisibility(View.INVISIBLE);                 //선택하기 안보이게
                        firstChoiceTxt.setEnabled(false);                             //선택하기 비활성화
                        idealCancelBtn1.setVisibility(View.VISIBLE);                  //새로운 x버튼 보이게
                        idealCancelBtn1.setEnabled(true);                             //새로운 x버튼 클릭가능

                    }
                    for(String priority2:ideal.getPriority2().keySet()) {
                        secondPriorityTxt.setText(idealMap.get(priority2));
                        secondChoiceTxt.setVisibility(View.INVISIBLE);
                        secondChoiceTxt.setEnabled(false);
                        idealCancelBtn2.setVisibility(View.VISIBLE);
                        idealCancelBtn2.setEnabled(true);

                    }
                    for(String priority3:ideal.getPriority3().keySet()) {
                        thirdPriorityTxt.setText(idealMap.get(priority3));
                        thirdChoiceTxt.setVisibility(View.INVISIBLE);
                        thirdChoiceTxt.setEnabled(false);
                        idealCancelBtn3.setVisibility(View.VISIBLE);
                        idealCancelBtn3.setEnabled(true);

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeListener() {

        //글자 누르면 이벤트발생
        firstChoiceTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //우선순위 int값 1 넘겨줌
                showPriorityDialog(1,idealMap);
                // showPriorityDialog(1);

            }
        });

        secondChoiceTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //우선순위로 int값 2 넘겨줌
                showPriorityDialog(2,idealMap);

            }
        });

        thirdChoiceTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //우선순위로 int값 2 넘겨줌
                showPriorityDialog(3,idealMap);

            }
        });
        idealCancelBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIdeal(1);

            }
        });

        idealCancelBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIdeal(2);

            }
        });

        idealCancelBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIdeal(3);

            }
        });

    }

    private void showPriorityDialog(int priority,Map<String,String> idealMap) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View view=inflater.inflate(R.layout.ideal_prioity_dialog, null);
        RecyclerView recyclerView=view.findViewById(R.id.recycler_idealPriority);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        builder.setView(view);

        AlertDialog dialog = builder.create();
        recyclerView.setAdapter(new RecyclerIdealAdapter(this,priority,idealMap,dialog));


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();
    }


    private void deleteIdeal(int priority){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference priorityRef= FirebaseDatabase.getInstance().getReference().child("ideals")
                .child(user.getUid());

        priorityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    switch (priority){
                        case 1:
                            priorityRef.child("priority1").removeValue();
                            firstPriorityTxt.setText("");                            //선택한 ideal이 순위에 뜨게
                            firstChoiceTxt.setVisibility(View.VISIBLE);                 //선택하기 보이게
                            firstChoiceTxt.setEnabled(true);                             //선택하기 활성화
                            idealCancelBtn1.setVisibility(View.INVISIBLE);                  //x버튼 안보이게
                            idealCancelBtn1.setEnabled(false);                             //x버튼 비활성화

                            break;
                        case 2:
                            priorityRef.child("priority2").removeValue();
                            secondPriorityTxt.setText("");
                            secondChoiceTxt.setVisibility(View.VISIBLE);
                            secondChoiceTxt.setEnabled(true);
                            idealCancelBtn2.setVisibility(View.INVISIBLE);
                            idealCancelBtn2.setEnabled(false);
                            break;

                        case 3:
                            priorityRef.child("priority3").removeValue();
                            thirdPriorityTxt.setText("");
                            thirdChoiceTxt.setVisibility(View.VISIBLE);
                            thirdChoiceTxt.setEnabled(true);
                            idealCancelBtn3.setVisibility(View.INVISIBLE);
                            idealCancelBtn3.setEnabled(false);

                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}