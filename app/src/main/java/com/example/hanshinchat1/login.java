package com.example.hanshinchat1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private static final String PREF_USER_REGISTERED = "isUserRegistered";
    Button go_signUp, loginBtn;
    EditText email_edit, password_edit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        go_signUp = findViewById(R.id.go_signUp);
        loginBtn = findViewById(R.id.loginBtn);
        email_edit = findViewById(R.id.email_edit);
        password_edit = findViewById(R.id.password_edit);

        go_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signUp.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_edit.getText().toString();
                String password = password_edit.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(login.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.endsWith("@hs.ac.kr")) {
                    Toast.makeText(login.this, "한신대 이메일을 입력하여주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    // 이미 로그인된 상태이므로 다음 단계로 이동하거나 필요한 작업 수행
                    Toast.makeText(login.this, "이미 로그인되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, profile.class);
                    startActivity(intent);
                } else {
                    // 회원가입을 거치지 않고 로그인 처리
                    loginUser(email, password);
                }
            }
        });
        boolean isUserRegistered = getUserRegisteredStatus();
        if (isUserRegistered) {
            String savedEmail = getEmailFromSharedPreferences();
            String savedPassword = getPasswordFromSharedPreferences();

            if (!TextUtils.isEmpty(savedEmail) && !TextUtils.isEmpty(savedPassword)) {
                loginUser(savedEmail, savedPassword);
            }
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(login.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, profile.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(login.this, "로그인 실패. 이메일 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // SharedPreference에서 회원가입 여부를 확인하는 메소드
    private boolean getUserRegisteredStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREF_USER_REGISTERED, false);
    }

    // SharedPreference에서 저장된 이메일을 가져오는 메소드
    private String getEmailFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    // SharedPreference에서 저장된 비밀번호를 가져오는 메소드
    private String getPasswordFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }
}