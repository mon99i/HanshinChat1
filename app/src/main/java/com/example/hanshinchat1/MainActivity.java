package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.hanshinchat1.board.BoardActivity;
import com.example.hanshinchat1.board.ListActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public abstract class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount gsa;

    private static final String TAG = "MainActivity";

    protected void checkCurrentUser() {     //현재 사용자 확인
        // [START check_current_user]
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
        } else {
            Toast.makeText(getApplicationContext(), "로그인 정보가 없습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            // No user is signed in
        }
        // [END check_current_user]

    }


    protected void checkProfileExist() {   //프로필 존재유무 확인
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersRef = myRef.child("users").child(user.getUid());

        if(user!=null){

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {         //User is signed in && 프로필도 지정완료


                    } else {
                        Toast.makeText(getApplicationContext(), "프로필 설정을 안하셨군요!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }

    protected void deleteUser() {  //앱상에서 유저 삭제
        // [START delete_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "유저정보 삭제 성공!!", Toast.LENGTH_SHORT).show();
                            checkCurrentUser();       //다시 로그인창으로
                        }
                    }
                });



        /*
        DatabaseReference usersRef = myRef.child("users").child(user.getUid());
        usersRef.removeValue()                                        //이건 데이터베이스까지 완전 삭제
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        Toast.makeText(getApplicationContext(), "유저정보 삭제 성공!!", Toast.LENGTH_SHORT).show();
                        *//*Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();*//*

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "유저정보 삭제 실패!!", Toast.LENGTH_SHORT).show();

                    }
                });*/
    }




    protected void signOut() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    mAuth.signOut();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), R.string.success_logout, Toast.LENGTH_SHORT).show();

                });
        gsa = null;


    }


    protected void getGoogleCredentials() {
        String googleIdToken = "";
        // [START auth_google_cred]
        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
        // [END auth_google_cred]
    }

    protected void editProfile() {

    }

    protected void clickMenu() {
        ImageButton menuBtn = findViewById(R.id.menu);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void clickHome() {
        ImageButton homeBtn = findViewById(R.id.home);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void clickRoom() {
        ImageButton roomBtn = findViewById(R.id.room);
        roomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    protected void clickChat() {
        ImageButton chatBtn = findViewById(R.id.chat);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), ChatActivity.class); chatRoomActivity 안되면 이걸로
                Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    protected void clickBoard() {
        ImageButton boardBtn = findViewById(R.id.post);
        boardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void clickProfile() {
        ImageButton profileBtn = findViewById(R.id.info);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        // mAuth.addAuthStateListener(authStateListener);

    }
    @Override
    public void onBackPressed() { //뒤로버튼 누를시
        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
        //signOut();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // mAuth.removeAuthStateListener(authStateListener);
    }

    protected void checkMatchRequest(){
        /*FirebaseDatabase.getInstance().getReference().child("chatRooms")
                .orderByChild("users/" + myUid).equalTo(true)

        Query query=FirebaseDatabase.getInstance().getReference().child("matchRooms");
*/

        FirebaseDatabase.getInstance().getReference().child("matchRooms")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot item:snapshot.getChildren()){
                            String hostUid=item.getValue(MatchRoom.class).getHost();

                            for(DataSnapshot subItem:item.child("guest").getChildren()){
                                Boolean request= (Boolean) subItem.child("request").getValue();

                                if (user.getUid().equals(hostUid) && request.equals(true)) {
                                    showAlertDialog();

                                }
                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Log.d(TAG, "checkRequest3onCancelled: ");
                    }
                });

    }

    protected void showAlertDialog(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View alert_dialog= inflater.inflate(R.layout.alert_dialog, null);

        // 커스텀 레이아웃의 버튼 설정
        Button declineButton = alert_dialog.findViewById(R.id.declineButton);
        Button acceptButton = alert_dialog.findViewById(R.id.acceptButton);

        // AlertDialog.Builder를 사용하여 커스텀 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(alert_dialog);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setGravity(Gravity.TOP); //상단에 위치
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //밖에 배경 어둡지않게
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));  //투명하게

        // 거절 버튼 클릭 이벤트 처리
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                // 거절 동작 처리
            }
        });

        // 수락 버튼 클릭 이벤트 처리
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                // 수락 동작 처리
            }
        });/**/
        // 다이얼로그 표시
        alertDialog.show();
    }



    /* authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    //로그인
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    //로그아웃
                }
            }
        };*/

    /*private void revokeAccess() {  //g회원 사  ??
        mAuth.signOut();
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }*/

 /*   protected void getUserInfo() {               // 사용자 프로필 가져오기
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
        // [END get_user_profile]
    }*/


   /* public void getProviderUserInfo() {     //제공업체의 프로필 가져오기
        // [START get_provider_data]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo userInfo: user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String hobby= userInfo.getHobby();
                String mbti=userInfo.getMbti();
                String major=userInfo.getMajor();
                String nickname=userInfo.getNickName();
                String photoUrl=userInfo.getPhotoUrl();
                // UID specific to the provider

            }
        }
        // [END get_provider_data]
    }*/

}