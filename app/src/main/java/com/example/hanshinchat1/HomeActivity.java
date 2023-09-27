package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.hanshinchat1.Match.MBTIMatchActivity;
import com.example.hanshinchat1.Match.MatchHome;
import com.example.hanshinchat1.utils.Utils;

//import com.example.hanshinchat1.Match.MBTIMatchActivity;

public class HomeActivity extends MainActivity {


    private final String CHANNEL_ID = "my_notification_chanel";
    static final int NOTIFICATION_ID = 1001;
    private static final String TEXT_REPLY = "text_reply";

    private static final String TAG="채널생성실패";

    private Button idealMatchingBtn;
    private Button mbtiMatchingBtn;
    private Button aroundMatchingBtn;
    private Button recentRegisterMatchingBtn;
    private Button recentContectMatchingBtn;
    private Button topUserMatchingBtn;

    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        clickHome();
        clickRoom();
        clickChat();
        clickBoard();
        clickProfile();
        checkMatchRequest();


        initializeView();
        initializeListener();

    }

    private void initializeView(){
        idealMatchingBtn=findViewById(R.id.idealMatchingBtn);
        mbtiMatchingBtn=findViewById(R.id.mbtiMatchingBtn);
        aroundMatchingBtn=findViewById(R.id.aroundMatchingBtn);
        recentRegisterMatchingBtn=findViewById(R.id.recentRegisterMatchingBtn);
        recentContectMatchingBtn=findViewById(R.id.recentConnectMatchingBtn);
        topUserMatchingBtn=findViewById(R.id.topUserMatchingBtn);
    }

    private void initializeListener(){

        //모두 로그만 뜸 , 유저뜨는거 구현해야함
        idealMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //수정필요
              Utils.checkIdealExists(context);
            }
        });

        mbtiMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), SetProfile2NameActivity.class);
                startActivity(intent);
                finish();*/
            }
        });

        aroundMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.arroundMatching();

            }
        });

        recentRegisterMatchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.recentRegisterMatching();
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
                Utils.recentConnectMatching();
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
