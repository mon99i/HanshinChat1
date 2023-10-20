package com.example.hanshinchat1;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RoomActivity extends MainActivity {

    Dialog findRoomDialog, findRoomDialog2;
    Dialog makeRoomDialog;
    Dialog makeRoom1Dialog;
    Dialog makeRoom2Dialog;
    Dialog makeRoom3Dialog;
    RecyclerView recycler_matchRooms;
    RecyclerMatchRoomsAdapter recyclerMatchRoomsAdapter;
    public static String[] participants = {"1명", "2명", "3명", "4명", "5명", "6명", "7명", "8명"};
    public static String[] gender = {"남자", "여자"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.room);

        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();

        Button makeRoom = findViewById(R.id.make_room);
        Button findRoom = findViewById(R.id.find_room);
        Button findRoom2 = findViewById(R.id.find_room2);
        recycler_matchRooms=findViewById(R.id.recycler_matchRooms);

        makeRoomDialog = new Dialog(RoomActivity.this);
        makeRoomDialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        makeRoomDialog.setContentView(R.layout.make_room_dialog);

        findRoomDialog = new Dialog(RoomActivity.this);
        findRoomDialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        findRoomDialog.setContentView(R.layout.find_room_dialog);

        findRoomDialog2 = new Dialog(RoomActivity.this);
        findRoomDialog2.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        findRoomDialog2.setContentView(R.layout.find_room2_dialog);

        recyclerMatchRoomsAdapter = new RecyclerMatchRoomsAdapter(this); // Create the adapter here
        setUpRecycler();

        makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRoomDialog.show();

                Button makeCategory1Btn = makeRoomDialog.findViewById(R.id.make_category1);
                Button makeCategory2Btn = makeRoomDialog.findViewById(R.id.make_category2);
                Button makeCategory3Btn = makeRoomDialog.findViewById(R.id.make_category3);
                Button makeCategory4Btn = makeRoomDialog.findViewById(R.id.make_category4);

                makeCategory1Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        makeRoomDialog.dismiss();
                        makeRoom1();
                    }
                });
                makeCategory2Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        makeRoomDialog.dismiss();
                        makeRoom2();
                    }
                });
                makeCategory3Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        makeRoomDialog.dismiss();
                        makeRoom3();
                    }
                });
                makeCategory4Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeRoomDialog.dismiss();
                        makeRoom4();
                    }
                });
            }
        });

        findRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findRoomDialog.show();

                Button findRoomSearchBtn = findRoomDialog.findViewById(R.id.findRoomSearchBtn);
                findRoomSearchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //검색 눌렀을 때 기능 구현

                        findRoomDialog.dismiss();
                    }
                });
            }
        });

        findRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findRoomDialog2.show();

                Button category1Btn = findRoomDialog2.findViewById(R.id.category1);
                Button category2Btn = findRoomDialog2.findViewById(R.id.category2);
                Button category3Btn = findRoomDialog2.findViewById(R.id.category3);
                Button category4Btn = findRoomDialog2.findViewById(R.id.category4);

                category1Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerMatchRoomsAdapter.setSelectedCategory("과팅");
                        recyclerMatchRoomsAdapter.notifyDataSetChanged();
                        findRoomDialog2.dismiss();
                    }
                });
                category2Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerMatchRoomsAdapter.setSelectedCategory("미팅");
                        recyclerMatchRoomsAdapter.notifyDataSetChanged();
                        findRoomDialog2.dismiss();
                    }
                });
                category3Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerMatchRoomsAdapter.setSelectedCategory("밥팅");
                        recyclerMatchRoomsAdapter.notifyDataSetChanged();
                        findRoomDialog2.dismiss();
                    }
                });
                category4Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerMatchRoomsAdapter.setSelectedCategory("기타");
                        recyclerMatchRoomsAdapter.notifyDataSetChanged();
                        findRoomDialog2.dismiss();
                    }
                });
            }
        });
    }

    private void setUpRecycler() {
        recycler_matchRooms.setLayoutManager(new LinearLayoutManager(this));
        recycler_matchRooms.setAdapter(recyclerMatchRoomsAdapter);
    }


    private void makeRoom1() {
        makeRoom1Dialog = new Dialog(RoomActivity.this);
        makeRoom1Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom1Dialog.setContentView(R.layout.make_room1);
        makeRoom1Dialog.show();

        EditText edt_roomTitle = makeRoom1Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom1Dialog.findViewById(R.id.btn_makeRoom);
        Spinner participants_Spinner = makeRoom1Dialog.findViewById(R.id.participants_spinner);
        Spinner department_Spinner = makeRoom1Dialog.findViewById(R.id.department_spinner);
        Spinner gender_Spinner = makeRoom1Dialog.findViewById(R.id.gender_spinner);
        String selectedCategory = "과팅";
        Resources res = getResources();
        String[] department = res.getStringArray(R.array.학과);

        ArrayAdapter<String> participants_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, participants);
        ArrayAdapter<String> department_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, department);
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, gender);

        participants_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        participants_Spinner.setAdapter(participants_adapter);
        department_Spinner.setAdapter(department_adapter);
        gender_Spinner.setAdapter(gender_adapter);

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo =new RoomInfo();
                roomInfo.setHost(user.getUid());
                roomInfo.setNum(participants_Spinner.getSelectedItem().toString());
                roomInfo.setDepartment(department_Spinner.getSelectedItem().toString());
                roomInfo.setGender(gender_Spinner.getSelectedItem().toString());
                roomInfo.setTitle(edt_roomTitle.getText().toString());
                roomInfo.setCategory(selectedCategory);

                MatchRoom matchRoom=new MatchRoom(roomInfo,null);

                myRef.child("matchRooms").push().setValue(matchRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        makeRoom1Dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),RoomActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
    private void makeRoom2() {
        makeRoom2Dialog = new Dialog(RoomActivity.this);
        makeRoom2Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom2Dialog.setContentView(R.layout.make_room2);
        makeRoom2Dialog.show();

        EditText edt_roomTitle = makeRoom2Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom2Dialog.findViewById(R.id.btn_makeRoom);
        Spinner gender_Spinner = makeRoom2Dialog.findViewById(R.id.gender_spinner);
        String selectedCategory = "미팅";
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, gender);

        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_Spinner.setAdapter(gender_adapter);

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo =new RoomInfo();
                roomInfo.setHost(user.getUid());
                roomInfo.setGender(gender_Spinner.getSelectedItem().toString());
                roomInfo.setTitle(edt_roomTitle.getText().toString());
                roomInfo.setCategory(selectedCategory);

                MatchRoom matchRoom=new MatchRoom(roomInfo,null);

                myRef.child("matchRooms").push().setValue(matchRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        makeRoom2Dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),RoomActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    private void makeRoom3() {
        makeRoom2Dialog = new Dialog(RoomActivity.this);
        makeRoom2Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom2Dialog.setContentView(R.layout.make_room2);
        makeRoom2Dialog.show();

        EditText edt_roomTitle = makeRoom2Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom2Dialog.findViewById(R.id.btn_makeRoom);
        Spinner gender_Spinner = makeRoom2Dialog.findViewById(R.id.gender_spinner);
        String selectedCategory = "밥팅";
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, gender);

        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_Spinner.setAdapter(gender_adapter);

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo =new RoomInfo();
                roomInfo.setHost(user.getUid());
                roomInfo.setGender(gender_Spinner.getSelectedItem().toString());
                roomInfo.setTitle(edt_roomTitle.getText().toString());
                roomInfo.setCategory(selectedCategory);

                MatchRoom matchRoom=new MatchRoom(roomInfo,null);

                myRef.child("matchRooms").push().setValue(matchRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        makeRoom2Dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),RoomActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }


    private void makeRoom4() {
        makeRoom3Dialog = new Dialog(RoomActivity.this);
        makeRoom3Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom3Dialog.setContentView(R.layout.make_room3);
        makeRoom3Dialog.show();

        EditText edt_roomTitle = makeRoom3Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom3Dialog.findViewById(R.id.btn_makeRoom);
        Spinner participants_Spinner = makeRoom3Dialog.findViewById(R.id.participants_spinner);
        Spinner gender_Spinner = makeRoom3Dialog.findViewById(R.id.gender_spinner);
        String selectedCategory = "기타";

        ArrayAdapter<String> participants_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, participants);
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, gender);

        participants_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        participants_Spinner.setAdapter(participants_adapter);
        gender_Spinner.setAdapter(gender_adapter);

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomInfo roomInfo =new RoomInfo();
                roomInfo.setHost(user.getUid());
                roomInfo.setNum(participants_Spinner.getSelectedItem().toString());
                roomInfo.setGender(gender_Spinner.getSelectedItem().toString());
                roomInfo.setTitle(edt_roomTitle.getText().toString());
                roomInfo.setCategory(selectedCategory);

                MatchRoom matchRoom=new MatchRoom(roomInfo,null);

                myRef.child("matchRooms").push().setValue(matchRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        makeRoom3Dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),RoomActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }
}
