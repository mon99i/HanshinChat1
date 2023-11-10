package com.example.hanshinchat1.MainMenu;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanshinchat1.R;
import com.example.hanshinchat1.RecyclerMatchRoomsAdapter;
import com.example.hanshinchat1.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainRoomFragment extends Fragment {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final String TAG = "RoomActivity";
    private Dialog findRoomDialog, findRoomDialog2;
    private Dialog makeRoomDialog;
    private Dialog makeRoom1Dialog;
    private Dialog makeRoom2Dialog;
    private CheckBox departmentCheckbox;
    private Button makeRoom;
    private Button findRoom;
    private RecyclerView recycler_matchRooms;
    private RecyclerMatchRoomsAdapter recyclerMatchRoomsAdapter;
    private boolean checkBoxChecked = false;
    private String selectedCategory = null;
    public static String[] participants1 = {"1명", "2명", "3명", "4명", "5명", "6명", "7명", "8명"};
    public static String[] participants2 = {"2명", "3명", "4명", "5명", "6명", "7명", "8명"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.roomfragment, container, false);
        getView();
        initializeView(view);
        initializeListener();

        return view;

    }

    private void initializeView(View view) {
        recycler_matchRooms = view.findViewById(R.id.recycler_matchRooms);
        recyclerMatchRoomsAdapter = new RecyclerMatchRoomsAdapter(getContext(), recycler_matchRooms);
        recycler_matchRooms.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_matchRooms.setAdapter(recyclerMatchRoomsAdapter);

        departmentCheckbox = view.findViewById(R.id.department_checkbox);
        makeRoom = view.findViewById(R.id.make_room);
        findRoom = view.findViewById(R.id.find_room);

        makeRoomDialog = new Dialog(getContext(), R.style.RoundedDialog);
        makeRoomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoomDialog.setContentView(R.layout.make_room_dialog);

        findRoomDialog = new Dialog(getContext(), R.style.RoundedDialog);
        findRoomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        findRoomDialog.setContentView(R.layout.find_room_dialog);
    }

    private void initializeListener() {
        departmentCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxChecked = isChecked;
                recyclerMatchRoomsAdapter.setUpRooms(selectedCategory, checkBoxChecked);
                Log.d(TAG, "onCheckedChanged: " + selectedCategory + " " + checkBoxChecked);
            }
        });

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
                        selectedCategory = null;
                        recyclerMatchRoomsAdapter.setUpRooms(null, checkBoxChecked);
                        findRoomDialog.dismiss();
                    }
                });
                category1Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCategory = "과팅";
                        recyclerMatchRoomsAdapter.setUpRooms(selectedCategory, checkBoxChecked);
                        findRoomDialog.dismiss();

                    }
                });
                category2Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCategory = "미팅";
                        recyclerMatchRoomsAdapter.setUpRooms(selectedCategory, checkBoxChecked);
                        findRoomDialog.dismiss();

                    }
                });
                category3Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCategory = "밥팅";
                        recyclerMatchRoomsAdapter.setUpRooms(selectedCategory, checkBoxChecked);
                        findRoomDialog.dismiss();
                    }
                });
                category4Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCategory = "기타";
                        recyclerMatchRoomsAdapter.setUpRooms(selectedCategory, checkBoxChecked);
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


    }

    private void makeRoom1() {
        makeRoom1Dialog = new Dialog(getContext(), R.style.RoundedDialog);
        makeRoom1Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom1Dialog.setContentView(R.layout.make_room1);
        makeRoom1Dialog.show();

        EditText edt_roomTitle = makeRoom1Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom1Dialog.findViewById(R.id.btn_makeRoom);
        ImageView cancelBtn = makeRoom1Dialog.findViewById(R.id.make_room_cancel);
        Spinner participants_Spinner = makeRoom1Dialog.findViewById(R.id.participants_spinner);
        ImageView category = makeRoom1Dialog.findViewById(R.id.room_category);
        category.setBackgroundResource(R.drawable.icon2);
        String categoryType = "과팅";
        Resources res = getResources();

        ArrayAdapter<String> participants_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, participants2);
        participants_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        participants_Spinner.setAdapter(participants_adapter);

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Room room = new Room();
                room.setHost(user.getUid());
                room.setNum(participants_Spinner.getSelectedItem().toString());
                room.setTitle(edt_roomTitle.getText().toString());
                room.setCategory(categoryType);

                myRef.child("rooms").push().setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        makeRoom1Dialog.dismiss();
                        selectedCategory = null;
                        if (checkBoxChecked == false) {
                            recyclerMatchRoomsAdapter.setUpRooms(selectedCategory, checkBoxChecked);
                        } else departmentCheckbox.setChecked(false);
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
        makeRoom2Dialog = new Dialog(getContext(), R.style.RoundedDialog);
        makeRoom2Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom2Dialog.setContentView(R.layout.make_room2);
        makeRoom2Dialog.show();

        EditText edt_roomTitle = makeRoom2Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom2Dialog.findViewById(R.id.btn_makeRoom);
        ImageView cancelBtn = makeRoom2Dialog.findViewById(R.id.make_room_cancel);
        ImageView category = makeRoom2Dialog.findViewById(R.id.room_category);
        category.setBackgroundResource(R.drawable.icon1);
        String categoryType = "미팅";

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Room room = new Room();
                room.setHost(user.getUid());
                room.setTitle(edt_roomTitle.getText().toString());
                room.setCategory(categoryType);


                myRef.child("rooms").push().setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        makeRoom2Dialog.dismiss();
                        selectedCategory = null;
                        if (checkBoxChecked == false) {
                            recyclerMatchRoomsAdapter.setUpRooms(selectedCategory, checkBoxChecked);
                        } else departmentCheckbox.setChecked(false);
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
        makeRoom2Dialog = new Dialog(getContext(), R.style.RoundedDialog);
        makeRoom2Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom2Dialog.setContentView(R.layout.make_room2);
        makeRoom2Dialog.show();

        EditText edt_roomTitle = makeRoom2Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom2Dialog.findViewById(R.id.btn_makeRoom);
        ImageView cancelBtn = makeRoom2Dialog.findViewById(R.id.make_room_cancel);
        ImageView category = makeRoom2Dialog.findViewById(R.id.room_category);
        category.setBackgroundResource(R.drawable.icon3);
        String categoryType = "밥팅";

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Room room = new Room();
                room.setHost(user.getUid());
                room.setTitle(edt_roomTitle.getText().toString());
                room.setCategory(categoryType);


                myRef.child("rooms").push().setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        makeRoom2Dialog.dismiss();
                        selectedCategory = null;
                        if (checkBoxChecked == false) {
                            recyclerMatchRoomsAdapter.setUpRooms(selectedCategory, checkBoxChecked);
                        } else departmentCheckbox.setChecked(false);
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
        makeRoom1Dialog = new Dialog(getContext(), R.style.RoundedDialog);
        makeRoom1Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeRoom1Dialog.setContentView(R.layout.make_room1);
        makeRoom1Dialog.show();

        EditText edt_roomTitle = makeRoom1Dialog.findViewById(R.id.edt_roomTitle);
        Button btn_makeRoom = makeRoom1Dialog.findViewById(R.id.btn_makeRoom);
        ImageView cancelBtn = makeRoom1Dialog.findViewById(R.id.make_room_cancel);
        ImageView category = makeRoom1Dialog.findViewById(R.id.room_category);
        category.setBackgroundResource(R.drawable.icon4);
        Spinner participants_Spinner = makeRoom1Dialog.findViewById(R.id.participants_spinner);
        String categoryType = "기타";
        ArrayAdapter<String> participants_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, participants1);

        participants_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        participants_Spinner.setAdapter(participants_adapter);

        btn_makeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Room room = new Room();
                room.setHost(user.getUid());
                room.setNum(participants_Spinner.getSelectedItem().toString());
                room.setTitle(edt_roomTitle.getText().toString());
                room.setCategory(categoryType);


                myRef.child("rooms").push().setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        makeRoom1Dialog.dismiss();
                        selectedCategory = null;
                        if (checkBoxChecked == false) {
                            recyclerMatchRoomsAdapter.setUpRooms(selectedCategory, checkBoxChecked);
                        } else departmentCheckbox.setChecked(false);
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
