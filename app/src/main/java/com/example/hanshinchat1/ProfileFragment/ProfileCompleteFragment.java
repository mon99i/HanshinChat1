package com.example.hanshinchat1.ProfileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ProfileCompleteFragment extends Fragment {
    DatabaseReference myRef;
    FirebaseUser user;
    private LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_complete_fragment, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }
    public void updateDB() {
        String currentTime=localDateTime.format(dateTimeFormatter);
        Map<String, Object> childUpdates= new HashMap<>();
        childUpdates.put("/creationTime/",currentTime);      //계정생성 시간
        childUpdates.put("/lastSignInTime/",currentTime);    //마지막 로그인시간
        DatabaseReference userRef = myRef.child("users").child(user.getUid());
        userRef.updateChildren(childUpdates);
    }
}
