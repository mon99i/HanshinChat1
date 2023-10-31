package com.example.hanshinchat1.ProfileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileGenderFragment extends Fragment {
    private AppCompatRadioButton ProfileMale, ProfileFemale;
    private RadioGroup GenderRadioGroup;
    DatabaseReference myRef;
    FirebaseUser user;
    public ProfileGenderFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_gender_fragment, container, false);

        ProfileMale = view.findViewById(R.id.gender_male_fragment);
        ProfileFemale = view.findViewById(R.id.gender_female_fragment);
        GenderRadioGroup = view.findViewById(R.id.gender_radio_group_fragment);;

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }
    public void updateDB() {
        if (myRef != null && user != null && GenderRadioGroup != null) {
            DatabaseReference userRef = myRef.child("users").child(user.getUid());
            if (ProfileMale.isChecked()) {
                String strGender = "남자";
                userRef.child("gender").setValue(strGender);
            } else if (ProfileFemale.isChecked()) {
                String strGender = "여자";
                userRef.child("gender").setValue(strGender);
            }
        }
        else {
            Toast.makeText(getContext(), "성별을 선택하세요", Toast.LENGTH_SHORT).show();
        }
    }
    public String editDB() {
        if (ProfileMale.isChecked()) {
            String strGender = "남자";
            return strGender;
        } else if (ProfileFemale.isChecked()) {
            String strGender = "여자";
            return strGender;
        }
        else {
            Toast.makeText(getContext(), "성별을 선택하세요", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
