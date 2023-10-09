package com.example.hanshinchat1;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MakeRoomActivity2 extends MainActivity {


    EditText edt_roomTitle;
    Button btn_makeRoom;;
    Spinner department_Spinner;
    String selectedCategory = "미팅";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_room2);

        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();

        initializeView();
        initializeListener();

        department_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeView(){
        department_Spinner=findViewById(R.id.department_spinner);
        Resources res = getResources();

        String[] department = res.getStringArray(R.array.학과);

        ArrayAdapter<String> department_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, department);

        department_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 어댑터 설정
        department_Spinner.setAdapter(department_adapter);

        edt_roomTitle=findViewById(R.id.edt_roomTitle);
        btn_makeRoom=findViewById(R.id.btn_makeRoom);
    }

    private void initializeListener(){
        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeRoom();
            }
        });

    }

    private void makeRoom() {

        department_Spinner=findViewById(R.id.department_spinner);

        edt_roomTitle=findViewById(R.id.edt_roomTitle);
        RoomInfo roomInfo =new RoomInfo();
        roomInfo.setHost(user.getUid());
        roomInfo.setDepartment(department_Spinner.getSelectedItem().toString());
        roomInfo.setTitle(edt_roomTitle.getText().toString());
        roomInfo.setCategory(selectedCategory);

        MatchRoom matchRoom=new MatchRoom(roomInfo,null);


        myRef.child("matchRooms").push().setValue(matchRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent=new Intent(getApplicationContext(),RoomActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
