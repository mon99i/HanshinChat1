package com.example.hanshinchat1.ProfileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFashionFemaleFragment extends Fragment {
    private AppCompatRadioButton ProfileFashion1, ProfileFashion2, ProfileFashion3, ProfileFashion4;
    DatabaseReference myRef;
    FirebaseUser user;

    public ProfileFashionFemaleFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fashion_female_fragment, container, false);

        ProfileFashion1 = view.findViewById(R.id.fashion_female_fragment1);
        ProfileFashion2 = view.findViewById(R.id.fashion_female_fragment2);
        ProfileFashion3 = view.findViewById(R.id.fashion_female_fragment3);
        ProfileFashion4 = view.findViewById(R.id.fashion_female_fragment4);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        AppCompatRadioButton.OnClickListener radioButtonClickListener = new AppCompatRadioButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFashion1.setChecked(false);
                ProfileFashion2.setChecked(false);
                ProfileFashion3.setChecked(false);
                ProfileFashion4.setChecked(false);
                ((AppCompatRadioButton) v).setChecked(true);
            }
        };

        ProfileFashion1.setOnClickListener(radioButtonClickListener);
        ProfileFashion2.setOnClickListener(radioButtonClickListener);
        ProfileFashion3.setOnClickListener(radioButtonClickListener);
        ProfileFashion4.setOnClickListener(radioButtonClickListener);

        return view;
    }

    public void updateDB() {
        if (myRef != null && user != null) {
            DatabaseReference userRef = myRef.child("users").child(user.getUid());
            String selectedFashion = editDB();
            userRef.child("fashion").setValue(selectedFashion);
        } else {
            Toast.makeText(getContext(), "패션 스타일을 선택하세요", Toast.LENGTH_SHORT).show();
        }
    }

    public String editDB() {
        if (ProfileFashion1.isChecked()) {
            return "캐주얼";
        } else if (ProfileFashion2.isChecked()) {
            return "스트릿";
        } else if (ProfileFashion3.isChecked()) {
            return "로맨틱";
        } else if (ProfileFashion4.isChecked()) {
            return "스포티";
        } else {
            Toast.makeText(getContext(), "패션 스타일을 선택하세요", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
