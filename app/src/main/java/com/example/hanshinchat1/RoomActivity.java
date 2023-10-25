package com.example.hanshinchat1;

import static com.example.hanshinchat1.CustomDialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hanshinchat1.Room.RoomInfoActivity;
import com.example.hanshinchat1.board.BoardActivity;
import com.example.hanshinchat1.viewpager.RecommendViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomActivity extends MainActivity {

    private ArrayList<MatchRoom> matchRoomsList;
    private ArrayList<String> matchKeyList;

    Dialog findRoomDialog, findRoomDialog2;
    Dialog makeRoomDialog;
    Dialog makeRoom1Dialog;
    Dialog makeRoom2Dialog;
    Dialog makeRoom3Dialog;
    RecyclerView recycler_matchRooms;
    RecyclerMatchRoomsAdapter recyclerMatchRoomsAdapter;
    public static String[] participants = {"1명", "2명", "3명", "4명", "5명", "6명", "7명", "8명"};
    public static String[] gender = {"남자", "여자"};
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.room);

        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();

        matchRoomsList = new ArrayList<>();
        matchKeyList = new ArrayList<>();


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


        recyclerMatchRoomsAdapter.setOnItemClickListener(new RecyclerMatchRoomsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                String matchkey = matchkeyList.get(position);
//                Intent intent = new Intent(getApplicationContext(), RoomInfoActivity.class);
//                intent.putExtra("key", matchkey);
//                startActivity(intent);

                MatchRoom matchRoom = matchRoomsList.get(position);

//                String matchkey = matchkeyList.get(position);
                showRecommendUserDialog(matchRoom);

            }
        });

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

    private void showRecommendUserDialog(MatchRoom matchRoom){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recommend_room_user_dialog, null);

        ViewPager2 recommendViewPager=view.findViewById(R.id.decisionViewPager);
        Button matchBtn=view.findViewById(R.id.matchBtn);
        TextView roomTitle=view.findViewById(R.id.roomTitle);

        roomTitle.setText(matchRoom.getRoomInfo().getTitle());
        recommendViewPager.setAdapter(new RecommendViewPagerAdapter((FragmentActivity) context, matchRoom));

        builder.setView(view);
        AlertDialog dialog = builder.create();
        //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();
        Log.w("RoomActivity", matchRoom.toString());



        matchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int adapterPosition = getAdapterPosition(); // 현재 아이템의 위치를 가져옵니다.
//                if (adapterPosition != RecyclerView.NO_POSITION) {
//                    MatchRoom matchRoom = matchRoomsList.get(adapterPosition);
//                    requestMatch(matchRoom); // 선택한 MatchRoom 객체를 전달
//                }
                requestMatch(recommendViewPager.getVerticalScrollbarPosition());
            }
        });

    }
    private void requestMatch(int position) {
        MatchRoom matchRoom = matchRoomsList.get(position);
        String matchKey = matchKeyList.get(position);

        String hostUid = matchRoom.getRoomInfo().getHost();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference matchInfoRef = FirebaseDatabase.getInstance().getReference().child("matchRooms").child(matchKey)
                .child("matchInfo").child(currentUid);

        if (!currentUid.equals(hostUid) && matchRoom != null) { //방만든애가 자기가 만든 방에 요청 안되게
            matchInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {  //매칭정보가 없을때 매칭요청가능하게, 이미 요청내역이 있을 경우 요청불가.
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue(MatchInfo.class) == null) {
                        MatchInfo matchInfo = new MatchInfo(true, null, null);
                        matchInfoRef.setValue(matchInfo);
                        Toast.makeText(context, "매칭 요청 완료!!", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(context, "이미 요청 하였습니다!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else Toast.makeText(context, "내가 만든방입니다.", Toast.LENGTH_SHORT).show();
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
