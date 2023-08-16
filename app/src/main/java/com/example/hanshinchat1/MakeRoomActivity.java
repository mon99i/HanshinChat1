package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MakeRoomActivity extends MainActivity {


    EditText edt_roomTitle;
    RadioGroup cateogry_radio_group;
    RadioGroup gender_radio_group;
    Button btn_makeRoom;;
    Spinner participants_Spinner;
    Spinner department_Spinner;

    String selectedGender;
    String selectedCategory;
    RadioButton radioButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_room);

        clickHome();
        clickRoom();
        clickChat();
        clickBoard();

        clickProfile();
        initializeView();
        initializeListener();

        participants_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        participants_Spinner=findViewById(R.id.participants_spinner);
        department_Spinner=findViewById(R.id.department_spinner);

        String[] participants = {"1명", "2명", "3명", "4명"};
        String[] department = {"컴퓨터공학", "물리학", "영문영문과", "전자공학"};

        // ArrayAdapter를 사용하여 어댑터 초기화
        ArrayAdapter<String> participants_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, participants);
        ArrayAdapter<String> department_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, department);


        // 드롭다운 스피너에 표시할 레이아웃 지정
        participants_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 어댑터 설정
        participants_Spinner.setAdapter(participants_adapter);
        department_Spinner.setAdapter(department_adapter);


        edt_roomTitle=findViewById(R.id.edt_roomTitle);
        cateogry_radio_group=findViewById(R.id.category_radio_group);
        gender_radio_group=findViewById(R.id.gender_radio_group);
        btn_makeRoom=findViewById(R.id.btn_makeRoom);
    }

    private void initializeListener(){
        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeRoom();
            }
        });

        gender_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.female_radio_button:
                        radioButton=findViewById(R.id.female_radio_button);
                        selectedGender=radioButton.getText().toString();

                        break;
                    case R.id.male_radio_button:
                        radioButton=findViewById(R.id.male_radio_button);
                        selectedGender=radioButton.getText().toString();
                        break;
                }
            }
        });

        cateogry_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.meeting_radio_button:
                        radioButton=findViewById(R.id.meeting_radio_button);
                        selectedCategory=radioButton.getText().toString();
                    break;
                    case R.id.groupMeeting_radio_button:
                        radioButton=findViewById(R.id.groupMeeting_radio_button);
                        selectedCategory=radioButton.getText().toString();
                        break;
                    case R.id.lunch_radio_button:
                        radioButton=findViewById(R.id.lunch_radio_button);
                        selectedCategory=radioButton.getText().toString();
                        break;
                    case R.id.other_radio_button:
                        radioButton=findViewById(R.id.other_radio_button);
                        selectedCategory=radioButton.getText().toString();
                        break;

                }
            }
        });

    }

    private void makeRoom() {

        participants_Spinner=findViewById(R.id.participants_spinner);
        department_Spinner=findViewById(R.id.department_spinner);

        edt_roomTitle=findViewById(R.id.edt_roomTitle);
        cateogry_radio_group=findViewById(R.id.category_radio_group);
        gender_radio_group=findViewById(R.id.gender_radio_group);

        RoomInfo roomInfo =new RoomInfo();
        roomInfo.setHost(user.getUid());
        roomInfo.setNum(participants_Spinner.getSelectedItem().toString());
        roomInfo.setDepartment(department_Spinner.getSelectedItem().toString());
        roomInfo.setTitle(edt_roomTitle.getText().toString());
        roomInfo.setGender(selectedGender);
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
