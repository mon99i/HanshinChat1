package com.example.hanshinchat1.ProfileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileGradeFragment extends Fragment {

    private EditText grade;
    DatabaseReference myRef;
    FirebaseUser user;

    public ProfileGradeFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_grade_fragment, container, false);
        grade = (EditText) view.findViewById(R.id.grade);
        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }
    public void updateDB() {
        String strGrade = grade.getText().toString();
        Integer intGrade = Integer.valueOf(strGrade);
        if (strGrade.isEmpty()) {
            grade.setError("올바른 학년을 입력하세요.");
        } else {
            DatabaseReference userRef = myRef.child("users").child(user.getUid());
            userRef.child("grade").setValue(intGrade);
        }
    }
}
