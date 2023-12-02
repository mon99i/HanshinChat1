package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanshinchat1.report.warning;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount gsa;


    private static final String TAG = "MainActivity";

    protected void checkHanshin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            if (email != null && email.endsWith("@hs.ac.kr")) {
                checkProfileExist();
                checkWarning();
            } else {
                Toast.makeText(getApplicationContext(), "한신대학교 학생이 아니므로 \n로그인이 제한됩니다.", Toast.LENGTH_SHORT).show();
                deleteUser();
            }
        }
    }


    protected void checkWarning() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            DatabaseReference userWarningsRef = FirebaseDatabase.getInstance().getReference().child("warnings").child(user.getUid());

            userWarningsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        warning userWarning = dataSnapshot.getValue(warning.class);
                        int warningCount = userWarning.getWarningCount();

                        if (warningCount >= 3) {
                            showWarningDialog();
                            signOut();

                        } else {
                            checkProfileExist();

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // 에러 처리
                }
            });
        }
    }

    private void showWarningDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this)
                .setTitle("로그인 실패")
                .setMessage("경고 3회로 계정이 일시정지 되었습니다.");

        mBuilder.show();

    }

    protected void checkProfileExist() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String currentTime = localDateTime.format(dateTimeFormatter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //마지막으로 프로필설정을 저장했던 액티비티로 이동
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        UserInfo userInfo = snapshot.getValue(UserInfo.class);
                        if (userInfo.getName() == null || userInfo.getGender() == null || userInfo.getAge() == null ||
                                userInfo.getGrade() == null || userInfo.getStudentId() == null || userInfo.getDepartment() == null ||
                                userInfo.getHeight() == null || userInfo.getForm() == null || userInfo.getAddress() == null ||
                                userInfo.getReligion() == null || userInfo.getSmoking() == null || userInfo.getDrinking() == null ||
                                userInfo.getInterest() == null || userInfo.getPersonality() == null || userInfo.getMbti() == null ||
                                userInfo.getFashion() == null || userInfo.getCreationTime() == null) {
                            Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
                            startActivity(intent);
                            finish();
                        } else { //모든 프로필 완료했을때, HomeActivity에서 넘어갈 setProfilActivity가 없을때 접속시간을 저장
                            Log.d(TAG, "onDataChange: 모든 프로필설정 완료.");
                            userRef.child("lastSignInTime").setValue(currentTime);
                            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                            intent.putExtra("openFragment", "fragmentHome");
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "프로필 설정을 안하셨군요!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SetProfilePhotoActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Log.d(TAG, "checkProfileExist: " + user.getUid());
        } else Log.d(TAG, "checkProfileExist: 로그인 정보가 없음");

    }


    protected void deleteUser() {  //앱상에서 유저 삭제
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "프로필 정보가 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                            signOut();
                        }
                    }
                });
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

                });
        gsa = null;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}