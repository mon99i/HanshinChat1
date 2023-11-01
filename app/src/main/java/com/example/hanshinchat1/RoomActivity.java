package com.example.hanshinchat1;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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


    private static final String TAG="RoomActivity";

    private ArrayList<Room> matchRoomsList;
    private ArrayList<String> matchKeyList;

    private Dialog findRoomDialog, findRoomDialog2;
    private Dialog makeRoomDialog;
    private Dialog makeRoom1Dialog;
    private Dialog makeRoom2Dialog;
    private CheckBox departmentCheckbox;
    private Button makeRoom;
    private Button findRoom;

    private RecyclerView recycler_matchRooms;
    private RecyclerMatchRoomsAdapter recyclerMatchRoomsAdapter;

    //private boolean isDepartmentFilterEnabled = false;
    private boolean checkBoxChecked = false;
    private String selectedCategory=null;
    public static String[] participants1 = {"1명", "2명", "3명", "4명", "5명", "6명", "7명", "8명"};
    public static String[] participants2 = {"2명", "3명", "4명", "5명", "6명", "7명", "8명"};
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

        initializeView();
        initializeListener();

    }
    private void initializeView() {
        matchRoomsList = new ArrayList<>();
        matchKeyList = new ArrayList<>();

        recycler_matchRooms=findViewById(R.id.recycler_matchRooms);
        recyclerMatchRoomsAdapter=new RecyclerMatchRoomsAdapter(this);
        recycler_matchRooms.setLayoutManager(new LinearLayoutManager(this));
        recycler_matchRooms.setAdapter(recyclerMatchRoomsAdapter);

        departmentCheckbox = findViewById(R.id.department_checkbox);
        makeRoom = findViewById(R.id.make_room);
        findRoom = findViewById(R.id.find_room);

        makeRoomDialog = new Dialog(RoomActivity.this, R.style.RoundedDialog);
        makeRoomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoomDialog.setContentView(R.layout.make_room_dialog);

        findRoomDialog = new Dialog(RoomActivity.this, R.style.RoundedDialog);
        findRoomDialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        findRoomDialog.setContentView(R.layout.find_room_dialog);

    }

    private void initializeListener() {
        departmentCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxChecked=isChecked;
                recyclerMatchRoomsAdapter.setUpRooms(selectedCategory,checkBoxChecked);
                Log.d(TAG, "onCheckedChanged: "+checkBoxChecked);
                /*if(checkBoxChecked){
                    if(selectedCategory!=null){ // 체크on, 카테고리on
                        recyclerMatchRoomsAdapter.loadAllMatchRooms();
                        recyclerMatchRoomsAdapter.filterMatchRoomsByDepartment();
                        recyclerMatchRoomsAdapter.removeMatchRoomsByCategory(selectedCategory);

                    }
                    else { // 체크on, 카테고리off
                        recyclerMatchRoomsAdapter.loadAllMatchRooms();
                        recyclerMatchRoomsAdapter.filterMatchRoomsByDepartment();

                    }

                    recyclerMatchRoomsAdapter.excludeSameDepartment();

                }
                else {
                    //recyclerMatchRoomsAdapter.setUpRooms(selectedCategory);
                    if(selectedCategory==null){
                        recyclerMatchRoomsAdapter.setUpRooms(null);
                    }else{
                        recyclerMatchRoomsAdapter.setUpRooms(selectedCategory);
                    }
                 *//*   if(selectedCategory!=null){ // 체크off, 카테고리on
                        recyclerMatchRoomsAdapter.loadAllMatchRooms();
                        recyclerMatchRoomsAdapter.filterMatchRoomsByCategory(selectedCategory);

                    }
                    else { // 체크off, 카테고리off
                        recyclerMatchRoomsAdapter.loadAllMatchRooms();
                        recyclerMatchRoomsAdapter.filterMatchRoomsByCategory(null);

                    }*//*
                }*/
            }
        });

      /*  departmentCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isDepartmentFilterEnabled = true;
                    if(selectedCategory!=null){ // 체크on, 카테고리on
                        recyclerMatchRoomsAdapter.loadAllMatchRooms();
                        recyclerMatchRoomsAdapter.filterMatchRoomsByDepartment();
                        recyclerMatchRoomsAdapter.removeMatchRoomsByCategory(selectedCategory);

                    }
                    else { // 체크on, 카테고리off
                        recyclerMatchRoomsAdapter.loadAllMatchRooms();
                        recyclerMatchRoomsAdapter.filterMatchRoomsByDepartment();

                    }
                }
                else {
                    isDepartmentFilterEnabled = false;
                    if(selectedCategory!=null){ // 체크off, 카테고리on
                        recyclerMatchRoomsAdapter.loadAllMatchRooms();
                        recyclerMatchRoomsAdapter.filterMatchRoomsByCategory(selectedCategory);

                    }
                    else { // 체크off, 카테고리off
                        recyclerMatchRoomsAdapter.loadAllMatchRooms();
                        recyclerMatchRoomsAdapter.filterMatchRoomsByCategory(null);

                    }
                }
            }
        });
*/

    /*    recyclerMatchRoomsAdapter.setOnItemClickListener(new RecyclerMatchRoomsAdapter.OnItemClickListener() {
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
        });*/

        makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRoomDialog.show();

                ImageButton makeCategory1Btn = makeRoomDialog.findViewById(R.id.make_category1);
                ImageButton makeCategory2Btn = makeRoomDialog.findViewById(R.id.make_category2);
                ImageButton makeCategory3Btn = makeRoomDialog.findViewById(R.id.make_category3);
                ImageButton makeCategory4Btn = makeRoomDialog.findViewById(R.id.make_category4);
                ImageView cancelBtn = makeRoomDialog.findViewById(R.id.make_room_cancel);


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
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeRoomDialog.dismiss();
                    }
                });
            }
        });


/*
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
*/


        findRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findRoomDialog.show();

                Button category0Btn = findRoomDialog.findViewById(R.id.category_all);
                ImageView category1Btn = findRoomDialog.findViewById(R.id.category1);
                ImageView category2Btn = findRoomDialog.findViewById(R.id.category2);
                ImageView category3Btn = findRoomDialog.findViewById(R.id.category3);
                ImageView category4Btn = findRoomDialog.findViewById(R.id.category4);
                ImageView cancelBtn = makeRoomDialog.findViewById(R.id.make_room_cancel);

                category0Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCategory=null;
                        recyclerMatchRoomsAdapter.setUpRooms(null,checkBoxChecked);
                        findRoomDialog.dismiss();
                    }
                });
                category1Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCategory="과팅";
                        recyclerMatchRoomsAdapter.setUpRooms(selectedCategory,checkBoxChecked);
                        findRoomDialog.dismiss();

                    }
                });
                category2Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCategory="미팅";
                        recyclerMatchRoomsAdapter.setUpRooms(selectedCategory,checkBoxChecked);
                        findRoomDialog.dismiss();

                    }
                });
                category3Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCategory="밥팅";
                        recyclerMatchRoomsAdapter.setUpRooms(selectedCategory,checkBoxChecked);
                        findRoomDialog.dismiss();
                    }
                });
                category4Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCategory="기타";
                        recyclerMatchRoomsAdapter.setUpRooms(selectedCategory,checkBoxChecked);
                       /* if(checkBoxChecked){
                            recyclerMatchRoomsAdapter.excludeSameDepartment();
                        }*/
                        findRoomDialog.dismiss();
                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findRoomDialog.dismiss();
                    }
                });
            }
        });



     /*   findRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findRoomDialog2.show();

                Button category0Btn = findRoomDialog2.findViewById(R.id.category_all);
                Button category1Btn = findRoomDialog2.findViewById(R.id.category1);
                Button category2Btn = findRoomDialog2.findViewById(R.id.category2);
                Button category3Btn = findRoomDialog2.findViewById(R.id.category3);
                Button category4Btn = findRoomDialog2.findViewById(R.id.category4);

                category0Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        departmentCheckbox.setChecked(false);
                        if(isDepartmentFilterEnabled = true){
                            recyclerMatchRoomsAdapter.setSelectedCategory(null);
                            findRoomDialog2.dismiss();
                        }
                        else {
                            recyclerMatchRoomsAdapter.filterMatchRoomsByDepartment();
                            findRoomDialog2.dismiss();
                        }
                    }
                });
                category1Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        departmentCheckbox.setChecked(false);
                        if(isDepartmentFilterEnabled = true){
                            recyclerMatchRoomsAdapter.setSelectedCategory("과팅");
                            findRoomDialog2.dismiss();
                        }
                        else{
                            recyclerMatchRoomsAdapter.setSelectedCategory("과팅");
                            recyclerMatchRoomsAdapter.removeMatchRoomsByDepartment();
                            findRoomDialog2.dismiss();
                        }
                    }
                });
                category2Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        departmentCheckbox.setChecked(false);
                        if(isDepartmentFilterEnabled = true){
                            recyclerMatchRoomsAdapter.setSelectedCategory("미팅");
                            findRoomDialog2.dismiss();
                        }
                        else{
                            recyclerMatchRoomsAdapter.setSelectedCategory("미팅");
                            recyclerMatchRoomsAdapter.removeMatchRoomsByDepartment();
                            findRoomDialog2.dismiss();
                        }
                    }
                });
                category3Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        departmentCheckbox.setChecked(false);
                        if(isDepartmentFilterEnabled = true){
                            recyclerMatchRoomsAdapter.setSelectedCategory("밥팅");
                            findRoomDialog2.dismiss();
                        }
                        else{
                            recyclerMatchRoomsAdapter.setSelectedCategory("밥팅");
                            recyclerMatchRoomsAdapter.removeMatchRoomsByDepartment();
                            findRoomDialog2.dismiss();
                        }
                    }
                });
                category4Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        departmentCheckbox.setChecked(false);
                        if(isDepartmentFilterEnabled = true){
                            recyclerMatchRoomsAdapter.setSelectedCategory("기타");
                            findRoomDialog2.dismiss();
                        }
                        else{
                            recyclerMatchRoomsAdapter.setSelectedCategory("기타");
                            recyclerMatchRoomsAdapter.removeMatchRoomsByDepartment();
                            findRoomDialog2.dismiss();
                        }
                    }
                });
            }
        });*/
    }



    /*private void showRecommendUserDialog(MatchRoom matchRoom){
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
    }*/



    private void makeRoom1() {
        makeRoom1Dialog = new Dialog(RoomActivity.this, R.style.RoundedDialog);
        makeRoom1Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom1Dialog.setContentView(R.layout.make_room1);
        makeRoom1Dialog.show();

        EditText edt_roomTitle = makeRoom1Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom1Dialog.findViewById(R.id.btn_makeRoom);
        ImageView cancelBtn = makeRoom1Dialog.findViewById(R.id.make_room_cancel);
        Spinner participants_Spinner = makeRoom1Dialog.findViewById(R.id.participants_spinner);
        ImageView category = makeRoom1Dialog.findViewById(R.id.room_category);
        category.setBackgroundResource(R.drawable.icon2);
        //Spinner department_Spinner = makeRoom1Dialog.findViewById(R.id.department_spinner);
        //Spinner gender_Spinner = makeRoom1Dialog.findViewById(R.id.gender_spinner);
        String selectedCategory = "과팅";
        Resources res = getResources();
        //String[] department = res.getStringArray(R.array.학과);

        ArrayAdapter<String> participants_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, participants2);
        //ArrayAdapter<String> department_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, department);
        //ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, gender);

        participants_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //department_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        participants_Spinner.setAdapter(participants_adapter);
        //department_Spinner.setAdapter(department_adapter);
        //gender_Spinner.setAdapter(gender_adapter);


        recyclerMatchRoomsAdapter=new RecyclerMatchRoomsAdapter(this);
        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Room room =new Room();
                room.setHost(user.getUid());
                room.setNum(participants_Spinner.getSelectedItem().toString());
                //room.setDepartment(department_Spinner.getSelectedItem().toString());
                //room.setGender(gender_Spinner.getSelectedItem().toString());
                room.setTitle(edt_roomTitle.getText().toString());
                room.setCategory(selectedCategory);

                myRef.child("rooms").push().setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     /*   makeRoom1Dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),RoomActivity.class);
                        startActivity(intent);
                        finish();*/
                        makeRoom1Dialog.dismiss();
                        recycler_matchRooms.setAdapter(recyclerMatchRoomsAdapter);
                        departmentCheckbox.setChecked(false);
                    }
                });
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRoom1Dialog.dismiss();
            }
        });
    }
    private void makeRoom2() {
        makeRoom2Dialog = new Dialog(RoomActivity.this, R.style.RoundedDialog);
        makeRoom2Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom2Dialog.setContentView(R.layout.make_room2);
        makeRoom2Dialog.show();

        EditText edt_roomTitle = makeRoom2Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom2Dialog.findViewById(R.id.btn_makeRoom);
        ImageView cancelBtn = makeRoom2Dialog.findViewById(R.id.make_room_cancel);
        ImageView category = makeRoom2Dialog.findViewById(R.id.room_category);
        category.setBackgroundResource(R.drawable.icon1);
        //Spinner gender_Spinner = makeRoom2Dialog.findViewById(R.id.gender_spinner);
        String selectedCategory = "미팅";
        //ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, gender);

        //gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //gender_Spinner.setAdapter(gender_adapter);

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Room room =new Room();
                room.setHost(user.getUid());
                //room.setGender(gender_Spinner.getSelectedItem().toString());
                room.setTitle(edt_roomTitle.getText().toString());
                room.setCategory(selectedCategory);


                myRef.child("rooms").push().setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRoom2Dialog.dismiss();
            }
        });
    }

    private void makeRoom3() {
        makeRoom2Dialog = new Dialog(RoomActivity.this, R.style.RoundedDialog);
        makeRoom2Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom2Dialog.setContentView(R.layout.make_room2);
        makeRoom2Dialog.show();

        EditText edt_roomTitle = makeRoom2Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom2Dialog.findViewById(R.id.btn_makeRoom);
        ImageView cancelBtn = makeRoom2Dialog.findViewById(R.id.make_room_cancel);
        ImageView category = makeRoom2Dialog.findViewById(R.id.room_category);
        category.setBackgroundResource(R.drawable.icon3);
        //Spinner gender_Spinner = makeRoom2Dialog.findViewById(R.id.gender_spinner);
        String selectedCategory = "밥팅";
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, gender);

        //gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //gender_Spinner.setAdapter(gender_adapter);

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Room room =new Room();
                room.setHost(user.getUid());
                //room.setGender(gender_Spinner.getSelectedItem().toString());
                room.setTitle(edt_roomTitle.getText().toString());
                room.setCategory(selectedCategory);


                myRef.child("rooms").push().setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRoom2Dialog.dismiss();
            }
        });
    }


    private void makeRoom4() {
        makeRoom1Dialog = new Dialog(RoomActivity.this, R.style.RoundedDialog);
        makeRoom1Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom1Dialog.setContentView(R.layout.make_room1);
        makeRoom1Dialog.show();

        EditText edt_roomTitle = makeRoom1Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom1Dialog.findViewById(R.id.btn_makeRoom);
        ImageView cancelBtn = makeRoom1Dialog.findViewById(R.id.make_room_cancel);
        ImageView category = makeRoom1Dialog.findViewById(R.id.room_category);
        category.setBackgroundResource(R.drawable.icon4);
        Spinner participants_Spinner = makeRoom1Dialog.findViewById(R.id.participants_spinner);
        //Spinner gender_Spinner = makeRoom3Dialog.findViewById(R.id.gender_spinner);
        String selectedCategory = "기타";

        ArrayAdapter<String> participants_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, participants1);
        //ArrayAdapter<String> gender_adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_spinner_item, gender);

        participants_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        participants_Spinner.setAdapter(participants_adapter);
        //gender_Spinner.setAdapter(gender_adapter);

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Room room =new Room();
                room.setHost(user.getUid());
                room.setNum(participants_Spinner.getSelectedItem().toString());
                //room.setGender(gender_Spinner.getSelectedItem().toString());
                room.setTitle(edt_roomTitle.getText().toString());
                room.setCategory(selectedCategory);


                myRef.child("rooms").push().setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRoom1Dialog.dismiss();
            }
        });

    }
}
