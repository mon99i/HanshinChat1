package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hanshinchat1.report.warning;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends MainActivity{

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton googleLogin;
    private Button logoutBtn;

    private boolean showOneTapUI = true;


    //테스트용으로 만든것뿐 나중에 없앨것
    private EditText edt_Id;
    private EditText edt_password;
    private Button exRegisterBtn;
    private Button exLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializeView();
        initializeListener();
        checkProfileExist();

            //테스트용으로 만든것뿐 나중에 없앨것
        exInitialize();

        //테스트용으로 만든것뿐 나중에 없앨것
        edt_Id=findViewById(R.id.edt_id);
        edt_password=findViewById(R.id.edt_password);
        exRegisterBtn=findViewById(R.id.exRegisterBtn);
        exLoginBtn=findViewById(R.id.exLoginBtn);
        exRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exCreateUser(edt_Id.getText().toString(),edt_password.getText().toString());
            }
        });
        exLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exLogin(edt_Id.getText().toString(),edt_password.getText().toString());

            }


        });

    }


    private void initializeView(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleLogin = (SignInButton)findViewById(R.id.signInBtn);
        logoutBtn=(Button)findViewById(R.id.logoutBtn);

    }

    private void initializeListener(){
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
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
       if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
           } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        Toast.makeText(getApplicationContext(), R.string.success_login, Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();

                        checkHanshin();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getApplicationContext(), R.string.failed_login, Toast.LENGTH_SHORT).show();

                    }
                });
    }
    // [END auth_with_google]

            //테스트용으로 만든것뿐 나중에 없앨것
    private void exInitialize(){
        edt_Id=findViewById(R.id.edt_id);
        edt_password=findViewById(R.id.edt_password);
        exRegisterBtn=findViewById(R.id.exRegisterBtn);
        exLoginBtn=findViewById(R.id.exLoginBtn);

        exRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exCreateUser(edt_Id.getText().toString(),edt_password.getText().toString());
            }
        });
        exLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exLogin(edt_Id.getText().toString(),edt_password.getText().toString());

            }


        });
    }

    private void exCreateUser(String id,String password){
        Log.d(TAG, "exCreateUserAndSignIn: "+id+password);
        mAuth.createUserWithEmailAndPassword(id, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "일반 유저 회원가입성공", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "일반 유저 회원가입성공");
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "이미 있는 아이디", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "일반 유저 회원가입실패", task.getException());
                        }
                    }
                });

    }

    private void exLogin(String id,String password){
        mAuth.signInWithEmailAndPassword(id, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            checkHanshin();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }
}