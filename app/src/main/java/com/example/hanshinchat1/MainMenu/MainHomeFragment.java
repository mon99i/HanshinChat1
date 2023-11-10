package com.example.hanshinchat1.MainMenu;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.Match;
import com.example.hanshinchat1.QuestionImageViewerDialog;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.simulation.Simulation0;
import com.example.hanshinchat1.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainHomeFragment extends Fragment {

    private static final String TAG = "채널생성실패";
    private Button idealMatchingBtn;
    private Button mbtiMatchingBtn;
    private Button aroundMatchingBtn;
    private Button recentRegisterMatchingBtn;
    private Button recentContectMatchingBtn;
    private Button topUserMatchingBtn;
    private ImageButton getRequestBtn;
    private int previousPhraseIndex = -1;
    private String[] phrases;
    private List<String> phrasesList;
    private Button speakerBtn;
    private ImageButton simulationBtn, questionBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment, container, false);


        checkNewRequest();

        idealMatchingBtn = view.findViewById(R.id.idealMatchingBtn);
        mbtiMatchingBtn = view.findViewById(R.id.mbtiMatchingBtn);
        aroundMatchingBtn = view.findViewById(R.id.aroundMatchingBtn);
        recentRegisterMatchingBtn = view.findViewById(R.id.recentRegisterMatchingBtn);
        recentContectMatchingBtn = view.findViewById(R.id.recentConnectMatchingBtn);
        topUserMatchingBtn = view.findViewById(R.id.topUserMatchingBtn);
        getRequestBtn = view.findViewById(R.id.getRequestBtn);

        initializeListener();

        // 상단 앱 관련 팁 기능
        phrases = getResources().getStringArray(R.array.random_speaker);
        phrasesList = Arrays.asList(phrases);
        speakerBtn = view.findViewById(R.id.home_speaker);
        speakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRandomPhrase();
            }
        });


        // 소개팅 시뮬레이션 기능
        simulationBtn = (ImageButton) view.findViewById(R.id.simulation);
        simulationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Simulation0.class);
                startActivity(intent);
            }
        });

        questionBtn = (ImageButton) view.findViewById(R.id.question);
        questionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionImageViewerDialog imageViewerDialog = new QuestionImageViewerDialog(getContext());
                imageViewerDialog.show();
            }
        });

        return view;
    }

    // 앱 관련 팁
    private void displayRandomPhrase() {
        int max = phrasesList.size();
        if (max == 0) {
            speakerBtn.setText("앱 이용 관련 팁");
            return;
        }
        Random random = new Random();
        int randomIndex;
        do {
            randomIndex = random.nextInt(max);
        } while (randomIndex == previousPhraseIndex);

        previousPhraseIndex = randomIndex;
        String randomPhrase = phrasesList.get(randomIndex);
        speakerBtn.setText(randomPhrase);
    }

    private void checkNewRequest() {
        ArrayList<String> matchKey = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference().child("rooms").orderByChild("host").equalTo(user.getUid());


        ArrayList<String> newRequestUids = new ArrayList<>();
        ArrayList<String> newMatchKeys = new ArrayList<>();
        ArrayList<Match> newMatches = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("matches")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        newRequestUids.clear();
                        newMatchKeys.clear();
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
                                                newRequestUids.add(subSnapshot.getKey());
                                                newMatchKeys.add(myRoomKey);
                                                newMatches.add(match);

                                            }
                                        }

                                    }

                                }
                                for (DataSnapshot subSnapshot : snapshot.child("users").child(user.getUid()).getChildren()) {
                                    Match match = subSnapshot.getValue(Match.class);
                                    if (match.getRequest() == true) {
                                        newRequestUids.add(subSnapshot.getKey());
                                        newMatchKeys.add(user.getUid());
                                        newMatches.add(match);

                                    }
                                }
                                Log.d(TAG, "onDataChange: " + newRequestUids.size() + " " + newMatchKeys.size() + " " + newMatches.size());

                                if (newMatches.size() > 0) {
                                    Log.d("왜???", "onDataChange: " + newMatches.size());
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


       /* ArrayList<String > myRoomKeys=new ArrayList<>();
        ArrayList<String> newUserRequestUids = new ArrayList<>();
        HashMap<String,ArrayList<String>> newRoomRequestUids=new HashMap<>();


        Query query=FirebaseDatabase.getInstance().getReference().child("rooms");
        FirebaseDatabase.getInstance().getReference().child("matches")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            *//*newUserRequestUids.clear();
                            newRoomRequestUids.clear();
                            Match match=snapshot.getValue(Match.class);
                            Map<String, State> users=match.getUsers();
                            Map<String, State> rooms=match.getRooms();

                            query .orderByChild("host").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot subSnapshot) {
                                    //내가만든 방이있으면 그 방 매칭기록 조회
                                    if(subSnapshot.exists()){
                                        myRoomKeys.clear();
                                        for(DataSnapshot item:subSnapshot.getChildren()){
                                            myRoomKeys.add(item.getKey());
                                        }
                                        for(String myRoomKey:myRoomKeys){
                                            State myRoomState=rooms.get(myRoomKey);
                                            if(myRoomState!=null){
                                                ArrayList<String> requestUids=new ArrayList<>();
                                                for(Map.Entry<String,Boolean> map:myRoomState.getRequest().entrySet()){
                                                    String uid=map.getKey();
                                                    Boolean request=map.getValue();
                                                    if(request==true){
                                                        requestUids.add(uid);
                                                    }
                                                }
                                                newRoomRequestUids.put(myRoomKey,requestUids);
                                            }

                                        }

                                    }

                                    //나한테 들어온 매칭 기록 조회
                                    State myMatchState=users.get(user.getUid());
                                    if(myMatchState!=null){
                                        for(Map.Entry<String,Boolean> map:myMatchState.getRequest().entrySet()){
                                            String uid=map.getKey();
                                            Boolean request=map.getValue();
                                            if(request==true){
                                                newUserRequestUids.add(uid);
                                            }
                                        }
                                    }

                                    if(!newUserRequestUids.isEmpty()||!newRoomRequestUids.isEmpty()){

                                        showNewRequestDialog();
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });*//*
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
*/

    }


    @Override
    public void onResume() {
        super.onResume();
        showNewRequestDialog();
    }


    //채팅요청이 왔다는 다이얼로그
    private void showNewRequestDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.new_request_dialog, null);
        ConstraintLayout layout = view.findViewById(R.id.alert_layout);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                Utils.goToGetRequestActivity(getContext());

                alertDialog.dismiss();
            }
        });


    }


    private void initializeListener() {
        idealMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.checkIdealExists(getContext());

            }
        });


        mbtiMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(getApplicationContext(), com.example.hanshinchat1.Match.MainActivity.class);
                startActivity(intent);*/
                Utils.MyUid(getContext());
            }
        });

        aroundMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.arroundMatching(getContext());

            }
        });

        recentRegisterMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.recentRegisterMatching(getContext());
            }
        });

        recentContectMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.recentConnectMatching(getContext());
            }
        });

        topUserMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.mostLikedMatching(getContext());
            }
        });


        getRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToGetRequestActivity(getContext());
            }
        });


    }


    //나중에쓸수있음
  /*  private void showNotification() {
        createNotificationChannel();  // notificatio 채널 생성

        RemoteViews remoteView= new RemoteViews(getPackageName(), R.layout.notification);

        // "수락" 버튼 클릭 시 실행될 Intent 및 PendingIntent 설정
        Intent acceptIntent = new Intent(this, MyBroadcastReceiver.class);
        acceptIntent.setAction("ACTION_ACCEPT");
        PendingIntent acceptPendingIntent =
                PendingIntent.getBroadcast(this, 0, acceptIntent, PendingIntent.FLAG_MUTABLE);
        remoteView.setOnClickPendingIntent(R.id.acceptButton,acceptPendingIntent);

        // "거절" 버튼 클릭 시 실행될 Intent 및 PendingIntent 설정
        Intent declineIntent=new Intent(this,MyBroadcastReceiver.class);
        declineIntent.setAction("ACTION_DECLINE");
        PendingIntent declinePendingIntent=
                PendingIntent.getBroadcast(this,0,declineIntent,PendingIntent.FLAG_MUTABLE);
        remoteView.setOnClickPendingIntent(R.id.declineButton,declinePendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCustomContentView(remoteView);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "showNotification: 실패");
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());


    }

    private void createNotificationChannel(){  //채널 먼저 생성해야함

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //String channelId = "my_channel_id";
            CharSequence channelName = "My Channel";   //채널이름
            String channelDescription = "My custom notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance); // Notification 채널 생성
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            //Log.d(TAG, "createNotificationChannel: 성공");
        }

    }
*/
}

