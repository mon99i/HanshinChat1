package com.example.chattingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.chattingapp.UserInfo;

public class MainActivity extends AppCompatActivity {

    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount gsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void checkCurrentUser() {     //현재 사용자 확인
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
        } else {

            Toast.makeText(getApplicationContext(), "로그인 정보가 없습니다", Toast.LENGTH_SHORT).show();
            // No user is signed in
        }
        // [END check_current_user]
    }



    public void getUserInfo() {               // 사용자 프로필 가져오기
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
    }

/*    public void getProviderUserInfo() {     //제공업체의 프로필 가져오기
        // [START get_provider_data]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            }
        }
        // [END get_provider_data]
    }*/


    public static boolean checkHanshin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url

            String email = user.getEmail();

            if (email != null && email.endsWith("@hs.ac.kr")) {
                //Toast.makeText(getApplicationContext(), "한신대 학생 확인!", Toast.LENGTH_SHORT).show();
                // 이메일이 hs@ac.kr과 일치하는 경우 처리할 작업 수행
                return true;
            } else {
                //Toast.makeText(getApplicationContext(), "한신대 학생이 아니므로 로그아웃 됩니다.!", Toast.LENGTH_SHORT).show();
                // 이메일이 hs@ac.kr과 일치하지 않는 경우 처리할 작업 수행
                return false;
            }
            // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            //String uid = user.getUid();
        }
        else return false;

    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    mAuth.signOut();
                    Toast.makeText(getApplicationContext(), R.string.success_logout, Toast.LENGTH_SHORT).show();
                    // ...
                });
        gsa = null;
    }

    public void editProfile(){

    }

}