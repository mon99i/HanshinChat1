package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.example.hanshinchat1.Match.MBTIMatchActivity;
//import com.example.hanshinchat1.Match.MatchHome;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hanshinchat1.fragment.ShowUserFragment1;
import com.example.hanshinchat1.fragment.ShowUserFragment2;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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


        initializeView();
        initializeListener();

    }

    public void checkNewRequest() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference matchRef = FirebaseDatabase.getInstance().getReference().child("matches").child(user.getUid());
        ArrayList<String> newRequestUids = new ArrayList<>();

        //나한테 요청온 목록 모두 조회
        matchRef.orderByChild("request").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newRequestUids.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    newRequestUids.add(item.getKey());
                    //요청중 확인하지 않은 uid 조회
                }
                if (newRequestUids.size() > 0) {
                    showNewRequestDialog();
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
