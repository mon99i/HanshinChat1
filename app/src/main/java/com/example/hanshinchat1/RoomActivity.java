package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class RoomActivity extends MainActivity {

    Dialog findRoomDialog;
    Dialog findRoomDialog2;

    RecyclerView recycler_matchRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.room);

        clickMenu();
        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();
        setUpRecycler();

        Button makeRoom = findViewById(R.id.make_room);
        Button findRoom = findViewById(R.id.find_room);
        Button findRoom2 = findViewById(R.id.find_room2);
        recycler_matchRooms=findViewById(R.id.recycler_matchRooms);

        findRoomDialog = new Dialog(RoomActivity.this);
        findRoomDialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        findRoomDialog.setContentView(R.layout.find_room_dialog);

        findRoomDialog2 = new Dialog(RoomActivity.this);
        findRoomDialog2.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        findRoomDialog2.setContentView(R.layout.find_room2_dialog);

        makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeRoomActivity.class);
                startActivity(intent);
                finish();
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
                        //과팅 찾기 눌렀을 때 기능 구현



                        findRoomDialog2.dismiss();
                    }
                });
                category2Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //미팅 찾기 눌렀을 때 기능 구현



                        findRoomDialog2.dismiss();
                    }
                });
                category3Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //밥팅 찾기 눌렀을 때 기능 구현



                        findRoomDialog2.dismiss();
                    }
                });
                category4Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //기타 찾기 눌렀을 때 기능 구현



                        findRoomDialog2.dismiss();
                    }
                });
            }
        });
    }

    private void setUpRecycler() {
    }
}
