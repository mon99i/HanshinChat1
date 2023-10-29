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


public class ProfileStudentIdFragment extends Fragment {

    private EditText studentId;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ProfileStudentIdFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_studentid_fragment, container, false);

        studentId = (EditText) view.findViewById(R.id.student_id);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }
    public void updateDB() {
        String strStudentId = studentId.getText().toString();
        Integer intStudentId = Integer.valueOf(strStudentId);
        if (strStudentId.isEmpty()) {
            studentId.setError("올바른 학번을 입력하세요.");
        } else {
            DatabaseReference userRef = myRef.child("users").child(user.getUid());
            userRef.child("studentId").setValue(intStudentId);
        }
    }
}
