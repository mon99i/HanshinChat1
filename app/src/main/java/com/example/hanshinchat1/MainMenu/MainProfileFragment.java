package com.example.hanshinchat1.MainMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hanshinchat1.ProfileEditActivity;
import com.example.hanshinchat1.R;
import com.example.hanshinchat1.SetIdealActivity;
import com.example.hanshinchat1.SettingActivity;
import com.example.hanshinchat1.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainProfileFragment extends Fragment {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final String TAG = "ProfileActivity";

    private TextView name, gender, age;
    private DatabaseReference databaseReference;
    private UserInfo userInfo;
    private ImageView profile;

    Button ideal_edit_btn, settingBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilefragment, container, false);



        profile = (ImageView) view.findViewById(R.id.profileImage);

        Button profileEditBtn = (Button) view.findViewById(R.id.profile_edit);

        settingBtn = (Button) view.findViewById(R.id.setting);

        ideal_edit_btn=view.findViewById(R.id.ideal_edit_btn);

        name = view.findViewById(R.id.name);
        gender = view.findViewById(R.id.gender);
        age = view.findViewById(R.id.age);

        databaseReference = FirebaseDatabase.getInstance().getReference("userInfo");

        myRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userInfo = snapshot.getValue(UserInfo.class);
                    String imageUrl=userInfo.getPhotoUrl();
                    Uri imageUri=Uri.parse(imageUrl);
                    Glide.with(getContext())
                            .load(imageUri)
                            .into(profile);
                    name.setText(userInfo.getName());
                    gender.setText(userInfo.getGender());
                    Integer intAge = userInfo.getAge();
                    age.setText(intAge.toString());
                }
                else Log.d(TAG, "onDataChange: 데이터없음");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileEditActivity.class);
                startActivity(intent);

            }
        });

        ideal_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SetIdealActivity.class);
                startActivity(intent);

            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
}
