package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanshinchat1.MainMenu.MainBoardFragment;
import com.example.hanshinchat1.MainMenu.MainChatFragment;
import com.example.hanshinchat1.MainMenu.MainHomeFragment;
import com.example.hanshinchat1.MainMenu.MainProfileFragment;
import com.example.hanshinchat1.MainMenu.MainRoomFragment;
import com.example.hanshinchat1.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {
    private static final String TAG = "MainMenuActivity";
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MainHomeFragment fragmentHome = new MainHomeFragment();
    private MainRoomFragment fragmentRoom = new MainRoomFragment();
    private MainChatFragment fragmentChat = new MainChatFragment();
    private MainBoardFragment fragmentBoard = new MainBoardFragment();
    private MainProfileFragment fragmentProfile = new MainProfileFragment();
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        int show_fragment = getIntent().getIntExtra("show_fragment", 1);

        if (show_fragment != -1) {
            switch (show_fragment) {
                case 1:
                    bottomNavigationView.setSelectedItemId(R.id.menu_home);
                    break;
                case 2:
                    bottomNavigationView.setSelectedItemId(R.id.menu_room);
                    break;
                case 3:
                    bottomNavigationView.setSelectedItemId(R.id.menu_chat);
                    break;
                case 4:
                    bottomNavigationView.setSelectedItemId(R.id.menu_board);
                    break;
                case 5:
                    bottomNavigationView.setSelectedItemId(R.id.menu_profile);
                    break;
            }
        }


        checkNewRequest();


    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    transaction.replace(R.id.menu_frame_layout, fragmentHome).commitAllowingStateLoss();
                    break;
                case R.id.menu_room:
                    transaction.replace(R.id.menu_frame_layout,new MainRoomFragment()).commitAllowingStateLoss();
                    break;
                case R.id.menu_chat:
                    transaction.replace(R.id.menu_frame_layout, fragmentChat).commitAllowingStateLoss();
                    break;
                case R.id.menu_board:
                    transaction.replace(R.id.menu_frame_layout, fragmentBoard).commitAllowingStateLoss();
                    break;
                case R.id.menu_profile:
                    transaction.replace(R.id.menu_frame_layout, fragmentProfile).commitAllowingStateLoss();
                    break;

            }
            return true;
        }
    }


    private void checkNewRequest() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference().child("rooms").orderByChild("host").equalTo(user.getUid());

        ArrayList<Match> newMatches = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("matches")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        newMatches.clear();
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot roomSnapshot) {
                                if (roomSnapshot.exists()) {        //내가만든방이있다면 해당 방 매칭기록 조회
                                    for (DataSnapshot item : roomSnapshot.getChildren()) {
                                        String myRoomKey = item.getKey();
                                        for (DataSnapshot subSnapshot : snapshot.child("rooms").child(myRoomKey).getChildren()) {
                                            Match match = subSnapshot.getValue(Match.class);
                                            if (match.getRequest() == true) {
                                                newMatches.add(match);

                                            }
                                        }

                                    }

                                }
                                for (DataSnapshot subSnapshot : snapshot.child("users").child(user.getUid()).getChildren()) {
                                    Match match = subSnapshot.getValue(Match.class);
                                    if (match.getRequest() == true) {
                                        newMatches.add(match);

                                    }
                                }
                                Log.d(TAG, "matchsize" + newMatches.size());

                                if (newMatches.size() > 0) {
                                    Log.d("newMatches.size() > 0", "onDataChange: " + newMatches.size());
                                    showNewRequestDialog();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }




    //채팅요청이 왔다는 다이얼로그
    private void showNewRequestDialog() {
        if(!isFinishing()){
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.new_request_dialog, null);
            ConstraintLayout layout = view.findViewById(R.id.alert_layout);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();

            alertDialog.getWindow().setGravity(Gravity.TOP); //상단에 위치
            alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //밖에 배경 어둡지않게
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));  // 배경 투명하게

            // 다이얼로그 표시
            alertDialog.show();

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //창 누르면 확인했으므로 다시 안뜨게
                    Utils.goToGetRequestActivity(context);
                    alertDialog.dismiss();
                }
            });
        }



    }
}
