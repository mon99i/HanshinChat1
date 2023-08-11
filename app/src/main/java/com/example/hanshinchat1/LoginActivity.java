package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.example.hanshinchat1.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends MainActivity{

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
   /* private FirebaseAuth mAuth;   //파이어베이스 인증 객체
    private GoogleSignInClient mGoogleSignInClient;  // 구글api클라이언트

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private GoogleSignInAccount gsa;  // 구글 계정
    private FirebaseAuth.AuthStateListener authStateListener;

*/
    private SignInButton googleLogin;
    private Button logoutBtn;

    private boolean showOneTapUI = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Initialize Firebase Auth


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleLogin = (SignInButton)findViewById(R.id.signInBtn);
        logoutBtn=(Button)findViewById(R.id.logoutBtn);

       if(user !=null){
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
        }



        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 기존에 로그인 했던 계정을 확인한다.
               /*gsa = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);

                if (gsa != null) {// 로그인 되있는 경우               아마 자동로그인
                    Toast.makeText(LoginActivity.this, R.string.status_login, Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    signIn();

                }*/
                signIn();

            }
        });
        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(view -> {
            signOut(); //로그아웃
        });


    }


    // [START signin]
    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
       if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
           } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {    //구글로긴누르고 여러가지 구글계정 나옴
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        Toast.makeText(getApplicationContext(), R.string.success_login, Toast.LENGTH_SHORT).show();

                        FirebaseUser user = mAuth.getCurrentUser();

                        updateUI(user);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getApplicationContext(), R.string.failed_login, Toast.LENGTH_SHORT).show();

                    }
                });
    }
    // [END auth_with_google]




    private void updateUI(FirebaseUser user) {

        DatabaseReference usersRef= myRef.child("users").child(user.getUid());
        if(checkHanshin()==true){

            {
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // 사용자의 프로필이 이미 존재하므로 HomeActivity로 이동   수정필요
                            //dataSnapshot.getValue(UserInfo.class).equals(null);
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "프로필 설정을 안하셨군요!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
                            startActivity(intent);
                            // 사용자의 프로필이 존재하지 않으므로 SetProfileActivity로 이동
                            // 추가적인 구현이 필요한 경우 여기에 작성
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 에러 처리
                        Toast.makeText(getApplicationContext(), "개같은 에러!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }

        else{
            Toast.makeText(getApplicationContext(), "한신대 학생이 아니므로 로그아웃 됩니다.!", Toast.LENGTH_SHORT).show();
            signOut();
        }

    }


    protected boolean checkHanshin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

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

        }
        else return false;

    }

    /* 로그아웃 *//*
    public void signOut() {



        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    mAuth.signOut();
                    Toast.makeText(LoginActivity.this, R.string.success_logout, Toast.LENGTH_SHORT).show();
                    // ...
                });
        gsa = null;
    }*/


    // [START onactivityresult]

}