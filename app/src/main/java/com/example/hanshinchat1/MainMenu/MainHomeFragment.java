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

