package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageButton;

//import com.example.hanshinchat1.Match.MBTIMatchActivity;
//import com.example.hanshinchat1.Match.MatchHome;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hanshinchat1.fragment.ShowUserFragment1;
import com.example.hanshinchat1.fragment.ShowUserFragment2;

import androidx.annotation.NonNull;

import com.example.hanshinchat1.utils.Utils;
import com.example.hanshinchat1.viewpager.RecommendViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//import com.example.hanshinchat1.Match.MBTIMatchActivity;

public class HomeActivity extends MainActivity {


    private final String CHANNEL_ID = "my_notification_chanel";
    static final int NOTIFICATION_ID = 1001;
    private static final String TEXT_REPLY = "text_reply";

    private static final String TAG = "채널생성실패";

    private Button idealMatchingBtn;
    private Button mbtiMatchingBtn;
    private Button aroundMatchingBtn;
    private Button recentRegisterMatchingBtn;
    private Button recentContectMatchingBtn;
    private Button topUserMatchingBtn;
    private ImageButton getRequestBtn;

    private Context context = this;

    private int previousPhraseIndex = -1;
    private String[] phrases;
    private List<String> phrasesList;
    private Button speakerBtn;
    private ImageButton simulationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        checkNewRequest();

        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();
        checkMatchRequest();

        initializeView();
        initializeListener();

        // 상단 앱 관련 팁 기능
        phrases = getResources().getStringArray(R.array.random_speaker);
        phrasesList = Arrays.asList(phrases);
        speakerBtn = findViewById(R.id.home_speaker);
        speakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRandomPhrase();
            }
        });


        // 소개팅 시뮬레이션 기능
        simulationBtn = (ImageButton) findViewById(R.id.simulation);
        simulationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Simulation1.class);
                startActivity(intent);
                finish();
            }
        });
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



    private void checkNewRequest(){
        ArrayList<String > myRoomKeys=new ArrayList<>();
        ArrayList<String> newUserRequestUids = new ArrayList<>();
        HashMap<String,ArrayList<String>> newRoomRequestUids=new HashMap<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query query=FirebaseDatabase.getInstance().getReference().child("rooms");
        FirebaseDatabase.getInstance().getReference().child("matches")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            newUserRequestUids.clear();
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
                            });
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }











    //채팅요청이 왔다는 다이얼로그
    private void showNewRequestDialog() {
        if (!isFinishing()) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.new_request_dialog, null);
            ConstraintLayout layout = view.findViewById(R.id.alert_layout);

            // AlertDialog.Builder를 사용하여 커스텀 다이얼로그 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();

            alertDialog.getWindow().setGravity(Gravity.TOP); //상단에 위치
            alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //밖에 배경 어둡지않게
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));  // 배경 투명하게
            //alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // 다이얼로그 표시
            alertDialog.show();

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //창 누르면 확인했으므로 다시 안뜨게
                    Utils.goToGetRequestActivity(context);

                    alertDialog.dismiss();
                    //showUserInfoDialog(context,chatRequestUsers);

                }
            });

        }


    }


    private void initializeView() {
        idealMatchingBtn = findViewById(R.id.idealMatchingBtn);
        mbtiMatchingBtn = findViewById(R.id.mbtiMatchingBtn);
        aroundMatchingBtn = findViewById(R.id.aroundMatchingBtn);
        recentRegisterMatchingBtn = findViewById(R.id.recentRegisterMatchingBtn);
        recentContectMatchingBtn = findViewById(R.id.recentConnectMatchingBtn);
        topUserMatchingBtn = findViewById(R.id.topUserMatchingBtn);
        getRequestBtn = findViewById(R.id.getRequestBtn);
    }

    private void initializeListener() {

        //모두 로그만 뜸 , 유저뜨는거 구현해야함
        idealMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //수정필요
                /*Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up);
                idealMatchingBtn.startAnimation(animation);*/
                Utils.checkIdealExists(context);

            }
        });


        mbtiMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(getApplicationContext(), com.example.hanshinchat1.Match.MainActivity.class);
                startActivity(intent);*/
                Utils.MyUid(context);
            }
        });

        aroundMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.arroundMatching(context);

            }
        });

        recentRegisterMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.recentRegisterMatching(context);
           /*     AlertDialog.Builder famDialog = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog);

                famDialog.setMessage("동일한 지역에 거주하는 상대를 검색합니다.")
                        .setTitle("내 주변 친구 매칭")
                        .setPositiveButton("검색", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 검색 눌렀을 때 기능 구현
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("Dialog", "취소");
                                Toast.makeText(v.getContext(), "취소", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setCancelable(false)
                        .show();*/

            }
        });

        recentContectMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.recentConnectMatching(context);
                //Utils.recentRegisterMatching();
               /* AlertDialog.Builder tumDialog = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog);

                tumDialog.setMessage("상위 5% 사용자를 검색합니다.\n\n*사용자 순위는 프로필의 좋아요 갯수를 합산해 매겨집니다.")
                        .setTitle("상위 5% 매칭")
                        .setPositiveButton("검색", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 검색 눌렀을 때 기능 구현
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("Dialog", "취소");
                                Toast.makeText(v.getContext(), "취소", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setCancelable(false)
                        .show();*/
            }
        });

        topUserMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.mostLikedMatching(context);

            }
        });


        getRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToGetRequestActivity(context);
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




   /* private void deleteUerInfo() {
        // [START delete_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete();
        myRef.child("users").child(user.getUid()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        Toast.makeText(getApplicationContext(), "유저정보 삭제 성공!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "유저정보 삭제 실패!!", Toast.LENGTH_SHORT).show();
                        // Intent intent=new Intent(SetProfileActivity.this,MainActivity.class);
                        //  startActivity(intent);

                        // Write failed
                        // ...
                    }
                });
    }*/

}
