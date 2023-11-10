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


public class ProfileAgeFragment extends Fragment {
    private EditText age;
    DatabaseReference myRef;
    FirebaseUser user;
    public ProfileAgeFragment() {}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_age_fragment, container, false);

        age = (EditText) view.findViewById(R.id.age_fragment);
        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }
    public void updateDB() {
        String strAge = age.getText().toString();
        Integer intAge = Integer.valueOf(strAge);
        if (strAge.isEmpty()) {
            age.setError("나이를 입력하세요.");
        } else if(strAge.length()!=2) {
            age.setError("올바른 나이를 입력하세요.");
        } else {
            DatabaseReference userRef = myRef.child("users").child(user.getUid());
            userRef.child("age").setValue(intAge);
        }
    }

    public String editDB() {
        String strAge = age.getText().toString();
        if (strAge.isEmpty()) {
            age.setError("나이를 입력하세요.");
            return null;
        } else if(strAge.length()!=2) {
            age.setError("올바른 나이를 입력하세요.");
            return null;
        } else {
           return strAge;
        }
    }
}
