package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MakeRoomActivity extends MainActivity {


    EditText edt_roomTitle;
    RadioGroup cateogry_radio_group;
    RadioGroup gender_radio_group;
    Button btn_makeRoom;;
    Spinner participants_Spinner;
    Spinner department_Spinner;



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


                //makeRoom();
                Intent intent=new Intent(getApplicationContext(),RoomActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void makeRoom() {



    }
}
